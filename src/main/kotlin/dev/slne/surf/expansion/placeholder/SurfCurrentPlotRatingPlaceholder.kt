package dev.slne.surf.expansion.placeholder

import dev.slne.surf.PossibleValuations
import dev.slne.surf.p2.P2Manager
import dev.slne.surf.selectedValuation
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import dev.slne.surf.utils.Glyphs
import dev.slne.surf.utils.buildStarString
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class SurfCurrentPlotRatingPlaceholder(): PapiPlaceholder("rating") {
    override fun parse (
        player: OfflinePlayer,
        args: List<String>
    ): String {
        return when(selectedValuation) {
            PossibleValuations.PLOTSQUARED -> {
                val user = player as? Player ?: return ""
                val plot = P2Manager.getStandingPlot(user) ?: return ""
                val average = plot.averageRating

                buildStarString(Glyphs.STAR_NO_BG, average)
            }
        }
    }
}