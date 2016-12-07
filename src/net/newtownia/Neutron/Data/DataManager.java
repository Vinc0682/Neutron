package net.newtownia.Neutron.Data;

import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Utils.LogUtils;

import java.util.*;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class DataManager
{
    private Neutron pl;
    private DataLoader loader;
    private Map<String, DataSet> patterns;
    private Map<UUID, DataSet> playerData;

    public DataManager(Neutron pl, DataLoader loader)
    {
        this.pl = pl;
        this.loader = loader;

        patterns = new HashMap<>();
        playerData = new HashMap<>();

        loadPatterns();
    }

    public void loadPatterns()
    {
        for (DataSet pattern : loader.loadAllData(pl))
            addPattern(pattern);
    }
    public void savePatterns()
    {
        loader.saveAllData(pl, getAllPatterns());
    }

    public List<DataSet> getAllPatterns()
    {
        return new ArrayList<>(patterns.values());
    }
    public DataSet getPattern(String name)
    {
        if (!patterns.containsKey(name))
            return null;
        return patterns.get(name);
    }
    public void addPattern(DataSet pattern)
    {
        patterns.put(pattern.getName(), pattern);
        LogUtils.debug("Added pattern " + pattern.getName());
    }

    public boolean hasPlayerData(UUID pUUID)
    {
        return playerData.containsKey(pUUID);
    }
    public DataSet getPlayerData(UUID pUUID)
    {
        if (!hasPlayerData(pUUID))
            return null;
        return playerData.get(pUUID);
    }
    public void setPlayerData(UUID pUUID, DataSet data)
    {
        if (data == null && hasPlayerData(pUUID))
            playerData.remove(pUUID);
        else
            playerData.put(pUUID, data);
    }
}
