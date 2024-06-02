package Projeto2parte

import Projeto1parte.Atributo
import Projeto1parte.Entidade
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


// 2º Fase do Trabalho - Biblioteca

/**
 * A anotação estará disponível para reflexão em tempo de execução, ou seja permite que a anotação seja acedida
 * via reflexão durante a execução do programa.
 * As anotações são usadas no Kotlin para diversos propósitos, como fornecer metadados
 * para o compilador, bibliotecas ou frameworks.
 **/
@Retention(AnnotationRetention.RUNTIME)

/**
 * Anotação para especificar o nome do elemento XML para uma classe.
 * @property name, O nome do elemento XML.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class XmlElemento(val name: String)

/**
 * Propriedade de extensão para obter o nome do elemento XML da anotação `XmlElemento`.
 */
val XmlElemento.nome: String
    get() = this.name


/**
 * Especifica que uma anotação é aplicada sobre a propriedade de uma classe.
 * Anotação `@XmlString` para permitir a conversão customizada de propriedades para strings em classes anotadas.
 **/
@Target(AnnotationTarget.PROPERTY)
annotation class XmlString(
    val clazz: KClass<out XmlToString>
)

/**
 * Interface que converte o valor de um objeto para uma representação em string customizada.
 * Esta interface é utilizada em conjunto com a anotação `@XmlString` para permitir a conversão customizada
 * de propriedades para strings em classes anotadas.
 **/
interface XmlToString {
    fun toString(obj: Any): String
}

/**
 * Esta anotação é aplicada a classes que funcionam como adaptadores XML, permitindo que a estrutura ou o conteúdo
 * de um objeto XML seja tratado de forma customizada.
 **/
@Target(AnnotationTarget.CLASS)
annotation class XmlAdapter(
    val clazz: KClass<out XmlAdapterInterface<*>>
)

/**
 * Interface que define um adaptador XML para converter um objeto para uma entidade XML.
 * Converte um objeto de um tipo específico em uma estrutura XML customizada representada por uma entidade.
 * A função [adapt] recebe uma instância do objeto a ser adaptada e retorna uma entidade XML que representa
 * a estrutura customizada do objeto na forma XML.
 *
 * @param Ent , tipo de objeto que será adaptado para XML.
 * @return ,A entidade XML que representa a estrutura customizada do objeto na forma XML.
 */
interface XmlAdapterInterface<Ent> {
    fun adapt(instance: Ent): Entidade
}

/**
 * Anotação para indicar que uma propriedade deve ser ignorada durante a instaciação ou adaptação para XML.
 * Quando esta anotação estiver presente em uma propriedade, ela será excluída do resultado final do processo de
 * serialização ou adaptação para XML.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Ignore

/**
 * Anotação para especificar a ordem de uma propriedade durante a instaciação ou adaptação para XML.
 * O valor da anotação indica a ordem pretendida para a propriedade. Quando várias propriedades têm a mesma ordem,
 * a ordem entre elas é arbitrária.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Order(val value: Int)

/**
 * Extensão para obter a lista de propriedades de uma data class na ordem do construtor primário.
 * @return, Lista de propriedades da data class na ordem do construtor primário.
 * @throws IllegalStateException Se a classe não for uma data class.
 */
val kotlin.reflect.KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        return primaryConstructor!!.parameters.map { p ->
            declaredMemberProperties.find { it.name == p.name }!!
        }
    }

/**
 * Converte um valor para sua representação em string com base em seu tipo.
 * A função recebe um objeto de qualquer tipo e retorna a sua representação em string, seguindo regras específicas
 * para diferentes tipos de dados.
 * @param obj , O objeto a ser convertido em string.
 * @return , A representação em string do objeto. Para valores nulos, retorna "NULL". Para tipos específicos,
 * retorna uma string correspondente ao valor do objeto.
 *
 * - Int: Retorna a representação em string do número inteiro.
 * - Double: Retorna a representação em string de um número flutuante.
 * - String: Retorna a própria string.
 * - Boolean: Retorna "TRUE" ou "FALSE" em maiúsculas.
 * - null: Retorna a string "NULL".
 * - Outros tipos: Retorna a string "Não especificado".
 **/
fun mapValue(obj: Any?) =
    when (obj) {
        is Int -> obj.toString()
        is Double -> obj.toString()
        is String -> "$obj"
        is Boolean -> "$obj".uppercase()
        null -> "NULL"
        else -> "Não especificado"
    }

