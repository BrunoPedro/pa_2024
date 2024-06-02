package Projeto
import Projeto1parte.Entidade
import Projeto1parte.escreverString
import Projeto2parte.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties


/**
 * Class para os testes unitários da 2ª parte do trabalho
 */
/**
 * Classe que implementa o interface [XmlToString] para adicionar uma percentagem ("%") no final de um objeto
 * durante operações de instaciação ou adaptação para XML.
 * Implementa o método [toString] da interface [XmlToString] para adicionar a percentagem ao final da string.
 * @param obj , objeto a ser convertido em string.
 * @return A representação em string do objeto com a percentagem adicionada ao final.
 */
class AddPercentage : XmlToString {
    override fun toString(obj: Any): String {
        return obj.toString() + "%"
    }
}

/**
* Adaptador para a classe FUC, implementando o interface XmlAdapterInterface.
* Esta classe fornece uma implementação personalizada para adaptar instâncias da FUC em entidades XML.
* É usada para criar e manipular a estrutura XML de acordo com as necessidades específicas, como trocar a ordem
* dos atributos ou adicionar informações adicionais.
*/
class FUCAdapter : XmlAdapterInterface<FUCA> {
    override fun adapt(instance: FUCA): Entidade {
        // Crie e manipule a entidade conforme necessário
        val entidade = Entidade("FUCAdapted")
        entidade.criarFilho(Entidade("codigoadp", instance.codigo))
        entidade.criarFilho(Entidade("nomeadp", instance.nome))
        entidade.criarFilho(Entidade("ectsadp", instance.ects.toString()))
        entidade.criarFilho(Entidade("observacoesadp", instance.observacoes))
        // Adapte outras propriedades conforme necessário
        return entidade
    }
}
/**
 * Representa um componente de avaliação com nome e peso.
 * A classe tem várias anotações
 * @property nome, nome do componente de avaliação.
 * @property peso, peso do componente de avaliação.
 */
@XmlElemento(name = "componente")
data class ComponenteAvaliacao(
    @XmlElemento(name = "nometeste")
    @Order(2)
    val nome: String,
    @XmlElemento(name = "pesoteste")
    @XmlString(AddPercentage::class)
    @Order(1)
    val peso: Int
)

/**
 * Representa uma classe (FUC) com as informações e componentes de avaliação.
 * A classe tem várias anotações
 * @property codigo, código da classe.
 * @property nome, nome da classe.
 * @property ects, créditos ECTS da classe.
 * @property observacoes, Observações adicionais da classe.
 * @property avaliacao, lista de componentes de avaliação da classe.
 */
@XmlElemento(name = "FUC")
class FUC(
    @XmlElemento(name = "codigotes")
    val codigo: String,
    @Order(3)
    @XmlElemento(name = "nometes")
    val nome: String,
    @XmlElemento(name = "ectstes")
    @Order(1)
    val ects: Double,
    @XmlElemento(name = "obsertes")
    @Ignore
    val observacoes: String,
    @XmlElemento(name = "avaliates")
    @Order(2)
    val avaliacao: List<ComponenteAvaliacao>
)

/**
 * Representa uma classe (FUC) com as informações e componentes de avaliação.
 * A classe tem várias anotações e a anotação XmlAdapter
 * @property codigo, código da classe.
 * @property nome, nome da classe.
 * @property ects, créditos ECTS da classe.
 * @property observacoes, Observações adicionais da classe.
 * @property avaliacao, lista de componentes de avaliação da classe.
 */
@XmlAdapter(FUCAdapter::class)
@XmlElemento(name = "FUCA")
class FUCA(
    @XmlElemento(name = "codigotes")
    val codigo: String,
    @Order(3)
    @XmlElemento(name = "nometes")
    val nome: String,
    @XmlElemento(name = "ectstes")
    @Order(2)
    val ects: Double,
    @XmlElemento(name = "obsertes")
    @Ignore
    val observacoes: String,
    @XmlElemento(name = "avaliates")
    @Order(1)
    val avaliacao: List<ComponenteAvaliacao>
)

/**
 * Representa um componente de avaliação com nome e peso.
 * @property nome, nome do componente de avaliação.
 * @property peso, peso do componente de avaliação.
 */
data class ComponenteAvaliacaox(
    val nome: String,
    val peso: Int
)

/**
 * Representa uma classe (FUC) com as informações e componentes de avaliação.
 * @property codigo, código da classe.
 * @property nome, nome da classe.
 * @property ects, créditos ECTS da classe.
 * @property observacoes, Observações adicionais da classe.
 * @property avaliacao, lista de componentes de avaliação da classe.
 */
class FUCX(
    val codigo: String,
    val nome: String,
    val ects: Double,
    val observacoes: String,
    val avaliacao: List<ComponenteAvaliacaox>
)


class UnitTests2 {

    @Test
    fun testMapValue() {
        assertEquals("123", mapValue(123))
        assertEquals("123.45", mapValue(123.45))
        assertEquals("TESTE", mapValue("TESTE"))
        assertEquals("TRUE", mapValue(true))
        assertEquals("FALSE", mapValue(false))
        assertEquals("NULL", mapValue(null))
        assertEquals("Não especificado", mapValue(listOf(1, 2, 3)))
    }

    @Test
    fun testAddPercentage() {
        val addPercentage = AddPercentage()
        assertEquals("100%", addPercentage.toString(100))
        assertEquals("45.5%", addPercentage.toString(45.5))
    }

