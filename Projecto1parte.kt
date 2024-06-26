package Projeto1parte

import java.io.File

/**
 * Definição da classe entidade
 * Uma entidade é composta por um nome, uma lista de filhos, uma referência para o seu pai,
 * um texto associado e uma lista de atributos.
 * Cria uma nova instância da "Entidade" com o nome identificado e, opcionalmente, um texto associado
 * A lista de filhos da entidade e a lista de atributos da entidade é inicializada a vazio
 * Valida se o nome tem caracteres inválidos, caso tenha elimina os mesmos.
 * @property nome, nome da entidade, sem caracteres inválidos, espaços e números
 * @property filhos, lista de filhos da entidade
 * @property pai, pai da entidade, se existir. Pode ser nulo se a entidade não tiver pai
 * @property texto, texto associado à entidade, se existir, pode ser nulo
 * @property atributos, lista de atributos da entidade
 * @constructor, Cria uma instância de [Entidade] com o nome identificado, elimina caracteres inválidos, espaços e números.
 * @throws IllegalArgumentException, Se o nome resultante após a eliminação de caracteres inválidos estiver vazio
 */

open class Entidade(nome: String, texto: String? = null) {
    var nome: String
    var filhos: MutableList<Entidade>
    var pai: Entidade? = null
    var texto: String? = null
    var atributos: MutableList<Atributo>

    init{
        // Elimina caracteres inválidos, espaços e números do nome
        val nomeCorreto = nome.replace(Regex("[^A-Za-z]"), "")
        // Valida se o nome resultante não está vazio
        require(nomeCorreto.isNotEmpty()) { "O nome não pode conter apenas caracteres inválidos" }

        this.nome = nomeCorreto
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
     * Adiciona vários filhos à lista de filhos desta entidade e define esta entidade como pai de cada filho adicionado
     * Se a lista de filhos estiver vazia, faz uma excepção e dá uma mensagem
     * @param filhos, lista de filhos a serem adicionados. Deve ter pelo menos um filho
     * @throws IllegalArgumentException Se a lista de filhos estiver vazia
     */
    fun criarVariosFilhos(vararg filhos: Entidade) {
        require(filhos.isNotEmpty()) { "A lista de filhos deve ter pelo menos um filho" }
        for (filho in filhos) {
            this.filhos.add(filho)
            filho.pai = this
        }
    }
    /**
     * Método para apagar um filho à lista de filhos da entidade
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
     * @throws IllegalArgumentException, Se já existir um atributo com o mesmo nome na lista de atributos
     */
    fun criarAtributo(novoAtributo: String, valor: String) {
        val atributo = Atributo(novoAtributo,valor)
        val nomeAtributoExiste = this.atributos.any { it.nomeatrib == novoAtributo }
        try {
            if (nomeAtributoExiste) {
                throw IllegalArgumentException("Já existe um atributo com o nome '$novoAtributo'!")

            } else {
                // Adiciona o novo atributo à lista de atributos da entidade
                this.atributos.add(atributo)
            }
        }
        catch (e: IllegalArgumentException){
            // handle the exception, e.g., print an error message
            println("Caught exception: ${e.message}")
            // handle the exception gracefully or recover from it
        }
    }

    /**
     * Função que cria vários atributos ao mesmo tempo, para a mesma entidade, adiciona-os à lista de atributos da entidade
     * Verifica que não existe um atributo com o mesmo nome, Se já existir um atributo com o mesmo nome,
     * dá uma mensagem de erro, senão, cria um novo atributo com o nome e valor especificados e adiciona à lista de atributos desta entidade
     * @param novosAtributos, Lista de atributos a serem criados
     * @throws IllegalArgumentException, Se já existir um atributo com o mesmo nome na lista de atributos
     */
    fun adicionarAtributos(vararg novosAtributos: Atributo) {
        for (atributo in novosAtributos) {
            // Verifica se já existe um atributo com o mesmo nome
            val nomeAtributoExiste = this.atributos.any { it.nomeatrib == atributo.nomeatrib }
            try {
                if (nomeAtributoExiste) {
                    throw IllegalArgumentException("Já existe um atributo com o nome '$atributo'!")

                } else {
                    // Adiciona o novo atributo à lista de atributos da entidade
                    this.atributos.add(atributo)
                }
            }
            catch (e: IllegalArgumentException){
                // handle the exception, e.g., print an error message
                println("Caught exception: ${e.message}")
                // handle the exception gracefully or recover from it
            }
        }
    }

    /**
     * Método para apagar atributos da lista de atributos da entidade
     * @param apagarAtributo, atributo a ser eliminado
     */
    fun apagarAtributo(apagarAtributo: Atributo) {
        this.atributos.remove(apagarAtributo)
    }

    /**
     * Apaga um atributo de uma entidade com base no nome do atributo
     * @param nomeAtributo, nome do atributo a ser apagado
     */
    fun apagarAtributoNome(nomeAtributo: String) {
        val iterator = this.atributos.iterator()
        while (iterator.hasNext()) {
            val atributo = iterator.next()
            if (atributo.nomeatrib == nomeAtributo) {
                iterator.remove()
            }
        }
    }
}


/**
 * Definição da classe atributo, que representa um atributo de uma entidade
 * Um atributo é composto por um nome e por um valor quando existe
 * Cria uma nova instância de "Atributo" com o nome identificado e um valor associado
 * Valida se o nome tem caracteres inválidos, caso tenha elimina os mesmos.
 *
 * @property nomeatrib, nome do atributo
 * @property valor, valor que o atributo pode ter
 * @throws IllegalArgumentException, Se o nome resultante após a eliminação de caracteres inválidos estiver vazio
 */
open class Atributo (nomeatrib: String, valor: String) {
 //   val nome: Unit
    var nomeatrib: String = ""
    var valor: String = ""

