import java.io.File


/**
 * Definição da classe entidade
 * Uma entidade é composta por um nome, uma lista de filhos, uma referência para o seu pai,
 * um texto associado e uma lista de atributos
 *
 * @property nome, nome da entidade
 * @property filhos, lista de filhos da entidade
 * @property pai, referência para o pai da entidade
 * @property texto, texto associado à entidade
 * @property atributos, lista de atributos da entidade
 */
open class Entidade {
    var nome: String
    var filhos: MutableList<Entidade>
    var pai: Entidade? = null
    var texto: String? = null
    var atributos: MutableList<Atributo>

    /**
     *  Cria uma nova instância da "Entidade" com o nome identificado e, opcionalmente, um texto associado
     *  A lista de filhos da entidade e a lista de atributos da entidade é inicializada a vazio
     *
     *  @param nome, nome da entidade
     *  @param texto, texto associado à entidade (padrão é uma string vazia)
     */
    constructor (nome: String, texto: String? = null) {
        this.nome = nome
        this.filhos = mutableListOf()
        this.texto = texto
        this.atributos = mutableListOf()
    }

    /**
     *  Método para criar o pai da entidade
     *
     *  @param novoPai, novo pai desta entidade
     */
    fun criarPai(novoPai: Entidade) {
        this.pai = novoPai
    }

    /**
     *  Método para adicionar um filho à lista de filhos de uma entidade
     *  Adiciona [novoFilho] à lista de filhos de uma entidade
     *  Define uma entidade como o pai de [novoFilho]
     *
     *  @param novoFilho é o novo filho a ser adicionado a uma entidade
     */
    fun criarFilho(novoFilho: Entidade) {
        this.filhos.add(novoFilho)
        novoFilho.pai = this
    }

    /**
     * Recebe um visitante e permite que ele visite uma entidade e os seus filhos
     * Permite que o [visitor] visite a entidade, fornecendo o [nivel] de aninhamento a percorrer
     *
     * @param visitor,  visitante que irá visitar a entidade
     * @param nivel, nível de aninhamento da entidade na hierarquia
     *
     */
    fun accept(visitor: EntidadeVisitor, nivel: Int) {
        visitor.visit(this, nivel)
        for (filho in filhos) {
            filho.accept(visitor, nivel + 1)
        }
    }

    /**
     * Método para apagar um filho à lista de filhos da entidade
     *
     * @param apagarFilho, a entidade filho a ser removida
     */
    fun apagarFilho(apagarFilho: Entidade) {
        this.filhos.remove(apagarFilho)
    }

    /**
     * Método para criar atributos e adicionar à lista de atributos da entidade
     * Verifica que não existe um atributo com o mesmo nome, Se já existir um atributo com o mesmo nome,
     * dá uma mensagem de erro, senão, cria um novo atributo com o nome e valor especificados e adiciona à lista de atributos desta entidade
     *
     * @param novoAtributo, nome do novo atributo a ser criado
     * @param valor, valor associado ao novo atributo
     */
    fun criarAtributo(novoAtributo: String, valor: String) {
        val nomeAtributoExiste = this.atributos.any { it.nomeatrib == novoAtributo }
        if (nomeAtributoExiste) {
            println("Já existe um atributo com esse nome!")
        } else {
            val vAtributo = Atributo(novoAtributo, valor)
            this.atributos.add(vAtributo)
        }
    }

    /**
     * Função que cria vários atributos ao mesmo tempo, para a mesma entidade, adiciona-os à lista de atributos da entidade
     * Verifica que não existe um atributo com o mesmo nome, Se já existir um atributo com o mesmo nome,
     * dá uma mensagem de erro, senão, cria um novo atributo com o nome e valor especificados e adiciona à lista de atributos desta entidade
     *
     * @param novosAtributos, Lista de atributos a serem criados
     */
    fun criarAtributos(vararg novosAtributos: Atributo) {
        for (atributo in novosAtributos) {
            // Verifica se já existe um atributo com o mesmo nome
            val nomeAtributoExiste = this.atributos.any { it.nomeatrib == atributo.nomeatrib }
            if (nomeAtributoExiste) {
                println("Já existe um atributo com o nome '${atributo.nomeatrib}'!")
            } else {
                // Adiciona o novo atributo à lista de atributos da entidade
                this.atributos.add(atributo)
            }
        }
    }

