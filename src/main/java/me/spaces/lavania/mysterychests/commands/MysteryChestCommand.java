package me.spaces.lavania.mysterychests.commands;

import me.spaces.lavania.mysterychests.commands.sub.AddItem;
import me.spaces.lavania.mysterychests.commands.sub.CreateMysteryChest;
import me.spaces.lavania.mysterychests.commands.sub.GiveChest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MysteryChestCommand implements CommandExecutor {

    Set<SubCommand> subCommands = new HashSet<SubCommand>();

    public MysteryChestCommand() {
        this.subCommands.add(new AddItem());
        this.subCommands.add(new CreateMysteryChest());
        this.subCommands.add(new GiveChest());
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(command.getName().equalsIgnoreCase("mysterychest")) {
            if (args.length == 0) {
                for (SubCommand c : subCommands) {
                    if(c.hasPermission() && sender.hasPermission(c.getPermission())) {
                        sender.sendMessage("/mysterychest " + c.getAliases()[0] + " " + c.getUsage());
                    }
                }
            } else {
                if(getSubCommand(args[0]) != null) {
                    SubCommand subCommand = getSubCommand(args[0]);
                    Vector<String> a = new Vector<String>(Arrays.asList(args));
                    a.remove(0);
                    args = a.toArray(new String[a.size()]);

                    if(subCommand.hasPermission() && sender.hasPermission(subCommand.getPermission())) {
                        subCommand.onCommand(sender, args);
                    }
                    else if(subCommand.hasPermission() && !sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                    } else {
                        subCommand.onCommand(sender, args);
                    }

                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "That is not a command.");
                    return false;
                }
            }
        }
        return false;
    }

    private String aliases(SubCommand cmd) {
        String fin = "";

        for (String a : cmd.getAliases()) {
            fin += a + " | ";
        }

        return fin.substring(0, fin.lastIndexOf(" | "));
    }

    private SubCommand getSubCommand(String name) {
        for (SubCommand cmd : subCommands) {
            if (cmd.getClass().getSimpleName().equalsIgnoreCase(name)) return cmd;

            for (String alias : cmd.getAliases()) if (name.equalsIgnoreCase(alias)) return cmd;
        }
        return null;
    }
}
