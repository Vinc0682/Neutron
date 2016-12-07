package net.newtownia.Neutron.Commands;

import net.newtownia.Neutron.Data.DataSet;
import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Processing.CheckingCallback;
import net.newtownia.Neutron.Processing.CheckingResult;
import net.newtownia.Neutron.Utils.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Vinc0682 on 23.05.2016.
 */
public class Check extends SubCommand implements CheckingCallback
{
    private Map<UUID, CommandSender> playerCheckStarter;

    public Check()
    {
        super("check");

        playerCheckStarter = new HashMap<>();
    }

    @Override
    public void execute(Neutron pl, CommandSender cs, Command cmd, String label, String[] args)
    {
        if (!cs.hasPermission("neutron.check"))
        {
            pl.getMessageUtils().printMessage(cs, "No-Permission");
            return;
        }

        if (args.length > 1)
        {
            Player p = Bukkit.getPlayer(args[1]);
            if(p != null)
            {
                UUID pUUID = p.getUniqueId();
                if (pl.getDataManager().hasPlayerData(pUUID))
                {
                    pl.getMessageUtils().printMessage(p, "Already-Logging", p.getName());
                    return;
                }
                long checkUntil = System.currentTimeMillis() + 10000;
                if (args.length > 2)
                {
                    try
                    {
                        checkUntil = DateUtils.parseDateDiff(args[2], true);
                    }
                    catch (Exception e)
                    {
                        pl.getMessageUtils().printMessage(cs, "Incorrect-Time", args[2]);
                    }
                }

                pl.getChecking().check(pl, p, checkUntil, this);
                playerCheckStarter.put(pUUID, cs);

                pl.getMessageUtils().printMessage(p, "Checking-Started");
                if (!isPlayerCommandSender(p, cs))
                    pl.getMessageUtils().printMessage(cs, "Checking-Started-Other", p.getName());
            }
            else
            {
                pl.getMessageUtils().printMessage(cs, "Player-Not-Found", args[1]);
            }
        }
    }

    @Override
    public void checkComplete(Player p, List<CheckingResult> results)
    {
        UUID pUUID = p.getUniqueId();
        CommandSender cs = playerCheckStarter.get(pUUID);
        List<CheckingResult> sorted = sort(results);
        CheckingResult detectedResult = sorted.get(0);
        DataSet detectedPattern = detectedResult.getData();

        Neutron.getInstance().getMessageUtils().printMessage(p, "Checking-Finished", detectedPattern.getName());
        if (!isPlayerCommandSender(p, cs))
            Neutron.getInstance().getMessageUtils().printMessage(p, "Checking-Finished-Other",
                    p.getName(), detectedPattern.getName());

        cs.sendMessage("Name: " + detectedPattern.getName());
        cs.sendMessage("Probability: " + detectedResult.getProbability());
    }

    private List<CheckingResult> sort(List<CheckingResult> results)
    {
        List<CheckingResult> result = new ArrayList<>();
        for (CheckingResult r : results)
        {
            for (int i = 0; i < result.size(); i += 1)
            {
                if (r.getProbability() > result.get(i).getProbability())
                {
                    result.add(i, r);
                    break;
                }
            }
            if (!result.contains(r))
                result.add(r);
        }
        return result;
    }

    private boolean isPlayerCommandSender(Player p, CommandSender cs) {
        return cs instanceof Player && p.getUniqueId() == ((Player) cs).getUniqueId();
    }
}