    /**
     * Método para apagar atributos da lista de atributos da entidade
     *
     * @param apagarAtributo, atributo a ser eliminado
     */
    fun apagarAtributo(apagarAtributo: Atributo) {
        this.atributos.remove(apagarAtributo)
    }

    /**
     *  Método para eliminar atributos pelo seu nome
     *  Verifica se o nome do atributo atual é igual a [vNome], se for, elimina o atributo da lista de atributos da entidade
     *
     *  @param vNome, nome do atributo a ser eliminado
     */
    fun apagarAtributoNome(vNome: String) {
        // apaga o atributo se o nome for igual
        atributos.removeIf { it.nomeatrib == vNome }
    }
}


/**
 * Definição da classe atributo, que representa um atributo de uma entidade
 * Um atributo é composto por um nome e por um valor quando existe
 *
 * @property nomeatrib, nome do atributo
 * @property valor, valor que o atributo pode ter
*/
class Atributo  {
    var nomeatrib: String = ""
    var valor: String = ""

    /**
    * Cria uma nova instância de "Atributo" com o nome identificado e um valor associado
    *
    * @param nomeatrib, nome do atributo
    * @param valor, valor do atributo
    */
    constructor(nomeatrib: String, valor: String) {
        this.nomeatrib = nomeatrib
        this.valor = valor
    }

}

/**
 * Adiciona os filhos especificados à lista de filhos de uma entidade pai, se ela não for nula
 *
 * @param entidadePai, a entidade pai à qual os filhos serão adicionados
 * @param filhos, as entidades filhos a serem adicionados à entidade pai
 */
fun criarFilhosPai(entidadePai: Entidade?, vararg filhos: Entidade) {
    if (entidadePai == null) return

    for (filho in filhos) {
        entidadePai.criarFilho(filho)
    }
}

/**
 * Altera o nome de uma entidade e dos seus filhos recursivamente, substituindo um nome antigo por um nome novo
 *
 * @param entidadePai, a entidade pai cujo o seu nome e dos seus filhos serão alterados
 * @param nomeAntigo, nome antigo a ser substituído
 * @param novoNome, nome novo a ser atribuído
 */

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

/**
 * Apaga uma entidade e os seus filhos recursivamente com base num nome identificado
 *
 * @param entidadePai, entidade pai da qual serão eliminadas as entidades
 * @param nome, nome da entidade a eliminar
 */
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

/**
 * Apaga a entidade especificada e seus filhos de sua árvore pai, se existir
 *
 * @param entidade, a entidade a ser apagada
 */
fun apagarEntidade(entidade: Entidade) {
    // Verifica se a entidade possui um pai
    entidade.pai?.let { pai ->
        // Apaga a entidade da lista de filhos do pai
        pai.apagarFilho(entidade)
    }
}

/**
 * A função percorre recursivamente a árvore de entidades a partir da entidade pai e elimina
 * o atributo com o nome identificado em todas as entidades filhas encontradas
 *
 * @param entidadePai, entidade pai onde é para eliminar os atributos
 * @param vNome, nome do atributo a eliminar
 */

fun apagarAtributoGlobalNome(entidadePai: Entidade, vNome: String){
    entidadePai.atributos.removeIf { it.nomeatrib == vNome }
    // Chamar recursivamente a função para cada filho
    for (filho in entidadePai.filhos) {
        apagarAtributoGlobalNome(filho, vNome)
    }
}

/**
 * Apaga um atributo de uma entidade com base no nome da entidade e do atributo, a partir da entidade pai elimina
 * o atributo com o nome identificado em todas as entidades filhas encontradas
 *
 * @param nomeEntidade, nome da entidade onde o atributo será apagado
 * @param nomeAtributo, nome do atributo a ser apagado
 */
fun apagarAtributoNomeEntidadeNomeAtributoGlobal(entidade: Entidade, nomeEntidade: String, nomeAtributo: String) {
    /**
     * Percorre todas as entidades na árvore, encontra a entidade com o nome correspondente
     * e apaga o atributo correspondente se existir
     *
     * @param entidade, entidade a ser verificada
     */
    fun apagarAtributoEntidadeNome(entidade: Entidade) {
        // Verifica se esta é a entidade que estamos a procurar
        if (entidade.nome == nomeEntidade) {
            // Apaga o atributo se ele existir na lista de atributos da entidade
            entidade.atributos.removeIf { it.nomeatrib == nomeAtributo}
        }
        // Percorre os filhos recursivamente
        for (filho in entidade.filhos) {
            apagarAtributoEntidadeNome(filho)
        }
    }
    // Inicia a procura da entida a partir da raiz da árvore de entidades que foi passada como argumento
    apagarAtributoEntidadeNome(entidade)
}

