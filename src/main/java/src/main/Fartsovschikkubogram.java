package src.main;

import com.Zrips.CMI.CMI;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class Fartsovschikkubogram extends JavaPlugin {

    public static Map<UUID, Integer> playersLvls = new HashMap<>();
    public static Map<UUID, quest> playersQuests = new HashMap<>();
    public static Map<UUID, List<singleton.matlip>> VarSales = new HashMap<>();
    public static Map<UUID, List<singleton.matlip>> VarBuys = new HashMap<>();
    public static Map<UUID, List<singleton.matlip>> VarDonBuys = new HashMap<>();

    //public static Map<UUID, List<singleton.challenge>> VarChallenges = new HashMap<>();
    @Override
    public void onEnable() {

        // фарцовщик изменил свои торги
        //lang.txt
        loadArray();
        singleton.ReadFile("menus.yml");
        singleton.ReadFile("items.yml");
        singleton.ReadFile("messages.yml");
        singleton.ReadFile("translations.yml");

        //UPDVarsSB();

        getCommand("market").setExecutor(new menu1());
        getServer().getPluginManager().registerEvents(new menu1Listener(), this);
        getServer().getPluginManager().registerEvents(new IfItJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayEventQuest(), this);
        new adminCommands(this);
        singleton.LastPreparation();

        //Enabling Cycles
        new BukkitRunnable() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String time = now.format(formatter);
                for (String s : singleton.datesArray) {
                    if (time.contains(s) || s.contains(time)) {
                        VarSales.clear();
                        VarBuys.clear();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(singleton.Messgaes.get(10));
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 1200); //Checkfor
        new BukkitRunnable() {
            @Override
            public void run() {
                saveArray();
            }
        }.runTaskTimerAsynchronously(this, 0, 6000); //AllAutoSave in 5 mins
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveArray();

    }

    public static void loadArray() {
        try {
            File file = new File("plugins/market/playersLVLS.dat");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                playersLvls = (Map<UUID, Integer>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File("plugins/market/PlayersQUESTS.dat");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                playersQuests = (Map<UUID, quest>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File("plugins/market/VarSales.dat");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                VarSales = (Map<UUID, List<singleton.matlip>>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/VarBuys.dat");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                VarBuys = (Map<UUID, List<singleton.matlip>>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/VarDonbuys.dat");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                VarDonBuys = (Map<UUID, List<singleton.matlip>>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveArray() {
        try {
            File file = new File("plugins/market/playersLVLS.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playersLvls);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/PlayersQUESTS.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playersQuests);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/VarSales.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(VarSales);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/VarBuys.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(VarBuys);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("plugins/market/VarDonbuys.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(VarBuys);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UPDVarsSB(Player player, int id) { //

            if(id == 0) {
                List<singleton.matlip> HESH = new ArrayList<>();
                HESH.clear();
                HESH.addAll(singleton.ItemsForSale);
                Collections.shuffle(HESH);
                List<singleton.matlip> OUT = new ArrayList<>();
                OUT.clear();
                for (int j = 0; j <= 10; j++) {
                    OUT.add(HESH.get(j));
                }
                VarSales.put(player.getUniqueId(), OUT);

                HESH = new ArrayList<>();
                HESH.clear();
                HESH.addAll(singleton.ItemsForBuy);
                Collections.shuffle(HESH);
                OUT = new ArrayList<>();
                OUT.clear();
                for (int j = 0; j <= 5; j++) {
                    OUT.add(HESH.get(j));
                }
                VarBuys.put(player.getUniqueId(), OUT);

                HESH = new ArrayList<>();
                HESH.clear();
                HESH.addAll(singleton.ItemsForDonateShop);
                Collections.shuffle(HESH);
                OUT = new ArrayList<>();
                OUT.clear();
                for (int j = 0; j <= 5; j++) {
                    OUT.add(HESH.get(j));
                }
                VarDonBuys.put(player.getUniqueId(), OUT);
            }
            else {
                List<singleton.matlip> HESH = new ArrayList<>();
                HESH.clear();
                HESH.addAll(singleton.ItemsForSale);
                Collections.shuffle(HESH);
                List<singleton.matlip> OUT = new ArrayList<>();
                OUT.clear();
                for (int j = 0; j <= 10; j++) {
                    OUT.add(HESH.get(j));
                }
                VarSales.put(player.getUniqueId(), OUT);

                HESH = new ArrayList<>();
                HESH.clear();
                HESH.addAll(singleton.ItemsForBuy);
                Collections.shuffle(HESH);
                OUT = new ArrayList<>();
                OUT.clear();
                for (int j = 0; j <= 5; j++) {
                    OUT.add(HESH.get(j));
                }
                VarBuys.put(player.getUniqueId(), OUT);
            }
            //GainerChange(player.getUniqueId());
    }

    public static void GainerChange(UUID p) {
        List<singleton.matlip> Updated = new ArrayList<>();
        double Gain1 = singleton.Gainer[0][Fartsovschikkubogram.playersLvls.get(p)-1];
        double Gain2 = singleton.Gainer[1][Fartsovschikkubogram.playersLvls.get(p)-1];
        double Gain3 = singleton.Gainer[2][Fartsovschikkubogram.playersLvls.get(p)-1];
        double Gain4 = singleton.Gainer[3][Fartsovschikkubogram.playersLvls.get(p)-1];

        for(singleton.matlip a : VarSales.get(p)) {
            if(Fartsovschikkubogram.playersLvls.get(p) >= 11) {
                Updated.add(new singleton.matlip(a.getMaterial(),-2,a.getPrice()*Gain2)); //Unlim
            }
            else {

                Updated.add(new singleton.matlip(a.getMaterial(),(int) Math.floor(a.getLimit() * Gain4),a.getPrice()*Gain2));
            }

        }
        VarSales.put(p,Updated);
        Updated = new ArrayList<>(); Updated.clear();

        for(singleton.matlip a : VarBuys.get(p)) {
            if(Fartsovschikkubogram.playersLvls.get(p) >= 11) {
                Updated.add(new singleton.matlip(a.getMaterial(),-2,a.getPrice()*Gain1));
            }
            else {
                Updated.add(new singleton.matlip(a.getMaterial(), (int) Math.floor(a.getLimit() * Gain3),a.getPrice()*Gain1));

            }

        }
        VarBuys.put(p,Updated);
    }

    public static void GenerateQuestsPerPlayer(Player player) {
        int lvl = playersLvls.get(player.getUniqueId());
        if(lvl >= 11) {
            return;
        }
        List<MatAmount> materials = new ArrayList<>();
        List<singleton.matquest> HESH = new ArrayList<>(singleton.ItemsForLVL.get(lvl-1));
        List<singleton.challenge> chalgs = new ArrayList<>();
        List<singleton.challenge> HESHchalgs = new ArrayList<>(singleton.ItemsChallenges.get(lvl-1));

        Collections.shuffle(HESH);
        int addon = 0;
        for (int i = 0; i < singleton.BlocksPerLevel[lvl-1]; i++) {
            if(HESH.get(i+addon).getMaterial() != null) {
                materials.add(new MatAmount(HESH.get(i+addon).getMaterial(),HESH.get(i+addon).GetRandomAmount(), HESH.get(i+addon).GetRandomReward()));
            }
            else {

                addon += 1;
                i--;
            }
        }
        Collections.shuffle(HESHchalgs);
        for (int i = 0; i <= 1; i++) {
            //Повтор 2 раза
            chalgs.add(HESHchalgs.get(i));

        }
        playersQuests.put(player.getUniqueId(),new quest(materials,singleton.MoneyPerLevel[lvl-1],chalgs));
    }

    public static class quest implements Serializable {
        private List<MatAmount> materials;
        private List<singleton.challenge> challenges;
        private double Money;

        public quest(List<MatAmount> materials, double Money, List<singleton.challenge> challenges)
        {
            this.materials = materials;
            this.challenges = challenges;
            this.Money = Money;
        }

        int Length() {
            return materials.size();
        }
        MatAmount GetMaterialAndDelete(int index) {
            MatAmount ret = materials.get(index);
            //materials.remove(0);
            return ret;
        }
        List<MatAmount> RetMaterials() {
            return materials;
        }
        double RetMoney() {
            return Money;
        }
        void SubstractMoney(double sub) {
            Money -= sub;
        }
        List<singleton.challenge> Retchallenges() {
            return challenges;
        }

    }

    public static class MatAmount implements Serializable {

        private final SerializableItemStack material;
        private int amount;
        private int reward;
        public MatAmount(ItemStack material, int amount, int reward)
        {
            if (material == null) {
                throw new IllegalArgumentException("Material cannot be null");
            }
            this.material = new SerializableItemStack(material);
            this.amount = amount;
            this.reward = reward;
        }

        ItemStack RetMaterial() {
            return material.getItemStack();
        }
        int RetAmount() {
            return amount;
        }
        int RetReward() {
            return reward;
        }
        void DecreaseAmount(int vol) {
            amount -= vol;
        }
        void DeleteReward() {
            reward = 0;
        }
    }

    public static void CheckForNewLevel(Player p) {
        boolean cheking = true;
        Fartsovschikkubogram.quest q = Fartsovschikkubogram.playersQuests.get(p.getUniqueId());
        if(q == null) {
            return;
        } //Ещё не создано
        for(singleton.challenge c : q.Retchallenges()) {
            if(!(c.RetAmount() <= 0)) {
                cheking = false;
                return;
            }
        }
        for(Fartsovschikkubogram.MatAmount Mat : q.RetMaterials()) {
            if(!(Mat.RetAmount() <= 0)) {
                cheking = false;
                return;
            }
        }
        if(!(q.RetMoney() <= 0)) {
            cheking = false;
            return;
        }


        if(cheking) {
                List<String> commands = singleton.CommandsPerLevel.get(Fartsovschikkubogram.playersLvls.get(p)-1);
                for (String command : commands) {
                    String replacedCommand = command.replace("%player%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedCommand);
                }
            Fartsovschikkubogram.playersLvls.put(p.getUniqueId(),Fartsovschikkubogram.playersLvls.get(p)+1);
            Fartsovschikkubogram.GenerateQuestsPerPlayer(p);
            singleton.Sounds.get(1).PlaySound(p);
        }



    }

    public static class SerializableItemStack implements Serializable {
        private Material mat;
        private Map<String, Integer> enchs = new HashMap<>();

        public SerializableItemStack(ItemStack itemStack) {
            if(itemStack != null) {
                this.mat = itemStack.getType();
                for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int level = entry.getValue();
                    enchs.put(enchantment.getKey().getKey() + "'" + enchantment.getKey().getNamespace(), level);
                }
            }
        }

        public ItemStack getItemStack() {
            ItemStack out = new ItemStack(mat);
            ItemMeta oMeta = out.getItemMeta();
            if(enchs.size() <= 0) {
            for (Map.Entry<String, Integer> entry : enchs.entrySet()) {
                Enchantment enchantment = Enchantment.getByKey(new NamespacedKey(entry.getKey().split("'")[1],entry.getKey().split("'")[0]));
                int level = entry.getValue();
                oMeta.addEnchant(enchantment, level, true);
            }
            }
            out.setItemMeta(oMeta);
            return out;
        }
    }





    
}