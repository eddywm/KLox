package io.eddywm.klox

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

object KLox {
    var hadError = false

    fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val source = reader.readLine()
            if(source == "quit()") System.exit(0)
            run(source)
            hadError = false
        }
    }

    fun runFile(path: String) {
        val bytes: ByteArray = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()

        for (token in tokens) {
            println(token)
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