/**
 * Altera o nome e o valor de um atributo de uma entidade existente
 *
 * @param entidade, entidade onde o atributo será alterado
 * @param nomeAntAtributo,  nome antigo do atributo que será alterado
 * @param nomeAtributo, nome novo para o atributo
 * @param valor, valor para o atributo
 */
fun alterarAtributo(entidade:Entidade, nomeAntAtributo: String, nomeAtributo: String, valor: String) {
    // Encontra o atributo com o nome antigo na lista de atributos da entidade
    val atributo = entidade.atributos.find { it.nomeatrib == nomeAntAtributo }
    // Verifica se o atributo foi encontrado
    if (atributo != null && !entidade.atributos.any { it.nomeatrib == nomeAtributo }) {
        // Atualiza o nome e o valor do atributo
        atributo.nomeatrib = nomeAtributo
        atributo.valor = valor
    }
}

/**
 * Altera o nome de um atributo em todas as entidades descendentes da entidade pai, desde que o nome da entidade
 * seja igual ao nome da entidade passada no parametro
 * A função percorre recursivamente a árvore de entidades a partir da entidade fornecida e verifica se a entidade
 * tem o nome da entidade identificada se for valida se os atributos dessa entidade têm o nome identificado,
 * se tiverem altera o nome do atributo identificado para o novo nome
 *
 * @param entidadePai, entidade pai a partir da qual vai ser feita a pesquisa incluindo os seus filhos
 * @param nomeEntidade, nome da entidade da qual vai ser alterado o nome do atributo
 * @param nomeVelho, nome atual do atributo que se pretende alterar globalmente
 * @param novoNome, nome novo que vai ser dado ao atributo
 */
fun alterarAtributoNomeEntidadeNomeAtributoGlobal(entidade: Entidade, nomeEntidade: String, nomeAtributo: String,nomeAtributoNovo: String) {

    fun alterarAtributoEntidadeNomeGlobal(entidade: Entidade) {
        // Verifica se esta é a entidade que estamos a procurar
        if (!entidade.atributos.any { it.nomeatrib == nomeAtributoNovo }) {
            // Pesquisa todos os atributos da entidade
            for (atributo in entidade.atributos) {
                // Verifica se o atributo tem o nome antigo
                if (atributo.nomeatrib == nomeAtributo) {
                    // Atualiza o nome do atributo
                    atributo.nomeatrib = nomeAtributoNovo
                }
            }
        }
        // Percorre os filhos recursivamente
        for (filho in entidade.filhos) {
            alterarAtributoEntidadeNomeGlobal(filho)
        }
    }
    // Inicia a procura da entida a partir da raiz da árvore de entidades que foi passada como argumento
    alterarAtributoEntidadeNomeGlobal(entidade)
}

/**
 * Altera o nome de um atributo em todas as entidades descendentes da entidade pai de forma global
 * A função percorre recursivamente a árvore de entidades a partir da entidade pai fornecida e altera
 * o nome do atributo identificado para o nome  novo em todas as entidades filhas encontradas
 *
 * @param entidadePai, entidade pai da qual vai ser alterado o nome do atributo e de todos os seus filhos
 * @param nomeVelho, nome atual do atributo que se pretende alterar globalmente
 * @param novoNome, nome novo que vai ser dado ao atributo
 */
fun alterarAtributoNomeEntidade(entidadePai: Entidade, nomeVelho : String, novoNome: String){
// Verifica se o novo nome não está sendo utilizado por outro atributo na entidade
    if (!entidadePai.atributos.any { it.nomeatrib == novoNome }) {
        // Pesquisa todos os atributos da entidade
        for (atributo in entidadePai.atributos) {
            // Verifica se o atributo tem o nome antigo
            if (atributo.nomeatrib == nomeVelho) {
                // Atualiza o nome do atributo
                atributo.nomeatrib = novoNome
            }
        }
    }
    // Chama recursivamente a função para cada filho
    for (filho in entidadePai.filhos) {
        alterarAtributoNomeEntidade(filho, nomeVelho, novoNome)
    }
}

