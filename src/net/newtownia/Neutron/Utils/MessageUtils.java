package net.newtownia.Neutron.Utils;

import net.newtownia.NTApi.Config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class MessageUtils
{
    JavaPlugin pl;
    Map<String, String> messages;

    public MessageUtils(JavaPlugin pl, String fileName)
    {
        this.pl = pl;

        messages = new HashMap<>();

        YamlConfiguration config = ConfigManager.loadOrCreateConfigFile(fileName, pl);

        for(Map.Entry<String, Object> entry : config.getValues(false).entrySet())
        {
            messages.put(entry.getKey(), config.getString(entry.getKey()));
        }
    }

    public void printMessage(CommandSender cs, String message, Object... args)
    {
        String msg = messages.get("Prefix") + String.format(messages.get(message), args);
        msg = formatMessage(msg);
        if (!(cs instanceof Player))
            msg = ChatColor.stripColor(msg);
        cs.sendMessage(msg);
    }

    public String formatMessage(String s)
    {
        String result = ChatColor.translateAlternateColorCodes('&', s);
        result = result.replace("%nn", "\n\n\n\n\n");
        result = result.replace("%n", "\n");

        for (Map.Entry<String, String> message: messages.entrySet())
        {
            String keyWord = "%%" + message.getKey() + "%%";
            if (result.contains(keyWord))
                result = result.replaceAll(keyWord, formatMessage(message.getValue()));
        }

        return result;
    }
}
