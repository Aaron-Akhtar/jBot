package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;

public class ShutdownThread implements Runnable {

    @Override
    public void run() {
        Utilities.sendInternalMessage(Utilities.Colour.RED_BG.get() + Utilities.Colour.WHITE.get() + "Killing ["+Main.connectedBots.size()+"] bot connections...");
        Utilities.executeCommandToNetwork("kill");
        Utilities.sendInternalMessage(Utilities.Colour.RED_BG.get() + Utilities.Colour.WHITE.get() + "Sent kill command to ["+Main.connectedBots.size()+"] bots...");
    }
}
