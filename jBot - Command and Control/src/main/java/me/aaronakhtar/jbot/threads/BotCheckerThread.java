package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.objects.Bot;

import java.net.Socket;
import java.util.List;

public class BotCheckerThread extends Thread {

    @Override
    public void run() {
        Utilities.sendInternalMessage("Started Bot Checker Successfully.");
        while(true){
            while(Main.connectedBots.isEmpty());    // wait until bot joins if there are no connected bots.

            try {

                final List<Bot> bots = Main.connectedBots;
                for (Bot bot : bots){
                    if (!Utilities.isSocketAlive(bot.getSocket())) Main.connectedBots.remove(bot);
                }

                Thread.sleep(10000);
                        // check every 10 seconds
            }catch (Exception e){
                Utilities.handleException(e);
            }

        }
    }
}
