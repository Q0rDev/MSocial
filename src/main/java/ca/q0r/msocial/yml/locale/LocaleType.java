package ca.q0r.msocial.yml.locale;

import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.yml.YmlManager;
import ca.q0r.msocial.yml.YmlType;

public enum LocaleType {
    FORMAT_PM_RECEIVED("format.pm.received"),
    FORMAT_PM_SENT("format.pm.sent"),
    FORMAT_SAY("format.say"),
    FORMAT_SHOUT("format.shout"),

    MESSAGE_CONVERSATION_ACCEPTED("message.convo.accepted"),
    MESSAGE_CONVERSATION_CONVERSATION("message.convo.convo"),
    MESSAGE_CONVERSATION_DENIED("message.convo.denied"),
    MESSAGE_CONVERSATION_ENDED("message.convo.ended"),
    MESSAGE_CONVERSATION_HAS_REQUEST("message.convo.hasRequest"),
    MESSAGE_CONVERSATION_INVITED("message.convo.invited"),
    MESSAGE_CONVERSATION_INVITE_SENT("message.convo.inviteSent"),
    MESSAGE_CONVERSATION_LEFT("message.convo.left"),
    MESSAGE_CONVERSATION_NOT_IN("message.convo.notIn"),
    MESSAGE_CONVERSATION_NOT_STARTED("message.convo.notStarted"),
    MESSAGE_CONVERSATION_NO_PENDING("message.convo.noPending"),
    MESSAGE_CONVERSATION_STARTED("message.convo.started"),
    MESSAGE_MUTE_MISC("message.general.mute"),
    MESSAGE_PM_NO_PM("message.pm.noPm"),
    MESSAGE_SHOUT_NO_INPUT("message.shout.noInput");

    private final String option;

    LocaleType(String option) {
        this.option = option;
    }

    public String getVal() {
        if (YmlManager.getYml(YmlType.LOCALE_YML).getConfig().isSet(option)) {
            return MessageUtil.addColour(YmlManager.getYml(YmlType.LOCALE_YML).getConfig().getString(option));
        }

        return "Locale Option '" + option + "' not found!";
    }

    public String getRaw() {
        if (YmlManager.getYml(YmlType.LOCALE_YML).getConfig().isSet(option)) {
            return YmlManager.getYml(YmlType.LOCALE_YML).getConfig().getString(option);
        }

        return "Locale Option '" + option + "' not found!";
    }
}