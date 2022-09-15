package com.github.justadeni.minechem.machines

import com.github.justadeni.minechem.MineChem
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.math.roundToInt

object Data {

    /**
     * checks if entity has string or int stored under specified key
     * @key         all keys are strings
     */
    fun Entity.has(key: String) : Boolean{
        val nsk = NamespacedKey(MineChem.plugin, key)
        with(this.persistentDataContainer){
            return has(nsk, PersistentDataType.STRING) || has(nsk, PersistentDataType.INTEGER)
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
            set(NamespacedKey(MineChem.plugin, "world"), PersistentDataType.STRING, value.world!!.uid.toString())
            set(NamespacedKey(MineChem.plugin, "x"), PersistentDataType.INTEGER, value.x.roundToInt())
            set(NamespacedKey(MineChem.plugin, "y"), PersistentDataType.INTEGER, value.y.roundToInt())
            set(NamespacedKey(MineChem.plugin, "z"), PersistentDataType.INTEGER, value.z.roundToInt())
        }
    }

    fun Entity.getString(key : String) : String?{
        return this.persistentDataContainer.get(NamespacedKey(MineChem.plugin, key), PersistentDataType.STRING)
    }

    fun Entity.getInt(key: String) : Int?{
        return this.persistentDataContainer.get(NamespacedKey(MineChem.plugin, key), PersistentDataType.INTEGER)
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