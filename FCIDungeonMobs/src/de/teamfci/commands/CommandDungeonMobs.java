package de.teamfci.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import DungeonMobsHandler.Handler;
import de.teamfci.fileManager.FileManager;

public class CommandDungeonMobs implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				if(p.hasPermission("fci.dungeonmobs.dungeonmobs")) {
					Handler.start();
					p.sendMessage("§a/dungeonmobs add nameid (NameID) (TranslatedNameID)");
				}
			}
			if(args.length == 4) {
				if(args[0].equalsIgnoreCase("add")) {
					if(args[1].equalsIgnoreCase("nameid")) {
						String nameid = args[2];
						String translation = args[3];
						FileManager.addNameIDToLibrary(nameid, translation);
						p.sendMessage("§aNameID: §e"+nameid+" §abedeutet übersetzt: §e"+translation);
					}
				}
				if(p.hasPermission("fci.dungeonmobs.dungeonmobs")) {
					
				}
			}
		}
		
		return false;
	}
	
}
