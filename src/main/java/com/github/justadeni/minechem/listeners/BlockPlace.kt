package com.github.justadeni.minechem.listeners

import com.github.justadeni.minechem.MineChem
import com.github.justadeni.minechem.enums.MachineEnum
import com.github.justadeni.minechem.data.Saver.putInt
import com.github.justadeni.minechem.data.Saver.putInv
import com.github.justadeni.minechem.data.Saver.putLoc
import com.github.justadeni.minechem.data.Saver.putString
import com.github.justadeni.minechem.machines.Machine
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot
import kotlin.math.abs

object BlockPlace : Listener{

    private fun List<Float>.closestValue(value: Float) = minByOrNull { abs(value - it) }

    private fun ArmorStand.init() : ArmorStand {
        this.setGravity(false)
        this.setBasePlate(false)
        this.removeWhenFarAway = false
        this.isPersistent = true
        this.isInvisible = true
        this.isInvulnerable = true
        this.isSmall = true
        this.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING)
        return this
    }

    @EventHandler
    fun onBlockPlace(e : BlockPlaceEvent){
        if (e.itemInHand.type != Material.STONE)
            return

        val machineid = e.itemInHand.itemMeta?.customModelData ?: return

        if (!MachineEnum.exists(machineid))
            return

        e.block.type = Material.BARRIER

        val loc = e.block.location
        var mainstand : ArmorStand = loc.world?.spawnEntity(Location(loc.world, loc.x,loc.y+100,loc.z),EntityType.ARMOR_STAND) as ArmorStand
        mainstand.equipment?.setHelmet(e.itemInHand, true)
        mainstand = mainstand.init()
        val yaw = e.player.location.yaw
        val angles = arrayListOf(0f,90f,180f,-90f)
        val mainstandyaw : Float = angles.closestValue(yaw)!!
        mainstand.teleport(Location(loc.world, loc.x+0.5,loc.y,loc.z+0.5, mainstandyaw, loc.pitch))

        val jointid = MachineEnum.joint(machineid)
        if (jointid != 0){
            var jointstand : ArmorStand = loc.world?.spawnEntity(Location(loc.world,loc.x,loc.y+100,loc.z),EntityType.ARMOR_STAND) as ArmorStand
            jointstand.equipment?.setHelmet(MachineEnum.item(jointid), true)
            jointstand = jointstand.init()

            val jointstandyaw = if (jointid == 4){
                when (mainstandyaw) {
                    0f -> 180f
                    90f -> -90f
                    180f -> 0f
                    -90f -> 90f
                    else -> mainstandyaw
                }
            } else {
                mainstandyaw
            }

            jointstand.teleport(Location(loc.world,loc.x+0.5,loc.y,loc.z+0.5, jointstandyaw, loc.pitch))
            mainstand.putString("uuid1", jointstand.uniqueId.toString())
        } else {
            mainstand.putString("uuid1", "")
        }
        mainstand.putString("uuid0", mainstand.uniqueId.toString())
        mainstand.putLoc(loc)
        mainstand.putInv(Bukkit.createInventory(null, 36, MachineEnum.name(machineid)))
        mainstand.putInt("machineid", machineid)
        mainstand.putInt("energy", 0)
        Machine.load(loc)
        val machine = Machine.get(loc)!!
        machine.save()
    }
}