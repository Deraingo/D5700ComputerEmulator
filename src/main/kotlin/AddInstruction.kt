class AddInstruction(cpu: CPU) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val registerX = (byte2.toInt() and 0x0F)
        val registerY = (byte2.toInt() shr 4) and 0x0F
        val registerZ = 0

        if (registerX !in cpu.registers.indices || registerY !in cpu.registers.indices || registerZ !in cpu.registers.indices) {
            throw IndexOutOfBoundsException("Register index out of bounds: X=$registerX, Y=$registerY, Z=$registerZ")
        }
        val valueX = cpu.registers[registerX].toInt()
        val valueY = cpu.registers[registerY].toInt()
        val result = valueX + valueY

        cpu.registers[registerZ] = (result and 0xFF).toByte()
    }
}