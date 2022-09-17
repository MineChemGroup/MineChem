package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.machines.Machine
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object BlockClick : Listener {

    @EventHandler
    fun onBlockClick(e : PlayerInteractEvent){
        if (e.hand != EquipmentSlot.HAND)
            return

        val block = e.clickedBlock ?: return
        val loc = block.location

        if (block.type != Material.BARRIER)
            return

        if (e.action == Action.LEFT_CLICK_BLOCK) {

            val machine = Machine.get(loc) ?: return
            machine.delete()
            block.type = Material.AIR
        } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
            Machine.load(loc)
            val machine = Machine.get(loc) ?: return
            e.player.openInventory(machine.inventory)
        }
    }
}