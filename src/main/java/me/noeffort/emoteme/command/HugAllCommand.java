package me.noeffort.emoteme.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.noeffort.emoteme.Main;
import me.noeffort.emoteme.Messages;
import me.noeffort.emoteme.util.MessageUtil;

public class HugAllCommand implements CommandExecutor {
	
	Main plugin;
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	public HugAllCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("emoteme.hug") && player.hasPermission("emoteme.players.all")) {
				int cooldowntime = 60;
				if(cooldowns.containsKey(player.getName())) {
					long secondsleft = ((cooldowns.get(player.getName()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
					if(player.hasPermission("emoteme.bypass.cooldown")) {
						secondsleft = 0;
						cooldowns.put(player.getName(), System.currentTimeMillis());
					}
					if(secondsleft > 0) {
						player.sendMessage(MessageUtil.translate(Messages.cooldown + secondsleft + " &cseconds."));
						return true;
					}
				}
				cooldowns.put(sender.getName(), System.currentTimeMillis());
				Collection<Player> collection = new ArrayList<Player>();
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p != player) {
						collection.add(p);
					}
					if(collection.size() == 0) {
						player.sendMessage(MessageUtil.translate(Messages.prefix + "&cThere are no other online players!"));
					} else {
						Bukkit.broadcastMessage(MessageUtil.translate("&e" + player.getName() + " &bhas hugged&e " + p.getName() + " &b❤"));
					}
				}
				return true;
			} else {
				player.sendMessage(MessageUtil.translate(Messages.permissions));
				return true;
			}
		} else {
			sender.sendMessage(MessageUtil.translate(Messages.player));
			return true;
		}
	}
}
