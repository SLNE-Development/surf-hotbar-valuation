package dev.slne.surf

import dev.slne.surf.surfapi.bukkit.api.extensions.server

enum class PossibleValuations(
    val requiredPluginName: String
) {
    PLOTSQUARED("PlotSquared");

    fun isPluginEnabled() = server.pluginManager.isPluginEnabled(requiredPluginName)
}