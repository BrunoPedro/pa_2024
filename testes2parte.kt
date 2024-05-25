package Projeto
import Projeto2parte.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties



/**
 * Class para os testes unitários da 2ª parte do trabalho
 */
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

    @Test
    fun testaanotacaoXmlString() {
        val componente = ComponenteAvaliacao("Quizzes", 20)
        val entidade = componente.escreveXml()

        val pesoAtributo = entidade.atributos.find { it.nome == "pesoteste" }
        assertNotNull(pesoAtributo)
        assertEquals("20%", pesoAtributo?.valor)
    }
}

/**
 * fazer testes de:
 *   anotação XmlElemento para o nome da classe e para o nome dos campos nas 2 classes
 *   anotação da order para a classe FUC
 *   fazer teste para a anotação Ignore
 *   fazer teste do escrevexml para :
 *   val z = FUC("M4310", "Programação Avançada", 6.0, "la la...",
 *     listOf(
 *          ComponenteAvaliacao("Quizzes", 20),
 *          ComponenteAvaliacao("Projeto", 80),
 *          ComponenteAvaliacao("Discussão", 40)
 *     )
 * )
 * e para
 * val z = FUC("M9876", "Cibersegurança", 6.0, "Com exame", emptyList())
 * e para
 * val c = ComponenteAvaliacao("Quizzes", 20)
 * Fazer teste da anotação XmlAdapter(FUCAdapter::class)
 * E depois de falar com o professor fazer teste sem as anotações
 */