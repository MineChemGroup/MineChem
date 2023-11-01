package com.github.justadeni.minechem.data

import com.github.justadeni.standapi.datatype.Rotation
import eu.hoefel.chemistry.Element
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.inventory.Recipe
import kotlin.math.abs
import kotlin.math.floor


object Helpers {

    fun List<Float>.closestValue(value: Float) = minByOrNull {
        abs(value - it)
    }

    fun String.capitalise() : String {
        return this.replaceFirstChar { it.titlecase() }
    }

    fun String.clearcolor() : String {
        return ChatColor.translateAlternateColorCodes('&', "&r$this")
    }

    fun Element.radioactive() : Boolean {
        return (this.atomicNumber() == 43 || this.atomicNumber() == 61 || (84..118).contains(this.atomicNumber()))
    }

    fun Location.center(): Location {
        return Location(this.world, this.x.toInt() + 0.5, this.y.toInt().toDouble(), this.z.toInt() + 0.5)
    }

    fun Location.modify(x: Double, y: Double, z: Double): Location {
        return Location(this.world, this.x + x, this.y + y, this.z + z)
    }

    fun Rotation.modify(deltaPitch: Float, deltaYaw: Float, deltaRoll: Float): Rotation {
        return Rotation(this.pitch + deltaPitch, this.yaw + deltaYaw, this.roll + deltaRoll)
    }

    fun Player.add(amount: Int, item: ItemStack): Boolean {
        val inv = this.inventory
        var amount = amount
        val itemslots = hashMapOf<Int, ItemStack>()
        val emptyslots = mutableListOf<Int>()

        for (i in 0..inv.size) {
            val itemStack = inv.getItem(i)
            if (itemStack == null || itemStack.type.isAir) {
                emptyslots.add(i)
                continue
            }
            if (itemStack.isSimilar(item) && itemStack.amount < 64) {
                itemslots[i] = itemStack
                continue
            }
        }
        //First we put it into slots that contain item already
        for (key in itemslots.keys) {
            if (amount == 0) continue
            val itemStack = itemslots[key]!!
            val amountfree = 64 - itemStack.amount

            //if (amountfree == 0)
            //    continue;
            if (amountfree >= amount) {
                itemStack.amount += amount
                amount = 0
            } else {
                itemStack.amount = 64
                amount -= amountfree
            }
        }
        //Then we put it into free slots
        if (amount != 0) {
            var divided =
                if (amount <= emptyslots.size * 64) floor((amount / 64f).toDouble()).toInt() else emptyslots.size * 64
            var rest = if (amount < emptyslots.size * 64) amount - 64 * divided else 0
            amount -= divided * 64 + rest
            for (slot in emptyslots) {
                if (divided == 0 && rest == 0) break
                val itemStack: ItemStack = item.clone()
                if (divided > 0) {
                    itemStack.amount = 64
                    divided--
                } else if (rest > 0) {
                    itemStack.amount = rest
                    rest = 0
                }
                itemslots[slot] = itemStack
            }
        }

        //We put the items in player's inventory
        for (slot in itemslots.keys) {
            inv.setItem(slot, itemslots[slot])
        }

        //Finally we drop the rest on the ground
        if (amount != 0) {
            val location = this.location.modify(0.0,0.3,0.0)
            val itemStack: ItemStack = item.clone()
            itemStack.amount = if(amount > 64)
                64
            else
                amount
            location.getWorld()!!.dropItemNaturally(location, itemStack)
            return false
        }
        return true
    }
}