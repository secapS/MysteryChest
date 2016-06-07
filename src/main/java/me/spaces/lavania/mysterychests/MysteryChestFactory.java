package me.spaces.lavania.mysterychests;

import me.spaces.lavania.MysteryChests;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.*;

public class MysteryChestFactory {

    private MysteryChests plugin;

    public MysteryChestFactory(MysteryChests plugin) {
        this.plugin = plugin;
    }

    public void saveMysteryChest(MysteryChest mysteryChest, List<String> lore) {
        String chestConfigName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        this.plugin.getConfig().set("chests." + chestConfigName + ".name", mysteryChest.getName());
        this.plugin.getConfig().set("chests." + chestConfigName + ".max-size", mysteryChest.getMaxSize());
        this.plugin.getConfig().set("chests." + chestConfigName + ".lore", lore);
        this.plugin.saveConfig();
    }

    public boolean createMysteryChest(String name, int maxSize, List<String> lore) {
        if(this.plugin.getConfig().getConfigurationSection("chests") != null) {
            for(String chest : this.plugin.getConfig().getConfigurationSection("chests").getKeys(false)) {
                if(chest.equalsIgnoreCase(name)) {
                    return false;
                }
            }
        }
        MysteryChest mysteryChest = new MysteryChest(name, maxSize);
        this.saveMysteryChest(mysteryChest, lore);
        return true;
    }

    public double getItemRarity(MysteryChest mysteryChest, String itemStack) {
        String chests = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        for(String itemNames : this.plugin.getConfig().getConfigurationSection("chests." + chests + ".items").getKeys(false)) {
            if(itemNames.equalsIgnoreCase(itemStack)) {
                return (this.plugin.getConfig().getInt("chests." + chests + ".items." + itemNames + ".rarity") / 100.0);
            }
        }
        return 0.0;
    }

    public Map<ItemStack, Integer> getMysteryChestItems(MysteryChest mysteryChest) {
        Map<ItemStack, Integer> temp = new HashMap<ItemStack, Integer>();
        String chestConfigName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        for(String items : this.plugin.getConfig().getConfigurationSection("chests." + chestConfigName + ".items").getKeys(false)) {
            for(String item : this.plugin.getConfig().getConfigurationSection("chests." + chestConfigName + ".items." + items).getKeys(false)) {
                ItemStack itemStack = this.plugin.getConfig().getItemStack("chests." + chestConfigName + ".items." + items + ".itemStack");
                int rarity = this.plugin.getConfig().getInt("chests." + chestConfigName + ".items." + items + ".rarity");
                temp.put(itemStack, rarity);
            }
        }
        if(temp.size() > 0) return temp;
        else return null;
    }

    double round(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    public void openMysteryChest(MysteryChest mysteryChest, Player player) {
        List<ItemStack> randomItems = new ArrayList<ItemStack>(mysteryChest.getMaxSize());
        String chestConfigName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        while(randomItems.size() < mysteryChest.getMaxSize()) {
            for(String items : this.plugin.getConfig().getConfigurationSection("chests." + chestConfigName + ".items").getKeys(false)) {
                for(String item : this.plugin.getConfig().getConfigurationSection("chests." + chestConfigName + ".items." + items).getKeys(false)) {
                    double random = round(Math.random());
                    if(this.getItemRarity(mysteryChest, item) >= random) {
                        ItemStack itemStack = this.plugin.getConfig().getItemStack("chests." + chestConfigName + ".items." + items + ".itemStack");
                        if(!randomItems.contains(itemStack)) randomItems.add(itemStack);
                    }
                }
            }
        }

        if(randomItems.size() > 0) {
            for(ItemStack item : randomItems) {
                player.getInventory().addItem(item);
            }
        }
    }

    public void addItemToMysteryChest(MysteryChest mysteryChest, String itemName, ItemStack itemStack, int rarity) {
        String chestConfigName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        this.plugin.getConfig().set("chests." + chestConfigName + ".items." + itemName + ".itemStack", itemStack);
        this.plugin.getConfig().set("chests." + chestConfigName + ".items." + itemName + ".rarity", rarity);
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
    }

    public void giveMysteryChest(MysteryChest mysteryChest, int amount, Player player) {
        String chestConfigName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName())).toLowerCase();
        ItemStack mysteryChestItem = new ItemStack(Material.CHEST, amount);
        ItemMeta itemMeta = mysteryChestItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mysteryChest.getName()) + " Chest");
        List<String> lore = new ArrayList<String>();
        for(String s : this.plugin.getConfig().getStringList("chests." + chestConfigName + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s).replace("_", " "));
        }
        itemMeta.setLore(lore);
        mysteryChestItem.setItemMeta(itemMeta);
        player.getInventory().addItem(mysteryChestItem);
    }

    public MysteryChest getMysteryChest(String name) {
        for(String chest : this.plugin.getConfig().getConfigurationSection("chests").getKeys(false)) {
            if(chest.equalsIgnoreCase(name)) {
                return new MysteryChest(this.plugin.getConfig().getString("chests." + chest + ".name"), this.plugin.getConfig().getInt("chests." + chest + ".max-size"));
            }
        }
        return null;
    }

    public boolean isMysteryChest(ItemStack itemStack) {
        for(String chest : this.plugin.getConfig().getConfigurationSection("chests").getKeys(false)) {
            if(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("chests." + chest + ".name")) + " Chest")) {
                return true;
            }
        }
        return false;
    }
}
