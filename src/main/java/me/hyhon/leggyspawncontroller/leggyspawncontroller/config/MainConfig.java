package me.hyhon.leggyspawncontroller.leggyspawncontroller.config;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import info.pixelmon.repack.ninja.leaping.configurate.ConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import io.leangen.geantyref.TypeToken;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers.BigDecimalSerializer;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers.TextComponentStringSerializer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
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
			loadConfig();
		} catch (Exception e){}
	}


	private void loadConfig() throws IOException {
		loader = HoconConfigurationLoader.builder().setFile(configFile).build();
		rootNode = loader.load();



		if (configFile.exists() && configFile.length() > 0) {
			//FMLLog.log(Level.WARN, "111111111111111111111111111111");


			if (rootNode.getNode("delay").isVirtual()) {
				rootNode.getNode("delay").getNode("Rayquaza").setValue(100);
				loader.save(rootNode);
			}
			else {
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
		} catch (IOException e) {
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
}
