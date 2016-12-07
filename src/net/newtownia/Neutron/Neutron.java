package net.newtownia.Neutron;

import net.newtownia.Neutron.Commands.NeutronCommand;
import net.newtownia.Neutron.Comperators.ComparatorManager;
import net.newtownia.Neutron.Data.DataLoader;
import net.newtownia.Neutron.Data.DataManager;
import net.newtownia.Neutron.Listeners.ListenerManager;
import net.newtownia.Neutron.Processing.Checking;
import net.newtownia.Neutron.Processing.Training;
import net.newtownia.Neutron.Utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public class Neutron extends JavaPlugin
{
    private static Neutron instance;

    private DataLoader loader;
    private DataManager dataManager;
    private ListenerManager listenerManager;
    private ComparatorManager comparatorManager;

    private Training training;
    private Checking checking;

    private MessageUtils messageUtils;

    @Override
    public void onEnable()
    {
        instance = this;
        getCommand("neutron").setExecutor(new NeutronCommand(this));
        reload();
    }

    public void reload()
    {
        loader = new DataLoader();
        dataManager = new DataManager(this, loader);
        listenerManager = new ListenerManager(this);
        comparatorManager = new ComparatorManager();

        training = new Training();
        checking = new Checking();

        messageUtils = new MessageUtils(this, "messages.yml");
    }

    public static Neutron getInstance() {
        return instance;
    }

    public DataLoader getLoader() {
        return loader;
    }
    public DataManager getDataManager() {
        return dataManager;
    }
    public ListenerManager getListenerManager() {
        return listenerManager;
    }
    public ComparatorManager getComparatorManager() {
        return comparatorManager;
    }

    public Training getTraining() {
        return training;
    }
    public Checking getChecking() {
        return checking;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }
}
