package ca.q0r.msocial.events;

import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.events.custom.MessagingEvent;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {
    public ChatListener() { }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        UUID pUUID = player.getUniqueId();

        String msg = event.getMessage();

        if (SocialApi.isMuted(pUUID)) {
            event.setCancelled(true);
            return;
        }

        if (SocialApi.isConvo(pUUID)) {
            Player recipient = Bukkit.getServer().getPlayer(SocialApi.getConvoPartner(pUUID));

            if (recipient != null) {
                MessagingEvent mEvent = new MessagingEvent(player, recipient, msg);

                Bukkit.getPluginManager().callEvent(mEvent);

                if (!mEvent.isCancelled()) {
                    String world = player.getWorld().getName();
                    String format = Parser.parseChatMessage(pUUID, world, mEvent.getMessage());

                    recipient.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + format));
                    player.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + format));
                    MessageUtil.log(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + format));

                    event.setCancelled(true);
                }
            }
        }
    }
}