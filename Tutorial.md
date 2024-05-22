# Guia do Utilizador

Indice
1. [Entidade](#1-entidade)
   1. [Criar Entidade](#1i-criar-uma-entidade)
   2. [Definir Entidade Pai](#1ii-definir-entidade-pai)
   3. [Definir Entidade Filho](#1iii-definir-entidade-filho)
   4. [Apagar Filhos](#1iv-apagar-filhos)
   5. [Criar Atributos](#1v-criar-atributos)
   6. [Apagar Atributos](#1vi-apagar-atributo)
2. [Funções Globais](#2-funções-globais)
   1. [Alterar nome de uma Entidade](#2i-alterar-o-nome-de-uma-entidade)
   2. [Apagar Entidade Globalmente](#2ii-apagar-entidade)
   3. [Apagar Atributo Globalmente](#2iii-apagar-atributo-globalmente)
   4. [Alterar Atributo](#2iv-alterar-atributo)
3. c

Este guia irá cobrir todos os tópicos da biblioteca de manipulação de
XML desenvolvida em Kotlin, fornecendo exemplos práticos de todas as
funcionalidades desta API.

Esta biblioteca tem três principais classes que permitem a manipulação do XML:

- **Entidade** - Classe que permite manipular cada entidade do XML, desde
alterar nome de cada entidade, adicionar e remover entidades
filho/pai, adicionar e remover atributos
- **Atributo** - Classe que permite criar atributos de cada entidade
- **Documento** - Classe que guarda cada ficheiro XML num objeto

## 1 Entidade

Entidade é a classe principal desta API, onde se faz a maior parte da
manipulação de todo o XML.

A estrutura de um objeto Entidade é a seguinte:

- Nome(String) - Nome da entidade, quando se instancia um objeto é feita uma validação do nome para garantir que apenas possui caracteres [a-Z], não permite caracteres especiais sendo estes removidos, no caso do nome fornecido ser nulo é chamada um exceção
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
criarPai(), onde se passa outra entidade como argumento, que será o
pai da Entidade que está a ser manipulada

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val entidadeFilho1 = Entidade("Filho 1")

// Estabelecer a relação pai-filho usando a função criarPai()
entidadeFilho1.criarPai(entidadePai)
```

Neste exemplo são instanciadas duas entidades, de seguida é feita aligação pai filho entre elas através da função criarPai() onde a entidadePai fica definida como pai da entidadeFilho

### 1.iii Definir Entidade Filho

Para se definir o(s) filho(s) de uma Entidade pode ser utilizada a
função criarVariosFilhos(), onde se passa outra(s) entidade(s) como
argumento, que ser o pai da Entidade que está a ser manipulada

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val entidadeFilho1 = Entidade("Filho 1")

// Estabelecer a relação pai-filho usando a função criarFilho
entidadePai.criarFilho(entidadeFilho1)
```

### 1.iv Apagar Filhos

Para remover um filho de uma dada Entidade é usada a função apagarFilho() que possui um parâmetro de entrada:

- entidadeFilho(Entidade) - objeto Entidade que será removida da lista de filhos de uma Entidade
  
```Kotlin
val entidade_Pai = Entidade("Plano")
val entidade_Filho = Entidade("FUC")

entidade_Pai.criarFilho(entidade_Filho)
entidade_Pai.apagarFilho(entidade_Filho)
```

Neste exemplo é primeiro instanciado duas Entidades, depois é feita a ligação entre estas de pai e filho, por fim utiliza-se a função **apagarFilho()** para remover o Atributo **entidade_Filho** da lista de Atributos da **Entidade_Pai**.

### 1.v Criar Atributos

Existem dois métodos de adicionar Atributos a uma Entidade.

Em ambos os métodos quando a função é chamada é feita uma validação inicial que verifica se na lista de Atributos já existe um Atributo com o mesmo nome, se não existir o novo(s) Atributo(s) é/são adicionado(s) à lista, se já existir é enviada uma mensagem avisando que já existe um Atributo com esse nome e o novo Atributo **não é adicionado** à lista.

O primeiro utiliza a função **criarAtributo()** que associa um objeto Atributo a um objeto Entidade, tendo como parâmetros de entrada:

- nomeAtributo(String) - nome do Atributo
- valor(String) - valor do Atributo

```Kotlin
val entidade_exemplo = Entidade("FUC")
entidade_exemplo.criarAtributo("codigo1", "M4310")
```

Neste exemplo é instanciado um objeto Entidade, de seguida é chamada a função **criarAtributo()** onde é associado um novo Atributo à Entidade, com o nome **"codigo1"** e valor **"M4310"**.

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

Para apagar um Atributo de uma Entidade é usada a função **apagarAtributoNome()**, que possui como parâmetro de entrada:

- nomeAtributo(String) - Nome do Atributo que será removido

```Kotlin
val entidade_exemplo = Entidade("FUC")
entidade_exemplo.criarAtributo("codigo1", "M4310")

entidade_exemplo.removerAtributoNome("codigo1")
```

Neste exemplo é instanciado um objeto Entidade e adicionado um objeto Atributo à lista de Atributos, de seguida é utilizada a função **removerAtributoNome()** com o nome "codigo1" como nome do Atributo a remover.

## 2 Funções Globais

### 2.i Alterar o nome de uma Entidade

Para alterar o nome de uma Entidade é usada a função **alterarNomeEntidade()**, que tem como parâmetros de entrada:

- Entidade(Entidade) - Objeto Entidade que irá ser alterada
- nomeAntigo(String) - Nome da Entidade como forma de validação
- novoNome(String) - Novo nome da Entidade

```Kotlin
val entidade_exemplo = Entidade("FUC")
alterarNomeEntidade(entidade_exemplo, "FUC", "Curso")
```

Neste exemplo é instanciado um objeto Entidade com o nome "FUC", de seguida é utilizada a função **alterarNomeEntidade()** para **alterar o nome** da entidade_exemplo de "FUC" para **"Curso"**.

### 2.ii Apagar Entidade

Para apagar qualquer Entidade é utilizada a função **apagarEntidadeNome()**, que tem como parâmetros de entrada:

- Entidade(Entidade) - Objeto Entidade que irá ser apagada
- nome(String) - Nome da Entidade que irá ser apagada

```Kotlin
val entidade_exemplo = Entidade("FUC")
apagarEntidadeNome(entidade_exemplo, "FUC")
```

Neste exemplo é instanciada um objeto Entidade, de seguida é utilizada a função **apagarEntidadeNome()** para **apagar** a todas as Entidades que tenham o nome "FUC" recursivamente.

```Kotlin
val entidade_exemplo = Entidade("FUC")
apagarEntidade(entidade_exemplo)
```

Neste exemplo é instanciada um objeto Entidade, de seguida é utilizada a função **apagarEntidade()** para **apagar** a Entidade.

A diferença entre os dois métodos é que no primeiro é especificada a entidade raíz a partir de onde se irão remover todas as Entidades que possuam o nome correspondente, no segundo é especificada a Entidade a ser removida e por conseguinte todos os seus filhos independetemente do nome.

### 2.iii Apagar Atributo Globalmente

Para apagar um atributo globlamente, ou seja, independentemente da Entidade(s) em que este exista é usada a função **apagarAtributoGlobalNome()**, que tem como parâmetros de entrada:

- Entidade(Entidade) - Entidade pai a partir de onde se irá remover o atributo
- Nome(String) - Nome do atributo a ser removido

```Kotlin
val entidade_exemplo = Entidade("FUC")
val atributo_exemplo = Atributo("codigo1", "M4310")

entidade_exemplo.adicionarAtributos(atributo_exemplo)

apagarAtributoGlobalNome(entidade_exemplo, "codigo1")
```

**apagarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- Entidade(Entidade) - Entidade pai a partir de onde se irá remover o atributo
- nomEntidade(String) - Nome do Entidade
- nomeAtributo(String) - Nome do atributo a ser removido

### 2.iv Alterar Atributo

Para alterar o nome e/ou valor de um atributo é usada a função **alterarAtributo()**, tem como parâmetros de entrada:

- Entidade(Entidade)
- nomeAtributo(String)
- valor(String)

```Kotlin
val entidade = Entidade("Entidade")
val atributo = Atributo("nomeAtributo", "valorInicial")

entidade.adicionarAtributos(atributo)

alterarAtributo(entidade,"nomeAtributo","novoNome","novoValor")
```

**alterarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- Entidade(Entidade)
- nomeEntidade(String)
- nomeAtributo(String)
- nomeAtributoNovo(String)

```Kotlin
// Inicializar objetos entidade
val e1 = Entidade("entidadeUm")
val e2 = Entidade("entidadeDois", "textoDois")

// Inicializar objetos atributo
val a1 = Atributo("atributoUm", "valorUm")
val a2 = Atributo("atributoDois", "valorDois")

// Adicionar o filho à lista de filhos do pai
e1.criarVariosFilhos(e2)

// Adicionar os atributos à entidade
e1.adicionarAtributos(a1, a2)
e2.adicionarAtributos(a1)

// Alterar nome do atributo de uma entidade específica
alterarAtributoNomeEntidadeNomeAtributoGlobal(e1, "entidadeUm", "atributoUm", "novoNome")
```

**alterarAtributoNomeEntidade()**, tem como parâmetros de entrada:

- entidadePai(Entidade)
- nomeVelho(String)
- novoNome(String)

```Kotlin
// Inicializar objetos entidade
val entidadePai = Entidade("Pai")
val filho1 = Entidade("Filho1")
val filho2 = Entidade("Filho2")

val atributo1 = Atributo("nome", "valor1")
val atributo2 = Atributo("idade", "25")
val atributo3 = Atributo("nome", "valor2")
val atributo4 = Atributo("nome", "valor3")

// Adicionar os filhos à lista de filhos do pai
entidadePai.criarVariosFilhos(filho1, filho2)

// Adicionar os atributos à entidade
entidadePai.adicionarAtributos(atributo1, atributo2)
filho1.adicionarAtributos(atributo3)
filho2.adicionarAtributos(atributo4)

// Alterar o nome de um atributo numa entidade especifica e nos seus filhos
alterarAtributoNomeEntidade(entidadePai, "nome", "novoNome")
```

**criarAtributoNomeEntidadeNomeAtributoGlobal()**, tem como parâmetros de entrada:

- entidadePai(Entidade)
- nomeEntidade(String)
- nomeAtributo(String)
- valor(String)

```Kotlin
val e1 = Entidade("entidadeUm")
val e2 = Entidade("entidadeDois")
val e3 = Entidade("entidadeTres")

// Adicionar os filhos à lista de filhos do pai
e1.criarVariosFilhos(e2, e3)

// Criar um novo atributo globalmente numa dada entidade
criarAtributoNomeEntidadeNomeAtributoGlobal(e1, "entidadeUm", "novoAtributo", "valorNovoAtributo")
```

**acederEntidadMaeeFilhos()**, tem como parâmetros de entrada:

- entidade(Entidade)
- nivel(Int)

```Kotlin
val e1 = Entidade("entidadeUm")
val e2 = Entidade("entidadeDois", "textoDois")
val e3 = Entidade("entidadeTres")

// Inicializar objetos atributo
val a1 = Atributo("atributoUm", "valorUm")
val a2 = Atributo("atributoDois", "valorDois")

// Adicionar os filhos à lista de filhos do pai
e1.criarVariosFilhos(e2, e3)

// Adicionar os atributos à entidade
e1.adicionarAtributos(a1, a2)


val outputStreamCaptor = ByteArrayOutputStream()
System.setOut(PrintStream(outputStreamCaptor))

// Aceder a uma entidade e aos seus filhos e escreve o resultado em formato XML
acederEntidadMaeeFilhos(e1)
```

**encontrarEntidadesPorXPath()**, tem como parâmetros de entrada:

- entidade(Entidade)
- xpath(String)

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
val e13 = Entidade("componente")
val e14 = Entidade("componente")
val e15 = Entidade("componente")

// Inicializar objetos atributo
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
e1.criarVariosFilhos(e2, e3, e9)
e3.criarVariosFilhos(e4, e5, e6)
e9.criarVariosFilhos(e10, e11, e12)
e6.criarVariosFilhos(e7, e8)
e12.criarVariosFilhos(e13, e14,e15)
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
```

**imprimirResultados()**, tem como parâmetros de entrada:

- resultados(List)

```Kotlin
val e1 = Entidade("entidadeUm")
val e2 = Entidade("entidadeDois", "textoDois")

// Inicializar objetos atributo
val a1 = Atributo("atributoUm", "valorUm")
val a2 = Atributo("atributoDois", "valorDois")

// Criar lista de resultados
val resultados = listOf(
    Pair(e1, listOf(a1, a2)),
    Pair(e2, emptyList())
)

val outputStreamCaptor = ByteArrayOutputStream()
System.setOut(PrintStream(outputStreamCaptor))

// Escrever os resultados
imprimirResultados(resultados)
```
