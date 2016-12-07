package net.newtownia.Neutron.Commands;

import net.newtownia.Neutron.Neutron;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class NeutronCommand implements CommandExecutor
{
    private Neutron pl;
    private HashMap<String, SubCommand> commands;

    public NeutronCommand(Neutron pl)
    {
        this.pl = pl;

        commands = new HashMap<>();
        add(new Version());
        add(new Train());
        add(new Check());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
    {
        String commandName = "version";
        if (args.length > 0)
            commandName = args[0];
        commandName = commandName.toLowerCase();
        SubCommand command = null;
        if (commands.containsKey(commandName))
            command = commands.get(commandName);
        if (command == null)
            command = commands.get("version");
        command.execute(pl, cs, cmd, label, args);
        return true;
    }

    private void add(SubCommand command)
    {
        commands.put(command.getName().toLowerCase(), command);
    }
}
