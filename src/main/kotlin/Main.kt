import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
    println("Welcome to the D5700 emulator! Please type a command to run:")
    val romPath = readLine()

    if (romPath.isNullOrBlank()) {
        println("No command provided. Exiting...")
        return
    }

    val emulator = D5700Emulator()
    emulator.loadProgram(romPath!!)
    emulator.start()
}


