package src.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class IfItJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(Fartsovschikkubogram.playersLvls.get(event.getPlayer().getUniqueId()) == null) {
            Fartsovschikkubogram.playersLvls.put(event.getPlayer().getUniqueId(),1);
        }

    }
}
