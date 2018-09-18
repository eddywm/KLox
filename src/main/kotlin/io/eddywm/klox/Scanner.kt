package io.eddywm.klox

import io.eddywm.klox.TokenType.*
import java.lang.Character.isDigit


class Scanner(private var source: String) {
    private var tokens = ArrayList<Token>()
    private var start = 0
    private var current = 0
    private var line = 1

    companion object {
        val keywords = mutableMapOf(
            "and" to AND,
            "class" to CLASS,
            "else" to ELSE,
            "false" to FALSE,
            "for" to FOR,
            "fun" to FUN,
            "if" to IF,
            "nil" to NIL,
            "or" to OR,
            "print" to PRINT,
            "return" to RETURN,
            "super" to SUPER,
            "this" to THIS,
            "true" to TRUE,
            "let" to LET,
            "while" to WHILE
        )
    }


    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current

            scanToken()
        }

        tokens.add(Token(EOF, "", null, line))

        return tokens
    }

    private fun scanToken() {

        val c = advance()

        when (c) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)

            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)

            '/' -> {
                when {
                    match('/') -> if (peek() != '\n' && !isAtEnd()) advance()
                    else -> addToken(SLASH)
                }
            }

            ' ', '\r', '\t' -> {
            }

            '\n' -> line++

            '"' -> string()


            else -> {
                when {
                    isDigit(c) -> number()
                    isAlpha(c) -> identifier()
                    else -> KLox.error(line, "Unexpected character.")
                }
            }
        }

    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()

        val text = source.substring(start, current)

        var tokenType = keywords[text]

        if (tokenType == null) tokenType = IDENTIFIER

        addToken(tokenType)
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return isAlpha(c) || isDigit(c)
    }

    private fun isAlpha(c: Char): Boolean {
        return (c in 'a'..'z') || (c in 'A'..'Z') || c == '_'
    }

    private fun number() {
        while (isDigit(peek())) advance()
        if (peek() == '.' && isDigit(peekNext())) {
            advance()
            while (isDigit(peek())) advance()

        }

        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) return '\u0000'
        return source[current + 1]
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd()) {
            KLox.error(line, "Unterminated string.")
            return
        }
        // Closing "
        advance()

        // Trim the " from the string value
        var value = source.substring(start + 1, current - 1)
        addToken(STRING, value)
    }

    private fun peek(): Char {
        if (isAtEnd()) return '\u0000'
        return source[current]
    }

    private fun match(expectedChar: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expectedChar) return false
        current++
        return true
    }

    private fun addToken(tokenType: TokenType) {
        addToken(tokenType, null)
    }

    private fun addToken(tokenType: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(tokenType, text, literal, line))
    }

    private fun advance(): Char {
        current++
        return source[current - 1]
    }

    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

}