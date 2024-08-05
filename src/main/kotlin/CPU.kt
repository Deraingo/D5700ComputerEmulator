class CPU {
    val registers = ByteArray(8)
    var programCounter: Int = 0
    private val timer = Timer()
    private val instructions: Map<Byte, Instruction>

    init {
        val cpu = this
        instructions = mapOf(
            0x60.toByte() to StoreInstruction(cpu),
            0x10.toByte() to AddInstruction(cpu),
            0x20.toByte() to SubInstruction(cpu)
        )
    }

    fun executeNextInstruction() {
        val byte1 = Memory.readByte(programCounter)
        val byte2 = Memory.readByte(programCounter + 1)
        executeInstruction(byte1, byte2)
        programCounter += 2
    }

    fun executeInstruction(byte1: Byte, byte2: Byte) {
        val instruction = instructions[byte1]
        instruction?.execute(byte1, byte2)
    }
}