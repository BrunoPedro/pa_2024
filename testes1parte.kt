package Projeto

import Projeto1parte.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream


/**
 * Class para os testes unitários
 */
class UnitTests {

    @Test
    fun testNomeValido() {
        val entidade = Entidade("NomeValido")
        assertEquals("NomeValido", entidade.nome)
    }

    @Test
    fun testNomeComCaracteresInvalidos() {

        val entidade = Entidade("NomeInválido")
        println(entidade.nome)
        assertTrue(entidade.nome == "NomeInvlido")
        assertFalse(entidade.nome == "Nome Inválido")
    }

    @Test
    fun testNomeApenasComNumeros() {
        assertThrows<IllegalArgumentException> {
            Entidade("12345")
        }
    }
    private inline fun <reified T : Throwable> assertThrows(block: () -> Unit) {
        try {
            block()
            throw AssertionError("Expected ${T::class.simpleName}, but no exception was thrown.")
        } catch (e: Throwable) {
            if (e !is T) {
                throw AssertionError("Expected ${T::class.simpleName}, but caught ${e::class.simpleName} instead.")
            }
        }
    }

    @Test
    fun testNomeComEspacos() {
        val entidade = Entidade("Nome Com Espacos")
        assertTrue(entidade.nome == "NomeComEspacos")
        assertFalse(entidade.nome == "Nome Com Espacos")
    }



    /**
     * Função para testar a criação da entidade pai
     */
    @Test
    fun testCriarPai() {
        // Inicializar objetos entidade
        val entidadePai = Entidade("Pai")
        val entidadeFilho1 = Entidade("Filho 1")

        // Estabelecer a relação pai-filho usando a função criarPai
        entidadeFilho1.criarPai(entidadePai)

        // Verificar se a referência para o pai foi configurada corretamente
        assertEquals(entidadePai, entidadeFilho1.pai)
    }

    /**
     * Função para testar a criação de uma entidade filho
     */
    @Test
    fun testCriarFilho() {
        // Inicializar objetos entidade
        val entidadePai = Entidade("Pai")
        val entidadeFilho1 = Entidade("Filho 1")

        // Estabelecer a relação pai-filho usando a função criarFilho
        entidadePai.criarFilho(entidadeFilho1)

        // Verificar se a referência para o pai foi configurada corretamente no filho
        assertEquals(entidadePai, entidadeFilho1.pai)

        // Verificar se o filho foi adicionado corretamente à lista de filhos do pai
        assertEquals(1, entidadePai.filhos.size)
        assertEquals(entidadeFilho1, entidadePai.filhos[0])
    }

    /**
     * Teste para criar múltiplos filhos a uma entidade pai
     */
    @Test
    fun testCriarFilhosPai() {
        // Inicializar objetos entidade
        val e1 = Entidade("Pai")
        val e2 = Entidade("Filho1")
        val e3 = Entidade("Filho2")

        // Adicionar as entidades filhas à entidade pai
        e1.criarVariosFilhos(e2, e3)

        // Verificar se as entidades filhas foram adicionadas corretamente à entidade pai
        assertTrue(e1.filhos.contains(e2))
        assertTrue(e1.filhos.contains(e3))
    }

    /**
     * Função para testar a remoção de entidades filho
     */
    @Test
    fun testApagarFilho() {
        // Inicializar objetos entidade
        val e1 = Entidade("Pai")
        val e2 = Entidade("Filho1")
        val e3 = Entidade("Filho2")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarFilho(e2)
        e1.criarFilho(e3)

        // Apagar um filho da lista de filhos do pai
        e1.apagarFilho(e2)

        // Verificar se o filho foi removido corretamente
        assertEquals(1, e1.filhos.size)
        assertEquals(e3, e1.filhos[0])
    }


    /**
     * Teste para remover uma entidade pelo nome
     */
    @Test
    fun testApagarEntidadePorNome() {
        // Inicializar objetos entidade
        val e1 = Entidade("EntidadeUm")
        val e2 = Entidade("EntidadeDois")
        val e3 = Entidade("EntidadeTres")
        val e4 = Entidade("EntidadeQuatro")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarFilho(e2)
        e2.criarFilho(e3)
        e3.criarFilho(e4)

        // Verificar se os filhos forem corretamente adicionados
        assertTrue(e1.filhos.contains(e2))
        assertTrue(e2.filhos.contains(e3))
        assertTrue(e3.filhos.contains(e4))

        // Remover uma entidade
        apagarEntidadePorNome(e1, "EntidadeDois")

        // Verificar se a entidade foi removida corretamente
        assertFalse(e1.filhos.contains(e2))
    }


