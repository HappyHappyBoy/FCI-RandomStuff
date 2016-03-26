package de.teamfci.fileManager;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class FileManager {
	
	public static Location getSpawnLocation(String mobType, String nameid) {
		Location loc = null;
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//"+mobType+"//"+nameid+"//dungeonmob.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String w = cfg.getString("DungeonMobs.DungeonMob.Config.Location.World");
		double x = (double) cfg.getInt("DungeonMobs.DungeonMob.Config.Location.X");
		double y = (double) cfg.getInt("DungeonMobs.DungeonMob.Config.Location.Y");
		double z = (double) cfg.getInt("DungeonMobs.DungeonMob.Config.Location.Z");
		loc = new Location(Bukkit.getWorld(w), x, y, z);
//		Player p = Bukkit.getPlayer("HappyHappyBoy");
//		p.teleport(loc);
		return loc;
	}
	
	public static void addNewDungeonMob(String mobType, String nameid, int radius, int health, int spawnDelay, int amount, Location loc) {
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//"+mobType+"//"+nameid+"//dungeonmob.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("DungeonMobs.DungeonMob.Config.MobType", mobType);
		cfg.set("DungeonMobs.DungeonMob.Config.NameID", nameid);
		cfg.set("DungeonMobs.DungeonMob.Config.Spawn-Radius", radius);
		cfg.set("DungeonMobs.DungeonMob.Config.Spawn-Delay", spawnDelay);
		cfg.set("DungeonMobs.DungeonMob.Config.Spawn-Amount", amount);
		cfg.set("DungeonMobs.DungeonMob.Config.Health", health);
		cfg.set("DungeonMobs.DungeonMob.Config.Location.World", loc.getWorld().getName());
		cfg.set("DungeonMobs.DungeonMob.Config.Location.X", loc.getX());
		cfg.set("DungeonMobs.DungeonMob.Config.Location.Y", loc.getY());
		cfg.set("DungeonMobs.DungeonMob.Config.Location.Z", loc.getZ());
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.broadcastMessage("§7DungeonMobs   §f|  §eEin neuer DungeonMob wurde hinzugefügt!");
	}
	
	public static void addNameIDToLibrary(String NameID, String TranslatedNameID) {
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//NameID Library.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set(NameID, TranslatedNameID);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getExistingNameID(String NameID) {
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//NameID Library.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		boolean b = cfg.contains(NameID);
		return b;
	}
	
	public static String getTranslatedNameID(String NameID) {
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//NameID Library.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		String b = cfg.getString(NameID);
		return b;
	}
	
	public static void spawnDungeonMob(String mobType, String nameid, Location loc) {
		EntityType enttype = identifyEntityType(mobType);
		File file = new File("plugins//Fortress-Combat-System//Fortress-Combat-DungeonMobs//"+mobType+"//"+nameid+"//dungeonmob.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double health = cfg.getInt("DungeonMobs.DungeonMob.Config.Health");
		String name = getTranslatedNameID(nameid);
		int amount = cfg.getInt("DungeonMobs.DungeonMob.Config.Spawn-Amount");
		int radius = cfg.getInt("DungeonMobs.DungeonMob.Config.Spawn-Radius");
		for(int i = 0; i < amount; i++) {
			Location l = getRadialLocation(loc, radius);
			LivingEntity mob = (LivingEntity) l.getWorld().spawnEntity(l, enttype);
			mob.setMaxHealth(health);
			mob.setHealth(health);
			mob.setCustomName(name.replace("&",  "§"));
			mob.setCustomNameVisible(true);
		}
		Bukkit.broadcastMessage("§aSpawned!");
	}
	
	static Location getRadialLocation(Location loc, int radius) {
		Location l = null;
		
		ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
		Location al = a.getLocation();
		Random r = new Random();
		float yaw = al.getYaw();
		for(int i = 0; i < 360; i++) {
			yaw = yaw + 1;
			al.setYaw(yaw);
			a.teleport(al);
			Location li = al;
			li.setYaw(yaw);
		    BlockIterator blocksToAdd = new BlockIterator(li, 0, radius);
		    Location blockToAdd = null;
		    if(r.nextInt(6) == 1) {
			    while(blocksToAdd.hasNext()) {
			        blockToAdd = blocksToAdd.next().getLocation();
			        l = blockToAdd;
					int n = r.nextInt(radius);
					if(n == (int) radius / 2) {
						break;
					}
			    }
		    }
//			int n = r.nextInt(16);
//			if(n == 2) {
//				break;
//			}
		}
		a.remove();
		return l;
	}
	
	public static EntityType identifyEntityType(String type) {
		EntityType entType = null;
		if(type.equals("creeper")) {
			entType = EntityType.CREEPER;
		}
		if(type.equals("skeleton")) {
			entType = EntityType.SKELETON;		
		}
		if(type.equals("spider")) {
			entType = EntityType.SPIDER;
		}
		if(type.equals("zombie")) {
			entType = EntityType.ZOMBIE;
		}
		if(type.equals("slime")) {
			entType = EntityType.SLIME;
		}
		if(type.equals("ghast")) {
			entType = EntityType.GHAST;
		}
		if(type.equals("pigman")) {
			entType = EntityType.PIG_ZOMBIE;
		}
		if(type.equals("enderman")) {
			entType = EntityType.ENDERMAN;
		}
		if(type.equals("cavespider")) {
			entType = EntityType.CAVE_SPIDER;
		}
		if(type.equals("silverfish")) {
			entType = EntityType.SILVERFISH;
		}
		if(type.equals("blaze")) {
			entType = EntityType.ZOMBIE;
		}
		if(type.equals("magmaslime")) {
			entType = EntityType.BLAZE;
		}
		if(type.equals("witch")) {
			entType = EntityType.WITCH;
		}
		if(type.equals("endermite")) {
			entType = EntityType.ENDERMITE;
		}
		if(type.equals("guardian")) {
			entType = EntityType.GUARDIAN;
		}
		if(type.equals("elder_guardian")) {
			entType = EntityType.GUARDIAN;
		}
		if(type.equals("giant")) {
			entType = EntityType.GIANT;
		}
		if(type.equals("wither")) {
			entType = EntityType.WITHER;
		}
		if(type.equals("enderdragon")) {
			entType = EntityType.ENDER_DRAGON;
		}
		if(type.equals("snowgolem")) {
			entType = EntityType.SNOWMAN;
		}
		if(type.equals("irongolem")) {
			entType = EntityType.IRON_GOLEM;
		}
		if(type.equals("bat")) {
			entType = EntityType.BAT;
		}
		if(type.equals("pig")) {
			entType = EntityType.PIG;
		}
		if(type.equals("sheep")) {
			entType = EntityType.SHEEP;
		}
		if(type.equals("cow")) {
			entType = EntityType.COW;
		}
		if(type.equals("chicken")) {
			entType = EntityType.CHICKEN;
		}
		if(type.equals("squid")) {
			entType = EntityType.SQUID;
		}
		if(type.equals("wolf")) {
			entType = EntityType.WOLF;
		}
		if(type.equals("mushroom_cow")) {
			entType = EntityType.MUSHROOM_COW;
		}
		if(type.equals("ocelot")) {
			entType = EntityType.OCELOT;
		}
		if(type.equals("horse")) {
			entType = EntityType.HORSE;
		}
		if(type.equals("rabbit")) {
			entType = EntityType.RABBIT;
		}
		if(type.equals("villager")) {
			entType = EntityType.VILLAGER;
		}
		return entType;
	}
	
}
