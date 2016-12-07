package net.newtownia.Neutron.Comperators;

import net.newtownia.Neutron.Data.DataSet;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public abstract class AbstractComparator
{
    private double weight = 1;

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public abstract double compare(DataSet toCheck, DataSet pattern);
}