    /**
     * Teste para remover uma entidade
     */
    @Test
    fun testApagarEntidade() {
        // Inicializar objetos entidade
        val e1 = Entidade("pai")
        val e2 = Entidade("filho1")
        val e3 = Entidade("filho2")
        val e4 = Entidade("filho3")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarFilho(e2)
        e2.criarFilho(e3)
        e3.criarFilho(e4)

        // Verificar se os filhos foram corretamente adicionados
        assertTrue(e1.filhos.contains(e2))
        assertTrue(e2.filhos.contains(e3))
        assertTrue(e3.filhos.contains(e4))

        // Remover a entidade e3
        apagarEntidade(e3)

        // Verificar se a entidade foi removida corretamente
        assertFalse(e2.filhos.contains(e3))
    }

    /**
     *  Teste para remover todas as entidades que têm nomes que começam com o prefixo identificado
     */
    @Test
    fun testApagarEntidadePorNomeV() {
        // Inicializar objetos entidade
        val e1 = Entidade("pai")
        val e2 = Entidade("filho1")
        val e3 = Entidade("filho2")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarVariosFilhos(e2, e3)

        // Remover todos os filhos cujo nome comece por f
        apagarEntidadePorNomeV(e1, "f")

        // Verificar se as entidades foram removidas
        assertEquals(0, e1.filhos.size)
        assertFalse(e1.filhos.any { it.nome.startsWith("f") })
    }

    /**
     * Teste para alterar o nome de uma entidade
     */
    @Test
    fun testAlterarNomeEntidade() {
        // Inicializar objeto entidade
        val entidade = Entidade("nomeOriginal")

        // Alterar o nome da entidade
        alterarNomeEntidade(entidade,"nomeOriginal","nomeNovo")

        // Verificar se o nome da entidade foi alterado corretamente
        assertEquals("nomeNovo", entidade.nome)
    }

    /**
     * Função para testar a criação de um atributo
     */
    @Test
    fun testCriarAtributo() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Adicionar um objeto atributo à lista de atributos da entidade
        entidade.criarAtributo("Atributo", "Valor")

