package dev.slne.surf.listener

import dev.slne.surf.HotbarValuationProcess
import dev.slne.surf.PossibleValuations
import dev.slne.surf.p2.P2HotbarValuationProcess
import dev.slne.surf.p2.P2Manager
import dev.slne.surf.selectedValuation
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.utils.Items
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

private val valuations = mutableObject2ObjectMapOf<UUID, HotbarValuationProcess>()

object ValuationItemListener : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (!event.action.isRightClick) {
            return
        }

        if (!item.isSimilar(Items.VALUATION_ITEM)) {
            return
        }

        event.isCancelled = true

        if (valuations.containsKey(player.uniqueId)) {
            return
        }

        val process = when (selectedValuation) {
            PossibleValuations.PLOTSQUARED -> {
                val plot = P2Manager.getStandingPlot(player) ?: run {
                    player.sendText {
                        appendPrefix()

                        error("Du stehst auf keinem Grundstück.")
                    }

                    return
                }

                P2HotbarValuationProcess(
                    plot = plot,
                    valuator = player,
                    onEnd = {
                        valuations.remove(player.uniqueId)
                        player.inventory.remove(item)
                    }
                )
            }
        }

        valuations[player.uniqueId] = process

        process.start()
    }

}