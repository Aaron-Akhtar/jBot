package me.aaronakhtar.jbot.file_manager.files;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.file_manager.ConfigFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccessKeysFile implements ConfigFile {

    public static List<String> getAccessKeys(){
        final List<String> accessKeys = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(new AccessKeysFile().getFile()))){
            String k;
            while((k = reader.readLine()) != null){
                if (!k.isEmpty() && k.charAt(0) != '#') accessKeys.add(k);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return accessKeys;
    }

    @Override
    public String getFileName() {
        return "jKeys.txt";
    }

    @Override
    public File getFile() {
        final File file = new File(ConfigFile.getConfigDirectory() + "/" + this.getFileName());
        try {
            if (!file.exists()) {
                file.createNewFile();

                try(PrintWriter writer = new PrintWriter(new FileWriter(file), true)){
                    writer.println("# The first key in this file will be the Administrator Key - Be sure to change it and rerun "+Main.name+".\n" +
                                   "defaultKey123");
                }

                Utilities.sendInternalMessage("Created ["+getFileName()+"] Access-Keys file.");
            }
        }catch (Exception e){
            Utilities.handleException(e);
        }
        return file;
    }
}
