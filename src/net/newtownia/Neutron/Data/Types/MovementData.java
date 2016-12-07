package net.newtownia.Neutron.Data.Types;

import net.newtownia.Neutron.Data.AbstractData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class MovementData extends AbstractData {
    private double deltaX;
    private double deltaY;
    private double deltaZ;
    private float deltaYaw;
    private float deltaPitch;

    public MovementData() {
        super("MovementData");
    }
    public MovementData(ConfigurationSection config) {
        this();
        load(config);
    }
    public MovementData(Location from, Location to)
    {
        this();
        deltaX = to.getX() - from.getX();
        deltaY = to.getY() - from.getY();
        deltaZ = to.getZ() - from.getZ();
        deltaYaw = to.getYaw() - from.getYaw();
        deltaPitch = to.getPitch() - from.getPitch();
    }

    public double getDeltaX() {
        return deltaX;
    }
    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }
    public double getDeltaY() {
        return deltaY;
    }
    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }
    public double getDeltaZ() {
        return deltaZ;
    }
    public void setDeltaZ(double deltaZ) {
        this.deltaZ = deltaZ;
    }
    public float getDeltaYaw() {
        return deltaYaw;
    }
    public void setDeltaYaw(float deltaYaw) {
        this.deltaYaw = deltaYaw;
    }
    public float getDeltaPitch() {
        return deltaPitch;
    }
    public void setDeltaPitch(float deltaPitch) {
        this.deltaPitch = deltaPitch;
    }

    @Override
    public AbstractData copy() {
        return new MovementData();
    }

    @Override
    public void save(ConfigurationSection config)
    {
        super.save(config);
        config.set("DeltaX", deltaX);
        config.set("DeltaY", deltaY);
        config.set("DeltaZ", deltaZ);
        config.set("DeltaYaw", deltaYaw);
        config.set("DeltaPitch", deltaPitch);
    }

    @Override
    public void load(ConfigurationSection config)
    {
        super.load(config);
        deltaX = Double.valueOf(config.getString("DeltaX"));
        deltaY = Double.valueOf(config.getString("DeltaY"));
        deltaZ = Double.valueOf(config.getString("DeltaZ"));
        deltaYaw = Float.valueOf(config.getString("DeltaYaw"));
        deltaPitch = Float.valueOf(config.getString("DeltaPitch"));
    }
}
