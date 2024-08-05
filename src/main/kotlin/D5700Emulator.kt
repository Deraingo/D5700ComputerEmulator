import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
            1000L / 500L,
            TimeUnit.MILLISECONDS
        )
        try {
            cpuFuture.get()
        } catch (e: Exception) {
            executor.shutdown()
            println("Emulator stopped.")
        }
    }
}