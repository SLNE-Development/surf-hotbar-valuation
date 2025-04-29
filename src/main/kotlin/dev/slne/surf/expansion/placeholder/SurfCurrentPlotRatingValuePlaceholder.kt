package dev.slne.surf.expansion.placeholder

import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import org.bukkit.OfflinePlayer

class SurfCurrentPlotRatingValuePlaceholder(): PapiPlaceholder("current_rating_value") {
    override fun parse (
        player: OfflinePlayer,
        args: List<String>
    ): String {
        TODO("Not yet implemented")
    }
}