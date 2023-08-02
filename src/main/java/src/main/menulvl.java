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

import java.util.*;

public class menulvl {

    public static boolean OpenMenu(CommandSender sender) {
        singleton ST = new singleton();
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        if(Fartsovschikkubogram.playersQuests.get(player.getUniqueId()) == null) {
            Fartsovschikkubogram.GenerateQuestsPerPlayer(player);
        } //Если квесты не сгенерированы
        Fartsovschikkubogram.quest linked = Fartsovschikkubogram.playersQuests.get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 54, singleton.menu3.replace("%lvl%",String.valueOf(Fartsovschikkubogram.playersLvls.get(player.getUniqueId()))));


        ItemStack endCrystal = new ItemStack(Material.HONEYCOMB);
        ItemMeta itemMeta = endCrystal.getItemMeta();
        itemMeta.setDisplayName(singleton.infoL.get(0));
        itemMeta.setLore(ST.removeFirstElement(singleton.infoL));
        endCrystal.setItemMeta(itemMeta);


        ItemStack pufferfish = new ItemStack(Material.TIPPED_ARROW);
        itemMeta = pufferfish.getItemMeta();
        itemMeta.setDisplayName(ST.returer.get(0));
        itemMeta.setLore(ST.removeFirstElement(ST.returer));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        pufferfish.setItemMeta(itemMeta);


        // Устанавливаем их в нужные слоты инвентаря
        inventory.setItem(46, endCrystal); //HoneyComb
        inventory.setItem(52, pufferfish);
        // Создаем боковые панели
        ItemStack purplePane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemMeta purplePaneMeta = purplePane.getItemMeta();
        purplePaneMeta.setDisplayName(" ");
        purplePane.setItemMeta(purplePaneMeta);
        for (int i = 0; i < 54; i=i+9) {
            inventory.setItem(i, purplePane);
            inventory.setItem(i+8, purplePane);
        }
        //Other staffs
        //Сдать блоки

        //Доступный сегмент ;
        List<Fartsovschikkubogram.MatAmount> materials = new ArrayList<>(linked.RetMaterials());
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(player.getUniqueId()); //??????????
        int[] ChestPosition = singleton.ChestPosition[q.Length()-1];
        int index = 0;
        for (int i = 0; i < 10; i++) {
            if (ChestPosition[i] == 1) {
                Fartsovschikkubogram.MatAmount m = q.GetMaterialAndDelete(index);
                index++;
                ItemStack itemDrop = singleton.ZeroAmountIS(m.RetMaterial());
                ItemMeta itemDropMeta = itemDrop.getItemMeta();
                String RewardDisplacement = "";
                if (m.RetReward() == 0) {
                    RewardDisplacement = "вы получили награду.";
                } else {
                    RewardDisplacement = String.valueOf(m.RetReward());
                }
                Map<String, String> values = new HashMap<>();
                values.put("item", singleton.Translations.get(itemDrop.getType().toString()));
                values.put("amount", String.valueOf(m.RetAmount()));
                values.put("reward", RewardDisplacement);
                List<String> itemgetawayDN = new ArrayList<>();
                itemgetawayDN.add(singleton.itemgetaway);
                itemDropMeta.setDisplayName(singleton.replaceVariables(itemgetawayDN, values).get(0));
                itemDropMeta.setLore(singleton.replaceVariables(singleton.itemgetawayLore, values));
                itemDrop.setItemMeta(itemDropMeta);
                if (i < 5) {
                    inventory.setItem(i + 11, itemDrop);
                } else {
                    inventory.setItem(i + 15, itemDrop);
                }
            }
        }
        Fartsovschikkubogram.quest qest = Fartsovschikkubogram.playersQuests.get(player.getUniqueId());

        ItemStack itemDrop = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta itemDropMeta = itemDrop.getItemMeta();
        Map<String, String> values = new HashMap<>();
        values.put("amount", String.valueOf(qest.RetMoney()));
        List<String> itemgetawayDN = new ArrayList<>();
        itemgetawayDN.add(singleton.moneyrequired);
        itemDropMeta.setDisplayName(singleton.replaceVariables(itemgetawayDN, values).get(0));
        itemDropMeta.setLore(singleton.replaceVariables(singleton.moneyrequiredLore, values));
        itemDrop.setItemMeta(itemDropMeta);
        inventory.setItem(40, itemDrop);


        for (int i = 0; i < 2; i++) {
            int indd = (i == 0) ? 38 : 42;
            itemDrop = new ItemStack(Material.WHITE_CONCRETE, 1);
            itemDropMeta = itemDrop.getItemMeta();
            values = new HashMap<>();
            values.put("explain", Explanator(qest, i));
            values.put("reward", qest.Retchallenges().get(i).RetReward() + "");
            values.put("Amount", qest.Retchallenges().get(i).RetAmount() + "");
            itemgetawayDN = new ArrayList<>();
            itemgetawayDN.add(singleton.challengeitem);
            itemDropMeta.setDisplayName(singleton.processString(singleton.replaceVariables(itemgetawayDN, values).get(0)));
            itemDropMeta.setLore(singleton.replaceVariables(singleton.challengeitemLore, values));
            itemDrop.setItemMeta(itemDropMeta);
            inventory.setItem(indd, itemDrop);
        }

        player.openInventory(inventory);
        return true;
    }



    public static String Explanator(Fartsovschikkubogram.quest qest, int gett) {
        String explain = "";
        Map<String, String>  values = new HashMap<>();
        List<String> itemgetawayDN = new ArrayList<>();
        if(qest.Retchallenges().get(gett).RetModify().toUpperCase().equals("NULL")) {

            values.put("reward", qest.Retchallenges().get(gett).RetReward() + "");
            values.put("Amount", qest.Retchallenges().get(gett).RetAmount() + "");

            itemgetawayDN.add(singleton.ExplainNull.get(qest.Retchallenges().get(gett).RetEvent().toString()));
            explain = singleton.replaceVariables(itemgetawayDN, values).get(0);


        }
        else {
            values.put("reward", qest.Retchallenges().get(gett).RetReward() + "");
            values.put("Amount", qest.Retchallenges().get(gett).RetAmount() + "");

            itemgetawayDN.add(singleton.Explain.get(qest.Retchallenges().get(gett).RetEvent().toString()));
            explain = singleton.replaceVariables(itemgetawayDN, values).get(0);

        }
        return explain;
    }
}
