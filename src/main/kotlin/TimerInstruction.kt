class TimerInstruction(cpu: CPU, private val timer: Timer) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        when (byte1) {
            0xB0.toByte() -> timer.set(cpu.registers[3])
            0xC0.toByte() -> cpu.registers[1] = timer.get()
        }
    }
}