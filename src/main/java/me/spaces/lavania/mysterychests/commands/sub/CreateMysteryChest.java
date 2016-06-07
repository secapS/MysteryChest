package me.spaces.lavania.mysterychests.commands.sub;

import me.spaces.lavania.MysteryChests;
import me.spaces.lavania.mysterychests.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CreateMysteryChest extends SubCommand {

    public CreateMysteryChest() {
        super("creates new mysterychest.", "mysterychest.admin", "<chestName> <maxItems> <lore>", new String[] {"create"});
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage());
            return;
        } else {
            MysteryChests.factory.createMysteryChest(args[0], Integer.parseInt(args[1]), Arrays.asList(args[2].split(";")));
            sender.sendMessage(ChatColor.GREEN + "Chest created.");
        }
    }
}
