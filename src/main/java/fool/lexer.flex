package fool;

%%

%unicode
%public
%class Lexer
%standalone

%{
  public static final int CLASS = 1;
  public static final int VOID = 2;
  public static final int INT = 3;
  public static final int BOOL = 4;
  public static final int IF = 5;
  public static final int ELSE = 6;
  public static final int RETURN = 7;
  public static final int TRUE = 8;
  public static final int FALSE = 9;
  public static final int EQ = 10;
  public static final int LT = 11;
  public static final int GT = 12;
  public static final int PLUS = 13;
  public static final int TIMES = 14;
  public static final int ASSIGN = 15;
  public static final int NOT = 16;
  public static final int AND = 17;
  public static final int OR = 18;
  public static final int LPAREN = 19;
  public static final int RPAREN = 20;
  public static final int LBRACE = 21;
  public static final int RBRACE = 22;
  public static final int SEMICOLON = 23;
  public static final int COMMA = 24;
  public static final int IDENTIFIER = 25;
  public static final int INT_CONST = 26;

  private void printToken(int type, String value) {
    System.out.println("Token: " + type + " | Value: " + value);
  }
%}

%%

// Palavras reservadas
"class"             { printToken(CLASS, yytext()); }
"void"              { printToken(VOID, yytext()); }
"int"               { printToken(INT, yytext()); }
"bool"              { printToken(BOOL, yytext()); }
"if"                { printToken(IF, yytext()); }
"else"              { printToken(ELSE, yytext()); }
"return"            { printToken(RETURN, yytext()); }
"True"              { printToken(TRUE, yytext()); }
"False"             { printToken(FALSE, yytext()); }

// Operadores e símbolos
"=="                { printToken(EQ, yytext()); }
"<"                 { printToken(LT, yytext()); }
">"                 { printToken(GT, yytext()); }
"+"                 { printToken(PLUS, yytext()); }
"*"                 { printToken(TIMES, yytext()); }
"="                 { printToken(ASSIGN, yytext()); }
"not"               { printToken(NOT, yytext()); }
"and"               { printToken(AND, yytext()); }
"or"                { printToken(OR, yytext()); }
"("                 { printToken(LPAREN, yytext()); }
")"                 { printToken(RPAREN, yytext()); }
"{"                 { printToken(LBRACE, yytext()); }
"}"                 { printToken(RBRACE, yytext()); }
";"                 { printToken(SEMICOLON, yytext()); }
","                 { printToken(COMMA, yytext()); }

// Identificadores (variáveis, métodos, classes)
[a-zA-Z_][a-zA-Z0-9_]* { printToken(IDENTIFIER, yytext()); }

// Constantes numéricas
[0-9]+              { printToken(INT_CONST, yytext()); }

// Espaços em branco
[ \t\n\r]+          { /* ignora espaços em branco */ }