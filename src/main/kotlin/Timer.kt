class Timer {
    private var value: Byte = 0

    fun decrement() {
        if (value > 0) {
            value--
        }
    }

    fun set(value: Byte) {
        this.value = value
    }

    fun get(): Byte {
        return value
    }
}