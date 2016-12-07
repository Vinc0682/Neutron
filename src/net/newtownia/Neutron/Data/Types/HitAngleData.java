package net.newtownia.Neutron.Data.Types;

import net.newtownia.Neutron.Data.AbstractData;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public class HitAngleData extends AbstractData
{
    private double angle;

    public HitAngleData()
    {
        super("HitAngle");
    }
    public HitAngleData(ConfigurationSection config)
    {
        super(config, "HitAngle");
    }
    public HitAngleData(double angle) {
        this();
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public AbstractData copy() {
        return new HitAngleData();
    }
    @Override
    public void save(ConfigurationSection config)
    {
        super.save(config);
        config.set("Angle", angle);
    }
    @Override
    public void load(ConfigurationSection config)
    {
        super.load(config);
        angle = Double.valueOf(config.getString("Angle"));
    }
}
