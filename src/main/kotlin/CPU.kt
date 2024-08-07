class CPU(private val timer: Timer) {
    val registers = ByteArray(8)
    var programCounter: Int = 0
    private val instructions: Map<Byte, Instruction>

    init {
        val cpu = this
        val screen = Screen()
        instructions = mapOf(
            0x60.toByte() to StoreInstruction(cpu),
            0x10.toByte() to AddInstruction(cpu),
            0x20.toByte() to SubInstruction(cpu),
            0xF0.toByte() to DrawInstruction(cpu, screen),
            0xB0.toByte() to TimerInstruction(cpu, timer),
            0xC0.toByte() to TimerInstruction(cpu, timer)
        )
    }

    fun executeNextInstruction() {
        try {
            // Read bytes for the instruction
            val byte1 = Memory.readByte(programCounter)
            val byte2 = Memory.readByte(programCounter + 1)

            println("Program Counter: $programCounter")
            println("Fetched bytes: ${byte1.toInt().toString(16)}, ${byte2.toInt().toString(16)}")

            // Fetch the instruction based on byte1
            val instruction = instructions[byte1]
            if (instruction == null) {
                println("Instruction not found for opcode: ${byte1.toInt().toString(16)}")
            } else {
                println("Executing instruction: ${byte1.toInt().toString(16)} with ${byte2.toInt().toString(16)}")
                instruction.execute(byte1, byte2)
                println("Executed instruction: ${byte1.toInt().toString(16)}")
            }

            // Increment program counter
            programCounter += 2
        } catch (e: Exception) {
            println("Error during execution: ${e.message}")
            e.printStackTrace()
        }
    }
}