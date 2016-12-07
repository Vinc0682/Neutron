package net.newtownia.Neutron.Listeners;

import net.newtownia.Neutron.Neutron;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class ListenerManager
{
    private Neutron pl;
    private List<AbstractListener> listeners;

    public ListenerManager(Neutron pl)
    {
        this.pl = pl;
        addListeners();
        registerListeners();
    }

    private void addListeners()
    {
        listeners = new ArrayList<>();
        listeners.add(new AttackListener(pl));
        listeners.add(new MovementListener(pl));
    }

    private void registerListeners()
    {
        for (AbstractListener listener : listeners)
            Bukkit.getPluginManager().registerEvents(listener, pl);
    }
}
