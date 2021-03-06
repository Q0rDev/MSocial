package ca.q0r.msocial;

import ca.q0r.mchat.metrics.Metrics;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.util.Timer;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.commands.*;
import ca.q0r.msocial.events.ChatListener;
import ca.q0r.msocial.events.CommandListener;
import ca.q0r.msocial.vars.MSocialVars;
import ca.q0r.msocial.yml.YmlManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MSocial extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    public void onEnable() {
        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        try {
            // Initialize and Start the Timer
            Timer timer = new Timer();

            // Initialize Metrics
            getServer().getScheduler().runTaskLater(this, new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Metrics metrics = new Metrics(Bukkit.getPluginManager().getPlugin("MSocial"));
                        metrics.start();
                    } catch (Exception ignored) {
                    }
                }
            }, 200);

            // Load Yml
            YmlManager.initialize();

            // Load SocialApi
            SocialApi.initialize();

            setupCommands();

            setupEvents();

            setupVars();

            // Stop the Timer
            timer.stop();

            // Calculate Startup Timer
            long diff = timer.difference();

            MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + diff + "ms]");
        } catch (NoClassDefFoundError ignored) {
            pm.disablePlugin(this);
        }
    }

    public void onDisable() {
        try {
            // Initialize and Start the Timer
            Timer timer = new Timer();

            getServer().getScheduler().cancelTasks(this);

            // Unload Yml
            YmlManager.unload();

            // Stop the Timer
            timer.stop();

            // Calculate Shutdown Timer
            long diff = timer.difference();

            MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
        } catch (NoClassDefFoundError ignored) {
            System.err.println("[" + pdfFile.getName() + "] MChat not found disabling!");
            System.out.println("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
        }
    }

    void setupCommands() {
        regCommands("msocial", new MSocialCommand());
        regCommands("pmchat", new PMCommand());
        regCommands("pmchataccept", new ResponseCommand("pmchataccept", true));
        regCommands("pmchatdeny", new ResponseCommand("pmchatdeny", false));
        regCommands("pmchatinvite", new InviteCommand());
        regCommands("pmchatleave", new LeaveCommand());
        regCommands("pmchatreply", new ReplyCommand());
        regCommands("mchatmute", new MuteCommand());
        regCommands("mchatsay", new SayCommand());
        regCommands("mchatshout", new ShoutCommand());
    }

    void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor(executor);
        }
    }

    void setupEvents() {
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new CommandListener(), this);
    }

    void setupVars() {
        MSocialVars.addVars();
    }
}