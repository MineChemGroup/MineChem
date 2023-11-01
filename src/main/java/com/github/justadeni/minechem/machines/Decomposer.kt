package com.github.justadeni.minechem.machines

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Decomposer(location: Location) : Machine(location) {

    override val item: ItemStack
        get() {
            val item = ItemStack(Material.STONE)
            val meta = item.itemMeta!!
            meta.setCustomModelData(2)
            meta.setDisplayName("Decomposer")
            meta.lore = listOf("Allows you to decompose items and molecules")
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            item.itemMeta = meta
            return item
        }

    override fun tick() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }


    override fun inventory(player: Player) {
        TODO("Not yet implemented")
    }

}