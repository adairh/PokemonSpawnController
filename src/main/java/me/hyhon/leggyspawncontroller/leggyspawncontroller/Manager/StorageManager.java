package me.hyhon.leggyspawncontroller.leggyspawncontroller.Manager;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class StorageManager {

    private Path configDir;
    private HashMap<String, String> map;
    private Gson gson;
    private File file;
    public StorageManager(Path dir) {
        this.configDir = dir;
        this.gson = new Gson();
        this.map = new HashMap<String, String>();
        try {
            Files.createDirectories(configDir);
            file = new File(configDir.resolve("spawnData.json").toString());
            if (file.exists() && file.length() > 0) pullData();
            else {
                file.createNewFile();
            }
        } catch (Exception e) {

        }
    }

    public void pullData() {
        String s = readFile(file);
        map = gson.fromJson(s, HashMap.class);
        //fileWriter.write(json);
    }

    public void commitData(String k, String v) {

        if (map != null && map.size() > 0 && map.containsKey(k)) delData(k);
        map.
                put(k, v);
    }

    public void delData(String k) {
        map.remove(k);
    }


    public void pushData() {
        String json = gson.toJson(map);
        writeFile(file, json);
    }

    public void commitAndPush(String k, String v) {
        commitData(k, v);
        pushData();
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void writeFile(File file, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, String> getMap() {
        return map;
    }
}
