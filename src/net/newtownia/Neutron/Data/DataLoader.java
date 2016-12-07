package net.newtownia.Neutron.Data;

import net.newtownia.NTApi.Config.ConfigManager;
import net.newtownia.Neutron.Data.Types.AttackData;
import net.newtownia.Neutron.Data.Types.HitAngleData;
import net.newtownia.Neutron.Data.Types.MovementData;
import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Utils.LogUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public class DataLoader
{
    private List<AbstractData> dataTypes;

    public DataSet loadDataSetFromConfig(YamlConfiguration config)
    {
        DataSet result = new DataSet();
        result.setName(config.getString("Name"));

        Map<String, Object> values = config.getValues(false);
        for (Map.Entry<String, Object> entry : values.entrySet())
        {
            String basePath = entry.getKey();
            AbstractData dataType = getDataType(config.getString(basePath + ".Name"));
            if (dataType == null) // This shouldn't happen
                continue;
            AbstractData instance = dataType.copy();
            instance.load(config.getConfigurationSection(basePath));
            result.addData(instance);
        }
        return result;
    }

    public void saveDataSetToConfig(DataSet data, YamlConfiguration config)
    {
        config.set("Name", data.getName());

        int i = 0;
        for (AbstractData d : data.getData())
        {
            String basePath = "Data-" + i;
            config.createSection(basePath);
            ConfigurationSection section = config.getConfigurationSection(basePath);
            section.set("Name", d.getName());
            d.save(section);
            i += 1;
        }
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public List<DataSet> loadAllData(Neutron pl)
    {
        List<DataSet> result = new ArrayList<>();
        File folder = getDataFolder(pl);

        for (File patternFile : folder.listFiles())
        {
            if (!patternFile.isFile())
                continue;
            if (!patternFile.getName().endsWith(".pattern"))
                continue;
            LogUtils.debug("Found pattern file: " + patternFile.getName());
            YamlConfiguration config = ConfigManager.loadConfig(patternFile);
            result.add(loadDataSetFromConfig(config));
        }
        return result;
    }
    public void saveAllData(Neutron pl, List<DataSet> data)
    {
        for (DataSet d : data)
        {
            YamlConfiguration config = new YamlConfiguration();
            saveDataSetToConfig(d, config);

            String fileName = d.getName() + ".pattern";
            fileName = fileName.replace("\\.", "_");
            try
            {
                ConfigManager.SaveConfigurationToFile(config, fileName, pl);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private List<AbstractData> getDataTypes()
    {
        if (dataTypes == null)
        {
            dataTypes = new ArrayList<>();
            dataTypes.add(new HitAngleData());
            dataTypes.add(new AttackData());
            dataTypes.add(new MovementData());
            // TODO: Register more data-types here
        }
        return dataTypes;
    }

    private AbstractData getDataType(String name)
    {
        for (AbstractData d : getDataTypes())
            if (d.getName().equals(name))
                return d;
        return null;
    }
    private File getDataFolder(Neutron pl)
    {
        File folder = pl.getDataFolder();
        if (folder == null)
            return null;
        if (!folder.exists())
        {
            LogUtils.debug("Creating data folder!");
            folder.mkdirs();
        }
        return folder;
    }
}
