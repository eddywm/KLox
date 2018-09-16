package io.eddywm.klox

import io.eddywm.klox.KLox.runFile
import io.eddywm.klox.KLox.runPrompt

fun main(args: Array<String>) {

    when {
        args.size > 1 -> {
            System.out.println("Usage: Klox [script]")
            System.exit(46)
        }
        args.size == 1 -> runFile(args[0])
        else -> runPrompt()
    }
}