    @Test
    fun testordemPropriedades() {
        val propriedadesOrdenadas = ComponenteAvaliacao::class.memberProperties
            .sortedWith(compareBy(
                { it.findAnnotation<Order>()?.value ?: Int.MAX_VALUE },
                { ComponenteAvaliacao::class.dataClassFields.indexOf(it) }
            ))
        assertEquals("peso", propriedadesOrdenadas[0].name)
        assertEquals("nome", propriedadesOrdenadas[1].name)
    }

    /*
    @Test
    fun testaanotacaoXmlString() {
        val componente = ComponenteAvaliacao("Quizzes", 20)
        val entidade = componente.escreveXml()

        val pesoAtributo = entidade.atributos.find { it.nome == "pesoteste" }
        assertNotNull(pesoAtributo)
        assertEquals("20%", pesoAtributo?.valor)
    }
*/

    @Test
    fun testXmlElemento() {
        val componente = ComponenteAvaliacao("Teste", 10)
        val fuc = FUC("M1234", "Teste FUC", 5.0, "Nenhuma observação", listOf(componente))

        assertTrue(componente::class.annotations.any { it is XmlElemento && it.name == "componente" })
        assertTrue(ComponenteAvaliacao::nome.annotations.any { it is XmlElemento && it.name == "nometeste" })
        assertTrue(ComponenteAvaliacao::peso.annotations.any { it is XmlElemento && it.name == "pesoteste" })

        assertTrue(fuc::class.annotations.any { it is XmlElemento && it.name == "FUC" })
        assertTrue(FUC::codigo.annotations.any { it is XmlElemento && it.name == "codigotes" })
        assertTrue(FUC::nome.annotations.any { it is XmlElemento && it.name == "nometes" })
        assertTrue(FUC::ects.annotations.any { it is XmlElemento && it.name == "ectstes" })
        assertTrue(FUC::observacoes.annotations.any { it is Ignore })
        assertTrue(FUC::avaliacao.annotations.any { it is XmlElemento && it.name == "avaliates" })
    }

    @Test
    fun testIgnoreAnnotations() {
        val fuc = FUC("M1234", "Teste FUC", 5.0, "Nenhuma observação", emptyList())
        assertTrue(fuc::observacoes.annotations.any { it is Ignore })
    }


    @Test
    fun testXmlAdapter() {
        // Verifica se a anotação XmlAdapter está presente na classe FUC
        val xmlAdapterAnnotation = FUC::class.annotations.filterIsInstance<XmlAdapter>().firstOrNull()
        assertTrue(xmlAdapterAnnotation == null) { "A anotação XmlAdapter não está presente na classe FUC" }
        // Verifica se a anotação XmlAdapter está presente na classe FUCA
        val xmlAdapterAnnotation2 = FUCA::class.annotations.filterIsInstance<XmlAdapter>().firstOrNull()
        assertFalse(xmlAdapterAnnotation2 == null) { "A anotação XmlAdapter está presente na classe FUCA" }
    }

    @Test
    fun testEscreveXmlComAnotacoes() {
        val c = ComponenteAvaliacao("Quizzes", 20)
        val e1 = c.escreveXml()
        val stringBuilder = escreverString(e1, 0,StringBuilder())
        println(stringBuilder)

        val z1 = FUC("M4310", "Programação Avançada", 6.0, "la la...",
            listOf(
                ComponenteAvaliacao("Quizzes", 20),
                ComponenteAvaliacao("Projeto", 80),
                ComponenteAvaliacao("Discussão", 40)
            )
        )
        val e2 = z1.escreveXml()
        val stringBuilder2 = escreverString(e2, 0,StringBuilder())
        println(stringBuilder2)

        val z2 = FUC("M9876", "Cibersegurança", 6.0, "Com exame", emptyList())
        val e3 = z2.escreveXml()
        val stringBuilder3 = escreverString(e3, 0,StringBuilder())
        println(stringBuilder3)
    }

    @Test
    fun testEscreveXmlComAnotacaoAdapter() {

        val z1 = FUCA("M4310", "Programação Avançada", 6.0, "la la...",
            listOf(
                ComponenteAvaliacao("Quizzes", 20),
                ComponenteAvaliacao("Projeto", 80),
                ComponenteAvaliacao("Discussão", 40)
            )
        )
        val e2 = z1.escreveXml()
        val stringBuilder2 = escreverString(e2, 0,StringBuilder())
        println(stringBuilder2)

    }

    @Test
    fun testEscreveXmlSemAnotacoes() {
        val c = ComponenteAvaliacaox("Quizzes", 20)
        val e1 = c.escreveXml()
        val stringBuilder = escreverString(e1, 0,StringBuilder())
        println(stringBuilder)

        val z1 = FUCX("M4310", "Programação Avançada", 6.0, "la la...",
            listOf(
                ComponenteAvaliacaox("Quizzes", 20),
                ComponenteAvaliacaox("Projeto", 80),
                ComponenteAvaliacaox("Discussão", 40)
            )
        )
        val e2 = z1.escreveXml()
        val stringBuilder2 = escreverString(e2, 0,StringBuilder())
        println(stringBuilder2)

        val z2 = FUCX("M9876", "Cibersegurança", 6.0, "Com exame", emptyList())
        val e3 = z2.escreveXml()
        val stringBuilder3 = escreverString(e3, 0,StringBuilder())
        println(stringBuilder3)
    }
}