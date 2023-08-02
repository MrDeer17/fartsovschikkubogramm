package src.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class menu1 implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        Player p = (Player) sender;
        //Проверка можно ли выдавать новый уровень игроку
        Fartsovschikkubogram.CheckForNewLevel(p);

        //Конец проверки
        OpenMenu(p);



        return true;
    }

    public static boolean OpenMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, singleton.menu1);
        singleton ST = new singleton();
        // Создаем предметы
        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta itemMeta = book.getItemMeta();
        itemMeta.setDisplayName(ST.infoBook);
        itemMeta.setLore(ST.infoBookLore);
        book.setItemMeta(itemMeta);

        ItemStack bell = new ItemStack(Material.BELL);
        itemMeta = bell.getItemMeta();
        itemMeta.setDisplayName(ST.fartsovshik);
        itemMeta.setLore(ST.fartsovshikLore);
        bell.setItemMeta(itemMeta);

        ItemStack endCrystal = new ItemStack(Material.END_CRYSTAL);
        itemMeta = endCrystal.getItemMeta();
        itemMeta.setDisplayName(ST.levelUpping);
        itemMeta.setLore(ST.levelUppingLore);
        endCrystal.setItemMeta(itemMeta);


        ItemStack pufferfish = new ItemStack(Material.PUFFERFISH);
        itemMeta = pufferfish.getItemMeta();
        itemMeta.setDisplayName(ST.donate);
        itemMeta.setLore(ST.donateLore);
        pufferfish.setItemMeta(itemMeta);


        // Устанавливаем их в нужные слоты инвентаря
        inventory.setItem(10, book);
        inventory.setItem(12, bell);
        inventory.setItem(14, endCrystal);
        inventory.setItem(16, pufferfish);

        // Создаем боковые панели
        ItemStack purplePane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemMeta purplePaneMeta = purplePane.getItemMeta();
        purplePaneMeta.setDisplayName(" ");
        purplePane.setItemMeta(purplePaneMeta);
        inventory.setItem(0, purplePane);
        inventory.setItem(8, purplePane);
        inventory.setItem(9, purplePane);
        inventory.setItem(17, purplePane);
        inventory.setItem(18, purplePane);
        inventory.setItem(26, purplePane);

        // Открываем меню для игрока
        player.openInventory(inventory);
    return true;
    }



}
