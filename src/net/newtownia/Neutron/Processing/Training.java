package net.newtownia.Neutron.Processing;

import net.newtownia.Neutron.Data.DataManager;
import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Neutron;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class Training
{
    private Map<UUID, Long> playerTrainingTimes;
    private Map<UUID, TrainingCallback> playerCallbacks;
    private BukkitTask task = null;

    public Training()
    {
        playerTrainingTimes = new HashMap<>();
        playerCallbacks = new HashMap<>();
    }

    public void train(Neutron pl, Player p, long until, String name, TrainingCallback callback)
    {
        UUID pUUID = p.getUniqueId();

        DataSet data = new DataSet();
        data.setName(name);
        pl.getDataManager().setPlayerData(pUUID, data);

        playerTrainingTimes.put(pUUID, until);
        playerCallbacks.put(pUUID, callback);

        if (task == null)
        {
            task = Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
                @Override
                public void run() {finishTrainings();
                }
            }, 1L, 1L);
        }
    }

    private void finishTrainings()
    {
        List<UUID> removed = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : playerTrainingTimes.entrySet())
        {
            if (System.currentTimeMillis() > entry.getValue())
            {
                UUID pUUID = entry.getKey();
                Player p = Bukkit.getPlayer(pUUID);

                DataManager dataManager = Neutron.getInstance().getDataManager();
                DataSet data = dataManager.getPlayerData(pUUID);
                dataManager.addPattern(data);
                dataManager.setPlayerData(pUUID, null);
                dataManager.savePatterns();
                removed.add(pUUID);

                playerCallbacks.get(pUUID).trainingComplete(p, data);
            }
        }
        for (UUID pUUID : removed)
        {
            playerTrainingTimes.remove(pUUID);
            playerCallbacks.remove(pUUID);
        }
        if (playerTrainingTimes.size() == 0)
        {
            task.cancel();
            task = null;
        }
    }
}
