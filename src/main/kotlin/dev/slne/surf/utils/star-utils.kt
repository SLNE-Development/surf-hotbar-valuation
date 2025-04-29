package dev.slne.surf.utils

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

fun buildStarString(glyphs: Glyphs = Glyphs.STAR_NO_BG, value: Double, maxValue: Double = 5.0): String {
    val fullStars = value.toInt()
    val hasHalfStar = value % 1 != 0.0
    val totalStars = maxValue.toInt()

    return buildString {
        append(glyphs.getPrefix())
        appendSeparator(this, glyphs)
        append(buildString {
            repeat(fullStars) {
                append(glyphs.getFullGlyph())
                appendSeparator(this, glyphs)
            }

            if (hasHalfStar) {
                append(glyphs.getHalfGlyph())
                appendSeparator(this, glyphs)
            }

            val emptyStars = totalStars - fullStars - if (hasHalfStar) 1 else 0

            repeat(emptyStars) {
                append(glyphs.getEmptyGlyph())
                appendSeparator(this, glyphs)
            }
        })
        appendSeparator(this, glyphs)
        append(glyphs.getSuffix())
    }
}

private fun appendSeparator(builder: StringBuilder, glyphs: Glyphs) {
    if (glyphs.getSeparator().isNotEmpty()) {
        builder.append("<shift:-1>")
        builder.append(glyphs.getSeparator())
        builder.append("<shift:-1>")
    } else {
        builder.append("<shift:-1>")
    }
}

fun Double.roundToHalf(): Double {
    println("Rounding $this to half")

    val rounded = BigDecimal(this).setScale(1, RoundingMode.HALF_UP).toDouble()
    val base = floor(rounded)
    val decimal = rounded - base

    println("Rounded $this to $rounded")

    return if (decimal < 0.5) base else base + 0.5
}