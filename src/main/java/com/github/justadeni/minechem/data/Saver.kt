package com.github.justadeni.minechem.data

import com.github.justadeni.minechem.MineChem
import com.github.justadeni.minechem.enums.MachineEnum
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.inventory.Inventory
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.math.roundToInt


object Saver {


    fun Entity.hasString(key: String) : Boolean{
        val nsk = NamespacedKey(MineChem.plugin, key)
        with(this.persistentDataContainer){
            return this.has(nsk, PersistentDataType.STRING)
        }
    }

    fun Entity.putString(key : String, value : String){
        this.persistentDataContainer.set(NamespacedKey(MineChem.plugin, key), PersistentDataType.STRING, value)
    }

    fun Entity.putInt(key : String, value : Int){
        this.persistentDataContainer.set(NamespacedKey(MineChem.plugin, key), PersistentDataType.INTEGER, value)
    }

    fun Entity.putLoc(value : Location){
        with(this.persistentDataContainer) {
            set(NamespacedKey(MineChem.plugin, "world"), PersistentDataType.STRING, value.world!!.uid.toString().lowercase())
            set(NamespacedKey(MineChem.plugin, "x"), PersistentDataType.INTEGER, value.x.roundToInt())
            set(NamespacedKey(MineChem.plugin, "y"), PersistentDataType.INTEGER, value.y.roundToInt())
            set(NamespacedKey(MineChem.plugin, "z"), PersistentDataType.INTEGER, value.z.roundToInt())
        }
    }

    fun Entity.putInv(inventory : Inventory){
        val nsk = NamespacedKey(MineChem.plugin, "inventory")
        persistentDataContainer.set(nsk, PersistentDataType.BYTE_ARRAY, ItemSerialization.toByteArray(inventory))
    }

    fun Entity.getInv() : Inventory{
        val nsk = NamespacedKey(MineChem.plugin, "inventory")
        return ItemSerialization.fromByteArray(persistentDataContainer.get(nsk, PersistentDataType.BYTE_ARRAY)!!, MachineEnum.name(getInt("machineid")))!!
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
}