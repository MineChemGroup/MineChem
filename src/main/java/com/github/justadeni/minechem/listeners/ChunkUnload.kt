package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.data.Saver.getLoc
import com.github.justadeni.minechem.data.Saver.hasString
import com.github.justadeni.minechem.machines.Machine
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkUnloadEvent


object ChunkUnload : Listener {

    @EventHandler
    fun onChunkUnload(e : ChunkUnloadEvent){
        for (entity in e.chunk.entities){
            if (entity.type != EntityType.ARMOR_STAND)
                return

            if (entity.hasString("uuid0")) {
                val machine = Machine.get(entity.getLoc()) ?: return
                machine.save()
                machine.unload()
            }
        }
    }
}