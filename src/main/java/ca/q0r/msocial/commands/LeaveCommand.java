package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.MSocial;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LeaveCommand implements CommandExecutor {
    private MSocial plugin;

    public LeaveCommand(MSocial instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatleave")) {
            return true;
        }

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't use conversation commands.");
            return true;
        }

        Player player = (Player) sender;
        UUID pUUID = player.getUniqueId();
        String pWorld = player.getWorld().getName();

        UUID rUUID = plugin.chatPartner.get(pUUID);

        Boolean isConv = plugin.isConv.get(pUUID);

        if (rUUID == null || isConv == null) {
            plugin.isConv.put(pUUID, false);
            plugin.chatPartner.remove(pUUID);

            return true;
        }

        if (isConv) {
            Player recipient = plugin.getServer().getPlayer(rUUID);
            String rWorld = recipient.getWorld().getName();

            if (recipient != null) {
                MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_LEFT.getVal(), "player", Parser.parsePlayerName(rUUID, rWorld), IndicatorType.LOCALE_VAR));
                MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_ENDED.getVal(), "player", Parser.parsePlayerName(pUUID, pWorld), IndicatorType.LOCALE_VAR));
            }

            plugin.isConv.put(pUUID, false);
            plugin.isConv.put(rUUID, false);

            plugin.chatPartner.remove(rUUID);
            plugin.chatPartner.remove(pUUID);
        } else {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NOT_IN.getVal());
        }

        return true;
    }
}