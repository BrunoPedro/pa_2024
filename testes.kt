import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/*class EntidadeTest {

    @Test
    fun testCriarPai() {
        val pai = Entidade("pai")
        val filho = Entidade("filho")

        filho.criarPai(pai)

        assertEquals(pai, filho.pai)
    }

    @Test
    fun testCriarFilho() {
        val pai = Entidade("pai")
        val filho = Entidade("filho")

        pai.criarFilho(filho)

        assertTrue { filho in pai.filhos }
        assertEquals(pai, filho.pai)
    }

    @Test
    fun testApagarFilho() {
        val pai = Entidade("pai")
        val filho = Entidade("filho")
        pai.criarFilho(filho)

        pai.apagarFilho(filho)

        assertFalse { filho in pai.filhos }
    }

    @Test
    fun `testar criar e apagar entidades`() {
        val pai = Entidade("pai")
        val filho1 = Entidade("filho1")
        val filho2 = Entidade("filho2")

        pai.criarFilho(filho1)
        pai.criarFilho(filho2)

        assertEquals(2, pai.filhos.size)
        assertEquals(filho1, pai.filhos[0])
        assertEquals(filho2, pai.filhos[1])

        pai.apagarFilho(filho1)

        assertEquals(1, pai.filhos.size)
        assertEquals(filho2, pai.filhos[0])

        // Verificar se o filho1 foi removido corretamente
        assertFalse(filho1 in pai.filhos)
        // Verificar se o filho2 permanece na lista de filhos
        assertTrue(filho2 in pai.filhos)
    }



}

class AtributoTest {

    @Test
    fun testAdicionarAtributo() {
        val entidade = Entidade("entidade")
        entidade.criarAtributo("atributo", "valor")

        assertTrue { "atributo" in entidade.atributos[0].nomeatrib }
        assertEquals("valor", entidade.atributos[0].valor)
    }

    @Test
    fun testRemoverAtributoNome() {
        val entidade = Entidade("entidade")
        entidade.criarAtributo("atributo", "valor")

        entidade.apagarAtributoNome("atributo")

        assertEquals(0, entidade.atributos.size)
    }

    @Test
    fun testRemoverAtributo() {
        val entidade = Entidade("entidade")
        val atributo1 = Atributo("atributo", "valor")

        entidade.criarAtributos(atributo1)
        entidade.apagarAtributo(atributo1)

        assertEquals(0, entidade.atributos.size)
    }

    @Test
    fun `testar criar e apagar atributos`() {
        val entidade = Entidade("entidade")
        val atributo1 = Atributo("atributo1", "valor1")
        val atributo2 = Atributo("atributo2", "valor2")

        // Testar criação de atributos
        entidade.criarAtributos(atributo1)
        entidade.criarAtributos(atributo2)

        assertEquals(2, entidade.atributos.size)
        assertEquals(atributo1, entidade.atributos[0])
        assertEquals(atributo2, entidade.atributos[1])

        // Testar apagar atributo
        entidade.apagarAtributo(atributo1)

        assertEquals(1, entidade.atributos.size)
        assertEquals(atributo2, entidade.atributos[0])

        // Verificar se o atributo1 foi removido corretamente
        assertFalse(atributo1 in entidade.atributos)
        // Verificar se o atributo2 permanece na lista de atributos
        assertTrue(atributo2 in entidade.atributos)
    }

    @Test
    fun testObterValorAtributo() {
        val entidade = Entidade("entidade")
        entidade.criarAtributo("atributo", "valor")

        assertEquals("valor", entidade.atributos[0].valor)
    }
}*/

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
}