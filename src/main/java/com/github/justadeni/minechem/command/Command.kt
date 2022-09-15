package com.github.justadeni.minechem.command

import com.github.justadeni.minechem.MineChem
import com.github.justadeni.minechem.enums.MachineEnum
import com.github.justadeni.minechem.items.Elements
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Command : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "minechem")
            return false

        if (sender !is Player)
            return false

        when (args.size){
            0 -> {
                sender.sendMessage("Insufficient arguments")
            }
            1 -> {
                sender.sendMessage("Insufficient arguments")
            }
            2 -> {
                if (sender.inventory.itemInMainHand.type.isAir)
                    when (args[0]) {
                        "machine" -> sender.inventory.setItemInMainHand(MachineEnum.item(args[1].toInt()))
                        "element" -> sender.inventory.setItemInMainHand(Elements.item(args[1].toInt()))
                    }

            }
        }
        return true
    }

}