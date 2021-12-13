package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;

import java.io.BufferedWriter;

public class DynamicTitleThread extends Thread {

    @Override
    public void run() {
        while(true){
            try {
                for (BufferedWriter writer : Main.connectedClients.values()){
                    Utilities.setTerminalName(Main.name + " Network | ["+Main.connectedBots.size()+"]  ["+Main.connectedClients.size()+"]", writer);
                }
                Thread.sleep(25);
            }catch (Exception e){
                Utilities.handleException(e);
            }
        }
    }
}
