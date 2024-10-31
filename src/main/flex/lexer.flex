package fool;

import java_cup.runtime.Symbol;

%%

%cup
%public
%class Lexer

%%

// Palavras reservadas
"class"             { return new Symbol(sym.CLASS, yytext()); }
"void"              { return new Symbol(sym.VOID, yytext()); }
"int"               { return new Symbol(sym.INT, yytext()); }
"bool"              { return new Symbol(sym.BOOL, yytext()); }
"if"                { return new Symbol(sym.IF, yytext()); }
"else"              { return new Symbol(sym.ELSE, yytext()); }
"return"            { return new Symbol(sym.RETURN, yytext()); }
"True"              { return new Symbol(sym.TRUE, Boolean.parseBoolean(yytext())); }
"False"             { return new Symbol(sym.FALSE, Boolean.parseBoolean(yytext())); }

// Operadores e símbolos
"=="                { return new Symbol(sym.EQ, yytext()); }
"<"                 { return new Symbol(sym.LT, yytext()); }
">"                 { return new Symbol(sym.GT, yytext()); }
"+"                 { return new Symbol(sym.PLUS, yytext()); }
"*"                 { return new Symbol(sym.TIMES, yytext()); }
"="                 { return new Symbol(sym.ASSIGN, yytext()); }
"not"               { return new Symbol(sym.NOT, yytext()); }
"and"               { return new Symbol(sym.AND, yytext()); }
"or"                { return new Symbol(sym.OR, yytext()); }
"("                 { return new Symbol(sym.LPAREN, yytext()); }
")"                 { return new Symbol(sym.RPAREN, yytext()); }
"{"                 { return new Symbol(sym.LBRACE, yytext()); }
"}"                 { return new Symbol(sym.RBRACE, yytext()); }
";"                 { return new Symbol(sym.SEMICOLON, yytext()); }
","                 { return new Symbol(sym.COMMA, yytext()); }

// Identificadores (variáveis, métodos, classes)
[a-zA-Z_][a-zA-Z0-9_]* { return new Symbol(sym.IDENTIFIER, yytext()); }

// Constantes numéricas
[0-9]+              { return new Symbol(sym.INT_CONST, Integer.parseInt(yytext())); }

// Espaços em branco
[ \t\n\r]+          { /* Ignora espaços em branco */ }

// Símbolos inesperados
.                   { System.err.println("Token inesperado: " + yytext()); }
