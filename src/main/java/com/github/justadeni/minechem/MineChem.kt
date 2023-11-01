package com.github.justadeni.minechem

import com.github.justadeni.minechem.command.Command
import com.github.justadeni.minechem.listeners.*
import me.xflyiwnl.colorfulgui.ColorfulGUI
import org.bukkit.plugin.java.JavaPlugin

class MineChem : JavaPlugin() {
    companion object {
        lateinit var plugin: MineChem
        @JvmStatic
        lateinit var colorfulGUI: ColorfulGUI
    }

    override fun onEnable() {
        plugin = this
        colorfulGUI = ColorfulGUI(this)
        server.pluginManager.registerEvents(BlockPlace, this)
        server.pluginManager.registerEvents(BlockClick, this)
        server.pluginManager.registerEvents(ChunkLoad, this)
        server.pluginManager.registerEvents(ChunkUnload, this)
        getCommand("minechem")?.setExecutor(Command)
    }

    override fun onDisable() {

    }
}