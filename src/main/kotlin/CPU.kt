class CPU {
    val registers = ByteArray(8)
    private var programCounter: Int = 0
    private val timer = Timer()
    private val instructions: Map<Byte, Instruction>

    init {
        val cpu = this
        instructions = mapOf(
            0x00.toByte() to StoreInstruction(cpu),
            0x10.toByte() to AddInstruction(cpu)
        )
    }

    fun executeInstruction(byte1: Byte, byte2: Byte) {
        val instructionCode = (byte1.toInt() and 0xF0).toByte()
        val instruction = instructions[instructionCode]
        instruction?.execute(byte1, byte2)
    }
}