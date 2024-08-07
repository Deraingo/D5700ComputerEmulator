import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
    val scanner = Scanner(System.`in`)
    println("Welcome to the D5700 emulator! Please type a command to run:")
    val romPath = scanner.nextLine()

    val emulator = D5700Emulator()
    emulator.loadProgram(romPath)
    emulator.start()

    while (true) {
        println("Type 'stop' to stop the emulator:")
        val command = scanner.nextLine()
        if (command.equals("stop", ignoreCase = true)) {
            emulator.stop()
            break
        }
    }
}


