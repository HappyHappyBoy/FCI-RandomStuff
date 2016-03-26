package de.teamfci.spawnerData;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import de.teamfci.dungeonmobs.DungeonMobs;

public class SpawnManager {
	public static DungeonMobs pl;
	public SpawnManager(DungeonMobs pl) {
		this.pl = pl;
	}
	public static HashMap<String, BukkitRunnable> manager = new HashMap<String, BukkitRunnable>();
	
	public void start() {
		
		manager.put("task", new BukkitRunnable() {

			@Override
			public void run() {
				
			}
		
		});
		manager.get("task").runTaskTimer(pl, 20L, 20L);
		
	}
	
}
