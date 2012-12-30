package ca.q0r.msocial;

import ca.q0r.msocial.commands.*;
import ca.q0r.msocial.configs.ConfigUtil;
import ca.q0r.msocial.configs.LocaleUtil;
import ca.q0r.msocial.events.ChatListener;
import ca.q0r.msocial.events.CommandListener;
import ca.q0r.msocial.types.ConfigType;
import com.miraclem4n.mchat.metrics.Metrics;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.TimerUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class MSocial extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    // Metrics
    public Metrics metrics;

    // Spout
    public Boolean spoutB = false;

    // Maps
    public HashMap<String, Boolean> isMuted = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isConv = new HashMap<String, Boolean>();

    public HashMap<String, String> lastPMd = new HashMap<String, String>();
    public HashMap<String, String> getInvite = new HashMap<String, String>();
    public HashMap<String, String> chatPartner = new HashMap<String, String>();

    public void onEnable() {
        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        try {
            // Initialize and Start the Timer
            TimerUtil timer = new TimerUtil();

            initializeConfigs();

            setupPlugins();

            setupCommands();

            setupEvents();

            // Stop the Timer
            timer.stop();

            // Calculate Startup Timer
            long diff = timer.difference();

            MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + diff + "ms]");
        } catch(NoClassDefFoundError ignored) {
            pm.disablePlugin(this);
        }
    }

    public void onDisable() {
        try {
            // Initialize and Start the Timer
            TimerUtil timer = new TimerUtil();

            getServer().getScheduler().cancelTasks(this);

            unloadConfigs();

            // Stop the Timer
            timer.stop();

            // Calculate Shutdown Timer
            long diff = timer.difference();

            MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
        } catch(NoClassDefFoundError ignored) {
            System.err.println("[" + pdfFile.getName() + "] MChat not found disabling!");
            System.out.println("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
        }
    }

    Boolean setupPlugin(String pluginName) {
        Plugin plugin = pm.getPlugin(pluginName);

        if (plugin != null) {
            MessageUtil.log("[" + pdfFile.getName() + "] <Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }

    void setupPlugins() {
        spoutB = setupPlugin("Spout");

        if (!ConfigType.OPTION_SPOUT.getBoolean())
            spoutB = false;
    }

    void setupCommands() {
        regCommands("pmchat", new PMCommand(this));
        regCommands("pmchataccept", new AcceptCommand(this));
        regCommands("pmchatdeny", new DenyCommand(this));
        regCommands("pmchatinvite", new InviteCommand(this));
        regCommands("pmchatleave", new LeaveCommand(this));
        regCommands("pmchatreply", new ReplyCommand(this));
        regCommands("mchatmute", new MuteCommand(this));
        regCommands("mchatsay", new SayCommand(this));
        regCommands("mchatshout", new ShoutCommand(this));
    }

    void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null)
            getCommand(command).setExecutor(executor);
    }

    void setupEvents() {
        pm.registerEvents(new ChatListener(this), this);
        pm.registerEvents(new CommandListener(), this);
    }

    public void initializeConfigs() {
        ConfigUtil.initialize();
        LocaleUtil.initialize();
    }

    public void reloadConfigs() {
        ConfigUtil.initialize();
        LocaleUtil.initialize();
    }

    public void unloadConfigs() {
        ConfigUtil.dispose();
        LocaleUtil.dispose();
    }
}
