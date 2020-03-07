package me.eridamusLoL.safeBook;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;



public class safeBook implements Listener{

	private Main main;
	boolean cancelled = false;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		Action a = e.getAction();
		if(a.equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.LECTERN) && player.getInventory().getItemInMainHand().getType().equals(Material.AIR) && player.isOp()) {
			//Check if XYZ safeBook exist. 
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			String blockXYZ = Integer.toString(x).concat(Integer.toString(y)).concat(Integer.toString(z));
			System.out.println("STRING XYZ: "+ blockXYZ);
			ConfigurationSection sec = main.getConfig().getConfigurationSection("safeBooksXYZ");
	        if(sec.contains(blockXYZ)) {
	        	sec.set(blockXYZ, null);
	        	main.saveConfig();
	        	player.sendMessage("This book is not protected anymore.");
	        }else{
	        	sec.set(blockXYZ + ".x", x);
	        	sec.set(blockXYZ + ".y", y);
	        	sec.set(blockXYZ + ".z", z);
	        	main.saveConfig();
	        	player.sendMessage("This book is now protected.");
	        }
	        
		}
	}
	
	@EventHandler
	public void onPlayerTakeLecternBookEvent(PlayerTakeLecternBookEvent e) {
		Player player = e.getPlayer();
		Block block = e.getLectern().getBlock();
		String blockXYZ = Integer.toString(block.getX()).concat(Integer.toString(block.getY())).concat(Integer.toString(block.getZ()));
		ConfigurationSection sec = main.getConfig().getConfigurationSection("safeBooksXYZ");
        if(sec.contains(blockXYZ)) {
        	player.sendMessage("You can't take this book!");
    		e.setCancelled(true);
        }
	}
	
	public safeBook(Main main) {
		this.main = main;
	}
}
