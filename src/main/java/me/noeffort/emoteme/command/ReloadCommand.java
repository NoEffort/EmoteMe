package me.noeffort.emoteme.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.noeffort.emoteme.Main;
import me.noeffort.emoteme.Messages;
import me.noeffort.emoteme.util.MessageUtil;

public class ReloadCommand implements CommandExecutor {

	private final Main plugin;
	
	public ReloadCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	public void reload() {
		this.plugin.reloadConfig();
		Main.get().load(this.plugin.getConfig());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(sender.isOp()) {
				player.sendMessage(MessageUtil.translate(Messages.reload));
				reload();
			}
		} else {
			
		}
		return true;
	}
	
}
