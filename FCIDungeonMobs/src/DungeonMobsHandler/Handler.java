package DungeonMobsHandler;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import de.teamfci.dungeonmobs.DungeonMobs;
import de.teamfci.fileManager.FileManager;

public class Handler {
	static HashMap<String, BukkitRunnable> handle = new HashMap<String, BukkitRunnable>();
	public static DungeonMobs pl;
	public Handler(DungeonMobs pl) {
		this.pl = pl;
	}
	public static void start() {
		handle.put("handle", new BukkitRunnable() {

			@Override
			public void run() {
				Location loc = FileManager.getSpawnLocation("zombie", "hhb");
				loc.add(0,2,0);
				FileManager.spawnDungeonMob("zombie", "hhb", loc);
			}
			
		});
		handle.get("handle").runTaskTimer(pl, 10, 10);
	}
	
	public static void stop() {
		handle.get("handle").cancel();
		handle.clear();
	}
	
}
