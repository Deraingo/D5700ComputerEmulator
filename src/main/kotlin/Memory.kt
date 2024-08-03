object Memory {
    private val ram = ByteArray(4096)
    private val rom = ByteArray(4096)

    fun loadROM(bytes: ByteArray) {
        bytes.copyInto(rom)
    }

    fun read(address: Int): Byte {
        return if (address < 4096) ram[address] else rom[address - 4096]
    }

    fun write(address: Int, value: Byte) {
        if (address < 4096) {
            ram[address] = value
        } else {
            println("Attempt to write to ROM address $address")
        }
    }
}