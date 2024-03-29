package me.aaronakhtar.jbot.threads.servers;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.threads.handlers.BotHandler;
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientServerThread extends Thread {

    private int port;

    public ClientServerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket server = new ServerSocket(this.port)){
            Utilities.sendInternalMessage("Started Client Server Successfully.");
            while(Main.isRunning){
                try{
                    final Socket client = server.accept();

                    final Thread handlerThread = new Thread(new ClientHandler(client));
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
