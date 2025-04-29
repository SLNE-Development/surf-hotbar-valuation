package dev.slne.surf.utils

enum class Glyphs {
    STAR_NO_BG {
        override fun getFullGlyph() = "<glyph:star_full>"
        override fun getHalfGlyph() = "<glyph:star_half>"
        override fun getQuarterGlyph() = "<glyph:star_quarter>"
        override fun getEmptyGlyph() = "<glyph:star_empty>"
        override fun getPrefix() = ""
        override fun getSuffix() = ""
        override fun getSeparator() = " "
    },
    STAR_BG {
        override fun getFullGlyph() = "<glyph:star_bg_full>"
        override fun getHalfGlyph() = "<glyph:star_bg_half>"
        override fun getQuarterGlyph() = "<glyph:star_bg_quarter>"
        override fun getEmptyGlyph() = "<glyph:star_bg_empty>"
        override fun getPrefix() = "<glyph:star_bg_prefix>"
        override fun getSuffix() = "<glyph:star_bg_suffix>"
        override fun getSeparator() = ""
    },
    STAR_NO_BG_BIG {
        override fun getFullGlyph() = "<glyph:star_full_big>"
        override fun getHalfGlyph() = "<glyph:star_half_big>"
        override fun getQuarterGlyph() = "<glyph:star_quarter_big>"
        override fun getEmptyGlyph() = "<glyph:star_empty_big>"
        override fun getPrefix() = ""
        override fun getSuffix() = ""
        override fun getSeparator() = " "
    },
    STAR_BG_BIG {
        override fun getFullGlyph() = "<glyph:star_bg_full_big>"
        override fun getHalfGlyph() = "<glyph:star_bg_half_big>"
        override fun getQuarterGlyph() = "<glyph:star_bg_quarter_big>"
        override fun getEmptyGlyph() = "<glyph:star_bg_empty_big>"
        override fun getPrefix() = "<glyph:star_bg_prefix_big>"
        override fun getSuffix() = "<glyph:star_bg_suffix_big>"
        override fun getSeparator() = ""
    };

    abstract fun getFullGlyph(): String
    abstract fun getHalfGlyph(): String
    abstract fun getQuarterGlyph(): String
    abstract fun getEmptyGlyph(): String

    abstract fun getPrefix(): String
    abstract fun getSuffix(): String
    abstract fun getSeparator(): String

}