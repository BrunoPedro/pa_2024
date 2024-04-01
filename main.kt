import java.io.File
import javax.xml.xpath.XPathConstants


open class Entidade {
    var nome: String
    var filhos: MutableList<Entidade>
    var pai: Entidade? = null
    var texto: String

    /**
     *    Método para definir o pai da entidade
     */

    constructor (nome: String, texto: String = "") {
        this.nome = nome
        this.filhos = mutableListOf()
        this.texto = texto

    }

    /**
     *    Método para definir o pai da entidade
     */
    fun criarPai(novoPai: Entidade) {
        this.pai = novoPai
    }

    /**
     *    Método para adicionar um filho à lista de filhos da entidade
     */
    fun criarFilho(novoFilho: Entidade) {
        this.filhos.add(novoFilho)
        novoFilho.pai = this
    }

    /**
     *    Método para apagar um filho à lista de filhos da entidade
     */
    fun apagarFilho (apagarFilho: Entidade) {
        this.filhos.remove(apagarFilho)
    }


    /*   fun criarAtributo(novoAtributo : String="", novoValor : String) : Entidade{
    class Atributo {
        var nomeatrib : String
        var valor : String

        constructor (nomeatrib : String="", nomevalor : String) {
            this.nomeatrib = nomeatrib
            this.valor = nomevalor

        }
    }
    this.nomeatrib = novoAtributo
}
*/
}

class Atributo(nome: String, texto: String = "") : Entidade(nome, texto) {
    var nomeatrib: String = ""
    var valor: String = ""

    constructor(nome: String, texto: String, nomeatrib: String, valor: String) : this(nome, texto) {
        this.nomeatrib = nomeatrib
        this.valor = valor
    }

    fun criarAtributo(novoAtrib: String, novoValor: String) {
        this.nomeatrib = novoAtrib
        this.valor = novoValor

    }
}

fun criarFilhosPai(entidadePai: Entidade?, vararg filhos: Entidade) {
    if (entidadePai == null) return

    for (filho in filhos) {
        entidadePai.criarFilho(filho)
    }
}

fun encontarEntidadeeFilhos(entidade: Entidade, nivel: Int = 0) {
    val tabs = "\t".repeat(nivel)
    if (entidade.filhos.isEmpty()) {
        println("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>")
    } else {
        println("$tabs<${entidade.nome}>${entidade.texto}")
        for (filho in entidade.filhos) {
            encontarEntidadeeFilhos(filho, nivel + 1)
        }
        println("$tabs</${entidade.nome}>")
    }
}


fun alterarNomeEntidade(entidadePai: Entidade, nomeAntigo: String, novoNome: String) {
    // Verificar se a entidade pai tem o nome antigo
    if (entidadePai.nome == nomeAntigo) {
        // Alterar o nome da entidade pai
        entidadePai.nome = novoNome
    }
    // Percorrer os filhos da entidade pai recursivamente
    for (filho in entidadePai.filhos) {
        // Chamar recursivamente a função para cada filho
        alterarNomeEntidade(filho, nomeAntigo, novoNome)
    }
}

fun apagarEntidadePorNome(entidadePai: Entidade?, nome: String) {
    if (entidadePai == null) return
    // Verificar se a entidade pai tem o nome a apagar
    if (entidadePai.nome == nome) {
        // Apagar a entidade pai da lista de filhos de seu pai (se existir)
        entidadePai.pai?.apagarFilho(entidadePai)
        return
    }
    // Procurar pelo nome nos filhos da entidade pai
    for (filho in entidadePai.filhos.toList()) {
        // Apagar recursivamente os filhos da entidade que tem o nome correspondente
        apagarEntidadePorNome(filho, nome)
    }
}

fun apagarFicheiro(ficheiro: File) {
    /** Verificar se o arquivo existe
     */
    if (ficheiro.exists()) {
        /**    Eliminar o ficheiro se ele existir
         */
        ficheiro.delete()
    }
}

fun criarNivel0ficheiro(ficheiro: File, tipo: String) {
    ficheiro.writeText("<?xml version=\"1.0\" encoding=\"${tipo}\"?>\n")
}

fun escreverFicheiro(entidade: Entidade, ficheiro: File, nivel: Int) {
    val tabs = "\t".repeat(nivel)
    if (entidade.filhos.isEmpty()) {
        ficheiro.appendText("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>\n")
    } else {
        ficheiro.appendText("$tabs<${entidade.nome}>${entidade.texto}\n")
        for (filho in entidade.filhos) {
            escreverFicheiro(filho, ficheiro, nivel + 1)
        }
        ficheiro.appendText("$tabs</${entidade.nome}>\n")
    }
}

fun encontrarEntidadeXPath(entidade: Entidade?, xpath: String): Entidade? {
    if (entidade == null) return null
    if (entidade.nome == xpath) return entidade

    for (filho in entidade.filhos) {
        val resultado = encontrarEntidadeXPath(filho, xpath)
        if (resultado != null) return resultado
    }

    return null
}


fun main() {
    val e1 = Entidade("plano")
    val e2 = Entidade("curso", "Mestrado de Engenharia Informática")
    val e3 = Entidade("FUC")
    val e4 = Entidade("nome", "Programação Avançada")
    val e5 = Entidade("etcs", "6.0")
    val e6 = Entidade("avaliação")
    val e11 = Entidade("componente nome=Quizzes, peso 20%")
    val e12 = Entidade("componente nome=Projeto, peso 80%")
    val e7 = Entidade("FUC")
    val e8 = Entidade("nome", "Dissertação")
    val e9 = Entidade("etcs", "20.0")
    val e10 = Entidade("avaliação")

    criarFilhosPai(e1, e2, e3, e7)
    criarFilhosPai(e3, e4, e5, e6)
    criarFilhosPai(e7, e8, e9, e10)
    criarFilhosPai(e6, e11, e12)

    val entidadeEncontrada = encontrarEntidadeXPath(e1, "FUC/avaliação")
    println("Entidade encontrada: ${entidadeEncontrada?.nome}")

    encontarEntidadeeFilhos(e1)
    alterarNomeEntidade(e1,"FUC", "FUCnovo")
    /*
       apagarEntidadePorNome(e1,"FUCnovo")
   */
    val ficheiro = File("projeto.xml")
    val tipo = "UTF-8"
    apagarFicheiro(ficheiro)
    criarNivel0ficheiro(ficheiro,tipo)
    escreverFicheiro(e1, ficheiro, 0)

}