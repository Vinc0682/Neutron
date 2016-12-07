package net.newtownia.Neutron.Comperators;

import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Data.Types.HitAngleData;
import net.newtownia.Neutron.Utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class HitAngleComparator extends AbstractComparator
{
    private double maxAverageDiff = 15;

    @Override
    public double compare(DataSet toCheck, DataSet pattern)
    {
        List<Double> dataIntervals = MathUtils.getDifferences(getHitAngles(toCheck));
        List<Double> patternIntervals = MathUtils.getDifferences(getHitAngles(pattern));

        double averageDiff = MathUtils.getAverage(patternIntervals) - MathUtils.getAverage(dataIntervals);
        if (averageDiff < 0)
            averageDiff *= -1;
        if (averageDiff > maxAverageDiff)
            return 0;
        return (maxAverageDiff - averageDiff) / maxAverageDiff;
    }

    private List<Double> getHitAngles(DataSet data)
    {
        List<Double> result = new ArrayList<>();
        List<HitAngleData> angles = data.getData(HitAngleData.class);
        for (HitAngleData d : angles)
            result.add(d.getAngle());
        return result;
    }
}
