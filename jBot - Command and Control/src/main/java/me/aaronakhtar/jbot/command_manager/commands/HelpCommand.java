package me.aaronakhtar.jbot.command_manager.commands;

import me.aaronakhtar.jbot.Main;
import me.aaronakhtar.jbot.Utilities;
import me.aaronakhtar.jbot.command_manager.JBotCommand;
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class HelpCommand implements JBotCommand {

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "list all commands";
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }

    @Override
    public void doAction(String[] args, ClientHandler client, BufferedWriter out, BufferedReader in) throws Exception {
        out.write("\r\n" + Utilities.Colour.WHITE_BG.get() + Utilities.Colour.BLACK.get() + Main.name + " Commands"+ Utilities.Colour.RESET.get() +" ->"+"\r\n \r\n");
        for (Class<? extends JBotCommand> commandClass : Main.commands) {
            final JBotCommand command = commandClass.newInstance();
            out.write(Utilities.Colour.YELLOW.get() + command.getCommand() + " - " + command.getDescription());
        }
        out.write("\r\n");
        out.flush();

    }
}
