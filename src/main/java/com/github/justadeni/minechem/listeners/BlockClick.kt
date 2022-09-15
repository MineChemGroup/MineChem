package com.github.justadeni.minechem.listeners

import net.minecraft.world.entity.Entity
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.UUID

object BlockClick : Listener {

    @EventHandler
    fun onBlockClick(e : PlayerInteractEvent){
        if (e.hand != EquipmentSlot.HAND)
            return

        if (e.action != Action.LEFT_CLICK_BLOCK)
            return

        val block = e.clickedBlock!!

        if (block.type != Material.BARRIER)
            return

        if (!block.hasMetadata("uuid0"))
            return

        val uuid0 = block.getMetadata("uuid0")[0].value() as UUID
        val world = (block.world as CraftWorld).handle
        world.getEntity(uuid0)?.remove(Entity.RemovalReason.DISCARDED)
        if (block.hasMetadata("uuid1"))
            world.getEntity((block.getMetadata("uuid1")[0].value() as UUID))?.remove(Entity.RemovalReason.DISCARDED)
        BlockPlace.locs.remove(block.location)
        block.type = Material.AIR
    }
}