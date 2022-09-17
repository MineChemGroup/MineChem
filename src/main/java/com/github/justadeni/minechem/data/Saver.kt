package com.github.justadeni.minechem.data

import com.github.justadeni.minechem.MineChem
import com.github.justadeni.minechem.enums.MachineEnum
import org.apache.commons.codec.binary.Base32
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.math.roundToInt


object Saver {

    /**
     * checks if entity has string or int stored under specified key
     * @key         all keys are strings
     */
    fun Entity.hasString(key: String) : Boolean{
        val nsk = NamespacedKey(MineChem.plugin, key)
        with(this.persistentDataContainer){
            return this.has(nsk, PersistentDataType.STRING)
        }
    }

    /**
     * puts key and value into entity nbt
     */
    fun Entity.putString(key : String, value : String){
        this.persistentDataContainer.set(NamespacedKey(MineChem.plugin, key), PersistentDataType.STRING, value)
    }

    /**
     * puts key and value into entity nbt
     */
    fun Entity.putInt(key : String, value : Int){
        this.persistentDataContainer.set(NamespacedKey(MineChem.plugin, key), PersistentDataType.INTEGER, value)
    }

    /**
     * puts location of machine block into entity nbt with 4 tags
     */
    fun Entity.putLoc(value : Location){
        with(this.persistentDataContainer) {
            set(NamespacedKey(MineChem.plugin, "world"), PersistentDataType.STRING, value.world!!.uid.toString().lowercase())
            set(NamespacedKey(MineChem.plugin, "x"), PersistentDataType.INTEGER, value.x.roundToInt())
            set(NamespacedKey(MineChem.plugin, "y"), PersistentDataType.INTEGER, value.y.roundToInt())
            set(NamespacedKey(MineChem.plugin, "z"), PersistentDataType.INTEGER, value.z.roundToInt())
        }
    }

    fun Entity.putInv(inventory : Inventory){
        //with(this.persistentDataContainer) {
            var positions = ""
            for (i in 0..35) {
                val item = inventory.getItem(i)
                if (item != null && !item.type.isAir) {
                    positions += i.toString()
                    if (i < 35)
                        positions += "-"

                    putString(i.toString(), Conversor.serializeItemStack(item))
                }
            }
            putString("positions", positions)
        //}
    }

    fun Entity.getInv() : Inventory{
        //with(this.persistentDataContainer){
            val positions = arrayListOf<Int>().apply {
                for (i in getString("positions").split("-"))
                    add(i.toInt())
            }
            val inventory = Bukkit.createInventory(null, 36, MachineEnum.name(getInt("machineid")))
            for (i in positions){
                inventory.setItem(i, Conversor.deserializeItemStack(getString(i.toString())))
            }
            return inventory
        //}
    }

    fun Entity.getString(key : String) : String{
        return this.persistentDataContainer.get(NamespacedKey(MineChem.plugin, key), PersistentDataType.STRING)!!
    }

    fun Entity.getInt(key: String) : Int{
        return this.persistentDataContainer.get(NamespacedKey(MineChem.plugin, key), PersistentDataType.INTEGER)!!
    }

    fun Entity.getLoc() : Location {
        with(this.persistentDataContainer) {
            val world = Bukkit.getWorld(UUID.fromString(get(NamespacedKey(MineChem.plugin, "world"), PersistentDataType.STRING)))
            val x = get(NamespacedKey(MineChem.plugin, "x"), PersistentDataType.INTEGER)
            val y = get(NamespacedKey(MineChem.plugin, "y"), PersistentDataType.INTEGER)
            val z = get(NamespacedKey(MineChem.plugin, "z"), PersistentDataType.INTEGER)

            return Location(world, x!!.toDouble(), y!!.toDouble(), z!!.toDouble())
        }
    }

    fun Entity.getJoint(): CraftEntity? {
        val world = (world as CraftWorld).handle
        return world.getEntity(UUID.fromString(getString("uuid1")))?.bukkitEntity
    }
}