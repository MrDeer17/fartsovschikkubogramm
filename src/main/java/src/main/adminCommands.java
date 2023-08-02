package src.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class adminCommands implements CommandExecutor {

    public adminCommands(Fartsovschikkubogram plugin) {
        plugin.getCommand("marketFSet").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
            if(commandSender.hasPermission("Fartsovschik.admin") || commandSender.isOp()) {
                if(command.getName().equalsIgnoreCase("marketFSet")) {
                    if(args.length == 2) {
                        Fartsovschikkubogram.playersLvls.put(Bukkit.getPlayer(args[0]).getUniqueId(),Integer.parseInt(args[1]));
                        Fartsovschikkubogram.GenerateQuestsPerPlayer(Bukkit.getPlayer(args[0]));
                        //Fartsovschikkubogram.GainerChange(Bukkit.getPlayer(args[0]).getUniqueId());
                        commandSender.sendMessage(ChatColor.GREEN+"Succesfully");
                    }
                    else {
                        commandSender.sendMessage(ChatColor.RED+"/marketFSet %player% %level%");
                    }
                }
                else if(command.getName().equalsIgnoreCase("marketLook")) {
                    if(args.length == 1) {
                        Fartsovschikkubogram.playersLvls.put(Bukkit.getPlayer(args[0]).getUniqueId(),Integer.parseInt(args[1]));
                        Fartsovschikkubogram.GenerateQuestsPerPlayer((Player) commandSender);
                        commandSender.sendMessage(ChatColor.GREEN+args[0]+" "+Fartsovschikkubogram.playersLvls.get(args[0]));
                    }
                    else {
                        commandSender.sendMessage(ChatColor.RED+"/marketLook %player%");
                    }
                }
                else if(command.getName().equalsIgnoreCase("marketReload")) {
                    Plugin plugin = JavaPlugin.getPlugin(Fartsovschikkubogram.class);
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                    commandSender.sendMessage("Плагин перезапущен.");
                    return true;
                }
            }
            else {
                commandSender.sendMessage(ChatColor.RED+"Такой команды не существует");
                return true;
            }
        return true;

    }
}