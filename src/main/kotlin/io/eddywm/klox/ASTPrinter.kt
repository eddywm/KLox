package io.eddywm.klox

class ASTPrinter : Expr.Visitor<String> {
    override fun visitBinaryExpr(expr: Expr.Binary): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitGroupingExpr(expr: Expr.Grouping): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitLiteralExpr(expr: Expr.Literal): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitUnaryExpr(expr: Expr.Unary): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}