import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class D5700Emulator {
    private val timer = Timer()
    val cpu = CPU(timer)
    private val memory = Memory
    private val screen = Screen()
    private val keyboard = Keyboard()
    private val executor = Executors.newSingleThreadScheduledExecutor()
    @Volatile private var running = false

    fun loadProgram(path: String) {
        val romBytes = File(path).readBytes()
        memory.loadROM(romBytes)
        println("Loaded ROM:")
        running = true
        for (i in romBytes.indices) {
            println("Memory[$i]: ${romBytes[i].toInt()}")
        }
    }

    fun start() {
        println("Starting emulator...")
        val timerRunnable = Runnable {
            while (running) {
                timer.decrement()
                Thread.sleep(1000L / 500L)
            }
        }
        executor.submit(timerRunnable)

        val cpuRunnable = Runnable {
            while (running) {
                try {
                    println("Executing next instruction...")
                    cpu.executeNextInstruction()
                    printMemorySection(0, 20)
                    println("Register values: ${cpu.registers.joinToString()}")
                    Thread.sleep(1000L / 500L)
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    stop()
                }
            }
        }
        executor.submit(cpuRunnable)
    }

    fun stop() {
        running = false
        executor.shutdown()
        println("Emulator stopped.")
    }

    private fun printMemorySection(start: Int, end: Int) {
        for (i in start until end) {
            println("Memory[$i]: ${Memory.readByte(i).toInt().toString(16).padStart(2, '0')}")
        }
    }
}