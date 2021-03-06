package io.eddywm.klox

enum class TokenType {
    // Single char tokens
    LEFT_PAREN,
    RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // One or two chars tokens
    BANG,
    BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals
    IDENTIFIER,
    STRING, NUMBER,


    // Keywords
    AND,
    CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR, NOR,
    PRINT, RETURN, SUPER, THIS, TRUE, LET, WHILE,

    EOF
}