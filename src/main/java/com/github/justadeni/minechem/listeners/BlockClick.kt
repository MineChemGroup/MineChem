package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.MineChem
import com.github.justadeni.minechem.misc.Helpers.entities
import de.tr7zw.nbtapi.NBTEntity
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType

object BlockClick : Listener {

    @EventHandler
    fun onBlockClick(e : PlayerInteractEvent){
        if (e.hand != EquipmentSlot.HAND)
            return

        val block = e.clickedBlock ?: return

        if (e.action == Action.LEFT_CLICK_BLOCK) {

            if (block.type != Material.BARRIER)
                return

            if (!block.hasMetadata("uuid0"))
                return

            for (entity in block.location.entities())
                entity?.remove()

            block.type = Material.AIR
        } else if (e.action == Action.RIGHT_CLICK_BLOCK) {

            for (entity in block.location.entities())
                e.player.sendMessage(entity?.persistentDataContainer?.get(NamespacedKey(MineChem.plugin, "custom_string"), PersistentDataType.STRING))
        }
    }
}