package ca.q0r.msocial.events;

import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.MSocial;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {
    private MSocial plugin;

    public ChatListener(MSocial instance) {
        plugin = instance;
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        UUID pUUID = player.getUniqueId();

        String world = player.getWorld().getName();
        String msg = event.getMessage();
        String eventFormat = Parser.parseChatMessage(pUUID, world, msg);

        if (plugin.isMuted.get(pUUID) != null
                && plugin.isMuted.get(pUUID)) {
            event.setCancelled(true);
            return;
        }

        if (plugin.isConv.get(pUUID) == null) {
            plugin.isConv.put(pUUID, false);
        }

        if (plugin.isConv.get(pUUID)) {
            Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pUUID));
            recipient.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
            player.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
            MessageUtil.log(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
            event.setCancelled(true);
        }
    }
}