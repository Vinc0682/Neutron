package net.newtownia.Neutron.Commands;

import net.newtownia.Neutron.Neutron;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class Version extends SubCommand
{
    public Version()
    {
        super("version");
    }

    @Override
    public void execute(Neutron pl, CommandSender cs, Command cmd, String label, String[] args)
    {
        pl.getMessageUtils().printMessage(cs, "Version", pl.getDescription().getVersion());
    }
}
