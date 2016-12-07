package net.newtownia.Neutron.Listeners;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.newtownia.Neutron.Data.Types.AttackData;
import net.newtownia.Neutron.Data.Types.HitAngleData;
import net.newtownia.Neutron.Neutron;
import net.newtownia.Neutron.Utils.EntityUtils;
import net.newtownia.Neutron.Utils.LogUtils;
import net.newtownia.Neutron.Utils.MathUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Vinc0682 on 21.05.2016.
 */
public class AttackListener extends AbstractListener
{
    private PacketAdapter attackPacketEvent;

    public AttackListener(Neutron pl)
    {
        super(pl);

        attackPacketEvent = new PacketAdapter(pl, ListenerPriority.LOW, PacketType.Play.Client.USE_ENTITY)
        {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                onAttackPacket(event);
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(attackPacketEvent);
    }

    private void onAttackPacket(PacketEvent event)
    {
        Player p = event.getPlayer();
        UUID pUUID = p.getUniqueId();

        if (!hasData(pUUID))
            return;

        WrapperPlayClientUseEntity packet = new WrapperPlayClientUseEntity(event.getPacket());

        if (packet.getType() != EnumWrappers.EntityUseAction.ATTACK)
            return;

        addData(pUUID, new AttackData());

        Entity attacked = EntityUtils.getEntityByEntityID(packet.getTargetID(), p.getLocation().getWorld());
        if (attacked != null)
        {
            double angle = MathUtils.getYawDiff(p.getLocation(), attacked.getLocation());
            if (angle < 0)
                angle *= -1;
            addData(pUUID, new HitAngleData(angle));
        }
        else
            LogUtils.warn("Unable to find attacked entity with id \"" + packet.getTargetID() + "\".");
    }
}
