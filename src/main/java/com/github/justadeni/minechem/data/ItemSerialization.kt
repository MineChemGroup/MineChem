package com.github.justadeni.minechem.data

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

object ItemSerialization {

    fun toByteArray(inventory: Inventory): ByteArray {
        return try {
            val outputStream = ByteArrayOutputStream()
            val dataOutput = BukkitObjectOutputStream(outputStream)

            // Write the size of the inventory
            dataOutput.writeInt(inventory.size)

            // Save every element in the list
            for (i in 0 until inventory.size) {
                dataOutput.writeObject(inventory.getItem(i))
            }

            // Serialize that array
            dataOutput.close()
            outputStream.toByteArray()
        } catch (e: Exception) {
            throw IllegalStateException("Unable to save item stacks.", e)
        }
    }

    fun fromByteArray(bytes: ByteArray, title : String): Inventory? {
        try {
            val inputStream = ByteArrayInputStream(bytes)
            val dataInput = BukkitObjectInputStream(inputStream)
            val inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(), title)

            // Read the serialized inventory
            for (i in 0 until inventory.size) {
                val itemObject = dataInput.readObject() ?: continue
                val item = itemObject as ItemStack
                inventory.setItem(i, item)
            }
            dataInput.close()
            return inventory
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}