package me.aaronakhtar.jbot.threads.handlers;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.crypto.Aes;
import me.aaronakhtar.jbot.objects.Bot;
import me.aaronakhtar.jbot.objects.ConnectionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
                if (Aes.decrypt(input.trim(), Main.encryptionKey).equals(authPassword)){

                    if ((input = bufferedReader.readLine()) != null){
                        if ((input = Aes.decrypt(input.trim(), Main.encryptionKey)) != null){
                                                        // os#arch#timezone
                            final String[] is = input.split("#");
                            final Bot bot = new Bot(socket, is[0], is[1], is[2]);
                            isAuthenticated = true;
                            Main.connectedBots.add(bot);
                            Utilities.sendJoinMessage(ConnectionType.BOT, bot, null);
                        }
                    }

                    return;
                }
            }

        }catch (Exception e){
            Utilities.handleException(e);
        }finally {
            try {
                if (!isAuthenticated && Utilities.isSocketAlive(bufferedReader)) {
                    Utilities.closeSocket(this.socket);
                }
            }catch (Exception e){
                Utilities.closeSocket(this.socket);
            }
        }

    }

    public Socket getSocket() {
        return this.socket;
    }
}
