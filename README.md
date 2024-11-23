
# Parser da Linguagem FOOL
O parser da linguagem FOOL (Fake Object-Oriented Language) é projetado para analisar, realizar análise semântica e gerar código intermediário para a linguagem de programação FOOL, uma linguagem orientada a objetos simples, projetada para fins educacionais.

## Especificação da Linguagem FOOL

### Visão Geral

FOOL é uma linguagem de programação orientada a objetos simplificada que suporta construções básicas de programação, incluindo classes, métodos, variáveis, comandos de controle de fluxo e expressões. É estaticamente tipada e realiza verificação de tipos durante a fase de análise semântica.

### Tipos de Dados

FOOL suporta os seguintes tipos de dados:

-   **int**: Representa números inteiros.
-   **bool**: Representa valores booleanos (`true` ou `false`).
-   **void**: Usado como tipo de retorno para métodos que não retornam um valor.

### Variáveis

Variáveis devem ser declaradas antes de serem usadas. Declarações de variáveis especificam o tipo e o nome da variável.

    int num;
    bool flag;

### Métodos

Métodos são funções que pertencem a uma classe. Eles podem ter parâmetros e um tipo de retorno.

- **Declaração**
```
    int sum(int a, int b) {
        // corpo do método
        return a + b;
    }
```
-   **Parâmetros**: Métodos podem ter zero ou mais parâmetros. Cada parâmetro deve ter um tipo e um nome.
    
-   **Tipo de Retorno**: Métodos devem declarar um tipo de retorno. Use `void` se o método não retornar um valor.

### Expressões

Expressões podem ser constantes, variáveis, chamadas de métodos ou combinações destas usando operadores.

#### Constantes

-   **Constantes Inteiras**: Valores inteiros literais, por exemplo, `42`.
-   **Constantes Booleanas**: `true` ou `false`.

#### Variáveis

Variáveis podem ser usadas em expressões após terem sido declaradas.

#### Chamadas de Métodos

Métodos podem ser chamados usando seu nome e passando os argumentos necessários.
```
int resultado = sum(5, 10);
```

#### Operadores

FOOL suporta os seguintes operadores:

-   **Operadores Aritméticos**: `+`, `-`, `*`, `/`
-   **Operadores Relacionais**: `==`, `<`, `>`
-   **Operadores Lógicos**: `and`, `or`, `not`

#### Exemplos de Uso de Operadores
```
int a = 5 + 3;
bool b = (a > 5) and (a < 10);
```

### Comandos

FOOL suporta vários tipos de comandos para controlar o fluxo de execução.

#### Comando de Atribuição

Atribui o resultado de uma expressão a uma variável.

```
num = 10;
```

#### Comando `if`

Executa um bloco de código se uma condição for verdadeira.
```
if (flag) num = 10;
```

#### Comando `if-else`

Executa um bloco se uma condição for verdadeira, e outro se for falsa.

```
if (flag) num = 10; else num = 0;
```
#### Comando `while`

Executa um bloco de código repetidamente enquanto uma condição for verdadeira.

```
while (num < 20) num = num + 1;
```

#### Comando `return`

Sai de um método e opcionalmente retorna um valor.

```
return sum;
```

## Exemplo

Aqui está um exemplo de um programa FOOL demonstrando vários recursos da linguagem:
```
{ class SampleClass
    int num;
    int num2;
    bool flag;

    int sum(int a, int b) {
        return a + b;
    }

    int test() {
        num = 1;
        num2 = 2;
        return sum(num, num2);
    }

    void main() {
        flag = not False;
        if (flag) num = 10; else num = 0;
        while (num < 20) num = num + 1;
        test();
    }
}
```

## Construindo e Executando o Compilador

### Pré-requisitos

-   **Java Development Kit (JDK)**: Versão 17 ou superior.
- **Maven**

### Construindo o Compilador

1.  **Clone o Repositório**
```
git clone https://github.com/tcpassos/foolparser
cd foolparser
```
2. **Gere o Lexer e o Parser**
O projeto já está configurado para gerar o Parser.java e o Lexer.java, assim como a tabela de símbolos compartilhada entre eles, durante a etapa de build do projeto Maven. Sendo assim, para fazer o build e gerar os arquivos necessários, basta rodar:
```
mvn clean install
```
3. **Executando o exemplo**
A classe fool.Main contém um exemplo de utilização do Parser da linguagem FOOL, que irá usar o arquivo sample.fool da pasta de resources Java.