/**
 * Cria um novo atributo numa entidade específica, em que é feita uma pesquisa pelo nome da entidade,
 * e adiciona o atributo e o seu valor à lista de atributos da entidade
 *
 * @param entidade, Raiz da árvore de entidades onde a procura começa
 * @param nomeEntidade, nome da entidade onde o atributo será criado
 * @param nomeAtributo, nome do novo atributo a ser criado
 * @param valor, valor associado ao novo atributo
 */
fun criarAtributoNomeEntidadeNomeAtributoGlobal(entidade: Entidade, nomeEntidade: String, nomeAtributo: String, valor: String) {
    // Função para percorrer as entidades recursivamente
    fun percorrerEntidadesGlobal(entidade: Entidade) {
        // Verifica se esta é a entidade que procurada
        if (entidade.nome == nomeEntidade) {
           entidade.criarAtributo(nomeAtributo, valor)
        }
        // Percorre os filhos recursivamente
        for (filho in entidade.filhos) {
            percorrerEntidadesGlobal(filho)
        }
    }
    // Inicia a procura a partir da raiz da árvore de entidades
    percorrerEntidadesGlobal(entidade)
}

/**
 * Acessa uma entidade e aos seus filhos e imprime o resultado em formato XML
 *
 * @param entidade, entidade a ser acedida
 * @param nivel, nível de indentação para a formatação XML (opcional, padrão é 0)
 */
fun acederEntidadMaeeFilhos(entidade: Entidade, nivel: Int = 0) {
    val tabs = "\t".repeat(nivel)
    // Verifica se a entidade tem atributos
    if (entidade.atributos.isNotEmpty()) {
        // Escreve os atributos da entidade
        val atributosXml = entidade.atributos.joinToString(" ") { "${it.nomeatrib}=\"${it.valor}\"" }
        // Veriica se entidade tem filhos para definir como escreve a terminação
        if (entidade.filhos.isEmpty()){
            println("$tabs<${entidade.nome} $atributosXml/>")
        } else {
            println("$tabs<${entidade.nome} $atributosXml>")
        }
    } else {
        // Adiciona a tag de abertura da entidade se não houver atributos
        if (entidade.texto == null) {
            println("$tabs<${entidade.nome}>")
        } else {
            // Adiciona a tag de abertura da entidade com o texto se não houver atributos
            println("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>")
        }
    }
    // Verifica se a entidade tem filhos
    if (entidade.filhos.isNotEmpty()) {
        // Percorre os filhos da entidade e chama recursivamente a função para cada filho
        for (filho in entidade.filhos) {
            acederEntidadMaeeFilhos(filho, nivel + 1)
        }
        // Escreve a marca de fecho da entidade com base no nível
        println("$tabs</${entidade.nome}>")
    }
}

/**
 * Função que escreve o conteúdo de um StringBuilder num arquivo XML
 *
 * @param ficheiro, ficheiro onde o conteúdo XML será escrito
 * @param tipo, tipo de codificação para o arquivo XML
 * @param versão, versão do XML
 * @param xmlStringBuilder, StringBuilder contendo o conteúdo XML a ser escrito no ficheiro
 */
fun escreverFicheiro(ficheiro: File, tipo: String, versão: String,xmlStringBuilder: StringBuilder ) {
    // Escreve a declaração XML no ficheiro
    ficheiro.writeText("<?xml version=\"${versão}\" encoding=\"${tipo}\"?>\n")
    // Escreve o conteúdo do StringBuilder no ficheiro
    ficheiro.appendText(xmlStringBuilder.toString())
}

/**
 * A função escreve a representação XML da entidade e dos seus filhos numa StringBuilder
 *
 * @param entidade, entidade para a qual se está a escrever a representação XML
 * @param nivel, nível de indentação para a formatação XML
 * @param xmlStringBuilder, StringBuilder para acumular o conteúdo XML
 */
