import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import kotlin.test.Test

class D5700EmulatorTest {

    @Test
    fun testAdditionInstruction() {
        val romBytes = byteArrayOf(
            0x60.toByte(), 0x00.toByte(),
            0x61.toByte(), 0x00.toByte(),
            0x10.toByte(), 0x10.toByte(),
            0xF0.toByte(), 0x00.toByte()
        )

        val cpu = CPU()
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        cpu.executeNextInstruction()
        cpu.executeNextInstruction()
        println("Register r0: ${cpu.registers[0]}")
        assertEquals(0x00.toByte(), cpu.registers[0], "Register r0 should contain 0x00")
    }

    @Test
    fun testStoreInstruction() {
        val romBytes = byteArrayOf(
            0x60.toByte(), 0xFF.toByte(),
            0xF0.toByte(), 0x00.toByte()
        )

        val cpu = CPU()
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        println("Register r0: ${cpu.registers[0]}")
        assertEquals(0xFF.toUByte(), cpu.registers[0].toUByte(), "Register r0 should contain 0xFF")
    }

    @Test
    fun testAddInstruction() {
        val romBytes = byteArrayOf(
            0x10.toByte(),
            0x21.toByte(),
            0x01.toByte()
        )
        val cpu = CPU()
        cpu.registers[1] = 10
        cpu.registers[2] = 15
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        assertEquals(25.toByte(), cpu.registers[0])
    }

    @Test
    fun testSubInstruction() {
        val romBytes = byteArrayOf(
            0x20.toByte(),
            0x10.toByte(),
            0x01.toByte()
        )
        val cpu = CPU()
        cpu.registers[0] = 15
        cpu.registers[1] = 10
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        assertEquals(5.toByte(), cpu.registers[0])
    }

    @Test
    fun testAddOverflow() {
        val romBytes = byteArrayOf(
            0x10.toByte(), 0x12.toByte()
        )
        val cpu = CPU()
        cpu.registers[1] = 127
        cpu.registers[2] = 1
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        assertEquals((-128).toByte(), cpu.registers[0])
    }

    @Test
    fun testSubUnderflow() {
        val romBytes = byteArrayOf(
            0x20.toByte(),
            0x12.toByte(),
            0x01.toByte()
        )
        val cpu = CPU()
        cpu.registers[1] = -128
        cpu.registers[2] = 0
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()

        val expected = 128.toByte()
        val actual = cpu.registers[0]

        assertEquals(expected, actual)
    }

    @Test
    fun testMemoryReadWrite() {
        val cpu = CPU()
        Memory.writeByte(0x1000, 0xAA.toByte())
        assertEquals(0xAA.toByte(), Memory.readByte(0x1000))
        Memory.writeByte(0x0000, 0xBB.toByte())
        assertNotEquals(0xBB.toByte(), Memory.readByte(0x0000))
    }

    @Test
    fun testMemoryBoundary() {
        Memory.writeByte(4095, 0xAA.toByte())
        assertEquals(0xAA.toByte(), Memory.readByte(4095))
        // Write to the first byte of RAM space
        Memory.writeByte(4096, 0xBB.toByte())
        assertEquals(0xBB.toByte(), Memory.readByte(4096))
    }

    @Test
    fun testProgramCounter() {
        val romBytes = byteArrayOf(
            0x10.toByte(), 0x12.toByte(),
            0x20.toByte(), 0x12.toByte()
        )
        val cpu = CPU()
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        assertEquals(2, cpu.programCounter)
        cpu.executeNextInstruction()
        assertEquals(4, cpu.programCounter)
    }

    @Test
    fun testTimer() {
        val timer = Timer()
        timer.set(10.toByte())
        timer.decrement()
        assertEquals(9.toByte(), timer.get())
    }

    @Test
    fun testFullProgram() {
        val romBytes = byteArrayOf(
            0x60.toByte(), 0x0A.toByte(),
            0x10.toByte(), 0x01.toByte()
        )
        val cpu = CPU()
        cpu.registers[1] = 5
        Memory.loadROM(romBytes)
        cpu.executeNextInstruction()
        cpu.executeNextInstruction()
        assertEquals(15.toByte(), cpu.registers[0])
    }

}