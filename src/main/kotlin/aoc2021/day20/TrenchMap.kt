package aoc2021.day20

import kotlin.math.pow

class TrenchMap {
    fun countLitPixels(lines:List<String>, iterations:Int): Int {
        val imageEnhancementAlgorithm = lines[0]

        val imageInputLines = ArrayList<String>()

        for (i in 2 until lines.size) {
            imageInputLines.add(lines[i])
        }

        // A bit of a hack....
        // Check for special case, where the 9 cell sample around the center point being 0 will resolve to a pixel
        // being set.  That means, the infinite part of the image away from the image itself will all become set to #.
        // Which means, when it's toggled again, they will become '.' (because the rightmost setting of the image
        // enhancement must be a '.' or it would be impossible to calculate the solution.
        val offScreenChar = imageEnhancementAlgorithm[0]

        var imageInput = ImageInput(imageInputLines)

        repeat(iterations) {
            imageInput = imageInput.enhance(imageEnhancementAlgorithm, it % 2 == 1 && offScreenChar == '#')
        }

        return imageInput.countLitPixels()
    }
}

class ImageInput(val lines: List<String>) {
    private var imageMap:Array<BooleanArray>

    private var rows:Int = lines.size
    private var cols:Int = lines[0].length
    private var maxX:Int = cols - 1
    private var maxY:Int = rows - 1

    init {

        imageMap = Array(rows) { BooleanArray(0) }

        lines.forEachIndexed { index, line ->
            val pixels = BooleanArray(line.length)

            line.forEachIndexed { lineIndex, chr ->
                pixels[lineIndex] = chr == '#'
            }

            imageMap[index] = pixels
        }
    }

    fun enhance(imageEnhancementAlgorithm: String, offScreenValue:Boolean = false): ImageInput {
        val localLines = mutableListOf<String>()

        val offsetMagnitude = 6

        for (y in -offsetMagnitude..maxY + offsetMagnitude) {
            val sb = StringBuilder()
            for (x in -offsetMagnitude..maxX + offsetMagnitude) {
                val gridValue = calculateGridValue(x, y, offScreenValue)
                sb.append(imageEnhancementAlgorithm[gridValue])
            }
            localLines.add(sb.toString())
        }

        return ImageInput(localLines)
    }

    fun print() {
        lines.forEach {
            println(it)
        }

        println()
    }

    private fun calculateGridValue(x: Int, y: Int, offScreenValue:Boolean = false): Int {
        var gridValue = 0

        var iter = 0

        for (yOffset in -1..1) {
            for (xOffset in -1..1) {
                if (isLightPixel(x + xOffset, y + yOffset, offScreenValue)) {
                    gridValue += 2.0.pow(8.0 - iter).toInt()
                }
                iter++
            }
        }

        return gridValue
    }

    // If pixel coordinates are out of bounds, it is not lit.
    private fun isLightPixel(x: Int, y: Int, offScreenValue:Boolean = false): Boolean {
        var lightPixel = offScreenValue

        if (x in 0..maxX && y in 0..maxY) {
            lightPixel = imageMap[y][x]
        }

        return lightPixel
    }

    fun countLitPixels(): Int {
        return imageMap.sumOf { it.count { pixel ->
            pixel
        }}
    }
}