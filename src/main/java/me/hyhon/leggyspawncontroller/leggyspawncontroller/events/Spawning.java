package me.hyhon.leggyspawncontroller.leggyspawncontroller.events;

import com.pixelmonmod.pixelmon.api.events.spawning.LegendarySpawnEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.spawning.SpawnAction;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnActionPokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.LeggySpawnController;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.config.MainConfig;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.Manager.StorageManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Spawning {

    @SubscribeEvent
    public void onLegendarySpawn(SpawnEvent event) {
        StorageManager sm = LeggySpawnController.storageManager;
        MainConfig mc = LeggySpawnController.mainConfig;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sdate = sdf.format(new Date());

        SpawnAction<? extends Entity> action = event.action;

        if (!(action instanceof SpawnActionPokemon)) {
            return;
        }

        EntityPixelmon pixelmon = ((SpawnActionPokemon)action).getOrCreateEntity();

        /*if (sm.getMap() == null) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }
        if (sm.getMap().size() <= 0) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }
        if (!sm.getMap().containsKey(pixelmon.getName())) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }
        if (mc.getConfigMap() == null) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }

        if (mc.getConfigMap().size() <= 0) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }
        if (!mc.getConfigMap().containsKey(pixelmon.getName())) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }*/
        try {
            Date date = sdf.parse(sm.getMap().get(pixelmon.getName()));
            long sec = mc.getConfigMap().get(pixelmon.getName());
            if (new Date().getTime() - date.getTime() >= sec * 1000) {
                sm.getMap().remove(pixelmon.getName());
                sm.pushData();
                sm.commitAndPush(pixelmon.getName(), sdate);
                return;
            }
        } catch (Exception e) {
            sm.commitAndPush(pixelmon.getName(), sdate);
            return;
        }



        event.setCanceled(true);

    }
}
