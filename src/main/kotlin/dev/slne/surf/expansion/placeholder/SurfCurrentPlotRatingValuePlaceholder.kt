package dev.slne.surf.expansion.placeholder

import dev.slne.surf.PossibleValuations
import dev.slne.surf.p2.P2Manager
import dev.slne.surf.selectedValuation
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class SurfCurrentPlotRatingValuePlaceholder(): PapiPlaceholder("current_rating_value") {
    override fun parse (
        player: OfflinePlayer,
        args: List<String>
    ): String {
        return when(selectedValuation) {
            PossibleValuations.PLOTSQUARED -> {
                val user = player as? Player ?: return ""
                val plot = P2Manager.getStandingPlot(user) ?: return ""

                (plot.averageRating * 2).toString()
            }
        }
    }
}