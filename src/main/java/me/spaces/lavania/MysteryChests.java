package me.spaces.lavania;

import me.spaces.lavania.mysterychests.MysteryChestFactory;
import me.spaces.lavania.mysterychests.commands.MysteryChestCommand;
import me.spaces.lavania.mysterychests.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MysteryChests extends JavaPlugin {

    public static MysteryChestFactory factory;

    public void onEnable() {
        factory = new MysteryChestFactory(this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getCommand("mysterychest").setExecutor(new MysteryChestCommand());
        if(this.getConfig().getString("broadcast-message") == null) {
            this.getConfig().set("broadcast-message", "&a{player} opened a {chest} chest!");
        }
        this.saveConfig();
    }
}