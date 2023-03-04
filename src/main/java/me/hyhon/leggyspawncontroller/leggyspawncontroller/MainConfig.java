package me.hyhon.leggyspawncontroller.leggyspawncontroller;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@ConfigSerializable
public class MainConfig {

	private File configFile;
	private Configuration config;
	private HashMap<String, Long> configMap;

	public MainConfig(File configFile) {
		this.configMap = new HashMap<String, Long>();
		this.configFile = configFile;
		try {
			loadConfig();
		} catch (Exception e){}
	}

	public Property getProperty(String categoryName, String propertyName, int defaultValue, String comment) {
		Property prop = config.get(categoryName, propertyName, defaultValue, comment);
		if (config.hasChanged()) {
			config.save();
		}
		return prop;
	}

	private void loadConfig() {
		config = new Configuration(configFile);
		config.load();
		if (configFile.exists() && configFile.length() > 0) {
			//FMLLog.log(Level.WARN, "111111111111111111111111111111");

			if (config.getCategoryNames().size() <= 0) {

				Property myProperty = getProperty("delay", "Rayquaza", 100, "");
				myProperty.set(100);

				if (config.hasChanged()) {
					config.save();
				}
			} else {
				for (String categoryName : config.getCategoryNames()) {
					for (String propertyName : config.getCategory(categoryName).getValues().keySet()) {
						long propertyValue = config.get(categoryName, propertyName, 0).getInt();
						configMap.put(propertyName, propertyValue);
					}
				}
			}
		}
		config.save();

	}

	public Configuration getConfiguration() {
		return config;
	}

	public void reloadConfig() {
		config.load();
		configMap.clear();
		loadConfig();
	}

	public void saveConfig() {
		config.save();
	}

	public HashMap<String, Long> getConfigMap() {
		return configMap;
	}
}
