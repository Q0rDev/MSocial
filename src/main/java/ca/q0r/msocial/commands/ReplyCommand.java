package ca.q0r.msocial.commands;

import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.events.custom.MessagingEvent;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {
    public ReplyCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatreply")
                || !CommandUtil.hasCommandPerm(sender, "mchat.pm.reply")) {
            return true;
        }

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        Player player = (Player) sender;
        UUID pUUID = player.getUniqueId();

        String message = "";

        for (String arg : args) {
            message += " " + arg;
        }

        UUID rUUID = SocialApi.getLastMessaged(pUUID);

        if (rUUID == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_PM_NO_PM.getVal());
            return true;
        }

        Player recipient = Bukkit.getServer().getPlayer(rUUID);

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