    init {
        // Elimina caracteres inválidos, espaços e números do nome
        val nomeAtribCorreto = nomeatrib.replace(Regex("[^A-Za-z]"), "")
        // Valida se o nome resultante não está vazio
        require(nomeAtribCorreto.isNotEmpty()) { "O nome não pode conter apenas caracteres inválidos" }
        this.nomeatrib = nomeAtribCorreto
        this.valor = valor
    }
}


/**
 * Altera o nome de uma entidade e dos seus filhos recursivamente, substituindo um nome antigo por um nome novo
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
 * @param entidadePai, entidade pai onde é para eliminar os atributos
 * @param vNome, nome do atributo a eliminar
 */

fun apagarAtributoGlobalNome(entidade: Entidade, vNome: String){
    entidade.atributos.removeIf { it.nomeatrib == vNome }
    // Chamar recursivamente a função para cada filho
    for (filho in entidade.filhos) {
        apagarAtributoGlobalNome(filho, vNome)
    }
}

/**
 * Apaga um atributo de uma entidade com base no nome da entidade e do atributo, a partir da entidade pai elimina
 * o atributo com o nome identificado em todas as entidades filhas encontradas
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
 * @param entidade, entidade onde o atributo será alterado
 * @param nomeAntAtributo,  nome antigo do atributo que será alterado
 * @param nomeAtributo, nome novo para o atributo
 * @param valor, valor para o atributo
 */
fun alterarAtributo(entidade: Entidade, nomeAntAtributo: String, nomeAtributo: String, valor: String) {
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
 * @param entidadePai, entidade pai a partir da qual vai ser feita a pesquisa incluindo os seus filhos
 * @param nomeEntidade, nome da entidade da qual vai ser alterado o nome do atributo
 * @param nomeVelho, nome atual do atributo que se pretende alterar globalmente
 * @param novoNome, nome novo que vai ser dado ao atributo
 */
fun alterarAtributoNomeEntidadeNomeAtributoGlobal(entidade: Entidade, nomeEntidade: String, nomeAtributo: String, nomeAtributoNovo: String) {

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
 * Acede a uma entidade e aos seus filhos e escreve o resultado em formato XML
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
 * Encontra as entidades na árvore de entidades que correspondem à expressão XPath definida
 * Retorna uma lista de pares onde cada par corresponde a entidade encontrada e aos seus atributos
 * @param entidade, raiz da árvore de entidades por onde começa a pesquisa
 * @param xpath, expressão XPath com os valores a pesquisar
 * @return, Uma lista de pares onde cada par consiste na entidade encontrada e nos seus atributos
 */
fun encontrarEntidadesPorXPath(entidade: Entidade, xpath: String): List<Pair<Entidade, List<Atributo>>> {
    // Lista para guardar os resultados
    val resultados = mutableListOf<Pair<Entidade, List<Atributo>>>()

    // Função que vai percorrer a árvore de entidades recursivamente
    fun percorrerEntidades(entidade: Entidade, caminho: List<String>) {
        // Verifica se o caminho está vazio, ou seja já foram encontrados todos os elementos do XPath
        if (caminho.isEmpty()) {
            // Adiciona a entidade atual à lista de resultados juntamente com seus atributos
            resultados.add(entidade to entidade.atributos)
        } else {
            // Obtém o próximo elemento do caminho
            val proximo = caminho.first()
            // Obtém os filhos da entidade referente ao próximo elemento do caminho
            val filhosProximo = entidade.filhos.filter { it.nome == proximo }
            // Percorre os filhos do próximo caminho
            for (filho in filhosProximo) {
                // Chama recursivamente a função para o filho com o restante caminho
                percorrerEntidades(filho, caminho.drop(1))
            }
        }
    }

    // Divide a expressão XPath em partes separadas por '/'
    val conteudo = xpath.split("/")
    // Chama a função para iniciar a procura a partir da raiz da árvore de entidades
    percorrerEntidades(entidade, conteudo)
    // Retorna os resultados encontrados
    return resultados
}

/**
 * Imprime as entidades e os seus atributos no formato XML, referente à lista de resultados
 * @param resultados, lista de resultados contendo pares de entidades e dos seus atributos
 */
fun imprimirResultados(resultados: List<Pair<Entidade, List<Atributo>>>) {
    for ((entidade, atributos) in resultados) {
        if (atributos.isNotEmpty()) {
            val juntaCampos = atributos.joinToString(" ") { "${it.nomeatrib}=\"${it.valor}\"" }
            println("<${entidade.nome} $juntaCampos/>")
        }else {
            if (entidade.texto == null) {
                println("<${entidade.nome}>")
            } else {
                println("<${entidade.nome}>${entidade.texto}</${entidade.nome}>")
            }
        }
    }
}

/**
 * Interface para definir um visitor que visita entidades na árvore
 */
interface EntidadeVisitor {
    /**
     * Método para visitar uma entidade na árvore
     *
     * @param entidade, entidade a ser visitada
     * @param nivel, nível da entidade na árvore
     * @return, true se a visita deve continuar para os filhos da entidade, false caso seja para terminar
     */
    fun visit(entidade: Entidade, nivel: Int): Boolean
}

/**
 * Aceita um visitor e permite que ele visite a entidade e os seus filhos na árvore
 * @param visitor,  visitor a ser aceite pela entidade
 * @param nivel, nível da entidade na árvore
 * @return, true se a visita deve continuar para os filhos da entidade, false caso seja para terminar
 */
fun Entidade.accept(visitor: EntidadeVisitor, nivel: Int): Boolean {
    val continueSearch = visitor.visit(this, nivel)
    if (!continueSearch) {
        return false
    }
    // Faz a pesquisa de iteração numa cópia da lista de filhos para evitar problemas ao apagar os elementos
    for (filho in filhos.toList()) {
        if (!filho.accept(visitor, nivel + 1)) {
            return false
        }
    }
    return true
}

/**
 * Visitor para identificar e selecionar as entidades que têm nomes que começam com o nome identificado
 * @property nome,  nome a ser verificado nos nomes das entidades
 * @property entidadesRemover,  lista de entidades encontradas que devem ser eliminadas
 */
class ApagarEntidadePorNomeVisitor(private val nome: String) : EntidadeVisitor {
    /**
     * Lista de entidades encontradas que devem ser eliminadas
     */
    private val entidadesRemover: MutableList<Entidade> = mutableListOf()

    /**
     * Visita a entidade definida e verifica se o seu nome começa com o prefixo identificado
     * @param entidade, entidade a ser visitada
     * @param nivel, nível da entidade na árvore
     * @return, true para continuar a procurar outras entidades, false caso seja para terminar
     */
    override fun visit(entidade: Entidade, nivel: Int): Boolean {
        // Se o nome da entidade começar com o prefixo especificado, adiciona à lista de entidades para apagar
        if (entidade.nome.startsWith(nome)) {
            entidadesRemover.add(entidade)
            return true
        }
        return true
    }

    /**
     * Obtém a lista de entidades encontradas que devem ser eliminadas
     * @return,  Lista de entidades a serem eliminadas
     */
    fun getEntidadesRemover(): List<Entidade> {
        return entidadesRemover
    }
}

/**
 * Elimina todas as entidades que têm nomes que começam com o prefixo identificado, a partir da entidade da raiz especificada
 * @param entidade, entidade raiz a ser pesquisada
 * @param nome, prefixo do nome das entidades a serem eliminadas
 */
fun apagarEntidadePorNomeV(entidade: Entidade?, nome: String) {
    if (entidade == null) return
    // Visitor para encontrar as entidades com o nome identificado
    val visitor = ApagarEntidadePorNomeVisitor(nome)
    // Aceita o visitor para a entidade raiz onde vai para iniciar a procura
    entidade.accept(visitor, 0)
    // Apaga as entidades encontradas
    for (entidadeRemover in visitor.getEntidadesRemover()) {
        val entidadePai = entidadeRemover.pai
        // Verifica se a entidade tem pai antes de removê-la
        entidadePai?.apagarFilho(entidadeRemover)
    }
}

/**
 * Representa um documento XML.
 * @property nome, nome do documento. Caracteres inválidos, espaços e números são eliminados do nome.
 * @constructor Cria um novo documento com o nome identificado.
 * @param nome, nome do documento.
 * @throws IllegalArgumentException, Se o nome resultante após a eliminação de caracteres inválidos estiver vazio.
 */
class Documento(nome: String) {
    var nome: String

    init {
        // Elimina caracteres inválidos, espaços e números do nome
        val nomeCorreto = nome.replace(Regex("[^A-Za-z.]"), "")
        // Valida se o nome resultante não está vazio
        require(nomeCorreto.isNotEmpty()) { "O nome não pode conter apenas caracteres inválidos" }
        this.nome = nomeCorreto
    }

    /**
     * Função que escreve o conteúdo de um StringBuilder num arquivo XML
     * @param ficheiro, ficheiro onde o conteúdo XML será escrito
     * @param tipo, tipo de codificação para o arquivo XML
     * @param versão, versão do XML
     * @param xmlStringBuilder, StringBuilder contendo o conteúdo XML a ser escrito no ficheiro
     */
    fun escreverFicheiro(ficheiro: File, tipo: String, versão: String,xmlStringBuilder: StringBuilder) {
        // Escreve a declaração XML no ficheiro
        ficheiro.writeText("<?xml version=\"${versão}\" encoding=\"${tipo}\"?>\n")
        // Escreve o conteúdo do StringBuilder no ficheiro
        ficheiro.appendText(xmlStringBuilder.toString())
    }
}

/**
 * A função escreve a representação XML da entidade e dos seus filhos numa StringBuilder
 * @param entidade, entidade para a qual se está a escrever a representação XML
 * @param nivel, nível de indentação para a formatação XML
 * @param xmlStringBuilder, StringBuilder para acumular o conteúdo XML
 */
fun escreverString(entidade: Entidade, nivel: Int = 0, xmlStringBuilder: StringBuilder):StringBuilder {

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
    return xmlStringBuilder
}


