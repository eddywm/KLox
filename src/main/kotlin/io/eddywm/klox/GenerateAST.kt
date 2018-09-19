package io.eddywm.klox

import java.io.File
import java.io.PrintWriter
import java.nio.file.Path
import java.util.*

fun main(args: Array<String>) {
//    if(args.size != 1) {
//        println("Usage generate_ast <Output Directory>")
//        System.exit(1)
//    }

    val outputDir = "gen"

    defineAST(outputDir, "Expr", Arrays.asList(
        "Binary   : Expr left, Token operator, Expr right",
        "Grouping : Expr expression",
        "Literal  : Object value",
        "Unary    : Token operator, Expr right"
    ))
}

fun defineAST(outputDir: String, baseName: String, types: List<String>) {
    val path = "src/main/kotlin/$outputDir/$baseName.java"
    val file = File(path)

    file.parentFile.mkdir()
    file.createNewFile()


    val writer = PrintWriter(path, "UTF-8")

    writer.println("package gen ;")
    writer.println()
    writer.println("import io.eddywm.klox.Token ;")
    writer.println()

    writer.println("public abstract class $baseName {")

    defineVisitor(writer, baseName, types)

    types.forEach { type ->
        val className = type.split(":")[0].trim()
        val fields = type.split(":")[1].trim()

        defineType(writer, baseName, className, fields)
    }
    // Base accept method
    writer.println()
    writer.println("    abstract <R> R accept (Visitor<R> visitor);")

    writer.println("}")



    writer.close()
}

fun defineVisitor(writer: PrintWriter, baseName: String, types: List<String>) {

    writer.println("    public  interface Visitor<R> {")

    types.forEach { type ->
        val typeName = type.split(":")[0].trim()
        writer.println("    R visit$typeName$baseName ( $typeName ${baseName.toLowerCase()} );")
    }


    writer.println("}")

}

fun defineType(writer: PrintWriter, baseName: String, className: String, fieldList: String) {
    writer.println("    public static class $className extends $baseName {")

    //Constructor
    writer.println("        $className ( $fieldList ) {")

    //Store params in the fields
    val fields = fieldList.split(", ")

    fields.forEach { field ->
        val name = field.split(" ")[1]
        writer.println("            this.$name = $name ;")
    }
    writer.println("    }")

    // Visitor pattern
    writer.println()
    writer.println("    public <R> R accept (Visitor<R> visitor) {")
    writer.println("        return visitor.visit$className$baseName(this);")
    writer.println("    }")

    //Fields
    writer.println()
    for (field in fields) {
        writer.println("    final $field;")
    }

    writer.println(" }")

}
