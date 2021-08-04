package me.aaronakhtar.jbot.threads.handlers;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.crypto.Aes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class BotHandler implements Runnable {

    private static final String authPassword = Main.defaultConfig.get("bot-password").toString();

    private boolean isAuthenticated = false;
    private Socket socket;

    public BotHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        String input;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            if ((input = bufferedReader.readLine()) != null){
                if (Aes.decrypt(input, Main.encryptionKey).equals(authPassword)){
                    isAuthenticated = true;
                    Main.connectedBots.add(this.socket);
                    return;
                }
            }

        }catch (Exception e){
            Utilities.handleException(e);
        }finally {
            if (!isAuthenticated && Utilities.isSocketAlive(this.socket)) {
                Utilities.closeSocket(this.socket);
            }
        }

    }

    public Socket getSocket() {
        return this.socket;
    }
}
