package me.aaronakhtar.jbot;

import me.aaronakhtar.jbot.objects.Bot;
import me.aaronakhtar.jbot.objects.ConnectionType;
import me.aaronakhtar.jbot.threads.handlers.BotHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Utilities {

    public static boolean isSocketAlive(Socket socket){
        try{
            socket.getOutputStream().write(0);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void closeSocket(Socket socket){
        try {
            socket.close();
        }catch (Exception e){}
    }

    public static void setTerminalName(String n, BufferedWriter writer) throws IOException {
        writer.write("\033]0;"+n+"\007");
        writer.flush();
    }

    public static void sendInternalMessage(String s){
        System.out.println(Main.getPrefix() + s);
    }

    public static void sendJoinMessage(ConnectionType type, Bot bot, Socket client){
        switch (type){
            case BOT:{

                System.out.println(Colour.BRIGHT_RED.get() + "[Bot] " + Colour.RESET.get() +
                        "{"+bot.getSocket().getInetAddress().getHostAddress()+" | "+bot.getOperatingSystem()+" | "+bot.getArchitecture()+" | "+bot.getTimezone()+"}");
                return;
            }

            case CLIENT:{
                System.out.println(Colour.YELLOW.get() + "[Client] " + Colour.RESET.get() + "Client Connection @" + client.getInetAddress().getHostAddress() + "...");
                return;
            }
        }
    }

    public static void handleException(Exception e){

        e.printStackTrace();
    }

    public enum Colour {
        RESET("\u001b[0m"),
        BRIGHT_BLACK("\u001b[30;1m"),
        BRIGHT_RED("\u001b[31;1m"),
        BRIGHT_YELLOW("\u001b[33;1m"),
        BRIGHT_BLUE("\u001b[34;1m"),
        BRIGHT_MAGENTA("\u001b[35;1m"),
        BRIGHT_CYAN("\u001b[36;1m"),
        BRIGHT_WHITE("\u001b[37;1m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        MAGENTA("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m"),
        CLEAR("\033[H\033[2J"),
        NEW_LINE("\r\n");

        private String code;
        Colour(String code){
            this.code = code;
        }

        public String get(){
            return code;
        }

    }


}
