package aoc2021.day16

import java.security.InvalidParameterException

private const val typeIdSum = 0
private const val typeIdProduct = 1
private const val typeIdMinimum = 2
private const val typeIdMaximum = 3
private const val typeIdLiteral = 4
private const val typeIdGreaterThan = 5
private const val typeIdLessThan = 6
private const val typeIdEqualTo = 7

class PacketDecoder {
    private val versionLength = 3
    private val typeIdLength = 3

    fun part1(line: String, debug: Boolean = false): Int {

        val bitBuffer = createBitBufferFromHexString(line)

        val packet = buildPacket(bitBuffer, debug)

        return sumVersions(packet)
    }

    private fun sumVersions(packet: Packet): Int {
        return packet.version + packet.subPackets.sumOf { sumVersions(it) }
    }

    private fun createBitBufferFromHexString(hexString: String): BitBuffer {
        val sb = StringBuilder()
        hexString.forEach { sb.append(toBinaryString(it)) }
        val bitString = sb.toString()

        return BitBuffer(bitString)
    }

    fun part2(line: String, debug: Boolean = false): Long {
        val bitBuffer = createBitBufferFromHexString(line)

        if (debug) {
            println("Bit string: ${bitBuffer.bitString}")
        }

        // Returns a fully formed hierarchy of packets/subpackets
        val packet = buildPacket(bitBuffer, debug)

        return packet.resolve()
    }

    private fun buildPacket(bitBuffer: BitBuffer, debug: Boolean = false, indent: String = ""): Packet {
        val packet: Packet?

        // Packet start - get the version
        val version = bitBuffer.getInt(versionLength)

        // Get the type id
        val typeId = bitBuffer.getInt(typeIdLength)

        if (debug) {
            println("${indent}Version: $version, Type Id: $typeId")
        }

        // Handle the specific type id
        if (typeId == typeIdLiteral) {
            var literalTerminating = false

            val literalString = StringBuilder()
            while (!literalTerminating) {
                literalTerminating = bitBuffer.getInt(1) == 0

                // The remaining four bits are for the number itself.
                literalString.append(bitBuffer.getBitString(4))
            }
            // The first bit is the termation bit.

            val literal = literalString.toString().toLong(2)

            if (debug) {
                println("${indent}Parsed literal of $literal")
            }

            // Change packet to be a literal packet, without any subpackets.
            packet = Packet(version, typeId, literal)
        } else {
            packet = Packet(version, typeId)

            // Operator
            val operatorModeLengthTypeId = bitBuffer.getInt(1)
            // 0 means next 15 bit number represents the length in bits of the sub packet.

            if (operatorModeLengthTypeId == 0) {
                val subpacketLength = bitBuffer.getInt(15)

                val bbSub = BitBuffer(bitBuffer.getBitString(subpacketLength))

                if (debug) {
                    println("${indent}Subpacket length in bits: $subpacketLength")
                    println("${indent}BB sub: ${bbSub.bitString}")
                }

                while (!bbSub.isEmpty()) {
                    packet.subPackets.add(buildPacket(bbSub, debug, "$indent  "))
                }
            } else {
                val numberOfSubPackets = bitBuffer.getInt(11)

                repeat(numberOfSubPackets) {
                    packet.subPackets.add(buildPacket(bitBuffer, debug, "$indent  "))
                }
            }
        }

        return packet
    }

    private fun toBinaryString(chr: Char): String {
        return chr.digitToInt(16).toString(2).padStart(4, '0')
    }
}

// Could subclass this for each of the packet types, but this seemed simpler.
class Packet(val version: Int, val packetTypeId: Int, val literalValue: Long = 0) {
    val subPackets = ArrayList<Packet>()

    fun resolve(): Long {
        when (packetTypeId) {
            typeIdSum -> {
                return subPackets.sumOf { it.resolve() }
            }
            typeIdProduct -> {
                var total = 0L
                subPackets.forEachIndexed { index, p ->
                    if (index == 0) {
                        total = p.resolve()
                    } else {
                        total *= p.resolve()
                    }
                }

                return total
            }
            typeIdMinimum -> {
                return subPackets.minOf { it.resolve() }
            }
            typeIdMaximum -> {
                return subPackets.maxOf { it.resolve() }
            }
            typeIdLiteral -> {
                return literalValue
            }
            typeIdGreaterThan -> {
                val val1 = subPackets[0].resolve()
                val val2 = subPackets[1].resolve()

                return if (val1 > val2) 1 else 0
            }
            typeIdLessThan -> {
                val val1 = subPackets[0].resolve()
                val val2 = subPackets[1].resolve()

                return if (val1 < val2) 1 else 0
            }
            typeIdEqualTo -> {
                val val1 = subPackets[0].resolve()
                val val2 = subPackets[1].resolve()

                return if (val1 == val2) 1 else 0
            }
            else -> throw InvalidParameterException("Invalid packet type id of $packetTypeId")
        }
    }
}