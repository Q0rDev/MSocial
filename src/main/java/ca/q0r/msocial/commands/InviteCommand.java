package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.MSocial;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InviteCommand implements CommandExecutor {
    private MSocial plugin;

    public InviteCommand(MSocial instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatinvite")
                || !CommandUtil.hasCommandPerm(sender, "mchat.pm.invite")) {
            return true;
        }

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't use conversation commands.");
            return true;
        }

        Player player = (Player) sender;
        UUID pUUid = player.getUniqueId();
        String pWorld = player.getWorld().getName();

        if (args.length < 1) {
            return false;
        }

        Player recipient = API.getPlayer(args[0]);
        UUID rUUID = recipient.getUniqueId();
        String rWorld = recipient.getWorld().getName();

        if (!CommandUtil.isOnlineForCommand(sender, recipient)) {
            return true;
        }

        if (plugin.getInvite.get(rUUID) == null) {
            plugin.getInvite.put(rUUID, pUUid);

            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_INVITE_SENT.getVal(), "player", Parser.parsePlayerName(rUUID, rWorld), IndicatorType.LOCALE_VAR));
            MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_INVITED.getVal(), "player", Parser.parsePlayerName(pUUid, pWorld), IndicatorType.LOCALE_VAR));
        } else {
            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_HAS_REQUEST.getVal(), "player", Parser.parsePlayerName(rUUID, rWorld), IndicatorType.LOCALE_VAR));
        }

        return true;
    }
}