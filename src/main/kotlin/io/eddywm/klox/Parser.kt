package io.eddywm.klox

import io.eddywm.klox.TokenType.*





class Parser(private var tokens: List<Token>, private var current: Int = 0) {

    private fun expression(): Expr {
        return equality()
    }

    private fun equality(): Expr {
        var expr: Expr = comparison()

        while (match(BANG, BANG_EQUAL)) {
            val operator: Token = previous()
            val right: Expr = comparison()
            expr = Expr.Binary(expr, operator, right)
        }

        return expr
    }

    private fun comparison(): Expr {
        var expr = addition()

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            val operator: Token = previous()
            val right: Expr = addition()
            expr = Expr.Binary(expr, operator, right)
        }
        return  expr
    }

    private fun addition(): Expr {
        var expr: Expr = multiplication()
        while (match(MINUS, PLUS)) {
            val operator: Token = previous()
            val right: Expr = multiplication()
            expr = Expr.Binary(expr, operator, right)
        }
        return  expr
    }

    private fun multiplication(): Expr {
        var expr: Expr = unary()
        while (match(STAR, SLASH)) {
            val operator: Token = previous()
            val right: Expr = unary()
            expr = Expr.Binary(expr, operator, right)
        }
        return  expr
    }

    private fun unary(): Expr {
        if(match(MINUS, BANG)) {
            val operator = previous()
            val right = unary()
            return Expr.Unary(operator, right)
        }

        return  primary()
    }

    private fun primary(): Expr {
        if (match(FALSE)) return Expr.Literal(false)
        if (match(TRUE)) return Expr.Literal(true)
        if (match(NIL)) return Expr.Literal(null)

        if (match(NUMBER, STRING)) {
            return Expr.Literal(previous().literal)
        }

        if (match(LEFT_PAREN)) {
            val expr = expression()
//            consume(RIGHT_PAREN, "Expect ')' after expression.")
            return Expr.Grouping(expr)
        }
    }

    private fun match(vararg tokenTypes: TokenType): Boolean {

        tokenTypes.forEach { type ->
            if (check(type)) {
                advance()
                return true
            }
        }

        return false
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun check(type: TokenType): Boolean {
        if (isAtEnd()) return false
        return peek().tokenType == type
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun isAtEnd(): Boolean {
        return peek().tokenType == EOF
    }

    private fun previous(): Token {
        return tokens[current - 1]
    }


}