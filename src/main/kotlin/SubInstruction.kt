class SubInstruction(cpu: CPU) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val regX = byte1.toInt() and 0x0F
        val regY = (byte2.toInt() and 0xF0) shr 4
        val regZ = 0

        val valueX = cpu.registers[regX].toInt()
        val valueY = cpu.registers[regY].toInt()
        val result = valueX - valueY
        val wrappedResult = ((result + 128) % 256 - 128).toByte()

        cpu.registers[regZ] = wrappedResult
    }
}