class KeyboardInstruction(cpu: CPU, private val keyboard: Keyboard) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val regX = byte1.toInt() and 0x0F  // Register index from byte1
        cpu.registers[regX] = keyboard.getInput()
    }
}
