package io.eddywm.klox


data class Token(
        private val tokenType: TokenType,
        private val lexeme: String,
        private val literal: Any?,
        private val line: Int) {
    override fun toString(): String {
        return "$tokenType $lexeme $literal"
    }
}