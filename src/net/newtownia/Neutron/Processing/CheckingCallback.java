package net.newtownia.Neutron.Processing;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public interface CheckingCallback
{
    public void checkComplete(Player p, List<CheckingResult> results);
}