fun escreverString(entidade: Entidade, nivel: Int, xmlStringBuilder: StringBuilder) {
    // Cria uma string com espaços em branco para representar a indentação com base no nível
    val tabs = "\t".repeat(nivel)

    // Verifica se a entidade tem atributos
    if (entidade.atributos.isNotEmpty()) {
        // Escreve os atributos da entidade
        val atributosXml = entidade.atributos.joinToString(" ") { "${it.nomeatrib}=\"${it.valor}\"" }
        // Veriica se entidade tem filhos para definir como escreve a terminação
        if (entidade.filhos.isEmpty()){
            xmlStringBuilder.append("$tabs<${entidade.nome} $atributosXml/>\n")
        } else {
            xmlStringBuilder.append("$tabs<${entidade.nome} $atributosXml>\n")
        }
    } else {
        // Adiciona a tag de abertura da entidade se não houver atributos
        if (entidade.texto == null) {
            xmlStringBuilder.append("$tabs<${entidade.nome}>\n")
        } else {
            // Adiciona a tag de abertura da entidade com o texto se não houver atributos
            xmlStringBuilder.append("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>\n")
        }
    }
    // Verifica se a entidade tem filhos
    if (entidade.filhos.isNotEmpty()) {
        // Percorre os filhos da entidade e chama recursivamente a função para cada filho
        for (filho in entidade.filhos) {
            escreverString(filho, nivel + 1, xmlStringBuilder)
        }
        // Escreve a marca de fecho da entidade com base no nível
        xmlStringBuilder.append("$tabs</${entidade.nome}>\n")
    }
}


fun encontrarEntidadesPorXPath(entidade: Entidade, xpath: String): List<Pair<Entidade, List<Atributo>>> {
    // Lista para armazenar os resultados
    val resultados = mutableListOf<Pair<Entidade, List<Atributo>>>()

    // Função interna para percorrer a árvore de entidades recursivamente
    fun percorrerEntidades(entidade: Entidade, caminho: List<String>) {
        // Verifica se o caminho está vazio (todos os elementos do XPath foram correspondidos)
        if (caminho.isEmpty()) {
            // Adiciona a entidade atual à lista de resultados juntamente com seus atributos
            resultados.add(entidade to entidade.atributos)
        } else {
            // Obtém o próximo elemento do caminho
            val proximo = caminho.first()
            // Obtém os filhos da entidade que correspondem ao próximo elemento do caminho
            val filhosCorrespondentes = entidade.filhos.filter { it.nome == proximo }
            // Percorre os filhos correspondentes
            for (filho in filhosCorrespondentes) {
                // Chama recursivamente a função para o filho com o caminho restante
                percorrerEntidades(filho, caminho.drop(1))
            }
        }
    }

    // Divide a expressão XPath em partes separadas por '/'
    val partes = xpath.split("/")

    // Chama a função interna para iniciar a busca a partir da raiz da árvore de entidades
    percorrerEntidades(entidade, partes)

    // Retorna os resultados encontrados
    return resultados
}




/**
 *Interface para definir um visitor que pode ser utilizado para visitar entidades em uma estrutura de árvore
 *@param entidade, entidade a ser visitada
 *@param nivel,  nível da entidade na hierarquia da árvore de entidades
*/

interface EntidadeVisitor {
    fun visit(entidade: Entidade, nivel: Int)
}

/**
 * Classe do visitor para encontrar e imprimir entidades e os seus filhos na estrutura de árvore
 * Este visitor percorre recursivamente a árvore de entidades a partir da entidade fornecida
 * e imprime as entidades e seus filhos em um formato XML.
 *
 * @param entidade, entidade a ser visitada
 * @param nivel, nível da entidade na hierarquia da árvore de entidades
 */
class EncontrarEntidadeFilhosVisitor : EntidadeVisitor {
    override fun visit(entidade: Entidade, nivel: Int) {
        val tabs = "\t".repeat(nivel)
        if (entidade.filhos.isEmpty()) {
            println("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>")
        } else {
            println("$tabs<${entidade.nome}>${entidade.texto}")
            for (filho in entidade.filhos) {
                filho.accept(this, nivel + 1)
            }
            println("$tabs</${entidade.nome}>")
        }
    }
}

/**
 * Função principal que exemplifica  a criação de entidades, atributos e relacionamentos entre eles
 * Realização de operações como criação de arquivo XML, eliminar, criar e alterar atributos e entidades
 * Pesquisa de entidades
 */
