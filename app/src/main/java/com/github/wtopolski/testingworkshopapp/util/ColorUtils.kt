package com.github.wtopolski.testingworkshopapp.util

class ColorUtils {

    fun generateColor(red: Int, green: Int, blue: Int, coloration: ColorationType): String {
        when (coloration) {
            ColorationType.COLOR -> return generateRealColor(red, green, blue)
            ColorationType.BLACK_WHITE -> return generateBlackWhite(red, green, blue)
            ColorationType.SEPIA -> return generateSepia(red, green, blue)
            ColorationType.INVERSE -> return generateInverse(red, green, blue)
            else -> return generateRealColor(red, green, blue)
        }
    }

    private fun generateInverse(red: Int, green: Int, blue: Int): String {
        val localRed = 255 - red
        val localGreen = 255 - green
        val localBlue = 255 - blue
        return String.format("#%02X%02X%02X", localRed, localGreen, localBlue)
    }

    private fun generateSepia(red: Int, green: Int, blue: Int): String {
        val sepiaDepth = 20
        val gry = (red + green + blue) / 3

        var newBlue = gry
        var newGreen = gry
        var newRed = gry

        newRed += sepiaDepth * 2
        newGreen += sepiaDepth

        newRed = normalize(newRed)
        newGreen = normalize(newGreen)
        newBlue = normalize(newBlue)

        return String.format("#%02X%02X%02X", newRed, newGreen, newBlue)
    }

    private fun normalize(color: Int): Int {
        var localColor = color
        if (color > 255) {
            localColor = 255
        } else if (color < 0) {
            localColor = 0
        }
        return localColor
    }

    private fun generateRealColor(red: Int, green: Int, blue: Int): String {
        return String.format("#%02X%02X%02X", red, green, blue)
    }

    private fun generateBlackWhite(red: Int, green: Int, blue: Int): String {
        val localRed = (red * 0.299).toInt()
        val localGreen = (green * 0.587).toInt()
        val localBlue = (blue * 0.114).toInt()
        val result = localRed + localGreen + localBlue
        return String.format("#%02X%02X%02X", result, result, result)
    }
}