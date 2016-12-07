package net.newtownia.Neutron.Processing;

import net.newtownia.Neutron.Data.DataManager;
import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class Checking
{
    private Map<UUID, Long> playerCheckTimes;
    private Map<UUID, CheckingCallback> playerCallbacks;
    private BukkitTask task = null;

    public Checking()
    {
        playerCheckTimes = new HashMap<>();
        playerCallbacks = new HashMap<>();
    }

    public void check(Neutron pl, Player p, long until, CheckingCallback callback)
    {
        UUID pUUID = p.getUniqueId();

        DataSet data = new DataSet();
        data.setName(p.getName());
        pl.getDataManager().setPlayerData(pUUID, data);

        playerCheckTimes.put(pUUID, until);
        playerCallbacks.put(pUUID, callback);

        if (task == null)
            task = Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
                @Override
                public void run() {finishChecks();
                }
            }, 1L, 1L);
    }

    private void finishChecks()
    {
        List<UUID> removed = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : playerCheckTimes.entrySet())
        {
            if (System.currentTimeMillis() > entry.getValue())
            {
                UUID pUUID = entry.getKey();
                Player p = Bukkit.getPlayer(pUUID);

                DataManager dataManager = Neutron.getInstance().getDataManager();
                DataSet data = dataManager.getPlayerData(pUUID);
                dataManager.setPlayerData(pUUID, null);

                List<CheckingResult> results = new ArrayList<>();
                for (DataSet pattern : dataManager.getAllPatterns())
                {
                    double probability = Neutron.getInstance().getComparatorManager().compare(data, pattern);
                    results.add(new CheckingResult(pattern, probability));
                }
                playerCallbacks.get(pUUID).checkComplete(p, results);
                removed.add(pUUID);
            }
        }
        for (UUID pUUID : removed)
        {
            playerCheckTimes.remove(pUUID);
            playerCallbacks.remove(pUUID);
        }
        if (playerCheckTimes.size() == 0)
        {
            task.cancel();
            task = null;
        }
    }
}
