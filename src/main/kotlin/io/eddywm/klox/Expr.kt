package io.eddywm.klox

abstract class Expr {
    interface Visitor<R> {
        fun visitBinaryExpr(expr: Binary): R
        fun visitGroupingExpr(expr: Grouping): R
        fun visitLiteralExpr(expr: Literal): R
        fun visitUnaryExpr(expr: Unary): R
    }

    class Binary internal constructor(internal val left: Expr, internal val operator: Token, internal val right: Expr) :
        Expr() {

        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitBinaryExpr(this)
        }
    }

    class Grouping internal constructor(internal val expression: Expr) : Expr() {

        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitGroupingExpr(this)
        }
    }

    class Literal internal constructor(internal val value: Any) : Expr() {

        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitLiteralExpr(this)
        }
    }

    class Unary internal constructor(internal val operator: Token, internal val right: Expr) : Expr() {

        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitUnaryExpr(this)
        }
    }

    abstract fun <R> accept(visitor: Visitor<R>): R
}
