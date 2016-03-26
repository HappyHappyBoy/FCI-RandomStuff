package de.teamfci.dungeonmobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import DungeonMobsHandler.Handler;
import de.teamfci.commands.CommandDungeonMobs;
import de.teamfci.registerSpawn.registerMobSpawner;

public class DungeonMobs extends JavaPlugin {
	
	public void onEnable() {
		registerMobs();
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage("§7Mobs:");
			String m = registerMobSpawner.mobs.toString();
			m = m.replace("[", "").replace("]", "");
			p.sendMessage(m);
		}
		Handler.pl = this;
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new registerMobSpawner(), this);
		this.getCommand("dungeonmobs").setExecutor(new CommandDungeonMobs());
	}
	
	public static void registerMobs() {
		registerMobSpawner.mobs.add("creeper");
		registerMobSpawner.mobs.add("skeleton");
		registerMobSpawner.mobs.add("spider");
		registerMobSpawner.mobs.add("zombie");
		registerMobSpawner.mobs.add("slime");
		registerMobSpawner.mobs.add("ghast");
		registerMobSpawner.mobs.add("pigman");
		registerMobSpawner.mobs.add("enderman");
		registerMobSpawner.mobs.add("cavespider");
		registerMobSpawner.mobs.add("silverfish");
		registerMobSpawner.mobs.add("blaze");
		registerMobSpawner.mobs.add("magmaslime");
		registerMobSpawner.mobs.add("witch");
		registerMobSpawner.mobs.add("endermite");
		registerMobSpawner.mobs.add("guardian");
		registerMobSpawner.mobs.add("elder_guardian");
		registerMobSpawner.mobs.add("giant");
		registerMobSpawner.mobs.add("wither");
		registerMobSpawner.mobs.add("enderdragon");
		
		registerMobSpawner.mobs.add("snowgolem");
		registerMobSpawner.mobs.add("irongolem");
		
		registerMobSpawner.mobs.add("bat");
		registerMobSpawner.mobs.add("pig");
		registerMobSpawner.mobs.add("sheep");
		registerMobSpawner.mobs.add("cow");
		registerMobSpawner.mobs.add("chicken");
		registerMobSpawner.mobs.add("squid");
		registerMobSpawner.mobs.add("wolf");
		registerMobSpawner.mobs.add("mushroom_cow");
		registerMobSpawner.mobs.add("ocelot");
		registerMobSpawner.mobs.add("horse");
		registerMobSpawner.mobs.add("rabbit");
		registerMobSpawner.mobs.add("villager");	
	}
	
}
