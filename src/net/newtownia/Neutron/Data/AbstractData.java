package net.newtownia.Neutron.Data;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public abstract class AbstractData
{
    private String name;
    private long timeStamp;

    public AbstractData(String name)
    {
        this.name = name;
        timeStamp = System.currentTimeMillis();
    }

    public AbstractData(ConfigurationSection config, String name)
    {
        this(name);
        load(config);
    }

    public String getName()
    {
        return name;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public abstract AbstractData copy();
    public void save(ConfigurationSection config)
    {
        config.set("Timestamp", timeStamp);
    }
    public void load(ConfigurationSection config)
    {
        timeStamp = Long.valueOf(config.getString("Timestamp"));
    }
}