fun main() {
    // Criação de instâncias da classe Entidade com diferentes valores para os campos nome e texto
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
    e3.criarAtributos (a1)
    e9.criarAtributos (a6)
    e7.criarAtributos (a2, a3)
    e8.criarAtributos (a4, a5)
    e13.criarAtributos (a7, a8)
    e14.criarAtributos (a9, a10)
    e15.criarAtributos (a11, a12)

 /*
    //Exemplos de operações sobre entidades e atributos
    //(ponto 1 do exercicio)
    apagarEntidade(e9)
    //(ponto 2 do exercicio)
    e14.apagarAtributo(a9)
    alterarAtributo(e3,"codigo", "Teste","M4100" )
    // Altera um atributo para uma entidade especifica
    alterarAtributoNomeEntidade(e1, "codigo","codigo2")
    //(ponto 3 do exercicio)
    acederEntidadMaeeFilhos(e9)
    //(ponto 6 do exercicio)
    criarAtributoNomeEntidadeNomeAtributoGlobal(e1, "FUC", "code", "zzzzz")
    //(ponto 7 do exercicio)
    alterarNomeEntidade(e1,"FUC", "FUCnovo")
    //(ponto 8 do exercicio)
    alterarAtributoNomeEntidadeNomeAtributoGlobal(e1,"FUC", "codigo","codigo2")
    //(ponto 9 do exercicio)
    apagarEntidadePorNome(e1,"FUCnovo")
    //(ponto 10 do exercicio)
    apagarAtributoNomeEntidadeNomeAtributoGlobal(e1,"FUC", "codigo")
    //(Dois exercicio não solicitados em nenhum ponto do trabalho)
       //Apagar atributo globalmente só por nome de atributo
    apagarAtributoGlobalNome(e1,"codigo")
      //Alterar atributo globalmente só por nome de atributo
    alterarAtributoNomeEntidade(e1, "codigo","codigo2")
*/

/*
    //Exemplo de uso do XPath
    val listacaminho = encontrarEntidadeXPath(e1, "FUC/avaliação/componente")
    println(listacaminho)
*/

    val resultados = encontrarEntidadesPorXPath(e1, "FUC/avaliação/componente")
    for ((entidade, atributos) in resultados) {
        for (atributo in atributos) {
           val juntaCampos = entidade.atributos.joinToString(" ") { "${it.nomeatrib}=\"${it.valor}\"" }
            println("<${entidade.nome} $juntaCampos/>")
        }
    }
/*
    if (entidade.atributos.isNotEmpty()) {
        // Escreve os atributos da entidade
        val atributosXml = entidade.atributos.joinToString(" ") { "${it.nomeatrib}=\"${it.valor}\"" }
        // Veriica se entidade tem filhos para definir como escreve a terminação
        if (entidade.filhos.isEmpty()){
            println("$tabs<${entidade.nome} $atributosXml/>")
        } else {
            println("$tabs<${entidade.nome} $atributosXml>")
        }
    } else {
        // Adiciona a tag de abertura da entidade se não houver atributos
        if (entidade.texto == null) {
            println("$tabs<${entidade.nome}>")
        } else {
            // Adiciona a tag de abertura da entidade com o texto se não houver atributos
            println("$tabs<${entidade.nome}>${entidade.texto}</${entidade.nome}>")
        }
    }
    // Verifica se a entidade tem filhos
    if (entidade.filhos.isNotEmpty()) {
        // Percorre os filhos da entidade e chama recursivamente a função para cada filho
        for (filho in entidade.filhos) {
            acederEntidadMaeeFilhos(filho, nivel + 1)
        }
        // Escreve a marca de fecho da entidade com base no nível
        println("$tabs</${entidade.nome}>")
    }
}

   */





/**
    // Exemplo de uso do Visitor
    //(ponto 5 do exercicio)
    val visitor = EncontrarEntidadeFilhosVisitor()
    e1.accept(visitor, 0)
*/

    //(ponto 4 do exercicio)
    // Escreve a estrutura da árvore de entidades e atributos em um StringBuilder como XML
    val stringBuilder = StringBuilder()
    escreverString(e1, 0, stringBuilder)

    //(ponto 4 do exercicio)
    // Escreve o conteúdo do StringBuilder em um ficheiro XML
    val ficheiro = File("projeto.xml")
    val tipo = "UTF-8"
    val versão = "2.0"
    escreverFicheiro(ficheiro, tipo, versão,stringBuilder)
}

