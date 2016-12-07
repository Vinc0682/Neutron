package net.newtownia.Neutron.Commands;

import net.newtownia.Neutron.Neutron;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public abstract class SubCommand
{
    private String name;

    public SubCommand(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(Neutron pl, CommandSender cs, Command cmd, String label, String[] args);
}
