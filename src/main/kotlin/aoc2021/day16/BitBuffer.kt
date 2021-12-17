package aoc2021.day16

// Emulates Java's byte buffer, except allows for arbitrary bit sized values.
class BitBuffer(val bitString: String) {
    var bitIndex = 0

    fun getInt(bitLength: Int): Int {
        return getBitString(bitLength).toInt(2)
    }

    fun getBitString(bitLength: Int): String {
        val str = bitString.substring(bitIndex, bitIndex + bitLength)
        bitIndex += bitLength
        return str
    }

    fun isEmpty(): Boolean {
        return bitIndex == bitString.length
    }
}