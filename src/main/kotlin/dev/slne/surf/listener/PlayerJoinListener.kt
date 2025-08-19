package dev.slne.surf.listener

import com.plotsquared.core.PlotSquared
import com.plotsquared.core.player.PlotPlayer
import com.plotsquared.core.plot.Plot
import com.plotsquared.core.plot.PlotArea
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (player.hasPermission("surf.autoplot.bypass")) return

        val plotPlayer = PlotPlayer.from(player) ?: return
        val plots = plotPlayer.plots

        if (!plots.isEmpty()) return

        val plotArea: PlotArea = PlotSquared.get().plotAreaManager.allPlotAreas.firstOrNull() ?: return
        val freePlot: Plot? = plotArea.getNextFreePlot(plotPlayer, null)

        if (freePlot == null) return

        freePlot.claim(plotPlayer, true, null, true, true)
    }
}
