package io.eddywm.klox

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object KLox {
    var hadError = false

    fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            run(reader.readLine())
            hadError = false
        }
    }



    fun runFile(path: String) {
        val bytes: ByteArray = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))
    }


    fun run(source: String?) {
        val scanner: Scanner = io.eddywm.klox.Scanner(source)
        val tokens = scanner.scanTokens()

        for (token in tokens) {
            System.out.println(token)

            if(hadError) {
                System.exit(65)
            }
        }
    }


    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    private fun report(line: Int, where: String, message: String) {
        System.err.println(
                "[line $line] Error$where: $message")
        hadError = true
    }
}