/**
 * Converte qualquer objeto em uma instância da classe `Entidade`.
 *
 * A função utiliza várias anotações a anotação. Se as anotações não estiverem presentes, cria uma instância da classe
 * `Entidade`.
 * Anotações usadas na função:
 *     XmlElemento: Define o nome do elemento XML.
 *     XmlAdapter: Converte um objeto de um tipo específico em uma estrutura XML customizada representada por uma entidade.
 *     Order: Define a ordem das propriedades no XML.
 *     Ignore: Indica que a propriedade que deve ser ignorada na conversão para XML.
 *     XmlString: Define um adaptador para a conversão do valor da propriedade para string.
 * @receiver, Recebe qualquer instância de uma classe.
 * @return, Uma instância da classe `Entidade`.
 */
fun Any.escreveXml(): Entidade {
    val clazz = this::class
    val xmlElementAnnotation = clazz.findAnnotation<XmlElemento>()
    val tagName = xmlElementAnnotation?.nome ?: clazz.simpleName!!

    // Verificar se a classe tem a anotação XmlAdapter
    val xmlAdapterAnnotation = clazz.findAnnotation<XmlAdapter>()
    if (xmlAdapterAnnotation != null) {
        val adapterInstance = xmlAdapterAnnotation.clazz.constructors.first().call()
        return (adapterInstance as XmlAdapterInterface<Any>).adapt(this)
    }

    val entidade = Entidade(tagName)

    // Ordenar as propriedades pela anotação @Order
    val orderedProperties = clazz.memberProperties
        .sortedWith(compareBy(
            { it.findAnnotation<Order>()?.value ?: Int.MAX_VALUE }, // Ordena pelo valor da anotação @Order se existir
            { clazz.dataClassFields.indexOf(it) } // Ordena pela ordem do construtor se não existir anotação @Order
        ))

    orderedProperties.forEach { property ->
        val value = property.getter.call(this) // Converte para String
        if (value != null) {
            if (value is Collection<*>){
                val filho = Entidade(property.findAnnotation<XmlElemento>()?.nome ?: property.name)
                entidade.criarFilho(filho)
                value.forEach{
                    filho.criarFilho(criarAtrib(it))
                }
            }
            else {
               if (property.findAnnotation<Ignore>() == null) {
                    val xmlString = property.findAnnotation<XmlString>()
                    val nomeCamp = property.findAnnotation<XmlElemento>()?.nome ?: property.name
                    val valueCamp = if (xmlString != null) {
                        // Instancia a classe especificada na anotação XmlString
                        val xmlToStringInstance = xmlString.clazz.constructors.first().call()
                        xmlToStringInstance.toString(value ?: "")
                    } else {
                        value.toString()
                    }
                    entidade.criarFilho(Entidade(nomeCamp, mapValue(valueCamp)))
                }
                else {
                    null
                }
            }
        }
    }
    return entidade
}


fun criarAtrib(o: Any?): Entidade {
    if (o == null) {
        return Entidade("null", "null")
    }
    val clazz = o::class
    val ann = clazz.findAnnotation<XmlElemento>()
    val nomeAnotacao = ann?.nome ?: clazz.simpleName!!
    val nomeclasse = Entidade(nomeAnotacao)

    // Verificar se a classe tem a anotação XmlAdapter
    val xmlAdapterAnnotation = clazz.findAnnotation<XmlAdapter>()
    if (xmlAdapterAnnotation != null) {
        val adapterInstance = xmlAdapterAnnotation.clazz.constructors.first().call()
        return (adapterInstance as XmlAdapterInterface<Any>).adapt(o)
    }
    val properties = clazz.memberProperties
        .sortedBy { it.findAnnotation<Order>()?.value ?: Int.MAX_VALUE } // Ordenar pelas anotações @Order

    val attributes = properties.map { property ->
        val xmlElemento = property.findAnnotation<XmlElemento>()
        val xmlString = property.findAnnotation<XmlString>()
        val attributeName = xmlElemento?.name ?: property.name
        val attributeValue = if (xmlString != null) {
            // Instancia a classe especificada na anotação XmlString
            val xmlToStringInstance = xmlString.clazz.constructors.first().call()
            xmlToStringInstance.toString(property.getter.call(o) ?: "")
        } else {
            property.getter.call(o)?.toString() ?: ""
        }
        Atributo(mapValue(attributeName), mapValue(attributeValue))
    }
    nomeclasse.adicionarAtributos(*attributes.toTypedArray())
    return nomeclasse
}
