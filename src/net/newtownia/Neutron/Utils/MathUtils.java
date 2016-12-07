package net.newtownia.Neutron.Utils;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MathUtils
{
    public static double getYawDiff(Location attacker, Location target)
    {
        Location origin = attacker.clone();
        Vector originVec = origin.toVector();
        Vector targetVec = target.toVector();
        origin.setDirection(targetVec.subtract(originVec));
        double yaw = (double)origin.getYaw() - attacker.getYaw();
        return yaw % 180;
    }

    public static <T extends Number> double getAverage(List<T> numbers)
    {
        return getStatistics(numbers).getMean();
    }

    public static <T extends Number & Comparable<? super T>> double getMedian(List<T> numbers)
    {
        return getStatistics(numbers).getPercentile(50);
    }

    public static <T extends Number> DescriptiveStatistics getStatistics(List<T> numbers)
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (T n : numbers)
            stats.addValue(n.doubleValue());
        return stats;
    }

    public static <T extends Number> List<Double> getDifferences(List<T> numbers)
    {
        List<Double> result = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i += 1)
        {
            result.add(numbers.get(i).doubleValue() - numbers.get(i - 1).doubleValue());
        }
        return result;
    }

    public static boolean isPositionSame(Location loc1, Location loc2, double threshold)
    {
        return isSame(loc1.getX(), loc2.getX(), threshold) ||
                isSame(loc1. getY(), loc2.getY(), threshold) ||
                isSame(loc1.getZ(), loc2.getZ(), threshold);
    }

    public static boolean isRotationSame(Location loc1, Location loc2, double threshold)
    {
        return isSame((double) loc1.getYaw(), (double)loc2.getYaw(), threshold) ||
                isSame((double) loc2.getPitch(), (double) loc2.getPitch(), threshold);
    }

    public static boolean isSame(double a, double b, double threshold)
    {
        if (a == b)
            return true;
        if (threshold == 0)
            return a == b;

        double min = a - threshold;
        double max = a + threshold;
        return b > min && b < max;
    }
}
