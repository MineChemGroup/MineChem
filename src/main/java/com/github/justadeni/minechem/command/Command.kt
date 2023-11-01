package com.github.justadeni.minechem.command

import com.github.justadeni.minechem.items.Compounds
import com.github.justadeni.minechem.items.Elements
import com.github.justadeni.minechem.data.Helpers
import com.github.justadeni.minechem.data.Helpers.add
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


        if (args.size == 0 || args.size > 3)
            return false



        return true
    }

}