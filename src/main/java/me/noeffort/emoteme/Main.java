package me.noeffort.emoteme;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.noeffort.emoteme.command.BoopCommand;
import me.noeffort.emoteme.command.FacepalmCommand;
import me.noeffort.emoteme.command.HandshakeCommand;
import me.noeffort.emoteme.command.HugAllCommand;
import me.noeffort.emoteme.command.HugCommand;
import me.noeffort.emoteme.command.PunchCommand;
import me.noeffort.emoteme.command.SlapCommand;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public static HashMap<String, String> messageData = new HashMap<String, String>();
	
	@Override
	public void onEnable() {
		getLogger().info("Plugin Enabled! " + getVersion());
		registerCommands();
		
		File file = new File(getDataFolder() + File.separator + "messages.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		registerMessages();
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		for(String message : config.getConfigurationSection("").getKeys(false)) {
			messageData.put(message, config.getString(message));
		}
	}
	
	public void registerMessages() {
		setMessage("unused", "true");
	}
	
	private void setMessage(String name, String message) {
		File file = new File(getDataFolder() + File.separator + "messages.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!config.isSet(name)) {
			config.set(name, message);
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void load(FileConfiguration config) {
		File file = new File(getDataFolder() + File.separator + "messages.yml");
		config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerCommands() {
		this.getCommand("hug").setExecutor(new HugCommand(this));
		this.getCommand("slap").setExecutor(new SlapCommand(this));
		this.getCommand("punch").setExecutor(new PunchCommand(this));
		this.getCommand("facepalm").setExecutor(new FacepalmCommand(this));
		this.getCommand("hugall").setExecutor(new HugAllCommand(this));
		this.getCommand("handshake").setExecutor(new HandshakeCommand(this));
		this.getCommand("boop").setExecutor(new BoopCommand(this));
	}
	
	public String getVersion() {
		PluginDescriptionFile pdf = this.getDescription();
		return pdf.getVersion();
	}
	
	public static Main get() {
		return instance;
	}

}
