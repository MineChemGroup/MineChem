package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.data.Saver.getLoc
import com.github.justadeni.minechem.data.Saver.hasString
import com.github.justadeni.minechem.machines.Machine
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent

object ChunkLoad : Listener {

    @EventHandler
    fun onChunkLoad(e : ChunkLoadEvent){
        for (entity in e.chunk.entities){
            if (entity.type != EntityType.ARMOR_STAND)
                return

            println("debug0")
            if (entity.hasString("uuid0")){
                println("debug1")
                Machine.load(entity.getLoc())
                println("debug2")
            }
        }
    }
}