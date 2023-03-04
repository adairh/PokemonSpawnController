package me.hyhon.leggyspawncontroller.leggyspawncontroller.events;

import com.pixelmonmod.pixelmon.api.events.spawning.LegendarySpawnEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import jdk.jfr.internal.tool.Main;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.LeggySpawnController;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.MainConfig;
import me.hyhon.leggyspawncontroller.leggyspawncontroller.Manager.StorageManager;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.type.Exclude;
import org.spongepowered.api.event.filter.type.Include;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Spawning {

    @SubscribeEvent
    public void onLegendarySpawn(LegendarySpawnEvent.DoSpawn event) {
        StorageManager sm = LeggySpawnController.storageManager;
        MainConfig mc = LeggySpawnController.mainConfig;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sdate = sdf.format(new Date());
        if (sm.getMap() != null) {
            if (sm.getMap().size() > 0) {
                if (!sm.getMap().containsKey(event.getLegendary().name)) {
                    sm.commitAndPush(event.getLegendary().name, sdate);
                    //FMLLog.log(Level.WARN, "aaaaaaaaaaaaaaaaaa");
                    return;
                } else {
                    try {
                        Date date = sdf.parse(sm.getMap().get(event.getLegendary().name));
                        if (mc.getConfigMap() != null) {
                            if (mc.getConfigMap().size() > 0) {
                                if (mc.getConfigMap().containsKey(event.getLegendary().name)) {
                                    long sec = mc.getConfigMap().get(event.getLegendary().name);
                                    if (new Date().getTime() - date.getTime() >= sec * 1000) {
                                        //FMLLog.log(Level.WARN, "bbbbbbbbbbbbbbbbbb");
                                        sm.getMap().remove(event.getLegendary().name);
                                        sm.pushData();
                                        sm.commitAndPush(event.getLegendary().name, sdate);
                                        return;
                                    }
                                } else {
                                    //FMLLog.log(Level.WARN, "ccccccccccccccccccccc");
                                    sm.commitAndPush(event.getLegendary().name, sdate);
                                    return;
                                }
                            } else {
                                //FMLLog.log(Level.WARN, "dddddddddddddddddd");
                                sm.commitAndPush(event.getLegendary().name, sdate);
                                return;
                            }
                        } else {
                            //FMLLog.log(Level.WARN, "eeeeeeeeeeeeeeeeee");
                            sm.commitAndPush(event.getLegendary().name, sdate);
                            return;
                        }
                    } catch (ParseException e) {
                        //FMLLog.log(Level.WARN, "ffffffffffffffffff");
                        sm.commitAndPush(event.getLegendary().name, sdate);
                        return;
                    }
                }
            } else {
                //FMLLog.log(Level.WARN, "gggggggggggggggggg");
                sm.commitAndPush(event.getLegendary().name, sdate);
                return;
            }
        } else {
            //FMLLog.log(Level.WARN, "hhhhhhhhhhhhhhhhh");
            sm.commitAndPush(event.getLegendary().name, sdate);
            return;
        }
        //FMLLog.log(Level.WARN, "iiiiiiiiiiiiiiiiiiiiii");
        event.setCanceled(true);

    }
}
