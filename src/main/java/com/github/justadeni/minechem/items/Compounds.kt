package com.github.justadeni.minechem.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

object Compounds {

    private val cache = ConcurrentHashMap<Int, ItemStack>()

    fun item(id : Int) : ItemStack {
        if (cache.containsKey(id))
            return cache[id]!!

        val item = ItemStack(Material.POPPED_CHORUS_FRUIT)
        val meta = item.itemMeta
        meta?.setCustomModelData(id)
        meta?.setDisplayName("Compound $id")
        item.itemMeta = meta

        cache[id] = item

        return item
    }

}