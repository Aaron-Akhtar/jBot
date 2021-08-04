package me.aaronakhtar.jbot.threads;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer extends Thread {

    private int port;

    public ClientServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket server = new ServerSocket(this.port)){
            Utilities.sendInternalMessage("Started Client Server Successfully.");
            while(Main.isRunning){
                try{
                    final Socket client = server.accept();



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
