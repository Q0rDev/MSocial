package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.events.custom.MessagingEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PMCommand implements CommandExecutor {
    public PMCommand() {
    }

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

        if (args.length < 2) {
            return false;
        }

        String message = "";

        for (int i = 1; i < args.length; ++i) {
            message += " " + args[i];
        }

        Player recipient = API.getPlayer(args[0]);

        if (!CommandUtil.isOnlineForCommand(sender, recipient)) {
            return true;
        }

        MessagingEvent event = new MessagingEvent(player, recipient, message);

        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            SocialApi.sendMessage(event.getFrom(), event.getTo(), event.getMessage());
        }

        return true;
    }
}