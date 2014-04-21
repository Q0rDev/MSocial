package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.MSocial;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AcceptCommand implements CommandExecutor {
    private MSocial plugin;

    public AcceptCommand(MSocial instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchataccept")) {
            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't use conversation commands.");
            return true;
        }

        Player player = (Player) sender;
        UUID pUUID = player.getUniqueId();
        String pWorld = player.getWorld().getName();

        UUID rUUID = plugin.getInvite.get(pUUID);

        if (rUUID == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NO_PENDING.getVal());
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(rUUID);
        String rWorld = recipient.getWorld().getName();

        if (CommandUtil.isOnlineForCommand(sender, rUUID)) {
            plugin.getInvite.remove(pUUID);

            plugin.isConv.put(pUUID, true);
            plugin.isConv.put(rUUID, true);

            plugin.chatPartner.put(rUUID, pUUID);
            plugin.chatPartner.put(pUUID, rUUID);

            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_STARTED.getVal().replace("%player", Parser.parsePlayerName(rUUID, rWorld)));
            MessageUtil.sendMessage(recipient, LocaleType.MESSAGE_CONVERSATION_ACCEPTED.getVal().replace("%player", Parser.parsePlayerName(pUUID, pWorld)));
        }

        return true;
    }
}