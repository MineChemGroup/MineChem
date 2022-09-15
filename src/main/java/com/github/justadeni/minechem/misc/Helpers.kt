package com.github.justadeni.minechem.misc

import eu.hoefel.chemistry.Element
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity
import java.util.*
import kotlin.collections.ArrayList

object Helpers {

    fun String.cap() : String{
        return this.replaceFirstChar { it.titlecase() }
    }

    fun String.clearcolor() : String{
        return ChatColor.translateAlternateColorCodes('&', "&r$this")
    }

    fun Element.radioactive() : Boolean{
        return (this.atomicNumber() == 43 || this.atomicNumber() == 61 || (84..118).contains(this.atomicNumber()))
    }

    fun Location.entities() : ArrayList<CraftEntity?> {
        val list = arrayListOf<CraftEntity?>()
        val world = (this.block.world as CraftWorld).handle
        if (block.hasMetadata("uuid0"))
            list.add(world.getEntity(this.block.getMetadata("uuid0")[0].value() as UUID)?.bukkitEntity)
        if (block.hasMetadata("uuid1"))
            list.add(world.getEntity(this.block.getMetadata("uuid1")[0].value() as UUID)?.bukkitEntity)
        return list
    }
}