package dev.slne.surf.p2.listener

import com.google.common.eventbus.Subscribe
import com.plotsquared.bukkit.util.BukkitUtil
import com.plotsquared.core.PlotAPI
import com.plotsquared.core.events.PlayerEnterPlotEvent
import com.plotsquared.core.events.PlayerLeavePlotEvent
import com.plotsquared.core.plot.Plot
import com.plotsquared.core.plot.flag.implementations.DoneFlag
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.utils.Items
import dev.slne.surf.utils.ValuationPermissionRegistry
import org.bukkit.entity.Player

object P2Manager {

    private val plotApi by lazy {
        PlotAPI()
    }

    fun register() {
        plotApi.registerListener(this)
    }

    fun getStandingPlot(player: Player): Plot? {
        val plotPlayer = BukkitUtil.adapt(player)
        val location = plotPlayer.location
        val area = location.plotArea ?: return null

        return area.getPlot(location)
    }

    @Subscribe
    fun PlayerEnterPlotEvent.onPlotEnter() {
        val done = DoneFlag.isDone(plot)

        if (!done) {
            return
        }

        val player = plotPlayer.platformPlayer as Player

        if (!player.hasPermission(ValuationPermissionRegistry.CAN_VALUATE)) {
            return
        }

        if (plot.ratings.containsKey(plotPlayer.uuid)) {
            player.sendText {
                appendPrefix()
                
                info("Du hast bereits eine Bewertung für diesen Plot abgegeben.")
            }

            return
        }

        player.inventory.setItem(4, Items.VALUATION_ITEM)
    }

    @Subscribe
    fun PlayerLeavePlotEvent.onPlotLeave() {
        val player = plotPlayer.platformPlayer as Player

        player.inventory.removeItem(Items.VALUATION_ITEM)
    }

}