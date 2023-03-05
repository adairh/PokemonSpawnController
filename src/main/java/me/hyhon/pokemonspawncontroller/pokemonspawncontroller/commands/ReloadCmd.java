package me.hyhon.pokemonspawncontroller.pokemonspawncontroller.commands;

import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.PokemonSpawnController;
import me.hyhon.pokemonspawncontroller.pokemonspawncontroller.config.MainConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCmd extends CommandBase {
    @Override
    public String getName() {
        return "pokemonspawncontroller";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "&d/texturetoken <reload/give> <player> <token>";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        MainConfig config = PokemonSpawnController.mainConfig;
        config.reloadConfig();
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                config.reloadConfig();
                sender.sendMessage(config.getReloadMessage());
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        if (sender.canUseCommand(0, "leggyspawncontroller.command.reload") && args.length == 1) {
            possibleArgs.add("reload");
            return getListOfStringsMatchingLastWord(args, possibleArgs);
        }
        return Collections.singletonList("");
    }
}
