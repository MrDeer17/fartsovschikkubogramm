package src.main;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Economy.CMIEconomyAcount;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class menu1Listener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Проверяем, что кликнули по предмету в инвентаре
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.CHEST && event.getClickedInventory().getHolder() == null && CheckFor(event.getView().getTitle().toLowerCase(),event.getWhoClicked().getUniqueId())) {
            // Получаем кликнутый предмет
            ItemStack clickedItem = event.getCurrentItem();
            Player p = (Player) event.getWhoClicked();
            Fartsovschikkubogram.CheckForNewLevel(p);
            Inventory playerInv = p.getInventory();
            int slot = event.getRawSlot();
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            singleton.Sounds.get(0).PlaySound(p);
            if (event.getView().getTitle().toLowerCase().contains(singleton.menu1.toLowerCase())) { //menu1
                if (clickedItem.getType() == Material.BOOK) {


                } else if (clickedItem.getType() == Material.BELL) {
                    menufars.OpenMenu(p);
                } else if (clickedItem.getType() == Material.END_CRYSTAL) {
                    if (!(Fartsovschikkubogram.playersLvls.get(p.getUniqueId()) >= 11)) {
                        menulvl.OpenMenu(p);
                    }
                    else {
                        p.sendMessage(singleton.Messgaes.get(14));
                    }

                } else if (clickedItem.getType() == Material.PUFFERFISH) {
                    if (Fartsovschikkubogram.playersLvls.get(p.getUniqueId()) >= 11) {
                        menudon.OpenMenu(p);
                    } else {
                        p.sendMessage(singleton.Messgaes.get(11));
                    }

                }
            } //Main
            else if (event.getView().getTitle().toLowerCase().contains(singleton.menu2.toLowerCase())) {

                if (Fartsovschikkubogram.VarBuys.get(p.getUniqueId()).isEmpty() || Fartsovschikkubogram.VarSales.get(p.getUniqueId()).isEmpty()) {
                    Fartsovschikkubogram.UPDVarsSB(p, 0);
                }
                if (slot == 46) {
                    //Информация
                    return;
                } else if (slot == 49) {
                    if (PlayerPoints.getInstance().getAPI().look(p.getUniqueId()) >= singleton.refreshPrice) {
                        PlayerPoints.getInstance().getAPI().take(p.getUniqueId(), singleton.refreshPrice);
                        Fartsovschikkubogram.UPDVarsSB(p, 1);
                        menufars.OpenMenu(p);
                    }

                    return;
                } else if (slot == 52) {
                    menu1.OpenMenu(p);
                    return;
                }
                double bal = CMI.getInstance().getPlayerManager().getUser(p).getBalance();


                if (slot >= 29 && slot <= 33 && !clickedItem.getItemMeta().getDisplayName().contains("Недоступно")) {
                    singleton.Sounds.get(4).PlaySound(p);
                    singleton.matlip ThisMatlip = GetMatlip(clickedItem, p, Fartsovschikkubogram.VarBuys, singleton.ItemsForBuy);
                    if (ThisMatlip == null) {

                        return;
                    } //NullableError
                    if (event.getClick() == ClickType.RIGHT) {
                        // купить предмет (ПКМ)

                        if ((ThisMatlip.getLimit() >= 1 || ThisMatlip.getLimit() == -2)) {
                            if (bal >= ThisMatlip.getPrice()) {
                                CMI.getInstance().getPlayerManager().getUser(p).withdraw((double) ThisMatlip.getPrice());
                                addItemToPlayerInv(p, 1, clickedItem);
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, 1, Fartsovschikkubogram.VarBuys);
                                }
                            } else {
                                p.sendMessage(singleton.Messgaes.get(12)); //Price MSG
                            }
                        } else {
                            p.sendMessage(singleton.Messgaes.get(0)); //Limitation MSG
                        }
                    } else if (event.getClick() == ClickType.LEFT) {
                        // купить стак предметов (ЛКМ)
                        if (ThisMatlip.getLimit() >= 64) {
                            if (bal >= ThisMatlip.getPrice() * 64) {
                                CMI.getInstance().getPlayerManager().getUser(p).withdraw((double) ThisMatlip.getPrice() * 64);
                                addItemToPlayerInv(p, 64, clickedItem);
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, 64, Fartsovschikkubogram.VarBuys);
                                }
                            } else {
                                p.sendMessage(singleton.Messgaes.get(12));
                            }
                        } else {
                            p.sendMessage(singleton.Messgaes.get(3));
                        }
                    }
                } //Покупка
                else if (((slot >= 2 && slot <= 6) || (slot >= 11 && slot <= 15)) && !clickedItem.getItemMeta().getDisplayName().contains("Недоступно")) {
                    singleton.matlip ThisMatlip = GetMatlip(clickedItem, p, Fartsovschikkubogram.VarSales, singleton.ItemsForSale);
                    if (ThisMatlip == null) {
                        System.out.println("Interal error 2");
                        return;
                    } //Ошибка связи
                    singleton.Sounds.get(5).PlaySound(p);
                    if (event.getClick() == ClickType.RIGHT) {
                        // продать предмет (ПКМ)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count >= 1 && ThisMatlip.getLimit() >= 1) {
                            consumeItem(p, 1, clickedItem);
                            double notGot = ThisMatlip.getPrice();
                            CMI.getInstance().getPlayerManager().getUser(p).deposit(notGot);
                            if (ThisMatlip.getLimit() == -2) {

                            } else {
                                AmountWithdraver(clickedItem, p, 1, Fartsovschikkubogram.VarSales);
                            }
                        } else if (ThisMatlip.getLimit() <= 0) {
                            p.sendMessage(singleton.Messgaes.get(0));
                        } else if (count <= 0) {
                            p.sendMessage(singleton.Messgaes.get(1));
                        }

                    } else if (event.getClick() == ClickType.SHIFT_RIGHT) {
                        // продать все предметы (ПКМ+SHIFT)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count == 0) {
                            p.sendMessage(singleton.Messgaes.get(2));
                            return;
                        } else {
                            if (count > ThisMatlip.getLimit()) {
                                consumeItem(p, ThisMatlip.getLimit(), clickedItem);
                                double notGot = ThisMatlip.getPrice() * ThisMatlip.getLimit();
                                CMI.getInstance().getPlayerManager().getUser(p).deposit(notGot);
                                p.sendMessage(singleton.Messgaes.get(0));
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, ThisMatlip.getLimit(), Fartsovschikkubogram.VarSales);
                                }
                            } else {
                                consumeItem(p, count, clickedItem);
                                double notGot = ThisMatlip.getPrice() * count;
                                CMI.getInstance().getPlayerManager().getUser(p).deposit(notGot);
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, count, Fartsovschikkubogram.VarSales);
                                }
                            }

                        }
                    } else if (event.getClick() == ClickType.LEFT) {
                        // продать стак (ЛКМ)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count == 0) {
                            p.sendMessage(singleton.Messgaes.get(1));
                        } else if (count < 64) {
                            p.sendMessage(singleton.Messgaes.get(3));
                        } else {
                            if (ThisMatlip.getLimit() < 64) {
                                consumeItem(p, ThisMatlip.getLimit(), clickedItem);
                                double notGot = ThisMatlip.getPrice() * ThisMatlip.getLimit();
                                CMI.getInstance().getPlayerManager().getUser(p).deposit(notGot);
                                p.sendMessage(singleton.Messgaes.get(0));
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, ThisMatlip.getLimit(), Fartsovschikkubogram.VarSales);
                                }
                            } else {
                                consumeItem(p, 64, clickedItem);
                                double notGot = ThisMatlip.getPrice() * 64;
                                CMI.getInstance().getPlayerManager().getUser(p).deposit(notGot);
                                if (ThisMatlip.getLimit() == -2) {

                                } else {
                                    AmountWithdraver(clickedItem, p, 64, Fartsovschikkubogram.VarSales);
                                }
                            }
                        }
                    }
                } //Продажа
                menufars.OpenMenu(p);
            } //menufars
            else if (event.getView().getTitle().contains(singleton.menu3.replace("%lvl%", String.valueOf(Fartsovschikkubogram.playersLvls.get(p.getUniqueId()))))) {
                double bal = CMI.getInstance().getPlayerManager().getUser(p).getBalance();
                Fartsovschikkubogram.quest genQuest = Fartsovschikkubogram.playersQuests.get(p.getUniqueId());
                if (slot == 46) {
                    //Информация
                    return;
                } else if (slot == 52) {
                    menu1.OpenMenu(p);
                    return;
                } else if (slot == 40) {
                    singleton.Sounds.get(3).PlaySound(p);
                    if (bal >= 1) {
                        if (genQuest.RetMoney() < bal) {
                            CMI.getInstance().getPlayerManager().getUser(p).withdraw((double) genQuest.RetMoney());
                            genQuest.SubstractMoney(genQuest.RetMoney());
                        } else {
                            CMI.getInstance().getPlayerManager().getUser(p).withdraw(bal);
                            genQuest.SubstractMoney((int) bal);
                        }
                    }
                }
                Fartsovschikkubogram.MatAmount ThisMatamount = GetMatamount(p, clickedItem);
                if ((slot >= 11 && slot <= 15) || (slot >= 20 && slot <= 24)) {
                    singleton.Sounds.get(2).PlaySound(p);
                    if (event.getClick() == ClickType.RIGHT) {
                        // сдать предмет (ПКМ)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count >= 1 && ThisMatamount.RetAmount() >= 1) {
                            consumeItem(p, 1, clickedItem);
                            QuestItemAmountDecrease(clickedItem, p, 1);
                        } else if (count >= 1 && ThisMatamount.RetAmount() <= 0) {
                            p.sendMessage(singleton.Messgaes.get(4));
                        } else if (count <= 0) {
                            p.sendMessage(singleton.Messgaes.get(6));
                        }

                    } else if (event.getClick() == ClickType.SHIFT_RIGHT) {
                        // продать все предметы (ПКМ+SHIFT)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count == 0) {
                            p.sendMessage(singleton.Messgaes.get(7));
                            return;
                        } else {
                            if (count > ThisMatamount.RetAmount()) {
                                consumeItem(p, ThisMatamount.RetAmount(), clickedItem);
                                p.sendMessage(singleton.Messgaes.get(5));
                                QuestItemAmountDecrease(clickedItem, p, ThisMatamount.RetAmount());
                            } else {
                                consumeItem(p, count, clickedItem);
                                QuestItemAmountDecrease(clickedItem, p, count);
                            }

                        }
                    } else if (event.getClick() == ClickType.LEFT) {
                        // продать стак (ЛКМ)
                        int count = 0;
                        for (ItemStack item : playerInv.getContents()) {
                            if (item != null && item.getType() == clickedItem.getType()) {
                                count += item.getAmount();
                            }
                        } //Проверка сколько предметов в инвентаре
                        if (count < 64) {
                            p.sendMessage(singleton.Messgaes.get(8));
                        } else {
                            if (ThisMatamount.RetAmount() < 64) {
                                consumeItem(p, ThisMatamount.RetAmount(), clickedItem);
                                p.sendMessage(singleton.Messgaes.get(5));
                                QuestItemAmountDecrease(clickedItem, p, ThisMatamount.RetAmount());
                            } else {
                                consumeItem(p, 64, clickedItem);
                                QuestItemAmountDecrease(clickedItem, p, 64);
                            }
                        }
                    }

                    if (ThisMatamount.RetAmount() <= 0 && ThisMatamount.RetReward() >= 1) {
                        QuestGetItemReward(p, clickedItem);
                        p.sendMessage(singleton.Messgaes.get(9));
                    }

                }
                if (QuestCheckFor(Fartsovschikkubogram.playersQuests.get(p.getUniqueId()), Fartsovschikkubogram.playersLvls.get(p.getUniqueId()))) {
                    Fartsovschikkubogram.playersLvls.put(p.getUniqueId(), Fartsovschikkubogram.playersLvls.get(p.getUniqueId()) + 1);
                    /*Fartsovschikkubogram.GainerChange(p.getUniqueId());*/
                }
                //Проверка сделаны ли все квесты если да то +1лвл
                menulvl.OpenMenu(p);
                } //menulvl

            else if (event.getView().getTitle().toLowerCase().contains(singleton.menu4.toLowerCase())) {

                if (Fartsovschikkubogram.VarBuys.get(p.getUniqueId()).isEmpty() || Fartsovschikkubogram.VarSales.get(p.getUniqueId()).isEmpty()) {
                    Fartsovschikkubogram.UPDVarsSB(p,0);
                }
                if (slot == 19) {
                    //Информация
                    return;
                } else if (slot == 25) {
                    menu1.OpenMenu(p);
                    return;
                }
                double bal = PlayerPoints.getInstance().getAPI().look(p.getUniqueId());


                if (slot >= 10 && slot <= 16 && !clickedItem.getItemMeta().getDisplayName().contains("Недоступно")) {
                    singleton.Sounds.get(4).PlaySound(p);
                    singleton.matlip ThisMatlip = GetMatlip(clickedItem,p,Fartsovschikkubogram.VarDonBuys,singleton.ItemsForDonateShop);
                    if (ThisMatlip == null) {
                        System.out.println("Interal error 2");
                        return;
                    } //NullableError
                    if (event.getClick() == ClickType.RIGHT) {
                        // купить предмет (ПКМ)

                        if (ThisMatlip.getLimit() >= 1) {
                            if(bal >= ThisMatlip.getPrice()) {
                                PlayerPoints.getInstance().getAPI().take(p.getUniqueId(), (int) ThisMatlip.getPrice());
                                addItemToPlayerInv(p, 1, clickedItem);
                                AmountWithdraver(clickedItem, p, 1, Fartsovschikkubogram.VarDonBuys);
                            }
                            else {
                                p.sendMessage(singleton.Messgaes.get(12));
                            }
                        }
                        else {
                            p.sendMessage(singleton.Messgaes.get(0));
                        }
                    } else if (event.getClick() == ClickType.LEFT) {
                        // купить стак предметов (ЛКМ)
                        if (ThisMatlip.getLimit() >= 64) {
                            if(bal >= ThisMatlip.getPrice() * 64) {
                                PlayerPoints.getInstance().getAPI().take(p.getUniqueId(), (int) ThisMatlip.getPrice() * 64);
                                addItemToPlayerInv(p, 64, clickedItem);
                                AmountWithdraver(clickedItem, p, 64, Fartsovschikkubogram.VarDonBuys);
                            }
                            else {
                                p.sendMessage(singleton.Messgaes.get(12));
                            }
                        }
                        else {
                            p.sendMessage(singleton.Messgaes.get(0));
                        }
                    }
                } //Покупка
                menudon.OpenMenu(p);
            }


        }
    }

    private boolean QuestCheckFor(Fartsovschikkubogram.quest q, int lvl) {
        int notMetConditions = 0;
        if(!(q.RetMoney() <= 0)) {
            return false;
        }
        for(singleton.challenge a : q.Retchallenges()) {
            if(!(a.RetAmount() <= 0)) {
                return false;
            }
        }
        for(Fartsovschikkubogram.MatAmount a : q.RetMaterials()) {
            if(a.RetAmount() <= 0) {
                notMetConditions++;
            }
        }
        if(notMetConditions <= singleton.BlocksMinReq[lvl-1]) {
            return false;
        }
        return true;
    }

    private boolean CheckFor(String contName, UUID p) {
        if(contName.contains(singleton.menu1.toLowerCase()) || contName.contains(singleton.menu2.toLowerCase()) || contName.contains(singleton.menu3.replace("%lvl%",String.valueOf(Fartsovschikkubogram.playersLvls.get(p))).toLowerCase()) || contName.contains(singleton.menu4.toLowerCase())) {
            return true;
        }
        return false;
    }

    void QuestItemAmountDecrease(ItemStack clickedItem, Player p, int sub) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(p.getUniqueId());
        for(Fartsovschikkubogram.MatAmount a : q.RetMaterials()) {
            if(singleton.ZeroAmountIS(a.RetMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {

                a.DecreaseAmount(sub);
                return;
            }
        }
    }

    void QuestGetItemReward(Player p,ItemStack clickedItem) {
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(p.getUniqueId());
        for(Fartsovschikkubogram.MatAmount a : q.RetMaterials()) {
            if(singleton.ZeroAmountIS(a.RetMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {
                double dob = a.RetReward();
                CMI.getInstance().getPlayerManager().getUser(p).deposit(dob);
                a.DeleteReward();
                return;
            }
        }
    }
    void AmountWithdraver(ItemStack clickedItem, Player p, int sub, Map<UUID, List<singleton.matlip>> workzone) {
        for (singleton.matlip m : workzone.get(p.getUniqueId())) { //Fartsovschikkubogram.VarBuys
            if (singleton.ZeroAmountIS(m.getMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {
                m.SubstractLimit(sub);
                break;
            }
        }
    }

    singleton.matlip GetMatlip(ItemStack clickedItem, Player p, Map<UUID, List<singleton.matlip>> workzone, List<singleton.matlip> linked) {
        for (singleton.matlip a : linked) { //singleton.ItemsForBuy
            if (singleton.ZeroAmountIS(a.getMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {
                //Связывание элементов
                for (singleton.matlip m : workzone.get(p.getUniqueId())) { //Fartsovschikkubogram.VarBuys

                    if (singleton.ZeroAmountIS(m.getMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {
                        return m;

                    }
                }
                break;

            }
        }
        return null;
    }

    Fartsovschikkubogram.MatAmount GetMatamount(Player p,ItemStack clickedItem) {
        for(Fartsovschikkubogram.MatAmount a : Fartsovschikkubogram.playersQuests.get(p.getUniqueId()).RetMaterials()) {

            if(singleton.ZeroAmountIS(a.RetMaterial()).equals(singleton.ZeroAmountIS(clickedItem))) {
                return a;
            }
        }

        return null;
    }

    public void consumeItem(Player player, int count, ItemStack mat) {
        if (count > 0) {
            ItemStack item = new ItemStack(mat.getType(), count);
            item.addUnsafeEnchantments(mat.getEnchantments());
            player.getInventory().removeItem(item);
        }
    }

    public void addItemToPlayerInv(Player player, int count, ItemStack mat) {
        if (count > 0) {
            ItemStack item = new ItemStack(mat.getType(), count);
            item.addUnsafeEnchantments(mat.getEnchantments());
            player.getInventory().addItem(item);
        }
    }
}

