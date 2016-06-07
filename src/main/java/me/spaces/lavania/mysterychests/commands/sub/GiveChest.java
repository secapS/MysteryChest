package me.spaces.lavania.mysterychests.commands.sub;

import me.spaces.lavania.MysteryChests;
import me.spaces.lavania.mysterychests.MysteryChest;
import me.spaces.lavania.mysterychests.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveChest extends SubCommand {

    public GiveChest() {
        super("gives player a mystery chest.", "mysterychest.admin", "<chestName> <amount> <playerName> ", new String[] {"give"});
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage());
            return;
        } else {
            if(MysteryChests.factory.getMysteryChest(args[0]) != null) {
                MysteryChest mysteryChest = MysteryChests.factory.getMysteryChest(args[0]);
                if(Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    MysteryChests.factory.giveMysteryChest(mysteryChest, Integer.parseInt(args[1]), player);
                    sender.sendMessage(ChatColor.GREEN + "Giving " + player.getName() + " a " + mysteryChest.getName() + " chest");
                    player.sendMessage(ChatColor.GREEN + "Here is your " + mysteryChest.getName() + " chest");
                } else {
                    sender.sendMessage(ChatColor.RED + "That is not a player.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "That is not a mystery chest.");
            }
        }
    }
}
