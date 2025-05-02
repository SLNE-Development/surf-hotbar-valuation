package dev.slne.surf.expansion

import dev.slne.surf.expansion.placeholder.SurfCurrentPlotRatingPlaceholder
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiExpansion
import dev.slne.surf.surfapi.core.api.util.objectListOf

object SurfHotbarValuationPapiExpansion: PapiExpansion(
    "hotbarvaluation",
    objectListOf(SurfCurrentPlotRatingPlaceholder())
)