class DrawInstruction(cpu: CPU, private val screen: Screen) : Instruction(cpu) {
    override fun execute(byte1: Byte, byte2: Byte) {
        val char = byte1.toInt().toChar()
        val row = byte2.toInt() and 0xF
        val col = (byte2.toInt() shr 4) and 0xF

        println("Drawing character: '$char' at row $row, column $col")

        screen.update(row, col, char.toByte())
        screen.draw()
    }
}