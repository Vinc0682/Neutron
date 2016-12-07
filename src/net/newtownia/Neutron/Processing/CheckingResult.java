package net.newtownia.Neutron.Processing;

import net.newtownia.Neutron.Data.DataSet;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class CheckingResult
{
    private DataSet data;
    private double probability;

    public CheckingResult(DataSet data, double probability)
    {
        this.data = data;
        this.probability = probability;
    }

    public DataSet getData() {
        return data;
    }
    public double getProbability() {
        return probability;
    }
}
