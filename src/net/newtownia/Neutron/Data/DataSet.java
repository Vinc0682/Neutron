package net.newtownia.Neutron.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public class DataSet
{
    private String name;
    private List<AbstractData> data;

    public DataSet()
    {
        data = new ArrayList<>();
    }

    public DataSet(List<AbstractData> data)
    {
        this.data = data;
    }

    public void addData(AbstractData d)
    {
        data.add(d);
    }

    public List<AbstractData> getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractData> List<T> getData(Class<T> type)
    {
        List<T> result = new ArrayList<>();
        for (AbstractData d : data)
            if (d.getClass().equals(type))
                result.add((T) d);
        return result;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
