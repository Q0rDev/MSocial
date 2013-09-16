package ca.q0r.msocial.configs;

import ca.q0r.msocial.types.LocaleType;
import com.miraclem4n.mchat.api.API;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocaleUtil {
    private static YamlConfiguration config;
    private static File file;
    private static Boolean changed;

    public static void initialize() {
        load();
    }

    public static void dispose() {
        config = null;
        file = null;
        changed = null;
    }

    public static void load() {
        file = new File("plugins/MSocial/locale.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("MSocial Locale");

        changed = false;

        loadDefaults();

        API.setShoutFormat(LocaleType.FORMAT_SHOUT.getRaw());
    }

    private static void loadDefaults() {
        checkOption("format.pm.received", "%sender &1-&2-&3-&4> &fMe: %msg");
        checkOption("format.pm.sent", "&fMe &1-&2-&3-&4> &4%recipient&f: %msg");
        checkOption("format.say", "&6[Server]&e %msg");
        checkOption("format.shout", "[Shout]");

        checkOption("message.config.reloaded", "%config Reloaded.");
        checkOption("message.config.updated", "%config has been updated.");

        checkOption("message.convo.accepted", "Convo request with &5'&4%player&5'&4 has been accepted.");
        checkOption("message.convo.convo", "&4[Convo] ");
        checkOption("message.convo.denied", "You have denied a Convo request from &5'&4%player&5'&4.");
        checkOption("message.convo.ended", "Conversation with '%player' has ended.");
        checkOption("message.convo.hasRequest", "&5'&4%player&5'&4 Already has a Convo request.");
        checkOption("message.convo.inviteSent", "You have invited &5'&4%player&5'&4 to have a Convo.");
        checkOption("message.convo.invited", "You have been invited to a Convo by &5'&4%player&5'&4 use /pmchataccept to accept.");
        checkOption("message.convo.left", "You have left the Conversation with '%player'.");
        checkOption("message.convo.noPending", "No pending Convo request.");
        checkOption("message.convo.notIn", "You are not currently in a Convo.");
        checkOption("message.convo.notStarted", "Convo request with &5'&4%player&5'&4 has been denied.");
        checkOption("message.convo.started", "You have started a Convo with &5'&4%player&5'&4.");
        checkOption("message.shout.noInput", "You can't shout nothing!");
        checkOption("message.general.mute", "Target '%player' successfully %muted. To %mute use this command again.");
        checkOption("message.spout.pmFrom", "[PMChat] From:");
        checkOption("message.pm.noPm", "No one has yet PM'd you.");

        save();
    }

    public static void set(String key, Object obj) {
        config.set(key, obj);

        changed = true;
    }

    public static Boolean save() {
        if (!changed)
            return false;

        try {
            config.save(file);
            changed = false;
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    private static void checkOption(String option, Object defValue) {
        if (!config.isSet(option))
            set(option, defValue);
    }
}
