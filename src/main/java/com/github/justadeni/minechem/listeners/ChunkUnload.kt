package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.misc.Helpers.entities
import de.tr7zw.nbtapi.NBTEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkUnloadEvent


object ChunkUnload : Listener {

    @EventHandler
    fun onChunkUnload(e : ChunkUnloadEvent){

    }
}