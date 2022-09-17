package com.github.justadeni.minechem.machines

import com.github.justadeni.minechem.data.Saver.getInt
import com.github.justadeni.minechem.data.Saver.getInv
import com.github.justadeni.minechem.data.Saver.getString
import com.github.justadeni.minechem.data.Saver.putInt
import com.github.justadeni.minechem.data.Saver.putInv
import com.github.justadeni.minechem.data.Helpers.getEntity
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity
import org.bukkit.inventory.Inventory
import java.util.*
import kotlin.collections.ArrayList

abstract class Machine(val uuid0: String,
                       val uuid1: String,
                       val inventory: Inventory,
                       val energy: Int,
                       val loc: Location) {

    companion object {
        val list = arrayListOf<Machine>()

        fun get(loc: Location) : Machine?{
            for (i in list)
                if (i.loc == loc)
                    return i

            return null
        }

        fun load(loc: Location){
            with(getEntity(loc)!!) {
                when (getInt("machineid")) {
                    1 -> list.add(Microscope(getString("uuid0"), getString("uuid1"), getInv(), getInt("energy"), loc))
                    2 -> list.add(Decomposer(getString("uuid0"), getString("uuid1"), getInv(), getInt("energy"), loc))
                    4 -> list.add(Synthesizer(getString("uuid0"), getString("uuid1"), getInv(), getInt("energy"), loc))
                    else -> {}
                }
            }
        }

    }

    fun delete(){
        for (entity in getentitties())
            entity?.remove()
        list.remove(this)
    }

    fun save(){
        val entity0 = getentity0()
        entity0?.putInv(inventory)
        entity0?.putInt("energy", energy)
    }

    fun unload(){
        list.remove(this)
    }

    fun getentity0(): CraftEntity?{
        val world = (loc.world as CraftWorld).handle
        return world.getEntity(UUID.fromString(uuid0))?.bukkitEntity
    }

    fun getentitties(): ArrayList<CraftEntity?>{
        val world = (loc.world as CraftWorld).handle
        val entitylist = arrayListOf<CraftEntity?>()
        entitylist.add(world.getEntity(UUID.fromString(uuid0))?.bukkitEntity)
        if (uuid1 != "")
            entitylist.add(world.getEntity(UUID.fromString(uuid1))?.bukkitEntity)
        return entitylist
    }

    abstract fun tick()

    abstract fun move()

    abstract fun openinv()
}