import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream


class EntidadeTest {

    @Test
    fun testCriarPai() {
        // Criando algumas instâncias de Entidade
        val entidadePai = Entidade("Pai")
        val entidadeFilho1 = Entidade("Filho 1")

        // Estabelecendo uma relação pai-filho usando a função criarPai
        entidadeFilho1.criarPai(entidadePai)

        // Verificando se a referência para o pai foi configurada corretamente
        assertEquals(entidadePai, entidadeFilho1.pai)
    }

    @Test
    fun testCriarFilho() {
        // Criando algumas instâncias de Entidade
        val entidadePai = Entidade("Pai")
        val entidadeFilho1 = Entidade("Filho 1")

        // Estabelecendo uma relação pai-filho usando a função criarFilho
        entidadePai.criarFilho(entidadeFilho1)

        // Verificando se a referência para o pai foi configurada corretamente no filho
        assertEquals(entidadePai, entidadeFilho1.pai)

        // Verificando se o filho foi adicionado corretamente à lista de filhos do pai
        assertEquals(1, entidadePai.filhos.size)
        assertEquals(entidadeFilho1, entidadePai.filhos[0])
    }

    @Test
    fun testApagarFilho() {
        // Criando algumas instâncias de Entidade
        val entidadePai = Entidade("Pai")
        val entidadeFilho1 = Entidade("Filho 1")
        val entidadeFilho2 = Entidade("Filho 2")

        // Adicionando os filhos à lista de filhos do pai
        entidadePai.criarFilho(entidadeFilho1)
        entidadePai.criarFilho(entidadeFilho2)

        // Apagando um filho da lista de filhos do pai
        entidadePai.apagarFilho(entidadeFilho1)

        // Verificando se o filho foi removido corretamente
        assertNull(entidadeFilho1.pai)
        assertEquals(1, entidadePai.filhos.size)
        assertEquals(entidadeFilho2, entidadePai.filhos[0])
    }

    @Test
    fun testCriarAtributo() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Adicionando o atributo à lista de atributos da entidade
        entidade.criarAtributo("Atributo", "Valor")

