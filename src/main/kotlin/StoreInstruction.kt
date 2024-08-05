class StoreInstruction(cpu: CPU) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val registerIndex = byte1.toInt() and 0x0F
        cpu.registers[registerIndex] = byte2
    }
}