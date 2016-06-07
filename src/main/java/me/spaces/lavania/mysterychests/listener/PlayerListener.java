package me.spaces.lavania.mysterychests.listener;

import me.spaces.lavania.MysteryChests;
import me.spaces.lavania.mysterychests.MysteryChest;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private MysteryChests plugin;

    public PlayerListener(MysteryChests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if(MysteryChests.factory.isMysteryChest(itemStack)) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                if(itemStack.getAmount() > 1) {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                String name = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', itemStack.getItemMeta().getDisplayName().split(" ")[0]));
                MysteryChest mysteryChest = MysteryChests.factory.getMysteryChest(name);
                MysteryChests.factory.openMysteryChest(mysteryChest, player);
                for(Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("broadcast-message")).replace("{player}", player.getName()).replace("{chest}", mysteryChest.getName()));
                }
            }
        }
    }
}
