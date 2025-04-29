package dev.slne.surf.utils

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object ValuationPermissionRegistry : PermissionRegistry() {

    val CAN_VALUATE = create("surf.valuation.can_valuate")

}