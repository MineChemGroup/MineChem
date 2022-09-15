package com.github.justadeni.minechem.misc

import eu.hoefel.chemistry.Element
import org.bukkit.ChatColor

object Helpers {

    fun String.cap() : String{
        return this.replaceFirstChar { it.titlecase() }
    }

    fun String.clearcolor() : String{
        return ChatColor.translateAlternateColorCodes('&', "&r$this")
    }

    fun Element.radioactive() : Boolean{
        return (this.atomicNumber() == 43 || this.atomicNumber() == 61 || (84..118).contains(this.atomicNumber()))
    }
}