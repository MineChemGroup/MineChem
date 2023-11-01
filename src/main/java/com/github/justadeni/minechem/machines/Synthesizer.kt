package com.github.justadeni.minechem.machines

import com.zorbeytorunoglu.kLib.extensions.name
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Synthesizer(location: Location) : Machine(location) {

    override val item: ItemStack
        get() {
            val item = ItemStack(Material.STONE)
            val meta = item.itemMeta!!
            meta.setCustomModelData(4)
            meta.setDisplayName("Synthesizer")
            meta.lore = listOf("Allows you to combine elements and molecules")
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