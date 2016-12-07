package net.newtownia.Neutron.Data.Types;

import net.newtownia.Neutron.Data.AbstractData;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class AttackData extends AbstractData
{
    public AttackData()
    {
        super("Attack");
    }

    public AttackData(ConfigurationSection config)
    {
        this();
        load(config);
    }

    @Override
    public AbstractData copy()
    {
        return new AttackData();
    }

    @Override
    public void save(ConfigurationSection config)
    {
        super.save(config);
    }

    @Override
    public void load(ConfigurationSection config)
    {
        super.load(config);
    }
}
