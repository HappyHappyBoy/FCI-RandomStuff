package de.teamfci.registerSpawn;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import DungeonMobsHandler.Handler;
import de.teamfci.fileManager.FileManager;

public class registerMobSpawner implements Listener {
	public static ArrayList<String> mobs = new ArrayList<String>();
	@EventHandler
	public void onSignChanged(SignChangeEvent e) {
		Player p = (Player) e.getPlayer();
		
		if(e.getBlock().getState() instanceof Sign) {
			String l0 = e.getLine(0);
			String l1 = e.getLine(1);
			String l2 = e.getLine(2);
			String l3 = e.getLine(3);
			
			if(l0.equals("DungeonMobs")) {
				String type = l1.toLowerCase();
				if(mobs.contains(type)) {
					p.sendMessage("§a§l✔ §2MobType §e"+type);
					String[] array = l2.split(":");
					String NameID = array[0];
					boolean b = FileManager.getExistingNameID(NameID);
					if(b == false) {
						p.sendMessage("§c§l✖ §cDiese NameID es nicht vorhanden");
					} else {
						int radius = 0;
						int amount = 0;
						int health = 0;
						int spawnDelay = 0;
						String name = FileManager.getTranslatedNameID(NameID);
						p.sendMessage("§a§l✔ §2NameID §e"+name);
						try {
							radius = Integer.valueOf(array[1]);
							p.sendMessage("§a§l✔ §2Radius §e"+radius+" Blöcke");
							array = l3.split(":");
						} catch(Exception ex) {
							p.sendMessage("§a§l✖ §cDer Radius darf nur Ziffern enthalten!");
						}
						try {
							amount = Integer.valueOf(array[2]);
							p.sendMessage("§a§l✔ §2Anzahl §e"+amount);
						} catch(Exception ex) {
							p.sendMessage("§a§l✖ §cDie Anzahl darf nur Ziffern enthalten!");
						}
						array = l3.split(":");
						try {
							health = Integer.valueOf(array[0]);
							p.sendMessage("§a§l✔ §2Health §e"+health+"❤");
						} catch(Exception ex) {
							p.sendMessage("§a§l✖ §cDie Healthpoints dürfen nur Ziffern enthalten!");
						}
						try {
							spawnDelay = Integer.valueOf(array[1]);
							p.sendMessage("§a§l✔ §2Spawn-Delay §e"+spawnDelay+" Sekunden");
						} catch(Exception ex) {
							p.sendMessage("§a§l✖ §cDer Spawn-Delay därf nur Ziffern enthalten!");
						}
						Location loc = e.getBlock().getLocation();
						p.sendMessage("§a§l✔ §2Location §e"+(int)loc.getX()+" "+(int)loc.getY()+" "+(int)loc.getZ()+" "+loc.getWorld().getName());
						FileManager.addNewDungeonMob(type, NameID, radius, health, spawnDelay, amount, loc);
						Handler.start();
					}
				} else {
					p.sendMessage("§c§l✖ §cFehler: §7Der Mob-Typ wird nicht unterstüzt!");
					p.sendMessage("§7Mobs:");
					String m = mobs.toString();
					m = m.replace("[", "").replace("]", "");
					p.sendMessage(m);
				}
			} else {
				return;
			}
		}
	}
}
