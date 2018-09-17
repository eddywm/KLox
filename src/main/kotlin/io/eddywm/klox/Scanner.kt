package io.eddywm.klox

import io.eddywm.klox.TokenType.*


class Scanner(private var source: String?) {
    private var tokens = ArrayList<Token>()


    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        if (!isAtEnd()) {
            start = current

            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null, line))

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
        }

    }

    private fun addToken(tokenType: TokenType) {
        addToken(tokenType, null)
    }

    private fun addToken(tokenType: TokenType, literal: Any?) {
        val text = source!!.substring(start, current)
        tokens.add(Token(tokenType, text, literal, line))
    }

    fun advance(): Char {
        current++
        return source!![current - 1]
    }

    private fun isAtEnd(): Boolean {
        return current >= source!!.length
    }

}