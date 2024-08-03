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
    emulator.loadProgram(romPath)
    emulator.start()
}

class D5700Emulator {
    private val cpu = CPU()
    private val memory = Memory
    private val screen = Screen()
    private val keyboard = Keyboard()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    fun loadProgram(path: String) {
        val romBytes = File(path).readBytes()
        memory.loadROM(romBytes)
    }

    fun start() {
        val cpuRunnable = Runnable {
            cpu.executeNextInstruction()
        }

        val cpuFuture = executor.scheduleAtFixedRate(
            cpuRunnable,
            0,
            1000L / 500L, // 500Hz execution rate
            TimeUnit.MILLISECONDS
        )

        // Wait for the CPU to finish
        try {
            cpuFuture.get()
        } catch (e: Exception) {
            executor.shutdown()
            println("Emulator stopped.")
        }
    }
}