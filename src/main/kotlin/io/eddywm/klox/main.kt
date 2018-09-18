package io.eddywm.klox

import io.eddywm.klox.KLox.runFile
import io.eddywm.klox.KLox.runPrompt

// KLox Interpreter main entry point
fun main(args: Array<String>) {
    when {
        args.size > 1 -> {
            println("Usage: KLox [script]")
            System.exit(46)
        }
        args.size == 1 -> runFile(args[0])
        else -> runPrompt()
    }
}



