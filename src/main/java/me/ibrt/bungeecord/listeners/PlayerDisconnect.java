package me.ibrt.bungeecord.listeners;

import me.ibrt.bungeecord.constants.M;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnect implements Listener {

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        M.users.remove(p.getUniqueId().toString());
    }
}