        // Verificando se o atributo foi adicionado corretamente
        assertEquals(1, entidade.atributos.size)
        assertEquals("Atributo", entidade.atributos[0].nomeatrib)
        assertEquals("Valor", entidade.atributos[0].valor)
    }

    @Test
    fun testCriarAtributoNomeDuplicado() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Adicionando o atributo à lista de atributos da entidade
        entidade.criarAtributo("Atributo1", "Valor")
        entidade.criarAtributo("Atributo2", "Valor2")
        entidade.criarAtributo("Atributo1", "Valor3")


        // Verificando se o atributo duplicado não foi adicionado
        assertEquals(2, entidade.atributos.size)
        assertEquals("Atributo1", entidade.atributos[0].nomeatrib)
        assertEquals("Atributo2", entidade.atributos[1].nomeatrib)
    }

    @Test
    fun testAdicionarAtributos() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Criando uma lista de atributos
        val atributo1 = Atributo("Atributo1", "Valor1")
        val atributo2 = Atributo("Atributo2", "Valor2")
        val atributo3 = Atributo("Atributo3", "Valor3")


        // Adicionando os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Verificando se os atributos foram adicionados corretamente
        assertEquals(3, entidade.atributos.size)
        assertEquals(atributo1, entidade.atributos[0])
        assertEquals(atributo2, entidade.atributos[1])
        assertEquals(atributo3, entidade.atributos[2])
    }

    @Test
    fun testAdicionarAtributosNomeDuplicado() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Criando uma lista de atributos com um nome duplicado

        val atributo1 = Atributo("Atributo1", "Valor1")
        val atributo2 = Atributo("Atributo2", "Valor2")
        val atributo3 = Atributo("Atributo1", "Valor3")  // Nome duplicado


        // Adicionando os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Verificando se o atributo duplicado não foi adicionado
        assertEquals(2, entidade.atributos.size)
        assertEquals("Atributo1", entidade.atributos[0].nomeatrib)
        assertEquals("Atributo2", entidade.atributos[1].nomeatrib)
    }

    @Test
    fun testApagarAtributo() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Criando uma lista de atributos
        val atributo1 = Atributo("Atributo1", "Valor1")
        val atributo2 = Atributo("Atributo2", "Valor2")
        val atributo3 = Atributo("Atributo3", "Valor3")


        // Adicionando os atributos à entidade
        entidade.adicionarAtributos(atributo1,atributo2,atributo3)

        // Removendo um atributo específico
        entidade.apagarAtributo(atributo2)

        // Verificando se o atributo foi removido corretamente
        assertEquals(2, entidade.atributos.size)
        assertEquals("Atributo1", entidade.atributos[0].nomeatrib)
        assertEquals("Atributo3", entidade.atributos[1].nomeatrib)
    }

    @Test
    fun testApagarAtributoNome() {
        // Criando uma instância de Entidade
        val entidade = Entidade("Entidade")

        // Criando atributos
        entidade.criarAtributo("Atributo1", "Valor1")
        entidade.criarAtributo("Atributo2", "Valor2")
        entidade.criarAtributo("Atributo3", "Valor3")

        // Removendo um atributo específico pelo nome
        entidade.apagarAtributoNome("Atributo2")

        // Verificando se o atributo foi removido corretamente
        assertEquals(2, entidade.atributos.size)
        assertEquals("Atributo1", entidade.atributos[0].nomeatrib)
        assertEquals("Atributo3", entidade.atributos[1].nomeatrib)
    }

    @Test
    fun testCriarFilhosPai() {
        // Criando uma instância de Entidade
        val entidadePai = Entidade("Entidade Pai")
        val entidadeFilha1 = Entidade("Entidade Filha 1")
        val entidadeFilha2 = Entidade("Entidade Filha 2")

        // Adicionando as entidades filhas à entidade pai
        criarFilhosPai(entidadePai, entidadeFilha1, entidadeFilha2)

        // Verificando se as entidades filhas foram adicionadas corretamente à entidade pai
        assertTrue(entidadePai.filhos.contains(entidadeFilha1))
        assertTrue(entidadePai.filhos.contains(entidadeFilha2))
    }

    @Test
    fun testAlterarNomeEntidade() {
        // Criando uma instância de Entidade
        val entidade = Entidade("nomeAntigo")

        // Alterando o nome da entidade
        alterarNomeEntidade(entidade,"nomeAntigo","nomeNovo")

        // Verificando se o nome da entidade foi alterado corretamente
        assertEquals("nomeNovo", entidade.nome)
    }

    @Test
    fun testApagarEntidadePorNome() {
        // Criando a hierarquia de entidades para teste
        val plano = Entidade("plano")
        val curso = Entidade("curso")
        val disciplina = Entidade("disciplina")
        val avaliacao = Entidade("avaliacao")

        plano.criarFilho(curso)
        curso.criarFilho(disciplina)
        disciplina.criarFilho(avaliacao)

        assertTrue(plano.filhos.contains(curso))
        assertTrue(curso.filhos.contains(disciplina))
        assertTrue(disciplina.filhos.contains(avaliacao))

        // Removendo a entidade disciplina
        apagarEntidadePorNome(plano, "disciplina")

        // Verificando se a entidade foi removida corretamente
        assertFalse(curso.filhos.contains(disciplina))
    }


    @Test
    fun testApagarEntidade() {
        // Criando a hierarquia de entidades para teste
        val plano = Entidade("plano")
        val curso = Entidade("curso")
        val disciplina = Entidade("disciplina")
        val avaliacao = Entidade("avaliacao")

        plano.criarFilho(curso)
        curso.criarFilho(disciplina)
        disciplina.criarFilho(avaliacao)

        assertTrue(plano.filhos.contains(curso))
        assertTrue(curso.filhos.contains(disciplina))
        assertTrue(disciplina.filhos.contains(avaliacao))

        // Removendo a entidade disciplina
        apagarEntidade(disciplina)

        // Verificando se a entidade foi removida corretamente
        assertFalse(curso.filhos.contains(disciplina))
    }

    @Test
    fun testApagarAtributoGlobalNome() {
        val e1 = Entidade("plano")
        val e2 = Entidade("curso", "Mestrado de Engenharia Informática")
        val e3 = Entidade("FUC")

        // Criação de instâncias da classe Atributo com diferentes valores para os campos nomeatrib e valor
        val a1 = Atributo("codigo", "M4310")
        val a2 = Atributo("nome", "Quizzes")

        criarFilhosPai(e1, e2, e3)

        e1.adicionarAtributos(a1)
        e2.adicionarAtributos(a1)
        e3.adicionarAtributos(a1,a2)

        apagarAtributoGlobalNome(e1,"codigo")

        assertTrue(e1.atributos == emptyList<Atributo>())
        assertTrue(e2.atributos == emptyList<Atributo>())
        assertEquals(1, e3.atributos.size)
        assertTrue(e3.atributos[0].nomeatrib == "nome")

    }

    @Test
    fun testalterarAtributo() {
        val entidade = Entidade("Entidade")
        val atributo = Atributo("nomeAtributo", "valorInicial")

        entidade.adicionarAtributos(atributo)

        // alterar o nome e o valor do atributo
        alterarAtributo(entidade,"nomeAtributo","novoNome","novoValor")

        assertTrue(entidade.atributos[0].nomeatrib == "novoNome")
        assertTrue(entidade.atributos[0].valor == "novoValor")

    }


    @Test
    fun testEscreverString() {

        val entidade = Entidade("Nome", "texto")
        val stringBuilder = StringBuilder()

        escreverString(entidade, 0, stringBuilder)
        val resultado = stringBuilder.toString()

        assertEquals("<"+ entidade.nome + ">" + entidade.texto + "</"+ entidade.nome + ">\n", resultado)
    }

    @Test
    fun testEscreverFicheiro() {

        val stringBuilder = StringBuilder()


        //(ponto 4 do exercicio)
        // Escreve o conteúdo do StringBuilder em um ficheiro XML
        val ficheiro = File("projeto.xml")
        val tipo = "UTF-8"
        val versão = "2.0"
        escreverFicheiro(ficheiro, tipo, versão,stringBuilder)

        assertTrue(ficheiro.exists())
    }


    @Test
    fun testXPath() {
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
        // Criação de instâncias da classe Atributo com diferentes valores para os campos nomeatrib e valor
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
        criarFilhosPai(e1, e2, e3, e9)
        criarFilhosPai(e3, e4, e5, e6)
        criarFilhosPai(e9, e10, e11, e12)
        criarFilhosPai(e6, e7, e8)
        criarFilhosPai(e12, e13, e14,e15)
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

    @Test
    fun testImprimirResultados() {
        // Create some sample entities and attributes
        val e1 = Entidade("entidade1")
        val a1 = Atributo("atributo1", "valor1")
        val a2 = Atributo("atributo2", "valor2")
        val e2 = Entidade("entidade2", "texto2")
        val resultados = listOf(
            Pair(e1, listOf(a1, a2)),
            Pair(e2, emptyList())
        )

        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        imprimirResultados(resultados)

        val expectedOutput = "<entidade1 atributo1=\"valor1\" atributo2=\"valor2\"/>\n<entidade2>texto2</entidade2>\n"
        val actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n")
        assertEquals(expectedOutput, actualOutput)
    }

}
