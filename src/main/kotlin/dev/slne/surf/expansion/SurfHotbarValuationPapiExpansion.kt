package dev.slne.surf.expansion

import dev.slne.surf.expansion.placeholder.SurfCurrentPlotRatingGlyphsPlaceholder
import dev.slne.surf.expansion.placeholder.SurfCurrentPlotRatingValuePlaceholder
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiExpansion

class SurfHotbarValuationPapiExpansion: PapiExpansion (
    "surf_hotbar_valuation",
    listOf(SurfCurrentPlotRatingGlyphsPlaceholder(), SurfCurrentPlotRatingValuePlaceholder()),
    "ammo & red",
    "1.0.0",
    "SurfHotbarValuation",
)