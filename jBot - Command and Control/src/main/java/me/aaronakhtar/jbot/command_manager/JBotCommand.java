package me.aaronakhtar.jbot.command_manager;

import me.aaronakhtar.jbot.threads.handlers.ClientHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface JBotCommand {

    String getCommand();

    String getDescription();

    boolean isAdminCommand();

    void doAction(String[] args, ClientHandler client, BufferedWriter out, BufferedReader in) throws Exception;


}
