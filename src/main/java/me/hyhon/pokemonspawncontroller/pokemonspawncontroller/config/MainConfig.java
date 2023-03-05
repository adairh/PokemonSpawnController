package me.hyhon.pokemonspawncontroller.pokemonspawncontroller.config;

import info.pixelmon.repack.ninja.leaping.configurate.ConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class MainConfig {

	private File configFile;
	private ConfigurationNode rootNode;
	private HoconConfigurationLoader loader;
	private HashMap<String, Long> configMap;

	public TextComponentString getReloadMessage() {
		return reloadMsg;
	}

	@Setting("reload-message")
	private TextComponentString reloadMsg = new TextComponentString("&e&lConfig reloaded.");

	public MainConfig(File configFile) {
		this.configMap = new HashMap<String, Long>();
		this.configFile = configFile;
		try {
			if (!configFile.exists())
			{
				configFile.createNewFile();
			}
			loadConfig();
		} catch (Exception e){}
	}


	private void loadConfig() throws IOException, IllegalAccessException {
		loader = HoconConfigurationLoader.builder().setFile(configFile).build();
		rootNode = loader.load();
		if (rootNode.getNode("delay").isVirtual()) writeSampleConfig();
		//if (rootNode.getNode("settings").isVirtual()) writeSettingsToConfig();
		if (configFile.exists() && configFile.length() > 0) {
			if (!rootNode.getNode("delay").isVirtual()) {
				if (rootNode.getNode("delay").hasMapChildren()) {
					Map<Object, ? extends ConfigurationNode> childrenMap = rootNode.getNode("delay").getChildrenMap();
					for (Map.Entry<Object, ? extends ConfigurationNode> entry : childrenMap.entrySet()) {
						if (entry.getKey() != null && entry.getValue() != null) {
							configMap.put((String) entry.getKey(), entry.getValue().getLong());
							//FMLLog.log(Level.WARN, entry.getKey() + " " + entry.getValue().getLong());
						}
					}
				}
			}
		}
		rootNode = loader.load();

	}

	public void reloadConfig() {
		try {
			rootNode = loader.load();
			saveConfig();
			configMap.clear();
			loadConfig();
		} catch (Exception e) {
		}
	}

	public void saveConfig() {
		try {
			loader.save(rootNode);
		} catch (IOException e)  {
		}
	}

	public HashMap<String, Long> getConfigMap() {
		return configMap;
	}


	private void writeSampleConfig() throws IOException {
		rootNode.getNode("delay").getNode("Rayquaza").setValue(100);
		rootNode.getNode("delay").getNode("Rattata").setValue(100);
		loader.save(rootNode);
	}

}
