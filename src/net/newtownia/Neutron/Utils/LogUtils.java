package net.newtownia.Neutron.Utils;

import org.bukkit.Bukkit;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class LogUtils
{
    private static boolean DEBUG = true;
    private static boolean WARN = true;

    public static void debug(String message)
    {
        if (DEBUG)
            Bukkit.getConsoleSender().sendMessage("§7[§4NEUTRON§7] [§cDEBUG§7] §f" + message);
    }

    public static void warn(String message)
    {
        if (DEBUG)
            Bukkit.getConsoleSender().sendMessage("§7[§4NEUTRON§7] [§eWARNING§7] §f" + message);
    }
}
