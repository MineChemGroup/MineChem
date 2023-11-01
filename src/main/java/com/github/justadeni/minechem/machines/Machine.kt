package com.github.justadeni.minechem.machines

import com.github.justadeni.minechem.data.Helpers.center
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class Machine(location: Location) {

    val location = location.center()
    abstract val item: ItemStack
    //var energy = 0
    private val inputs = mutableListOf<ItemStack>()
    private val outputs = mutableListOf<ItemStack>()

    fun rotate(){
        //TODO: implement
    }

    abstract fun tick()

    abstract fun destroy()

    abstract fun inventory(player: Player)
}