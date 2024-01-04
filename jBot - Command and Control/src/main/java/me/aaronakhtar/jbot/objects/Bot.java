package me.vecnathewhisperd.jbot.objects;

import java.net.Socket;

public class Bot {

    private Socket socket;
    private String operatingSystem;
    private String architecture;
    private String timezone;

    public Bot(Socket socket, String operatingSystem, String architecture, String timezone) {
        this.socket = socket;
        this.operatingSystem = operatingSystem;
        this.architecture = architecture;
        this.timezone = timezone;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getArchitecture() {
        return architecture;
    }

    public String getTimezone() {
        return timezone;
    }
}
