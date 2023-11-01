package com.github.justadeni.minechem.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BlockPlace : Listener{

    @EventHandler
    fun onBlockPlace(e : BlockPlaceEvent){
        if (e.itemInHand.type != Material.STONE)
            return

        val machineid = e.itemInHand.itemMeta?.customModelData ?: return

        e.block.type = Material.BARRIER

        val loc = e.block.location

    }
}