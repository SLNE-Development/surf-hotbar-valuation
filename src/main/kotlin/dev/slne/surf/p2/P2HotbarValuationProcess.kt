package dev.slne.surf.p2

import com.plotsquared.core.plot.Plot
import com.plotsquared.core.plot.Rating
import dev.slne.surf.HotbarValuationProcess
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.utils.Items
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class P2HotbarValuationProcess(
    private val plot: Plot,
    valuator: Player,
    onEnd: () -> Unit = {},
) : HotbarValuationProcess(
    buildText {
        var ownerName = "Unbekannt"
        if (plot.owner != null) {
            ownerName = Bukkit.getOfflinePlayer(plot.owner!!).name ?: "Unbekannt"
        }

        variableValue(ownerName)
    },
    valuator,
    minValue = 0.5,
    maxValue = 5.0,
    startValue = plot.ratings[valuator.uniqueId]?.averageRating ?: 0.5,
    valueStepSize = ValueStepSize.HALF,
    onEnd = onEnd,
) {
    /**
     * Plot ratings are only 0-5 with half steps so we can safely multiply by 2
     * to get the value in the range of 0-10.
     */
    override fun onConfirm(value: Double) {
        if (plot.ratings.containsKey(valuator.uniqueId)) {
            return
        }

        plot.addRating(valuator.uniqueId, Rating((value * 2).toInt()))

        valuator.inventory.removeItem(Items.VALUATION_ITEM)
    }


    override fun onCancel(value: Double) {
        // Do nothing
    }
}