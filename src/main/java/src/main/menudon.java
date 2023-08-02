package src.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.floor;

public class menudon {

    //singleton.menu4

    public static boolean OpenMenu(CommandSender sender) {
        singleton ST = new singleton();
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 27, singleton.menu4);
        if (Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()) == null || Fartsovschikkubogram.VarSales.get(player.getUniqueId()) == null || Fartsovschikkubogram.VarBuys.get(player.getUniqueId()) == null) {
            Fartsovschikkubogram.UPDVarsSB(player,0);
        }
        //int ItC = Math.abs(player.getUniqueId().hashCode() % 100);

        // Создаем боковые панели
        ItemStack purplePane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemMeta purplePaneMeta = purplePane.getItemMeta();
        purplePaneMeta.setDisplayName(" ");
        purplePane.setItemMeta(purplePaneMeta);
        for (int i = 0; i < 27; i = i + 9) {
            inventory.setItem(i, purplePane);
            inventory.setItem(i + 8, purplePane);
        }
        //Other staffs
        ItemStack bell = new ItemStack(Material.HONEYCOMB);
        ItemMeta itemMeta = bell.getItemMeta();
        itemMeta.setDisplayName(singleton.infoD.get(0));
        itemMeta.setLore(ST.removeFirstElement(singleton.infoD));
        bell.setItemMeta(itemMeta);


        ItemStack pufferfish = new ItemStack(Material.TIPPED_ARROW);
        itemMeta = pufferfish.getItemMeta();
        itemMeta.setDisplayName(ST.returer.get(0));
        itemMeta.setLore(ST.removeFirstElement(ST.returer));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        pufferfish.setItemMeta(itemMeta);


        // Устанавливаем их в нужные слоты инвентаря
        inventory.setItem(19, bell);
        inventory.setItem(25, pufferfish);

        //Продажа
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bareta = barrier.getItemMeta();
        bareta.setDisplayName(ChatColor.RED + "Недоступно");
        List<String> lor = new ArrayList<>();
        lor.add(ChatColor.WHITE + "Прокачай фарцовщика что бы открыть эту ячейку");
        bareta.setLore(lor);
        barrier.setItemMeta(bareta);


        int adding = 2;
        //Покупка
        //Недоступный сегмент
        barrier = new ItemStack(Material.RED_CONCRETE);
        bareta = barrier.getItemMeta();
        bareta.setDisplayName(ChatColor.RED + "Недоступно");
        lor.clear();
        lor.add(ChatColor.WHITE + "Прокачай фарцовщика что бы открыть эту ячейку");
        bareta.setLore(lor);
        barrier.setItemMeta(bareta);
        adding = (int) floor(Fartsovschikkubogram.playersLvls.get(player.getUniqueId()) / 2) + 1;
        if (adding >= 5) {
            adding = 5;
        }
        for (int i = 5 - adding; i >= 0; i--) {
            inventory.setItem(15 - i, barrier);
        }

        //Доступный сегмент

        //System.out.println(player.getUniqueId());
        for (int i = 0; i < adding; i++) {
            ItemStack ab = new ItemStack(Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()).get(i).getMaterial());
            ItemMeta abmeta = ab.getItemMeta();
            abmeta.setDisplayName(singleton.processString(singleton.itembuy.replace("%item%", singleton.Translations.get(ab.getType().toString())).replace("%price%", String.valueOf(Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()).get(i).getPrice())).replace("%amount%", String.valueOf(Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()).get(i).getLimit()))));
            Map<String, String> values = new HashMap<>();
            values.put("item", singleton.Translations.get(ab.getType().toString()));
            values.put("price", String.valueOf(Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()).get(i).getPrice()));
            values.put("amount", String.valueOf(Fartsovschikkubogram.VarDonBuys.get(player.getUniqueId()).get(i).getLimit()));

            abmeta.setLore(singleton.replaceVariables(singleton.itembuyLore, values));
            ab.setItemMeta(abmeta);
            inventory.setItem(i + 11, ab);
        }
        player.openInventory(inventory);
        return true;
    }
}
