package me.aaronakhtar.jbot.threads.handlers;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.file_manager.files.AccessKeysFile;
import me.aaronakhtar.jbot.objects.ConnectionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    public static void writeToClient(String s, BufferedWriter writer) throws Exception {
        writer.write(s + Utilities.Colour.RESET.get());
        writer.flush();
    }

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(    Socket socket = this.socket;
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
            String input;

            Utilities.setTerminalName("*", writer);
            writeToClient(Utilities.Colour.GREEN.get() + "Access-Key: ", writer);

            input = reader.readLine();

            if (!AccessKeysFile.getAccessKeys().contains(input)){
                writeToClient(Utilities.Colour.CLEAR.get() + Utilities.Colour.RED.get() + "Failed to Authenticate your Access-Key...", writer);
                Thread.sleep(3800);
                return;
            }

            writeToClient(Utilities.Colour.CLEAR.get() + Utilities.Colour.GREEN.get() + "Successfully Authenticated your Access-Key...", writer);
            Utilities.sendJoinMessage(ConnectionType.CLIENT, null, socket);
            Thread.sleep(3800);
            writeToClient(Utilities.Colour.RESET.get(), writer);





        }catch (Exception e){
            Utilities.handleException(e);
        }finally {
            if (Main.connectedClients.contains(this.socket)){
                Main.connectedClients.remove(this.socket);
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }
}
