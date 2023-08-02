package src.main;

import com.Zrips.CMI.CMI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class PlayEventQuest implements Listener {

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId());
        if(q == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getItem().toString();

        if (hasEvent(uuid, EventType.ITEM_DAMAGE, Modify,event.getDamage())) {
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId());
        if(q == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (hasEvent(uuid, EventType.LEVEL_CHANGE, "NULL",event.getNewLevel()-event.getOldLevel())) {
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId());
        if(q == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location from = event.getFrom();
        Location to = event.getTo();
        if ((from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY()) && hasEvent(uuid, EventType.PLAYER_MOVE, "NULL",1)) {
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId());
        if(q == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getBrokenItem().getType().toString();

        if (hasEvent(uuid, EventType.ITEM_BREAK,Modify,1)) {
        }
    }

    @EventHandler
    public void onPlayerShearEntity(PlayerShearEntityEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (hasEvent(uuid, EventType.SHEAR_ENTITY, "NULL",1)) {
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getItemDrop().toString();

        if (hasEvent(uuid, EventType.DROP_ITEM, Modify,1)) {
        }
    }

    @EventHandler
    public void onPlayerHarvestBlock(PlayerHarvestBlockEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getHarvestedBlock().toString();

        if (hasEvent(uuid, EventType.HARVEST_BLOCK, Modify,1)) {
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getCaught().toString();

        if (hasEvent(uuid, EventType.PLAYER_FISH,Modify,1)) {
        }
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (hasEvent(uuid, EventType.EXP_CHANGE,"NULL",event.getAmount())) {
        }
    }

    @EventHandler
    public void onPlayerItemMend(PlayerItemMendEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = event.getItem().toString();

        if (hasEvent(uuid, EventType.ITEM_MEND, Modify,event.getRepairAmount())) {
        }
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        if(Fartsovschikkubogram.playersQuests.get(event.getPlayer().getUniqueId()) == null) {
            return;
        } //Пока не создано
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String Modify = Objects.requireNonNull(event.getAdvancement().getDisplay()).getTitle();

        if (hasEvent(uuid, EventType.ADVANCEMENT_DONE, Modify,1)) {
        }

    }


    private boolean hasEvent(UUID uuid, EventType eventType, String Modify, int sub) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(uuid);
        if(q == null) {
            return false;
        } //Пока не создано
        List<singleton.challenge> chalgs = q.Retchallenges();
        for(singleton.challenge cur : chalgs) {
            if(cur.RetEvent().equals(eventType)) {
                if(cur.RetModify().equals(Modify) || cur.RetModify().toUpperCase().trim().equals("NULL")) {
                    if(cur.RetAmount() == 0) {
                        if(cur.RetReward() > 0) {
                            CMI.getInstance().getPlayerManager().getUser(uuid).deposit((double)cur.RetReward());
                            cur.GetAndDeleteReward(Bukkit.getPlayer(uuid));
                        }
                    }
                    else if(cur.RetAmount() < sub) {
                        cur.Substract(cur.RetAmount());
                        if(cur.RetReward() > 0) {
                            CMI.getInstance().getPlayerManager().getUser(uuid).deposit((double)cur.RetReward());
                            cur.GetAndDeleteReward(Bukkit.getPlayer(uuid));
                        }
                    }
                    else {
                        cur.Substract(sub);
                    }

                    return true;
                }

            }
        }
        return false;
    }

    enum EventType {
        ITEM_DAMAGE,
        LEVEL_CHANGE,
        PLAYER_MOVE,
        ITEM_BREAK,
        SHEAR_ENTITY,
        BLOCK_BREAK,
        DROP_ITEM,
        HARVEST_BLOCK,
        PLAYER_FISH,
        EXP_CHANGE,
        ITEM_MEND,
        ADVANCEMENT_DONE
    }
}
