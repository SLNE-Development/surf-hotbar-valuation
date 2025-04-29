package dev.slne.surf

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import com.github.shynixn.mccoroutine.folia.ticks
import com.nexomc.nexo.api.NexoItems
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.event.unregister
import dev.slne.surf.surfapi.bukkit.api.extensions.server
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.utils.Glyphs
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import net.kyori.adventure.text.Component
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerQuitEvent

abstract class HotbarValuationProcess(
    val valuatedDisplayName: Component,
    val valuator: Player,
    private val minValue: Double = 0.0,
    private val maxValue: Double = 5.0,
    private val valueStepSize: ValueStepSize = ValueStepSize.HALF,
    startValue: Double = minValue,
    private val onStart: () -> Unit = {},
    private val onEnd: () -> Unit = {},
) : Listener {

    init {
        if (minValue > maxValue) {
            throw IllegalArgumentException("Min value $minValue is greater than max value $maxValue")
        }

        if (minValue < 0) {
            throw IllegalArgumentException("Min value $minValue is less than 0")
        }

        if (!valueInBounds(startValue)) {
            throw IllegalArgumentException("Start value $startValue is out of bounds [$minValue, $maxValue]")
        }
    }

    enum class ValueStepSize(val value: Double) {
        ONE(1.0),
        HALF(0.5),
    }

    private lateinit var valuationTask: Job

    private var currentValue: Double = startValue
        private set(value) {
            if (valueInBounds(value)) {
                field = value
            } else {
                throw IllegalArgumentException("Value $value is out of bounds [$minValue, $maxValue]")
            }
        }

    private fun incrementValue() {
        if (currentValue == maxValue) {
            playBuzz()
            return
        }

        NexoItems

        currentValue += valueStepSize.value
        playClick(true)
    }

    private fun decrementValue() {
        if (currentValue == minValue) {
            playBuzz()
            return
        }

        currentValue -= valueStepSize.value
        playClick(false)
    }

    @EventHandler
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
        val newSlot = event.newSlot
        val oldSlot = event.previousSlot
        var change = newSlot - oldSlot

        val changedFromLastToFirst = newSlot == 0 && oldSlot == 8
        val changedFromFirstToLast = newSlot == 8 && oldSlot == 0

        if (changedFromLastToFirst) {
            change = 1
        } else if (changedFromFirstToLast) {
            change = -1
        }

        if (change > 0) {
            decrementValue()
        } else if (change < 0) {
            incrementValue()
        }

        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        event.isCancelled = true

        if (event.action.isLeftClick) {
            end(true)
            return
        }

        end(false)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (event.player == valuator) {
            end(true)
        }
    }

    private fun playClick(positive: Boolean) {
        valuator.playSound {
            type(Sound.BLOCK_COMPARATOR_CLICK)
            source(net.kyori.adventure.sound.Sound.Source.BLOCK)
            pitch(if (positive) 1.0f else 0.5f)
        }
    }

    private fun playBuzz() {
        valuator.playSound {
            type(Sound.ENTITY_VILLAGER_NO)
            source(net.kyori.adventure.sound.Sound.Source.NEUTRAL)
        }
    }

    fun start() {
        onStart()
        register()

        valuationTask = plugin.launch(plugin.entityDispatcher(valuator)) {
            while (true) {
                valuator.sendActionBar(buildStarComponent(Glyphs.STAR_BG_BIG))
                delay(5.ticks)
            }
        }
    }

    private fun end(cancelled: Boolean, broadcast: Boolean = true) {
        onEnd()
        unregister()

        if (!valuationTask.isCompleted) {
            valuationTask.cancel()
        }

        if (cancelled) {
            onCancel(currentValue)

            valuator.sendText {
                appendPrefix()

                success("Du hast die Bewertung abgebrochen.")
            }
        } else {
            onConfirm(currentValue)

            valuator.playSound {
                type(Sound.ENTITY_PLAYER_LEVELUP)
                source(net.kyori.adventure.sound.Sound.Source.PLAYER)
            }

            if (broadcast) {
                server.onlinePlayers.forEach { online ->
                    online.sendText {
                        appendPrefix()

                        variableValue(valuator.name)
                        info(" hat ")
                        append(valuatedDisplayName)
                        info(" mit ")
                        append(buildStarComponent())

                        val formattedCurrentValue = String.format("%.1f", currentValue)
                        val formattedMaxValue = String.format("%.1f", maxValue)

                        variableValue(" ($formattedCurrentValue/$formattedMaxValue) ")
                        info("bewertet.")
                    }
                }
            }
        }
    }

    private fun buildStarComponent(glyphs: Glyphs = Glyphs.STAR_NO_BG) = buildText {
        text(buildStarString(glyphs))
    }

    private fun buildStarString(glyphs: Glyphs = Glyphs.STAR_NO_BG): String {
        val fullStars = currentValue.toInt()
        val hasHalfStar = currentValue % 1 != 0.0
        val totalStars = maxValue.toInt()

        return buildString {
            append(glyphs.getPrefix())
            appendSeparator(this, glyphs)
            append(buildString {
                repeat(fullStars) {
                    append(glyphs.getFullGlyph())
                    appendSeparator(this, glyphs)
                }

                if (hasHalfStar) {
                    append(glyphs.getHalfGlyph())
                    appendSeparator(this, glyphs)
                }

                val emptyStars = totalStars - fullStars - if (hasHalfStar) 1 else 0

                repeat(emptyStars) {
                    append(glyphs.getEmptyGlyph())
                    appendSeparator(this, glyphs)
                }
            })
            appendSeparator(this, glyphs)
            append(glyphs.getSuffix())
        }
    }

    private fun appendSeparator(builder: StringBuilder, glyphs: Glyphs) {
        if (glyphs.getSeparator().isNotEmpty()) {
            builder.append("<shift:-1>")
            builder.append(glyphs.getSeparator())
            builder.append("<shift:-1>")
        } else {
            builder.append("<shift:-1>")
        }
    }

    /**
     * Called when the process is ended and before the listener is unregistered.
     *
     * @param value The value that was selected by the player.
     */
    abstract fun onCancel(value: Double)

    /**
     * Called when the process is ended and before the listener is unregistered.
     *
     * @param value The value that was selected by the player.
     */
    abstract fun onConfirm(value: Double)

    private fun valueInBounds(value: Double) = value in minValue..maxValue
}