package dev.slne.surf

import dev.slne.surf.expansion.SurfHotbarValuationPapiExpansion
import dev.slne.surf.listener.ValuationItemListener
import dev.slne.surf.p2.P2Manager
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.hook.papi.papiHook
import org.bukkit.plugin.java.JavaPlugin

val plugin: HotbarValuationPlugin get() = JavaPlugin.getPlugin(HotbarValuationPlugin::class.java)
val selectedValuation = PossibleValuations.PLOTSQUARED

class HotbarValuationPlugin : JavaPlugin() {

    override fun onEnable() {
        if (!selectedValuation.isPluginEnabled()) {
            logger.warning("Plugin ${selectedValuation.requiredPluginName} is not enabled. Disabling HotbarValuationPlugin.")
            server.pluginManager.disablePlugin(this)
        }

        if (PossibleValuations.PLOTSQUARED.isPluginEnabled()) {
            P2Manager.register()
        }

        ValuationItemListener.register()

        papiHook.register(SurfHotbarValuationPapiExpansion)
    }
}
