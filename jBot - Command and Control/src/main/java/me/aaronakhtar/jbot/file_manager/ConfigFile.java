package me.aaronakhtar.jbot.file_manager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConfigFile {

    static String getConfigDirectory(){
        final File file = new File("./jConfig");
        if (!file.isDirectory()){
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    static Map<String, Object> getProperties(ConfigFile configFile){
        final File file = configFile.getFile();
        final Map<String, Object> properties = new HashMap<>();
        try{
            final List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
            for (String l : lines){
                if (!l.isEmpty()) {
                    if (l.charAt(0) == '#') continue; // allow comments in config files
                    if (!properties.containsKey(l.split("=")[0])) {
                        properties.put(l.split("=")[0], l.split("=")[1]);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }

    String getFileName();
    File getFile();

}
