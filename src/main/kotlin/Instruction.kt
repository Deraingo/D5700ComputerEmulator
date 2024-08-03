
abstract class Instruction(protected val cpu: CPU) {
    abstract fun execute(byte1: Byte, byte2: Byte)
}

class StoreInstruction(cpu: CPU) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val registerIndex = byte1.toInt() and 0x0F
        val value = byte2

        cpu.registers[registerIndex] = value
    }
}

class AddInstruction(cpu: CPU) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val regX = byte1.toInt() and 0x0F
        val regY = (byte2.toInt() and 0xF0) shr 4
        val regZ = byte2.toInt() and 0x0F

        cpu.registers[regZ] = (cpu.registers[regX] + cpu.registers[regY]).toByte()
    }
}