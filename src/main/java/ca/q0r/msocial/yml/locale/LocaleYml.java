package ca.q0r.msocial.yml.locale;

import ca.q0r.mchat.yml.Yml;

import java.io.File;

public class LocaleYml extends Yml {
    public LocaleYml() {
        super(new File("plugins/MSocial/locale.yml"), "MSocial Locale");

        loadDefaults();
    }

    public void loadDefaults() {
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
}
