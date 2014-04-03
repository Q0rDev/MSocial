package ca.q0r.msocial.yml.config;

import ca.q0r.mchat.yml.Yml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConfigYml extends Yml {
    private ArrayList<String> sayAliases;
    private ArrayList<String> shoutAliases;
    private ArrayList<String> muteAliases;
    private ArrayList<String> pmAliases;
    private ArrayList<String> replyAliases;
    private ArrayList<String> inviteAliases;
    private ArrayList<String> acceptAliases;
    private ArrayList<String> denyAliases;
    private ArrayList<String> leaveAliases;

    private HashMap<String, List<String>> aliasMap = new HashMap<>();

    public ConfigYml() {
        super(new File("plugins/MSocial/config.yml"), "MSocial Config");

        sayAliases = new ArrayList<>();
        shoutAliases = new ArrayList<>();
        muteAliases = new ArrayList<>();
        pmAliases = new ArrayList<>();
        replyAliases = new ArrayList<>();
        inviteAliases = new ArrayList<>();
        acceptAliases = new ArrayList<>();
        denyAliases = new ArrayList<>();
        leaveAliases = new ArrayList<>();

        loadDefaults();
    }

    public void loadDefaults() {
        loadAliases();

        checkOption("aliases.mchatsay", sayAliases);
        checkOption("aliases.pmchat", pmAliases);
        checkOption("aliases.pmchatreply", replyAliases);
        checkOption("aliases.pmchatinvite", inviteAliases);
        checkOption("aliases.pmchataccept", acceptAliases);
        checkOption("aliases.pmchatdeny", denyAliases);
        checkOption("aliases.pmchatleave", leaveAliases);
        checkOption("aliases.mchatshout", shoutAliases);
        checkOption("aliases.mchatmute", muteAliases);

        setupAliasMap();

        save();
    }

    public HashMap<String, List<String>> getAliasMap() {
        return aliasMap;
    }

    private void loadAliases() {
        sayAliases.add("say");

        shoutAliases.add("shout");
        shoutAliases.add("yell");

        muteAliases.add("mute");
        muteAliases.add("quiet");

        pmAliases.add("pm");
        pmAliases.add("msg");
        pmAliases.add("message");
        pmAliases.add("m");
        pmAliases.add("tell");
        pmAliases.add("t");

        replyAliases.add("reply");
        replyAliases.add("r");

        inviteAliases.add("invite");

        acceptAliases.add("accept");

        denyAliases.add("deny");

        leaveAliases.add("leave");
    }

    private void setupAliasMap() {
        Set<String> keys = config.getConfigurationSection("aliases").getKeys(false);

        for (String key : keys) {
            aliasMap.put(key, config.getStringList("aliases." + key));
        }
    }
}