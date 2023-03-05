package me.hyhon.leggyspawncontroller.leggyspawncontroller.commands;

import me.hyhon.leggyspawncontroller.leggyspawncontroller.LeggySpawnController;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.config.MainConfig;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.serializers.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReloadCmd extends CommandBase {
    @Override
    public String getName() {
        return "leggyspawncontroller";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "&d/texturetoken <reload/give> <player> <token>";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        MainConfig config = LeggySpawnController.mainConfig;
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
