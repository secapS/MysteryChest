package me.spaces.lavania.mysterychests.commands.sub;

import me.spaces.lavania.MysteryChests;
import me.spaces.lavania.mysterychests.MysteryChest;
import me.spaces.lavania.mysterychests.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddItem extends SubCommand {

    public AddItem() {
        super("adds held item to a mystery chest.", "mysterychest.admin", "<chestName> <itemName> <rarity>", new String[] {"add"});
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return;
        }
        Player player = (Player)sender;
        if(args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage());
            return;
        } else {
            if(MysteryChests.factory.getMysteryChest(args[0]) != null) {
                MysteryChest mysteryChest = MysteryChests.factory.getMysteryChest(args[0]);
                if(player.getItemInHand() != null) {
                    ItemStack itemStack = player.getItemInHand();
                    MysteryChests.factory.addItemToMysteryChest(mysteryChest, args[1], itemStack, Integer.parseInt(args[2]));
                    player.sendMessage(ChatColor.GREEN + "Item added to mystery chest.");
                } else {
                    player.sendMessage(ChatColor.RED + "You must hold an item.");
                }
            }
        }
    }
}
