package ca.q0r.msocial.api;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class SocialApi {
    private static HashMap<UUID, Boolean> shouting;
    private static HashMap<UUID, Boolean> convo;

    private static HashMap<UUID, UUID> lastPm;
    private static HashMap<UUID, UUID> invite;
    private static HashMap<UUID, UUID> partner;

    private static HashMap<UUID, Long> muted;

    public static void initialize() {
        shouting = new HashMap<UUID, Boolean>();
        convo = new HashMap<UUID, Boolean>();

        lastPm = new HashMap<UUID, UUID>();
        invite = new HashMap<UUID, UUID>();
        partner = new HashMap<UUID, UUID>();

        muted = new HashMap<UUID, Long>();
    }

    public static Boolean isShouting(UUID uuid) {
        return shouting.get(uuid) != null && shouting.get(uuid);
    }

    public static Boolean isMuted(UUID uuid) {
        return muted.get(uuid) != null && (muted.get(uuid) >= System.currentTimeMillis() || muted.get(uuid) == -1L);
    }

    public static Boolean isConvo(UUID uuid) {
        return convo.get(uuid) != null && convo.get(uuid);
    }

    public static UUID getLastMessaged(UUID uuid) {
        return lastPm.get(uuid);
    }

    public static UUID getInvitePartner(UUID uuid) {
        return invite.get(uuid);
    }

    public static UUID getConvoPartner(UUID uuid) {
        return partner.get(uuid);
    }

    public static Long getMuteTimeLeft(UUID uuid) {
        if (muted.get(uuid) != null && muted.get(uuid) != -1L) {
            return muted.get(uuid) - System.currentTimeMillis();
        }

        return null;
    }

    public static void setShouting(UUID uuid, Boolean state) {
        shouting.put(uuid, state);
    }

    public static void setMuted(UUID uuid, Boolean state) {
        if (state) {
            muted.put(uuid, -1L);
        } else {
            muted.remove(uuid);
        }
    }

    public static void setMuted(UUID uuid, Long duration) {
        muted.put(uuid, duration);
    }

    public static void setLastMessaged(UUID uuid, UUID other) {
        lastPm.put(uuid, other);
    }

    public static void setInvitePartner(UUID uuid, UUID other) {
        invite.put(uuid, other);
    }

    public static void setConvo(UUID uuid, UUID other, Boolean state) {
        invite.remove(uuid);

        convo.put(uuid, state);
        convo.put(other, state);

        if (state) {
            partner.put(uuid, other);
            partner.put(other, uuid);
        }
    }

    public static void sendMessage(Player from, Player to, String msg) {
        String senderName = Parser.parsePlayerName(from.getUniqueId(), from.getWorld().getName());

        TreeMap<String, String> rMap = new TreeMap<String, String>();

        rMap.put("recipient", Parser.parsePlayerName(to.getUniqueId(), to.getWorld().getName()));
        rMap.put("sender", senderName);
        rMap.put("msg", msg);

        from.sendMessage(API.replace(LocaleType.FORMAT_PM_SENT.getVal(), rMap, IndicatorType.LOCALE_VAR));

        SocialApi.setLastMessaged(to.getUniqueId(), from.getUniqueId());

        to.sendMessage(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));
        MessageUtil.log(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));
    }
}