package me.aaronakhtar.jbot.file_manager.config_files;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.file_manager.ConfigFile;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DefaultConfigurationFile implements ConfigFile {

    @Override
    public String getFileName() {
        return "jConfig.txt";
    }

    @Override
    public File getFile() {
        final File file = new File(ConfigFile.getConfigDirectory() + "/" + this.getFileName());
        try {
            if (!file.exists()) {
                file.createNewFile();

                try(PrintWriter writer = new PrintWriter(new FileWriter(file), true)){
                    writer.println(
                        "# this is the default " + Main.name + " configuration.\n\n" +
                                "encryption-key=jBotEncryptionKey\n" +
                                "bot-password=A4r0nAkht4r");
                }

                Utilities.sendInternalMessage("Created ["+getFileName()+"] configuration file.");
            }
        }catch (Exception e){
            Utilities.handleException(e);
        }
        return file;
    }
}
