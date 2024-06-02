# Guia do Utilizador

Índice

- [Guia do Utilizador](#guia-do-utilizador)
    - [1 Entidade](#1-entidade)
        - [1.i Criar uma Entidade](#1i-criar-uma-entidade)
        - [1.ii Definir Entidade pai](#1ii-definir-entidade-pai)
        - [1.iii Definir Entidade Filho](#1iii-definir-entidade-filho)
        - [1.iv Apagar Filhos](#1iv-apagar-filhos)
        - [1.v Criar Atributos](#1v-criar-atributos)
        - [1.vi Apagar Atributo](#1vi-apagar-atributo)
    - [2 Atributo](#2-atributo)
    - [3 Documento](#3-documento)
        - [3.1 Escrever Ficheiro](#31-escrever-ficheiro)
    - [4 Funções Globais](#4-funções-globais)
        - [4.i Alterar o nome de uma Entidade](#4i-alterar-o-nome-de-uma-entidade)
        - [4.ii Apagar Entidade](#4ii-apagar-entidade)
        - [4.iii Apagar Atributo Globalmente](#4iii-apagar-atributo-globalmente)
        - [4.iv Criar Atributos](#4iv-criar-atributos)
        - [4.v Alterar Atributo](#4v-alterar-atributo)
        - [4.vi Aceder a Entidade Mae e Filho](#4vi-aceder-a-entidade-mae-e-filho)
        - [4.vii XPath](#4vii-xpath)
        - [4.viii Escrever XML](#4viii-escrever-xml)
        - [4.ix Imprimir XML](#4ix-imprimir-xml)
    - [5 Mapeamento de Calsses XML](#5-mapeamento-de-calsses-xml)
        - [5.i Anotação XmlElemento](#5i-anotação-xmlelemento)
        - [5.ii Anotacão XmlString](#5ii-anotacão-xmlstring)
        - [5.iii Anotacão XmlAdapter](#5iii-anotacão-xmladapter)
        - [5.iv Anotacão Ignore](#5iv-anotacão-ignore)
        - [5.v Anotacão Order](#5v-anotacão-order)
        - [5.vi Função escreveXml](#5vi-função-escrevexml)

Este guia abrange todos os tópicos da biblioteca de manipulação de
XML desenvolvida em Kotlin, fornecendo exemplos práticos de todas as
funcionalidades desta API.

A biblioteca tem três classes que permitem a manipulação do XML:

- **Entidade** - Classe que permite manipular cada entidade do XML, desde
  alterar nome de cada entidade, adicionar e remover entidades
  filho/pai, adicionar e remover atributos
- **Atributo** - Classe que permite criar atributos de cada entidade
- **Documento** - Classe que guarda cada ficheiro XML num objeto

## 1 Entidade

Entidade é a classe principal desta API, onde se faz a maior parte da
manipulação de todo o XML.

A estrutura de um objeto Entidade é a seguinte:

- Nome(String) - Nome da entidade, quando se instancia um objeto é feita uma validação do nome para garantir que apenas tem caracteres de [a-Z], não permite caracteres especiais sendo estes removidos, no caso do nome fornecido ser nulo é chamada um exceção
- Texto(String) - Parâmetro opcional de uma Entidade onde se dá um
  valor descritivo da Entidade
- Filhos(MutableList\<Entidade>) - Lista dos filhos da Entidade,
  quando se instancia uma Entidade esta lista é vazia
- Pai(Entidade) - Define uma Entidade Pai da Entidade que se está a
  manipular, quando se instancia uma Entidade é vazio
- Atributos(MutableList\<Atributo>) - Lista dos Atributos da Entidade,
  quando se instancia uma Entidade esta lista é vazia

### 1.i Criar uma Entidade

Para se instanciar um objeto da classe entidade, deve-se chamar a
classe Entidade:

```Kotlin
val entidade_exemplo = Entidade("plano")
```

Neste exemplo é criado um objeto Entidade chamado entidade_exemplo
onde é fornecido apenas o parâmetro nome, sendo este "plano"

```Kotlin
val entidade_exemplo = Entidade("curso", "Mestrado de Engenharia Informática")
```

Neste exemplo é criado um objeto Entidade chamado entidade_exemplo,
onde é fornecido os parâmetros nome e texto, onde o nome é "curso" e o
texto é "Mestrado de Engenharia Informática"

### 1.ii Definir Entidade pai

Para se definir o pai de uma Entidade pode ser utilizada a função
criarPai(), tendo como parâmetros de entrada:

- novoPai(Entidade) -  Entidade que será o Pai da entidade que se está a manipular

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val entidadeFilho1 = Entidade("Filho 1")

// Estabelecer a relação pai-filho usando a função criarPai()
entidadeFilho1.criarPai(entidadePai)
```

Neste exemplo são instanciadas duas entidades, de seguida é feita a ligação pai filho entre elas através da função criarPai() onde a entidadePai fica definida como pai da entidadeFilho

### 1.iii Definir Entidade Filho

Para se definir o(s) filho(s) de uma Entidade pode ser utilizada a
função criarFilho(),  tendo como parâmetros de entrada:

- novoFilho(Entidade) - Entidade que será Filha da entidade que se está a manipular

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val entidadeFilho1 = Entidade("Filho 1")

// Estabelecer a relação pai-filho usando a função criarFilho
entidadePai.criarFilho(entidadeFilho1)
```

---

Outro método para adicionar filhos a uma Entidade é através da função **criarVariosFilhos()**, esta tem como parâmetros de entrada:

- filhos(Entidade) - É um parâmetro do tipo vararg o que possibilita que sejam criados vários filhos ao mesmo tempo, para a mesma entidade, adicionando-os à lista de filhos da entidade

```Kotlin
// Inicializar objetos entidade
val entidade_1 = Entidade("Pai")
val entidade_2 = Entidade("Filho1")
val entidade_3 = Entidade("Filho2")

// Adicionar as entidades filhas à entidade pai
entidade_1.criarVariosFilhos(entidade_2, entidade_3)
```

### 1.iv Apagar Filhos

Para apagar um filho de uma Entidade é usada a função apagarFilho() que tem um parâmetro de entrada:

- apagarFilho(Entidade) - objeto Entidade que será apagado da lista de filhos de uma Entidade

```Kotlin
val entidade_Pai = Entidade("Plano")
val entidade_Filho = Entidade("FUC")

entidade_Pai.criarFilho(entidade_Filho)
entidade_Pai.apagarFilho(entidade_Filho)
```

Neste exemplo é primeiro instanciado duas Entidades, depois é feita a ligação entre estas de pai e filho, por fim utiliza-se a função **apagarFilho()** para apagar  **entidade_Filho** da lista de filhos da **Entidade_Pai**.

### 1.v Criar Atributos

Existem dois métodos de adicionar Atributos a uma Entidade.

Em ambos os métodos quando a função é chamada é feita uma validação inicial que verifica se na lista de atributos já existe um atributo com o mesmo nome. Se não existir o novo(s) atributo(s) é/são adicionado(s) à lista, se já existir é mostrada uma mensagem avisando que já existe um atributo com esse nome e o novo atributo **não é adicionado** à lista.

O primeiro método utiliza a função **criarAtributo()** que associa um objeto Atributo a um objeto Entidade, tendo como parâmetros de entrada:

- novoAtributo(String) - nome do Atributo
- valor(String) - valor do Atributo

```Kotlin
val entidade_exemplo = Entidade("FUC")
entidade_exemplo.criarAtributo("codigo1", "M4310")
```

Neste exemplo é instanciado um objeto Entidade, de seguida é chamada a função **criarAtributo()** onde é associado um novo Atributo à Entidade, com o nome **"codigo1"** e valor **"M4310"**.

---

O segundo método é através da função **adicionarAtributos()**, esta função permite adicionar um ou mais Atributos a uma Entidade, tendo como parâmetros de entrada:

- novosAtributos(Atributo) - é um parâmetro do tipo vararg o que possibilita que sejam criados vários atributos ao mesmo tempo, para a mesma entidade, adicionando-os à lista de atributos da entidade

```Kotlin
val entidade_exemplo = Entidade("FUC")
val atributo_exemplo = Atributo("codigo1", "M4310")
val atributo_exemplo2 = Atributo("nome", "Quizzes")

entidade_exemplo.adicionarAtributos(atributo_exemplo, atributo_exemplo2)
```

Neste exemplo é instanciado um objeto Entidade e dois objetos Atributo, de seguida é chamada a função **adicionarAtributos()** onde são associados os dois Atributos à Entidade.

A diferença entre os dois métodos é que no primeiro apenas é instanciado o objeto Entidade e dentro deste é chamada a função criarAtributo() onde são passados os parâmetros para a criação de um Atributo, no segundo método é preciso instanciar o objeto Entidade e os objetos Atributo que serão adiconados à lista de Atributos da Entidade.

### 1.vi Apagar Atributo

Para apagar um Atributo de uma Entidade é usada a função **apagarAtributoNome()**, que tem como parâmetro de entrada:

- nomeAtributo(String) - Nome do Atributo a ser apagado.

```Kotlin
val entidade_exemplo = Entidade("FUC")
entidade_exemplo.criarAtributo("codigo1", "M4310")

entidade_exemplo.removerAtributoNome("codigo1")
```

Neste exemplo é instanciado um objeto Entidade e adicionado um objeto Atributo à lista de Atributos, de seguida é utilizada a função **removerAtributoNome()** com o nome "codigo1" como nome do Atributo a apagar.

---

Outro método para apagar atributos é através da função **apagarAtributo()**, que tem como parâmetro de entrada:

- apagarAtributo(Atributo) - Objeto atributo a ser eliminado

```Kotlin
val entidade = Entidade("Entidade")
val atributo1 = Atributo("AtributoUm", "ValorUm")

entidade.adicionarAtributos(atributo1)

entidade.apagarAtributo(atributo1)
```

Neste exemplo é instanciado um objeto Entidade e um objeto Atributo, feita a ligação entre estes, de seguida é utilizada a função **apagarAtributo()** para remover o atributo atributo1.

## 2 Atributo

A classe atributo é onde se define os atributos que serão atribuidos às diversas entidades, a sua estrutura é:

- nomeatrib(String) - Nome do atributo, quando se instancia um objeto é feita uma validação do nome para garantir que apenas tem caracteres de [a-Z], não permite caracteres especiais sendo estes eliminados, no caso do nome fornecido ser nulo é chamada um exceção
- valor(String) - valor dado ao atributo

```Kotlin
val atributo_exemplo = Atributo("Atributo", "Valor_Exemplo")
```

Neste exemplo é instanciado um objeto Atributo, com o nome "Atributo" e valor "Valor_Exemplo".

## 3 Documento

A classe Documento é onde se guarda o XML com as entidades e atributos após toda a sua manipulação em formato de string, a sua estrutura é:

- nome(String) - Nome do documento, quando se instancia um objeto é feita uma validação do nome para garantir que apenas tem caracteres de [a-Z], não permite caracteres especiais sendo estes removidos, no caso do nome fornecido ser nulo é chamada um exceção

```Kotlin
val documento = Documento("Documento_Exemplo")
```

Neste exemplo é instanciado um objeto Documento, com o nome "Documento_Exemplo".

### 3.1 Escrever Ficheiro

Para se escrever o ficheiro XML, é utilizada a função **escreveFicheiro()**, que tem como parâmetros de entrada:

- ficheiro(File) - Ficheiro onde o conteúdo XML será escrito
- tipo(String) - Tipo de codificação para o arquivo XML
- versao(String) - Versão do XML
- xmlStringBuilder(StringBuilder) - StringBuilder contendo o conteúdo XML a ser escrito no ficheiro

```Kotlin
// Inicializar objeto entidade
val entidade = Entidade("Nome", "texto")
//Instancia um objeto documento
val documento = Documento("projeto.xml")

val stringBuilder = escreverString(entidade, 0,StringBuilder())

// Escrever o conteúdo do StringBuilder em um ficheiro XML
documento.escreverFicheiro(File(documento.nome), "UTF-8", "1.0",stringBuilder)
```

Neste exemplo é instanciada uma entidade e um documento, é escrito para a variável stringBuilder o conteúdo da entidade, de seguida é utilizada a função **escreverFicheiro()** que irá escrever no ficheiro chamado "projeto.xml", toda a informação contida na variável stringBuilder em formato XML.

## 4 Funções Globais

### 4.i Alterar o nome de uma Entidade

Para alterar o nome de uma Entidade é usada a função **alterarNomeEntidade()**, que tem como parâmetros de entrada:

- entidadePai(Entidade) - Objeto Entidade a partir do qual faz a pesquisa, por norma o 1º objeto da árvore
- nomeAntigo(String) - Nome da Entidade como forma de validação
- novoNome(String) - Novo nome da Entidade

```Kotlin
entidade_exemplo = à 1ª entidade da árvore
alterarNomeEntidade(entidade_exemplo, "FUC", "Curso")
```

Neste exemplo é utilizada a função **alterarNomeEntidade()** para **alterar o nome** da entidade_exemplo de "FUC" para "Curso".

### 4.ii Apagar Entidade

Para apagar qualquer Entidade é utilizada a função **apagarEntidadePorNome()**, que tem como parâmetros de entrada:

- Entidade(Entidade) - Objeto Entidade que irá ser apagada
- nome(String) - Nome da Entidade que irá ser apagada

```Kotlin
entidade_exemplo = à 1ª entidade da árvore
apagarEntidadeNome(entidade_exemplo, "FUC")
```

Neste exemplo é instanciada um objeto Entidade, de seguida é utilizada a função **apagarEntidadePorNome()** para **apagar** a todas as Entidades que tenham o nome "FUC" recursivamente.

---

Outro método para apagar entidades é através da função **apagarEntidade()**, que tem como parâmetro de entrada:

- entidade(Entidade) - Entidade a ser apagada

```Kotlin
val entidade_exemplo = Entidade("FUC")
apagarEntidade(entidade_exemplo)
```

Neste exemplo é instanciada um objeto Entidade, de seguida é utilizada a função **apagarEntidade()** para **apagar** a Entidade.

A diferença entre os dois métodos é que no primeiro é especificada a entidade raíz a partir de onde se irão remover todas as Entidades que possuam o nome correspondente, no segundo é especificada a Entidade a ser removida e por conseguinte todos os seus filhos independetemente do nome.

### 4.iii Apagar Atributo Globalmente

Para apagar um atributo globlamente, ou seja, independentemente da Entidade(s) em que este exista é usada a função **apagarAtributoGlobalNome()**, que tem como parâmetros de entrada:

- Entidade(Entidade) - Entidade pai a partir de onde se irá pesquisar o atributo a remover
- vNome(String) - Nome do atributo a ser removido

```Kotlin
val entidade_exemplo = Entidade("FUC")
val atributo_exemplo = Atributo("codigo1", "M4310")

entidade_exemplo.adicionarAtributos(atributo_exemplo)

apagarAtributoGlobalNome(entidade_exemplo, "codigo1")
```

---

Outro método para apagar um Atributo globalmente é através da função **apagarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- Entidade(Entidade) - Entidade pai a partir de onde se irá pesquisar o atributo a remover
- nomeEntidade(String) - Nome do Entidade
- nomeAtributo(String) - Nome do atributo a ser removido

```Kotlin
// Inicializar objetos entidade
val entidade_1 = Entidade("pai")
val entidade_2 = Entidade("filhoUm")

// Inicializar objetos atributo
val atributo_1 = Atributo("atributoUm", "valorUm")
val atributo_2 = Atributo("atributoDois", "valorDois")

// Adicionar o filho à lista de filhos do pai
entidade_1.criarVariosFilhos(entidade_2)

// Adicionar os atributos à entidade
entidade_2.adicionarAtributos(atributo_1, atributo_2)

// Remover um atributo numa dada entidade pelo nome
apagarAtributoNomeEntidadeNomeAtributoGlobal(entidade_1, "filhoUm", "atributoUm")
```

Neste exemplo são criada duas entidades(entidade_1 e entidade_2) e dois atributos (atributo_1 e atributo_2), feita a ligação de pai e filho entre as entidades e atribuidos os atributos de cada, de seguida é usada a função **apagarAtributoNomeEntidadeNomeAtributoGlobal()** onde é especificada a entidade raiz de onde será procurado o atributo a ser removido, neste caso o atributo "atributoUm" da entidade "filhoUm".

---

Outro método para apagar entidades é através da função **apagarEntidadePorNomeV()**, nesta função são apagadas todas as entidades onde os seus nomes comecem com um prefixo especificado, tem como parâmetros de entrada:

- entidade(Entidade) - Entidade raíz a partir de onde será feita a procura da(s) entidade(s) a remover
- nome(String) - Prefixo da(s) entidade(s) a ser(em) removida(s)

```Kotlin
// Inicializar objetos entidade
val entidade_1 = Entidade("pai")
val entidade_2 = Entidade("filho1")
val entidade_3 = Entidade("filho2")

// Adicionar os filhos à lista de filhos do pai
e1.criarVariosFilhos(entidade_2, entidade_3)

// Remover todos os filhos cujo nome comece por f
apagarEntidadePorNomeV(entidade_1, "f")
```

Neste exemplo são instanciadas três entidades e feita a ligação pai e filho, de seguida é utilizada a função **apagarEntidadePorNomeV()** para remover todas as entidade que comecem por "f" e sejam filhas da entidade "entidade_1"

### 4.iv Criar Atributos

Para criar atributos globalmente é usada a função **criarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- entidade(Entidade) - Entidade raiz da árvore de entidades
- nomeEntidade(String) - Nome da entidade onde o atributo será criado
- nomeAtributo(String) - Nome do novo atributo a ser criado
- valor(String) - Valor associado ao novo atributo

```Kotlin
val entidade_1 = Entidade("entidadeUm")
val entidade_2 = Entidade("entidadeDois")
val entidade_3 = Entidade("entidadeTres")

// Adicionar os filhos à lista de filhos do pai
e1.criarVariosFilhos(entidade_2, entidade_3)

// Criar um novo atributo globalmente numa dada entidade
criarAtributoNomeEntidadeNomeAtributoGlobal(entidade_1, "entidadeUm", "novoAtributo", "valorNovoAtributo")
```

Neste exemplo são instanciadas três entidades, feita a ligação de pai e filho entre as entidades, de seguida é usada a função **criarAtributoNomeEntidadeNomeAtributoGlobal()** onde é especificada a entidade onde será criado o novo atributo, com o nome "novoAtributo" e valor "valorNovoAtributo".

### 4.v Alterar Atributo

Para alterar o nome e/ou valor de um atributo é usada a função **alterarAtributo()**, tem como parâmetros de entrada:

- entidade(Entidade) - Entidade onde será alterado o atributo
- nomeAntAtributo(String) - Nome do atributo a ser alterado
- nomeAtributo(String) - Novo nome do atributo
- valor(String) - Novo valor do atributo

```Kotlin
val entidade = Entidade("Entidade")
val atributo = Atributo("nomeAtributo", "valorInicial")

entidade.adicionarAtributos(atributo)

alterarAtributo(entidade,"nomeAtributo","novoNome","novoValor")
```

Neste exemplo é instanciada uma entidade e um atributo, de seguida é feita a ligação do atributo à entidade, depois é utilizada a função **alterarAtributo()** para modificar o atributo com um novo nome e valor.

**alterarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- entidade(Entidade) - Entidade onde será alterado o atributo
- nomeEntidade(String) - Nome da entidade
- nomeAtributo(String) - Nome do atributo a ser alterado
- nomeAtributoNovo(String) - Novo nome do atributo

```Kotlin
// Inicializar objetos entidade
val entidade_1 = Entidade("entidadeUm")
val entidade_2 = Entidade("entidadeDois", "textoDois")

// Inicializar objetos atributo
val atributo_1 = Atributo("atributoUm", "valorUm")
val atributo_2 = Atributo("atributoDois", "valorDois")

// Adicionar o filho à lista de filhos do pai
entidade_1.criarVariosFilhos(entidade_2)

// Adicionar os atributos à entidade
entidade_1.adicionarAtributos(atributo_1, atributo_2)
entidade_2.adicionarAtributos(atributo_1)

// Alterar nome do atributo de uma entidade específica
alterarAtributoNomeEntidadeNomeAtributoGlobal(entidade_1, "entidadeUm", "atributoUm", "novoNome")
```

Neste exemplo são instanciadas duas entidades e dois atributos, feita a ligação de pai e filho entre as entidades e atribuidos os atributos de cada, de seguida é usada a função **alterarAtributoNomeEntidadeNomeAtributoGlobal()** onde é especificada a entidade de onde será procurado o atributo a ser alterado, neste caso o atributo "atributoUm" tendo o seu nome alterado de "atributoUm" para "novoNome".

**alterarAtributoNomeEntidade()**, tem como parâmetros de entrada:

- entidadePai(Entidade) - Entidade pai da qual vai ser alterado o nome do atributo e de todos os seus filhos
- nomeVelho(String) - Nome atual do atributo que se pretende alterar globalmente
- novoNome(String) - Nome novo que vai ser dado ao atributo

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val filho1 = Entidade("Filho1")
val filho2 = Entidade("Filho2")

val atributo1 = Atributo("nome", "valor1")
val atributo2 = Atributo("idade", "25")

// Adicionar os filhos à lista de filhos do pai
entidadePai.criarVariosFilhos(filho1, filho2)

// Adicionar os atributos à entidade
entidadePai.adicionarAtributos(atributo1, atributo2)
filho1.adicionarAtributos(atributo1)
filho2.adicionarAtributos(atributo2)

// Alterar o nome de um atributo numa entidade especifica e nos seus filhos
alterarAtributoNomeEntidade(entidadePai, "nome", "novoNome")
```

Neste exemplo são instanciadas três entidades e dois atributos, feita a ligação de pai e filho entre as entidades e atribuidos os atributos de cada, de seguida é usada a função **alterarAtributoNomeEntidade()** onde é especificada a entidade raiz a partir de onde será procurado o atributo a ser alterado, neste caso o atributo com o nome "nome" e alterando para "novoNome" em todos as entidades onde existir.

### 4.vi Aceder a Entidade Mae e Filho

Para aceder a qualquer entidade instanciada, aos seus filhos e escrever em formato XML toda a informação das mesmas é utilizada a função **acederEntidadMaeeFilhos()**, tendo como parâmetros de entrada:

- entidade(Entidade) - Entidade a ser acedida
- nivel(Int) - Nível de indentação para a formatação XML (opcional, padrão é 0)

```Kotlin
val entidade_1 = Entidade("entidadeUm")
val entidade_2 = Entidade("entidadeDois", "textoDois")
val entidade_3 = Entidade("entidadeTres")

// Inicializar objetos atributo
val atributo_1 = Atributo("atributoUm", "valorUm")
val atributo2 = Atributo("atributoDois", "valorDois")

// Adicionar os filhos à lista de filhos do pai
entidade_1.criarVariosFilhos(entidade_2, entidade_3)

// Adicionar os atributos à entidade
entidade_1.adicionarAtributos(atributo_1, atributo_2)


val outputStreamCaptor = ByteArrayOutputStream()
System.setOut(PrintStream(outputStreamCaptor))

// Aceder a uma entidade e aos seus filhos e escreve o resultado em formato XML
acederEntidadMaeeFilhos(entidade1)
```

Neste exemplo são instanciadas três entidades e dois atributos, feita a ligação pai e filho entre as entidades e alocados os respetivos atributos, de seguida é usada a função **acederEntidadMaeeFilhos()** para encontrar todos os filhos da entidade pai fornecida, neste caso **entidade1** e irá escrever em formato XML essa informação.

### 4.vii XPath

Para obter fragmentos de XML é utilizada a função **encontrarEntidadesPorXPath()**, tendo como parâmetros de entrada:

- entidade(Entidade) -  Entidade raiz da árvore de entidades por onde começa a pesquisa
- xpath(String) - expressão XPath com os valores a pesquisar

```Kotlin
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

// Inicializar objetos atributo
val a1 = Atributo("codigo", "M4310")


//Criação de entidades 
e1.criarVariosFilhos(e2, e3, e9)
e3.criarVariosFilhos(e4, e5, e6)
e9.criarVariosFilhos(e10, e11, e12)
e6.criarVariosFilhos(e7, e8)
//Criação de atributos 
e3.adicionarAtributos (a1)

// Chamada da função XPath para pesquisa de expressões
val resultados = encontrarEntidadesPorXPath(e1, "FUC/etcs")
```

Neste exemplo são instanciadas várias entidades e um atributo, feitas as ligação entre entidades pai e filho e o respetivo atributo, de seguida é utilizada a função **encontrarEntidadesPorXPath()** para encontar os valores respetivos às entidades passadas no XPath, neste caso "FUC/etcs", ou seja retorna todos os resultados associados à pesquisa.

### 4.viii Escrever XML

Para escrever toda a informação manipulada das entidades e respetivos atributos em formato XML é utilizada a função **escreverString()**, tendo como parâmetros de entrada:

- entidade(Entidade) - Entidade a partir da qual se vai escrever a representação XML
- nivel(Int) - Nível de indentação para a formatação XML
- xmlStringBuilder(StringBuilder) - StringBuilder para acumular o conteúdo XML

```Kotlin
// Inicializar objeto entidade
val entidade = Entidade("Nome", "texto")

// Escrever a representação XML da entidade e dos seus filhos
val stringBuilder = escreverString(entidade, 0,StringBuilder())
```

Neste exemplo a função **escreverString()** vai guardar na variável stringBuilder todo o código xml referente a entidade, seus filhos e atributos.

### 4.ix Imprimir XML

Para imprimir as entidades e os seus atributos no formato XML é utilizada a função **imprimirResultados()**, tendo como parâmetro de entrada:

- resultados(List) - Lista dos pares entidade atributo

```Kotlin
// Inicializar objetos entidade
val e1 = Entidade("entidadeUm")
val e2 = Entidade("entidadeDois", "textoDois")

// Inicializar objetos atributo
val a1 = Atributo("atributoUm", "valorUm")
val a2 = Atributo("atributoDois", "valorDois")

e1.adicionarAtributos(a1,a2)
// Criar lista de resultados
val resultados = listOf(
    Pair(e1, e1.atributos),
    Pair(e2, e2.atributos)
)

val outputStreamCaptor = ByteArrayOutputStream()
System.setOut(PrintStream(outputStreamCaptor))

// Escrever os resultados
imprimirResultados(resultados)
```

Neste exemplo são instanciadas duas entidades e dois atributos, feita as ligação pai e filho das entidades e atribuidos os respetivos atributos, de seguida é feita uma lista dos pares entidade atributo e utilizada a função **imprimirResultados()** para escrever em formato XML essa lista.

## 5 Mapeamento de Calsses XML

Este módulo tem como objetivo a criação automática de entidades XML a partir de
objetos, com base na estrutura das respectivas classes, ou seja que as classes
do modelo possam ser instanciadas automaticamente a partir de objetos, oferecendo
alguma flexibilidade de personalização por via de anotações nas classes.

### 5.i Anotação XmlElemento

Esta anotação **@XmlElemento** colocada numa classe permite alterar o nome da classe ou alterar o nome de campos da classe

- XmlElemento(val name: String) - "name" é o nome a passar para dar um novo nome à classe ou aos atributos

```Kotlin
@XmlElemento(name = "componente")
data class ComponenteAvaliacao(
    val nome: String,
    @XmlElemento(name = "pesoteste")
    val peso: Int
)
```

Neste exemplo é a anotação **@XmlElemento** altera o nome da classe de "ComponenteAvaliacao" para "componente" e o nome do atributo "peso" para "pesoteste".

### 5.ii Anotacão XmlString

Esta anotação **@XmlString**, tem um interface que converte o valor de um objeto para uma representação em string customizada. Esta interface é utilizada em conjunto com a anotação **@XmlString** para permitir a conversão customizada de propriedades para strings em classes anotadas.
O interface **XmlToString**, recebe um objeto genérico (Any) e retorna uma string.

```Kotlin
class AddPercentage : XmlToString {
    override fun toString(obj: Any): String {
        return obj.toString() + "%"
    }
}

data class ComponenteAvaliacao(
    val nome: String,
    @XmlString(AddPercentage::class)
    val peso: Int
)
```

Neste exemplo foi criada uma classe **AddPercentage** que implementa o interface **XmlToString** para adicionar uma percentagem ("%") no final da string.
Na classe "ComponenteAvaliacao", foi colocada a anotação **@XmlString**, que tem como parâmetro a classe **AddPercentage**, ou seja ao valor que o "peso" tiver é acrescentada a percentagem. Ex: 10%.

### 5.iii Anotacão XmlAdapter

Esta anotação **XmlAdapter**, tem o interface **XmlAdapterInterface** que define um adaptador XML para converter um objeto para uma entidade XML, ou seja converte um objeto de um tipo específico em uma estrutura XML customizada representada por uma entidade.
A função **adapt** recebe uma instância do objeto a ser adaptada e retorna uma entidade XML que representa
a estrutura customizada do objeto na forma XML.

```Kotlin
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

@XmlAdapter(FUCAdapter::class)
@XmlElemento(name = "FUCA")
class FUCA(
    val codigo: String,
    val nome: String,
    val ects: Double,
    val observacoes: String,
    val avaliacao: List<ComponenteAvaliacao>
)

```

Neste exemplo foi criada uma classe **FUCAdapter** que implementa o interface **XmlAdapterInterface** para fornecer uma implementação personalizada que transforma os dados de uma instância FUCA em uma forma específica e diferente da representação padrão.
Na classe "FUCA", foi colocada a anotação **@XmlAdapter**, que tem como parâmetro a classe **FUCAdapter**, ou seja  vai alterar a estrutura da classe FUCA tanto ao nível de nomes como de ordem dos atributos.

### 5.iv Anotacão Ignore

A anotação **Ignore**, serve para indicar que uma propriedade deve ser ignorada durante a instaciação ou adaptação para XML.
Quando esta anotação estiver presente em uma propriedade, ela será excluída do resultado final do processo de serialização ou adaptação para XML.

```Kotlin

class FUC(
    val codigo: String,
    val nome: String,
    val ects: Double,
    @Ignore
    val observacoes: String,
    val avaliacao: List<ComponenteAvaliacao>
)

```

Neste exemplo foi associada a anotação **@Ignore** ao atributo "observacoes", ou seja, o atributo "observacoes" será excluído do resultado final do processo de
serialização ou adaptação para XML.

### 5.v Anotacão Order

A anotação **Order**, serve para indicar que ordem uma propriedade deve ser escrita durante a instaciação ou adaptação para XML.
Quando esta anotação estiver presente em uma propriedade, ela será alterada em termos de posição no resultado final do processo de serialização. As propriedaes que não tenham a anotação **Order** serão escritas no final.

```Kotlin

class FUC(
    val codigo: String,
    @Order(3)
    val nome: String,
    @Order(1)
    val ects: Double,
    @Order(2)
    val observacoes: String,
    val avaliacao: List<ComponenteAvaliacao>
)

```

Neste exemplo foi associada a anotação **@Order** a algumas propriedades ou seja, as proprieades serão escritas por ordem diferente no resultado final do processo de
serialização ou adaptação para XML.
No exemplo dado, a ordem é ects (1), observacoes (2), e nome (3), os restantes vêm na ordem do construtor.

### 5.vi Função escreveXml

A função **escreveXml**, tem como objetivo receber qualquer instância de uma classe e converte qualquer objeto em uma instância da classe "Entidade". A função permite assim, um controle detalhado sobre a geração do XML, incluindo a personalização dos nomes dos elementos, a ordem dos elementos, a transformação dos valores e a adaptação de objetos complexos.
Funciona com as seguintes anotações se tiverem presentes no objeto passado para a função:

- XmlElemento: Define o nome do elemento XML.
- XmlAdapter: Converte um objeto de um tipo específico em uma estrutura XML customizada representada por uma entidade.
- Order: Define a ordem das propriedades no XML.
- Ignore: Indica que a propriedade que deve ser ignorada na conversão para XML.
- XmlString: Define um adaptador para a conversão do valor da propriedade para string.

```Kotlin

class FUC(
    @Igonre
    val codigo: String,
    @Order(3)
    val nome: String,
    @Order(1)
    val ects: Double,
    @Order(2)
    val observacoes: String,
    val avaliacao: List<ComponenteAvaliacao>
)

@XmlElemento(name = "componente")
data class ComponenteAvaliacao(
    @XmlElemento(name = "nometeste")
    val nome: String,
    @XmlElemento(name = "pesoteste")
    @XmlString(AddPercentage::class)
    val peso: Int
)

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

```

Neste exemplo foram criadas duas classes, foi cria uma instância da classe FUC e atribuida à variável "Z1". A instância z1 passou a conter todos os dados fornecidos. Esta instância é convertida para XML usando a função **escreveXml**.
Fará a escrita do XML respeitando a ordem do contrutor da "Entidade" ou em função das anotações que foram associadas às classes.
