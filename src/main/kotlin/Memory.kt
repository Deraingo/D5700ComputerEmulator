object Memory {
    private val ram = ByteArray(4096)
    private val rom = ByteArray(4096)

    fun loadROM(bytes: ByteArray) {
        bytes.copyInto(rom)
    }

    fun readByte(address: Int): Byte {
        return if (address < rom.size) {
            rom[address]
        } else {
            ram[address - rom.size]
        }
    }

    fun writeByte(address: Int, value: Byte) {
        if (address < rom.size) {
            println("Attempt to write to ROM. Ignored.")
        } else {
            ram[address - rom.size] = value
        }
    }
}