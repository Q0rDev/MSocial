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

import java.util.TreeMap;
import java.util.UUID;

public class PMCommand implements CommandExecutor {
    MSocial plugin;

    public PMCommand(MSocial instance) {
        plugin = instance;
    }

    String message = "";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchat")
                || !CommandUtil.hasCommandPerm(sender, "mchat.pm.pm")) {
            return true;
        }

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        Player player = (Player) sender;
        UUID pUUID = player.getUniqueId();
        String world = player.getWorld().getName();

        if (args.length < 2) {
            return false;
        }

        message = "";

        for (int i = 1; i < args.length; ++i) {
            message += " " + args[i];
        }

        Player recipient = API.getPlayer(args[0]);

        if (!CommandUtil.isOnlineForCommand(sender, recipient)) {
            return true;
        }

        UUID rUUID = recipient.getUniqueId();
        String senderName = Parser.parsePlayerName(pUUID, world);

        TreeMap<String, String> rMap = new TreeMap<>();

        rMap.put("recipient", Parser.parsePlayerName(rUUID, recipient.getWorld().getName()));
        rMap.put("sender", senderName);
        rMap.put("msg", message);

        player.sendMessage(API.replace(LocaleType.FORMAT_PM_SENT.getVal(), rMap, IndicatorType.LOCALE_VAR));

        plugin.lastPMd.put(rUUID, pUUID);

        recipient.sendMessage(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));
        MessageUtil.log(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));

        return true;
    }
}