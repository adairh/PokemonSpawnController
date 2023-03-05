package me.hyhon.pokemonspawncontroller.pokemonspawncontroller;

import com.pixelmonmod.pixelmon.Pixelmon;
import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.Manager.StorageManager;
import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.commands.ReloadCmd;
import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.config.MainConfig;
import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.events.Spawning;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.nio.file.Path;

@Mod(
        modid = PokemonSpawnController.MOD_ID,
        name = PokemonSpawnController.MOD_NAME,
        version = PokemonSpawnController.VERSION,
        dependencies = "required-server-after:pixelmon",
        acceptableRemoteVersions = "*"
)
public class PokemonSpawnController {

    private static Path configDir;
    public static final String MOD_ID = "pokemonspawncontroller";
    public static final String MOD_NAME = "LeggySpawnController";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static PokemonSpawnController INSTANCE;

    public static StorageManager storageManager;
    public static MainConfig mainConfig;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        configDir = event.getModConfigurationDirectory().toPath().resolve("PokemonSpawnController");
        storageManager = new StorageManager(configDir);
        mainConfig = new MainConfig(new File(configDir.resolve("config.conf").toString()));

        Pixelmon.EVENT_BUS.register(new Spawning());
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ReloadCmd());
    }
}



