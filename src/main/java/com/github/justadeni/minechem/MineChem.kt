package com.github.justadeni.minechem

import com.github.justadeni.minechem.command.Command
import com.github.justadeni.minechem.listeners.BlockBreak
import com.github.justadeni.minechem.listeners.BlockClick
import com.github.justadeni.minechem.listeners.BlockPlace
import org.bukkit.plugin.java.JavaPlugin

class MineChem : JavaPlugin() {
    companion object {
        lateinit var plugin: MineChem
    }

    override fun onEnable() {
        plugin = this
        server.pluginManager.registerEvents(BlockPlace, this)
        server.pluginManager.registerEvents(BlockClick, this)
        server.pluginManager.registerEvents(BlockBreak, this)
        getCommand("minechem")?.setExecutor(Command)
    }

    override fun onDisable() {

    }
}