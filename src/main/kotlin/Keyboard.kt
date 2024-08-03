class Keyboard {
    fun getInput(): Byte {
        val input = readLine()
        return if (input.isNullOrBlank()) 0 else input.toInt(16).toByte()
    }
}