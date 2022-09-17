package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.machines.Machine
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

object InventoryClose : Listener {

    @EventHandler
    fun onInventoryClose(e : InventoryCloseEvent){
        for (machine in Machine.list)
            if (e.inventory == machine.inventory)
                machine.save()
    }

}