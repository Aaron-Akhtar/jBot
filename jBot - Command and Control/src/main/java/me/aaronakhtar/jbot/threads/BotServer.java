package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.threads.handlers.BotHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class BotServer extends Thread {

    private int port;

    public BotServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket server = new ServerSocket(this.port)){
            Utilities.sendInternalMessage("Started Bot Server Successfully.");
            while(Main.isRunning){
                try{
                    final Socket bot = server.accept();

                    final Thread handlerThread = new Thread(new BotHandler(bot));
                    handlerThread.start();

                    Thread.sleep(20);
                }catch (Exception e){
                    Utilities.handleException(e);
                }
            }

        }catch (Exception e){
            Utilities.handleException(e);
        }
    }
}
