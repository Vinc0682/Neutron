package net.newtownia.Neutron.Comperators;

import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Data.Types.MovementData;
import net.newtownia.Neutron.Utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class RotationSpeedComparator extends AbstractComparator
{
    private double maxAverageDiff = 30;
    private double minYawMovement = 5;

    @Override
    public double compare(DataSet toCheck, DataSet pattern)
    {
        List<Double> dataIntervals = MathUtils.getDifferences(getYawMovements(toCheck));
        List<Double> patternIntervals = MathUtils.getDifferences(getYawMovements(pattern));

        double averageDiff = MathUtils.getAverage(patternIntervals) - MathUtils.getAverage(dataIntervals);
        if (averageDiff < 0)
            averageDiff *= -1;
        if (averageDiff > maxAverageDiff)
            return 0;
        return (maxAverageDiff - averageDiff) / maxAverageDiff;
    }

    private List<Double> getYawMovements(DataSet data)
    {
        List<Double> result = new ArrayList<>();
        List<MovementData> movement = data.getData(MovementData.class);
        for (MovementData d : movement)
        {
            float yaw = d.getDeltaYaw() % 180;
            if (yaw < 0)
                yaw *= -1;
            if (yaw > minYawMovement)
                result.add((double)yaw);
        }
        return result;
    }
}
