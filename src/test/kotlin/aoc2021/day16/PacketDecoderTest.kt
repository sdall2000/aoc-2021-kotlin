package aoc2021.day16

import aoc2021.PuzzleInputTestUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PacketDecoderTest {

    @Test
    fun part1() {
        val line = PuzzleInputTestUtils.getPuzzleInputFile("day16.txt").readLines()[0]

        val packetDecoder = PacketDecoder()

        assertEquals(873, packetDecoder.part1(line))
    }

    @Test
    fun part2() {
        val line = PuzzleInputTestUtils.getPuzzleInputFile("day16.txt").readLines()[0]

        val packetDecoder = PacketDecoder()

        assertEquals(402817863665, packetDecoder.part2(line))
    }

    @Test
    fun part2Samples() {
        val packetDecoder = PacketDecoder()

        assertEquals(3, packetDecoder.part2("C200B40A82"))
        assertEquals(54L, packetDecoder.part2("04005AC33890"))
        assertEquals(7, packetDecoder.part2("880086C3E88112"))
        assertEquals(9, packetDecoder.part2("CE00C43D881120"))
        assertEquals(1, packetDecoder.part2("D8005AC2A8F0"))
        assertEquals(0, packetDecoder.part2("F600BC2D8F"))
        assertEquals(0, packetDecoder.part2("9C005AC2F8F0"))
        assertEquals(1, packetDecoder.part2("9C0141080250320F1802104A08"))
    }

    @Test
    fun samples() {
        val packetDecoder = PacketDecoder()

        assertEquals(16, packetDecoder.part1("8A004A801A8002F478"))
        assertEquals(12, packetDecoder.part1("620080001611562C8802118E34"))
        assertEquals(23, packetDecoder.part1("C0015000016115A2E0802F182340"))
        assertEquals(31, packetDecoder.part1("A0016C880162017C3686B18A3D4780"))
    }
}