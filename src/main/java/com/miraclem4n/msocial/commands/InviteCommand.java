package com.miraclem4n.msocial.commands;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import com.miraclem4n.msocial.MSocial;
import com.miraclem4n.msocial.types.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteCommand implements CommandExecutor {
    MSocial plugin;

    public InviteCommand(MSocial instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatinvite")
                || !MiscUtil.hasCommandPerm(sender, "mchat.pm.invite"))
            return true;

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't use conversation commands.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String pWorld = player.getWorld().getName();

        if (args.length < 1)
            return false;

        Player recipient = plugin.getServer().getPlayer(args[0]);
        String rName = recipient.getName();
        String rWorld = recipient.getWorld().getName();

        if (!MiscUtil.isOnlineForCommand(sender, recipient))
            return true;

        if (plugin.getInvite.get(rName) == null) {
            plugin.getInvite.put(rName, pName);

            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_INVITE_SENT.getVal(), "player", Parser.parsePlayerName(rName, rWorld), IndicatorType.LOCALE_VAR));
            MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_INVITED.getVal(), "player", Parser.parsePlayerName(pName, pWorld), IndicatorType.LOCALE_VAR));
        } else
            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_HAS_REQUEST.getVal(), "player", Parser.parsePlayerName(rName, rWorld), IndicatorType.LOCALE_VAR));

        return true;
    }
}
