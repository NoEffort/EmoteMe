package me.noeffort.emoteme.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.noeffort.emoteme.Main;
import me.noeffort.emoteme.Messages;
import me.noeffort.emoteme.util.MessageUtil;

public class SlapCommand implements CommandExecutor {
	
	Main plugin;
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	public SlapCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Player target = player.getServer().getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(MessageUtil.translate(Messages.target));
				return true;
			} else {
				if(sender.hasPermission("emoteme.slap")) {
					int cooldowntime = 60;
					if(cooldowns.containsKey(sender.getName())) {
						long secondsleft = ((cooldowns.get(sender.getName()) / 1000) + cooldowntime) - (System.currentTimeMillis() / 1000);
						if(sender.hasPermission("emoteme.bypass.cooldown")) {
							secondsleft = 0;
							cooldowns.put(sender.getName(), System.currentTimeMillis());
						}
						if(secondsleft > 0) {
							sender.sendMessage(MessageUtil.translate(Messages.cooldown + secondsleft + " &cseconds."));
							return true;
						}
					}
					cooldowns.put(sender.getName(), System.currentTimeMillis());
					Bukkit.broadcastMessage(MessageUtil.translate("&e" + sender.getName() + " &bhas &cslapped&e " + target.getName() + "&b!"));
					return true;
				} else {
					sender.sendMessage(MessageUtil.translate(Messages.permissions));
					return true;
				}
			}
		} else {
			sender.sendMessage(MessageUtil.translate(Messages.player));
			return true;
		}
	}
}

