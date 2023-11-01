package com.github.justadeni.minechem.machines

import com.github.justadeni.minechem.MineChem
import com.zorbeytorunoglu.kLib.extensions.setLore
import me.xflyiwnl.colorfulgui.`object`.Gui
import me.xflyiwnl.colorfulgui.`object`.StaticItem
import me.xflyiwnl.colorfulgui.provider.ColorfulProvider
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Microscope(location: Location): Machine(location) {

    override val item: ItemStack
        get() {
            val item = ItemStack(Material.STONE)
            val meta = item.itemMeta!!
            meta.setCustomModelData(1)
            meta.setDisplayName("Microscope")
            meta.lore = listOf("Allows you to observe structure of items and molecules")
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            item.itemMeta = meta
            return item
        }

    override fun tick() {

    }

    override fun destroy() {
        TODO("Not yet implemented")
    }

    override fun inventory(player: Player) {
        MineChem.colorfulGUI.gui()
            .title("Test gui!")
            .rows(5)
            .mask(
                listOf(
                "BBBBBBBBB",
                "BOOOOOOOB",
                "BOOOOOOOB",
                "BOOOOOOOB",
                "BBBBBBBBB"
            )
            )
            .holder(MicroscopeInventory(player))
            .build();
    }

    class MicroscopeInventory(player: Player): ColorfulProvider<Gui>(player) {

        override fun init() {
            val barrierItem: StaticItem = MineChem.colorfulGUI
                .staticItem()
                .material(Material.BLACK_STAINED_GLASS_PANE)
                .name("Barrier item")
                .build()
            gui.addMask("B", barrierItem)
        }

        override fun onOpen(event: InventoryOpenEvent) {

        }
    }

}