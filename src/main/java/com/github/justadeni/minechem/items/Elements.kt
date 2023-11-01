package com.github.justadeni.minechem.items

import com.github.justadeni.minechem.data.Helpers.capitalise
import com.github.justadeni.minechem.data.Helpers.clearcolor
import com.github.justadeni.minechem.data.Helpers.radioactive
import eu.hoefel.chemistry.Element
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

object Elements {

    private val cache = ConcurrentHashMap<Int, ItemStack>()

    fun item(id : Int) : ItemStack {
        if (cache.containsKey(id))
            return cache[id]!!

        val element : Element = Element.withAtomicNumber(id)
        val item = ItemStack(Material.SHULKER_SHELL)
        val meta = item.itemMeta
        meta?.setCustomModelData(id)
        meta?.setDisplayName(element.fullName().capitalise().clearcolor())
        val lore = arrayListOf<String>()

        lore.add(("Atomic number: " + element.atomicNumber()).clearcolor())
        lore.add(("Cathegory: " + element.category().toString().lowercase().capitalise()).clearcolor())
        lore.add(("Occurrence: " + element.occurence().toString().lowercase().capitalise()).clearcolor())
        if (element.radioactive())
            lore.add("Radioactive".clearcolor())
        meta?.lore = lore
        item.itemMeta = meta

        cache[id] = item

        return item
    }
}