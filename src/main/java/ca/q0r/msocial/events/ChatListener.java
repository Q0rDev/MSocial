package ca.q0r.msocial.events;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.events.custom.MessagingEvent;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.TreeMap;
import java.util.UUID;

public class ChatListener implements Listener {
    public ChatListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        UUID pUUID = player.getUniqueId();

        String msg = event.getMessage();

        if (SocialApi.isConvo(pUUID)) {
            Player recipient = Bukkit.getServer().getPlayer(SocialApi.getConvoPartner(pUUID));

            if (recipient != null) {
                MessagingEvent mEvent = new MessagingEvent(player, recipient, msg);

                Bukkit.getPluginManager().callEvent(mEvent);

                if (!mEvent.isCancelled()) {
                    String world = player.getWorld().getName();
                    String format = Parser.parseChatMessage(pUUID, world, mEvent.getMessage());

                    recipient.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getRaw() + format));
                    player.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getRaw() + format));
                    MessageUtil.log(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getRaw() + format));

                    event.setCancelled(true);
                }
            }
        }

        if (SocialApi.isMuted(pUUID)) {
            event.setCancelled(true);

            TreeMap<String, String> rMap = new TreeMap<String, String>();
            Long timeLeft = SocialApi.getMuteTimeLeft(pUUID);

            String time;

            if (timeLeft < 59000) {
                time = Math.round(timeLeft / 1000) + " " + LocaleType.MESSAGE_MUTE_UNIT_SECONDS.getRaw();
            } else if (timeLeft < 3590000) {
                time = Math.round(timeLeft / 60000) + " " + LocaleType.MESSAGE_MUTE_UNIT_MINUTES.getRaw();
            } else {
                time = Math.round(timeLeft / 3600000) + " " + LocaleType.MESSAGE_MUTE_UNIT_HOURS.getRaw();
            }

            rMap.put("time", time);

            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_MUTE_STILL_MUTED.getVal(), rMap, IndicatorType.LOCALE_VAR));
        }
    }
}