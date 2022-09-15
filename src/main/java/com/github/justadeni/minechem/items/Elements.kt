package com.github.justadeni.minechem.items

import com.github.justadeni.minechem.enums.MachineEnum
import com.github.justadeni.minechem.misc.Helpers.cap
import com.github.justadeni.minechem.misc.Helpers.clearcolor
import com.github.justadeni.minechem.misc.Helpers.radioactive
import eu.hoefel.chemistry.Element
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

object Elements {

    fun item(id : Int) : ItemStack {
        val element : Element = Element.withAtomicNumber(id)
        val item = ItemStack(Material.SHULKER_SHELL)
        val meta = item.itemMeta
        meta?.setCustomModelData(id)
        meta?.setDisplayName(element.fullName().cap().clearcolor())
        val lore = arrayListOf<String>()

        lore.add("Atomic number: " + element.atomicNumber())
        lore.add("Cathegory: " + element.category().toString().lowercase().cap())
        lore.add("Occurrence: " + element.occurence().toString().lowercase().cap())
        if (element.radioactive())
            lore.add("Radioactive")
        meta?.lore = lore
        item.itemMeta = meta
        return item
    }
}