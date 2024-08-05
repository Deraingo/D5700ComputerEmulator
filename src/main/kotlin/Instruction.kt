
abstract class Instruction(protected val cpu: CPU) {
    abstract fun execute(byte1: Byte, byte2: Byte)
}