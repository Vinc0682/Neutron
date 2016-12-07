package net.newtownia.Neutron.Processing;

import net.newtownia.Neutron.Data.DataSet;
import org.bukkit.entity.Player;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public interface TrainingCallback
{
    void trainingComplete(Player p, DataSet data);
}
