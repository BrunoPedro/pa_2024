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
    fun testEscreveXml() {
        val c = ComponenteAvaliacao("Quizzes", 20)
        println(c.escreveXml())

        val z1 = FUC("M4310", "Programação Avançada", 6.0, "la la...",
            listOf(
                ComponenteAvaliacao("Quizzes", 20),
                ComponenteAvaliacao("Projeto", 80),
                ComponenteAvaliacao("Discussão", 40)
            )
        )
        println(z1.escreveXml())

        val z2 = FUC("M9876", "Cibersegurança", 6.0, "Com exame", emptyList())
        println(z2.escreveXml())
    }

    @Test
    fun testXmlAdapter() {
        // Verifica se a anotação XmlAdapter está presente na classe FUC
        val xmlAdapterAnnotation = FUC::class.annotations.filterIsInstance<XmlAdapter>().firstOrNull()
        assertTrue(xmlAdapterAnnotation == null) { "A anotação XmlAdapter não está presente na classe FUC" }
    }

}