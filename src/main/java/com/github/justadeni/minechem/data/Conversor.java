package com.github.justadeni.minechem.data;


import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.HashMap;

public class Conversor {

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    private static HashMap<String, Class<?>> index = new HashMap<>();

    public static ItemStack deserializeItemStack(String data){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data.toUpperCase().replaceAll("-","="), 32).toByteArray());
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        ItemStack itemStack = null;
        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Class<?> nmsItemStackClass = getNMSClass("ItemStack");
            Object nbtTagCompound = getNMSClass("NBTCompressedStreamTools").getMethod("a", DataInputStream.class).invoke(null, dataInputStream);
            //Object nbtTagCompound = getNMSClass("NBTCompressedStreamTools").getMethod("a", DataInputStream.class).invoke(null, inputStream);
            Object craftItemStack = nmsItemStackClass.getMethod("createStack", nbtTagCompoundClass).invoke(null, nbtTagCompound);
            itemStack = (ItemStack) getOBClass("inventory.CraftItemStack").getMethod("asBukkitCopy", nmsItemStackClass).invoke(null, craftItemStack);
        } catch(ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    public static String serializeItemStack(ItemStack item) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Constructor<?> nbtTagCompoundConstructor = nbtTagCompoundClass.getConstructor();
            Object nbtTagCompound = nbtTagCompoundConstructor.newInstance();
            Object nmsItemStack = getOBClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            getNMSClass("ItemStack").getMethod("save", nbtTagCompoundClass).invoke(nmsItemStack, nbtTagCompound);
            getNMSClass("NBTCompressedStreamTools").getMethod("a", nbtTagCompoundClass, DataOutput.class).invoke(null, nbtTagCompound, (DataOutput)dataOutput);

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return new BigInteger(1, outputStream.toByteArray()).toString(32).toLowerCase().replaceAll("=", "-");
    }

    private static Class<?> getNMSClass(String name) {
        try {
            String classname = "net.minecraft.server." + version + "." + name;
            if (index.containsKey(classname))
                return index.get(classname);

            Class<?> clazz = Class.forName(classname);
            index.put(classname, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class<?> getOBClass(String name) {
        try {
            String classname = "org.bukkit.craftbukkit." + version + "." + name;
            if (index.containsKey(classname))
                return index.get(classname);

            Class<?> clazz = Class.forName(classname);
            index.put(classname, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
