package me.eridamusLoL.safeBook;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new safeBook(this), this);
	}
}
