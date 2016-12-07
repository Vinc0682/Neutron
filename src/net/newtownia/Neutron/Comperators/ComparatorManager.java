package net.newtownia.Neutron.Comperators;

import net.newtownia.Neutron.Data.DataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class ComparatorManager {
    private List<AbstractComparator> comparators;

    public ComparatorManager()
    {
        comparators = new ArrayList<>();
        comparators.add(new AttackFrequencyComparator());
        comparators.add(new HitAngleComparator());
        comparators.add(new RotationSpeedComparator());
        // TODO: Register comparators here
    }

    public List<AbstractComparator> getComparators() {
        return comparators;
    }

    public double compare(DataSet data, DataSet pattern)
    {
        double result = 0;
        for (AbstractComparator comparator : comparators)
        {
            result += comparator.compare(data, pattern) * comparator.getWeight();
        }
        return result / comparators.size();
    }
}
