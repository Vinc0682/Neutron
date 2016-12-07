package net.newtownia.Neutron.Listeners;

import net.newtownia.Neutron.Data.AbstractData;
import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Neutron;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class AbstractListener implements Listener
{
    Neutron pl;

    public AbstractListener(Neutron pl)
    {
        this.pl = pl;
    }

    protected DataSet getData(UUID pUUID)
    {
        return pl.getDataManager().getPlayerData(pUUID);
    }

    protected boolean hasData(UUID pUUID)
    {
        return getData(pUUID) != null;
    }

    protected void addData(UUID pUUID, AbstractData data)
    {
        if (!hasData(pUUID))
            return;
        getData(pUUID).addData(data);
    }
}
