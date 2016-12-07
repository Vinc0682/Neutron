package net.newtownia.Neutron.Comperators;

import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Data.Types.AttackData;
import net.newtownia.Neutron.Utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class AttackFrequencyComparator extends AbstractComparator
{
    private double maxAverageDiff = 100;

    @Override
    public double compare(DataSet toCheck, DataSet pattern)
    {
        List<Double> dataIntervals = MathUtils.getDifferences(getAttackTimes(toCheck));
        List<Double> patternIntervals = MathUtils.getDifferences(getAttackTimes(pattern));

        double averageDiff = MathUtils.getAverage(patternIntervals) - MathUtils.getAverage(dataIntervals);
        if (averageDiff < 0)
            averageDiff *= -1;
        if (averageDiff > maxAverageDiff)
            return 0;
        return (maxAverageDiff - averageDiff) / maxAverageDiff;
    }

    private List<Long> getAttackTimes(DataSet data)
    {
        List<Long> result = new ArrayList<>();
        List<AttackData> attacks = data.getData(AttackData.class);
        for (AttackData d : attacks)
            result.add(d.getTimeStamp());
        return result;
    }
}
