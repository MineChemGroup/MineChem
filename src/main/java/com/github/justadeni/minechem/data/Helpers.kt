package com.github.justadeni.minechem.data

import eu.hoefel.chemistry.Element
import net.minecraft.world.entity.Entity
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity
import java.util.*

object Helpers {

    fun String.cap() : String{
        return this.replaceFirstChar { it.titlecase() }
    }

    fun String.clearcolor() : String{
        return ChatColor.translateAlternateColorCodes('&', "&r$this")
    }

    fun String.removeEntity(loc: Location) {
        val world = (loc.world as CraftWorld).handle
        world.getEntity(UUID.fromString(this))?.remove(Entity.RemovalReason.DISCARDED)
    }

    fun Element.radioactive() : Boolean{
        return (this.atomicNumber() == 43 || this.atomicNumber() == 61 || (84..118).contains(this.atomicNumber()))
    }

    fun getEntity(loc : Location): CraftEntity? {
        val world = (loc.world as CraftWorld).handle
        return world.getEntity(loc.block.getMetadata("uuid0")[0].value() as UUID)?.bukkitEntity
    }
}