package com.github.justadeni.minechem.enums

import com.github.justadeni.minechem.misc.Helpers.clearcolor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack


enum class MachineEnum(val id : Int, val displayname : String, val joint : Int) {
    MICROSCOPE(1, "Microscope", 0),
    DECOMPOSER_1(2, "Decomposer", 3),
    DECOMPOSER_2(3, "", 0),
    SYNTHESIZER_1(4, "Synthesizer", 5),
    SYNTHESIZER_2(5, "", 0);

    companion object {

        private fun name(id : Int) : String{
            for (machine in MachineEnum.values())
                if (machine.id == id)
                    return machine.displayname

            return ""
        }

        fun exists(id: Int) : Boolean{
            for (machine in MachineEnum.values())
                if (machine.id == id)
                    return true

            return false
        }

        fun item(machine: MachineEnum) : ItemStack{
            return item(machine.id)
        }

        fun item(id : Int) : ItemStack{
            val item = ItemStack(Material.STONE)
            val meta = item.itemMeta
            meta?.setCustomModelData(id)
            meta?.setDisplayName(name(id).clearcolor())
            item.itemMeta = meta
            return item
        }

        fun joint(id : Int) : Int{
            for (machine in MachineEnum.values())
                if (machine.id == id)
                    return machine.joint

            return 0
        }
    }
}