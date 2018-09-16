package io.eddywm.klox

import org.omg.CORBA.Object


data class Token(
        private val tokenType: TokenType,
        private val lexeme: String,
        private val literal: Object,
        private val line: Int) {
    override fun toString(): String {
        return "$tokenType $lexeme $literal"
    }
}