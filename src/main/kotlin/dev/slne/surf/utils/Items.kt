package dev.slne.surf.utils

import dev.slne.surf.surfapi.bukkit.api.builder.ItemStack
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag

object Items {

    val VALUATION_ITEM by lazy {
        ItemStack(Material.NAME_TAG) {
            displayName {
                primary("Bewerten")
            }
            buildLore {
                line {
                    variableValue("Linksklick")
                    spacer(" bewertung abbrechen")
                }
                line {
                    variableValue("Rechtsklick")
                    spacer(" bewertung bestätigen")
                }
            }

            addUnsafeEnchantment(Enchantment.LURE, 1)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

}