        // Verificar se o atributo foi adicionado corretamente
        assertEquals(1, entidade.atributos.size)
        assertEquals("Atributo", entidade.atributos[0].nomeatrib)
        assertEquals("Valor", entidade.atributos[0].valor)
    }

    /**
     * Teste para garantir que uma entidade não tem dois atributos com o mesmo nome
     */
    @Test
    fun testCriarAtributoNomeDuplicado() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Adicionar objetos atributo à lista de atributos da entidade
        entidade.criarAtributo("AtributoUm", "Valor")
        entidade.criarAtributo("AtributoDois", "ValorDois")
        entidade.criarAtributo("AtributoUm", "ValorTres")


        // Verificar se o atributo duplicado não foi adicionado
        assertEquals(2, entidade.atributos.size)
        assertEquals("AtributoUm", entidade.atributos[0].nomeatrib)
        assertEquals("AtributoDois", entidade.atributos[1].nomeatrib)
    }

    /**
     * Teste para adicionar vários atributos simultaneamente
     */
    @Test
    fun testAdicionarAtributos() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Inicializar objetos atributo
        val atributo1 = Atributo("AtributoUm", "ValorUm")
        val atributo2 = Atributo("AtributoDois", "ValorDois")
        val atributo3 = Atributo("AtributoTres", "ValorTres")


        // Adicior os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Verificar se os atributos foram adicionados corretamente
        assertEquals(3, entidade.atributos.size)
        assertEquals(atributo1, entidade.atributos[0])
        assertEquals(atributo2, entidade.atributos[1])
        assertEquals(atributo3, entidade.atributos[2])
    }

    /**
     * Teste para garantir que uma entidade não tem dois atributos com o mesmo nome
     */
    @Test
    fun testAdicionarAtributosNomeDuplicado() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Inicializar objetos atributo
        val atributo1 = Atributo("AtributoUm", "ValorUm")
        val atributo2 = Atributo("AtributoDois", "ValorDois")
        val atributo3 = Atributo("AtributoUm", "ValorTres")  // Nome duplicado

        // Adicionar os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Verificar se o atributo duplicado não foi adicionado
        assertEquals(2, entidade.atributos.size)
        assertEquals("AtributoUm", entidade.atributos[0].nomeatrib)
        assertEquals("AtributoDois", entidade.atributos[1].nomeatrib)
    }

    /**
     * Teste para apagar um atributo
     */
    @Test
    fun testApagarAtributo() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Inicializar objetos atributo
        val atributo1 = Atributo("AtributoUm", "ValorUm")
        val atributo2 = Atributo("AtributoDois", "ValorDois")
        val atributo3 = Atributo("AtributoTres", "ValorTres")


        // Adicionar os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Remover um atributo específico
        entidade.apagarAtributo(atributo2)

        // Verificar se o atributo foi removido corretamente
        assertEquals(2, entidade.atributos.size)
        assertEquals("AtributoUm", entidade.atributos[0].nomeatrib)
        assertEquals("AtributoTres", entidade.atributos[1].nomeatrib)
    }

    /**
     * Teste para apagar um atributo pelo seu nome globalmente
     */
    @Test
    fun testApagarAtributoGlobalNome() {
        // Inicializar objetos entidade
        val e1 = Entidade("plano")
        val e2 = Entidade("curso", "Mestrado de Engenharia Informática")
        val e3 = Entidade("FUC")

        // Inicializar objetos atributo
        val a1 = Atributo("atributoUm", "valorUm")
        val a2 = Atributo("atributoDois", "valorDois")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarVariosFilhos(e2, e3)

        // Adicionar os atributos às entidades
        e1.adicionarAtributos(a1)
        e2.adicionarAtributos(a1)
        e3.adicionarAtributos(a1,a2)

        // Remover um atributo pelo nome
        apagarAtributoGlobalNome(e1,"atributoUm")

        // Verificar se o atributo foi removido globalemente
        assertTrue(e1.atributos == emptyList<Atributo>())
        assertTrue(e2.atributos == emptyList<Atributo>())
        assertEquals(1, e3.atributos.size)
        assertTrue(e3.atributos[0].nomeatrib == "atributoDois")

    }

    /**
     * Teste para alterar um atributo
     */
    @Test
    fun testalterarAtributo() {
        // Inicializar objeto entidade
        val entidade = Entidade("Entidade")

        // Inicializar objeto atributo
        val atributo = Atributo("nomeAtributo", "valorInicial")

        // Adicionar o atributo à entidade
        entidade.adicionarAtributos(atributo)

        // Alterar o nome e o valor do atributo
        alterarAtributo(entidade,"nomeAtributo","novoNome","novoValor")

        // Verificar se o nome e o valor foram corretamente alterados
        assertTrue(entidade.atributos[0].nomeatrib == "novoNome")
        assertTrue(entidade.atributos[0].valor == "novoValor")

    }


    /**
     *  Teste para remover um atributo de uma entidade com base no nome da entidade e do atributo
     */
    @Test
    fun testApagarAtributoNomeEntidadeNomeAtributoGlobal() {
        // Inicializar objetos entidade
        val e1 = Entidade("pai")
        val e2 = Entidade("filhoUm")

        // Inicializar objetos atributo
        val a1 = Atributo("atributoUm", "valorUm")
        val a2 = Atributo("atributoDois", "valorDois")

        // Adicionar o filho à lista de filhos do pai
        e1.criarVariosFilhos(e2)

        // Adicionar os atributos à entidade
        e2.adicionarAtributos(a1, a2)

        // Remover um atributo numa dada entidade pelo nome
        apagarAtributoNomeEntidadeNomeAtributoGlobal(e1, "filhoUm", "atributoUm")

        // Verificar se o atributo foi removido
        assertEquals(1, e2.atributos.size)
        assertEquals("atributoDois", e2.atributos[0].nomeatrib)
    }

    /**
     * Teste para garantir que o nome de um atributo é alterado em todas as entidades descendentes da entidade pai
     */
    @Test
    fun testAlterarAtributoNomeEntidadeNomeAtributoGlobal() {
        // Inicializar objetos entidade
        val e1 = Entidade("entidadeUm")
        val e2 = Entidade("entidadeDois", "textoDois")

        // Inicializar objetos atributo
        val a1 = Atributo("atributoUm", "valorUm")
        val a2 = Atributo("atributoDois", "valorDois")

        // Adicionar o filho à lista de filhos do pai
        e1.criarVariosFilhos(e2)

        // Adicionar os atributos à entidade
        e1.adicionarAtributos(a1, a2)
        e2.adicionarAtributos(a1)

        // Alterar nome do atributo de uma entidade específica
        alterarAtributoNomeEntidadeNomeAtributoGlobal(e1, "entidadeUm", "atributoUm", "novoNome")

        // Verificar se o nome foi corretamente alterado
        assertEquals("novoNome", e1.atributos[0].nomeatrib)
        assertEquals("novoNome", e1.atributos[0].nomeatrib)
    }

    /**
     *  Teste para garantir a criação um novo atributo numa entidade específica
     */
    @Test
    fun testCriarAtributoNomeEntidadeNomeAtributoGlobal() {
        // Inicializar objetos entidade
        val e1 = Entidade("entidadeUm")
        val e2 = Entidade("entidadeDois")
        val e3 = Entidade("entidadeTres")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarVariosFilhos(e2, e3)

        // Criar um novo atributo globalmente numa dada entidade
        criarAtributoNomeEntidadeNomeAtributoGlobal(e1, "entidadeUm", "novoAtributo", "valorNovoAtributo")

        //Verificar se o atributo foi corretamente criado nessa entidade
        val createdAttribute = e1.atributos.find { it.nomeatrib == "novoAtributo" }
        assertEquals("valorNovoAtributo", createdAttribute?.valor)
    }

    /**
     * Teste para garantir que o formato xml e os conteúdos estão corretos
     */

    @Test
    fun testEscreverString() {
        // Inicializar objeto entidade
        val entidade = Entidade("Nome", "texto")

        // Escrever a representação XML da entidade e dos seus filhos
        val stringBuilder = escreverString(entidade, 0,StringBuilder())

        val resultado = stringBuilder.toString()//stringBuilder.toString()

        // Resultado esperado
        val valorEsperado = "<"+ entidade.nome + ">" + entidade.texto + "</"+ entidade.nome + ">\n"

        // Verificar se a representação XML corresponde ao esperado
          assertEquals(valorEsperado, resultado)
    }


    /**
     * Teste para garantir que ficheiro xml é criado
     */
    @Test
    fun testEscreverFicheiro() {
        // Inicializar objeto entidade
        val entidade = Entidade("Nome", "texto")
        //Instancia um objeto documento
        val documento = Documento("projeto.xml")

        val stringBuilder = escreverString(entidade, 0,StringBuilder())

        // Escrever o conteúdo do StringBuilder em um ficheiro XML
        documento.escreverFicheiro(File(documento.nome), "UTF-8", "1.0",stringBuilder)
        // Verificar se o ficheiro foi criado
        val ficheiro = File(documento.nome)
        assertTrue(ficheiro.exists())
    }

    /**
     * Teste para garantir que o XPath vai buscar a informação correta
     */
    @Test
    fun testXPath() {
        // Inicializar objetos entidade
        val e1 = Entidade("plano")
        val e2 = Entidade("curso", "Mestrado de Engenharia Informática")
        val e3 = Entidade("FUC")
        val e4 = Entidade("nome", "Programação Avançada")
        val e5 = Entidade("etcs", "6.0")
        val e6 = Entidade("avaliação")
        val e7 = Entidade("componente")
        val e8 = Entidade("componente")
        val e9 = Entidade("FUC")
        val e10 = Entidade("nome", "Dissertação")
        val e11 = Entidade("etcs", "42.0")
        val e12 = Entidade("avaliação")
        val e13 = Entidade("componente")
        val e14 = Entidade("componente")
        val e15 = Entidade("componente")

        // Inicializar objetos atributo
        val a1 = Atributo("codigo", "M4310")
        val a2 = Atributo("nome", "Quizzes")
        val a3 = Atributo("peso", "20%")
        val a4 = Atributo("nome", "Projeto")
        val a5 = Atributo("peso", "80%")
        val a6 = Atributo("codigo", "03782")
        val a7 = Atributo("nome", "Dissertação")
        val a8 = Atributo("peso", "60%")
        val a9 = Atributo("nome", "Apresentação")
        val a10 = Atributo("peso", "20%")
        val a11 = Atributo("nome", "Discussão")
        val a12 = Atributo("peso", "20%")


        //Criação de entidades (ponto 1 do exercicio)
        e1.criarVariosFilhos(e2, e3, e9)
        e3.criarVariosFilhos(e4, e5, e6)
        e9.criarVariosFilhos(e10, e11, e12)
        e6.criarVariosFilhos(e7, e8)
        e12.criarVariosFilhos(e13, e14,e15)
        //Criação de atributos (ponto 2 do exercicio)
        e3.adicionarAtributos (a1)
        e9.adicionarAtributos (a6)
        e7.adicionarAtributos (a2, a3)
        e8.adicionarAtributos (a4, a5)
        e13.adicionarAtributos (a7, a8)
        e14.adicionarAtributos (a9, a10)
        e15.adicionarAtributos (a11, a12)

        // Chamada da função XPath para pesquisa de expressões
        val resultados = encontrarEntidadesPorXPath(e1, "FUC/etcs")

        // Construir a representação esperada dos resultados
        val esperado = "<${e5.nome}>${e5.texto}</${e5.nome}>\n<${e11.nome}>${e11.texto}</${e11.nome}>\n"

        // Verificar se a representação dos resultados é igual à esperada
        assertTrue(esperado == resultados.joinToString("") { "<${it.first.nome}>${it.first.texto}</${it.first.nome}>\n" })
    }

    /**
     * Teste para imprimir as entidades e os seus atributos no formato XML, referente à lista de resultados
     */
    @Test
    fun testImprimirResultados() {
        // Inicializar objetos entidade
        val e1 = Entidade("entidadeUm")
        val e2 = Entidade("entidadeDois", "textoDois")

        // Inicializar objetos atributo
        val a1 = Atributo("atributoUm", "valorUm")
        val a2 = Atributo("atributoDois", "valorDois")

        // Criar lista de resultados
        val resultados = listOf(
            Pair(e1, listOf(a1, a2)),
            Pair(e2, emptyList())
        )

        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        // Escrever os resultados
        imprimirResultados(resultados)


        // Verificar se os resultados obtidos foram os esperados
        val expectedOutput = "<entidadeUm atributoUm=\"valorUm\" atributoDois=\"valorDois\"/>\n<entidadeDois>textoDois</entidadeDois>\n"
        val actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n")
        assertEquals(expectedOutput, actualOutput)
    }

    /**
     * Teste para alterar um atributo de uma dada entidade
     */
    @Test
    fun testAlterarAtributoNomeEntidade() {
        // Inicializar objetos entidade
        val entidadePai = Entidade("Pai")
        val filho1 = Entidade("Filho1")
        val filho2 = Entidade("Filho2")

        val atributo1 = Atributo("nome", "valor1")
        val atributo2 = Atributo("idade", "25")
        val atributo3 = Atributo("nome", "valor2")
        val atributo4 = Atributo("nome", "valor3")

        // Adicionar os filhos à lista de filhos do pai
        entidadePai.criarVariosFilhos(filho1, filho2)

        // Adicionar os atributos à entidade
        entidadePai.adicionarAtributos(atributo1, atributo2)
        filho1.adicionarAtributos(atributo3)
        filho2.adicionarAtributos(atributo4)


        // Alterar o nome de um atributo numa entidade especifica e nos seus filhos
        alterarAtributoNomeEntidade(entidadePai, "nome", "novoNome")

        // Verificar se o nome do atributo foi corretamente alterado na entidade pai e filhos
        assertEquals("novoNome", atributo1.nomeatrib)
        assertEquals("idade", atributo2.nomeatrib)
        assertEquals("novoNome", atributo3.nomeatrib)
        assertEquals("novoNome", atributo4.nomeatrib)
    }

    /**
     * Teste para aceder a uma entidade e aos seus filhos e escreve o resultado em formato XML
     */
    @Test
    fun testAcederEntidadeMaeeFilhos() {
        // Inicializar objetos entidade
        val e1 = Entidade("entidadeUm")
        val e2 = Entidade("entidadeDois", "textoDois")
        val e3 = Entidade("entidadeTres")

        // Inicializar objetos atributo
        val a1 = Atributo("atributoUm", "valorUm")
        val a2 = Atributo("atributoDois", "valorDois")

        // Adicionar os filhos à lista de filhos do pai
        e1.criarVariosFilhos(e2, e3)

        // Adicionar os atributos à entidade
        e1.adicionarAtributos(a1, a2)


        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        // Aceder a uma entidade e aos seus filhos e escreve o resultado em formato XML
        acederEntidadMaeeFilhos(e1)

        // Valor esperado
        val valorEsperado = "<${e1.nome} ${a1.nomeatrib}=\"${a1.valor}\" ${a2.nomeatrib}=\"${a2.valor}\">\n" +
                "\t<${e2.nome}>${e2.texto}</${e2.nome}>\n\t<${e3.nome}>\n</${e1.nome}>\n"
        val valorObtido = outputStreamCaptor.toString().replace("\r\n", "\n")

        // Verificar se o valor obtido é igual ao esperado
        assertEquals(valorEsperado, valorObtido)
    }
}

