package net.newtownia.Neutron.Commands;

import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Processing.TrainingCallback;
import net.newtownia.Neutron.Utils.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class Train extends SubCommand implements TrainingCallback
{
    private Map<UUID, CommandSender> playerTrainingStarter;

    public Train()
    {
        super("train");

        playerTrainingStarter = new HashMap<>();
    }

    @Override
    public void execute(Neutron pl, CommandSender cs, Command cmd, String label, String[] args)
    {
        if (!cs.hasPermission("neutron.train"))
        {
            pl.getMessageUtils().printMessage(cs, "No-Permission");
            return;
        }

        if (args.length > 2)
        {
            Player p = Bukkit.getPlayer(args[1]);
            if(p != null)
            {
                UUID pUUID = p.getUniqueId();
                if (pl.getDataManager().hasPlayerData(pUUID))
                {
                    pl.getMessageUtils().printMessage(p, "Already-Logging");
                    return;
                }
                long trainingUntil = System.currentTimeMillis() + 10000;
                if (args.length > 3)
                {
                    try
                    {
                        trainingUntil = DateUtils.parseDateDiff(args[3], true);
                    }
                    catch (Exception e)
                    {
                        pl.getMessageUtils().printMessage(cs, "Incorrect-Time", args[3]);
                    }
                }

                pl.getTraining().train(pl, p, trainingUntil, args[2], this);
                playerTrainingStarter.put(pUUID, cs);

                pl.getMessageUtils().printMessage(p, "Training-Started");
                if (!isPlayerCommandSender(p, cs))
                    pl.getMessageUtils().printMessage(cs, "Training-Started-Other", p.getName());
            }
            else
            {
                pl.getMessageUtils().printMessage(cs, "Player-Not-Found", args[1]);
            }
        }
    }

    @Override
    public void trainingComplete(Player p, DataSet data)
    {
        UUID pUUID = p.getUniqueId();
        CommandSender cs = playerTrainingStarter.get(pUUID);

        Neutron.getInstance().getMessageUtils().printMessage(p, "Training-Finished", data.getName());
        if (!isPlayerCommandSender(p, cs))
            Neutron.getInstance().getMessageUtils().printMessage(p, "Training-Finished-Other",
                    p.getName(), data.getName());
    }

    private boolean isPlayerCommandSender(Player p, CommandSender cs) {
        return cs instanceof Player && p.getUniqueId() == ((Player) cs).getUniqueId();
    }
}
