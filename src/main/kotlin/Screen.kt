class Screen {
    private val frameBuffer = ByteArray(64)

    fun draw() {
        for (i in frameBuffer.indices) {
            if (i % 8 == 0 && i != 0) {
                println()
            }
            print(frameBuffer[i].toChar())
        }
        println()
    }

    fun update(row: Int, column: Int, value: Byte) {
        val index = row * 8 + column
        if (index in frameBuffer.indices) {
            frameBuffer[index] = value
        }
        println("Screen updated at row $row, column $column with value ${value.toChar()}") // Debug statement
    }
}