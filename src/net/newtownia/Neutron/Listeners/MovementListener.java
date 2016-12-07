package net.newtownia.Neutron.Listeners;

import net.newtownia.Neutron.Data.Types.MovementData;
import net.newtownia.Neutron.Neutron;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

/**
 * Created by Vinc0682 on 22.05.2016.
 */
public class MovementListener extends AbstractListener
{
    public MovementListener(Neutron pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player p = event.getPlayer();
        UUID pUUID = p.getUniqueId();

        if (!hasData(pUUID))
            return;
        addData(pUUID, new MovementData(event.getFrom(), event.getTo()));
    }
}
