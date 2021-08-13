package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.objects.Bot;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
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
                    if (!Utilities.isSocketAlive(new BufferedWriter(new OutputStreamWriter(bot.getSocket().getOutputStream())))){
                        offline.add(bot);
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
