package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResponseCommand implements CommandExecutor {
    String cmd;
    Boolean response;

    public ResponseCommand(String cmd, Boolean response) {
        this.cmd = cmd;
        this.response = response;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(cmd)) {
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

        UUID rUUID = SocialApi.getInvitePartner(pUUID);

        if (rUUID == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NO_PENDING.getVal());
            return true;
        }

        Player recipient = Bukkit.getServer().getPlayer(rUUID);
        String rWorld = recipient.getWorld().getName();

        if (CommandUtil.isOnlineForCommand(sender, rUUID)) {
            SocialApi.setConvo(pUUID, rUUID, response);

            String start = LocaleType.MESSAGE_CONVERSATION_STARTED.getVal();
            String reply = LocaleType.MESSAGE_CONVERSATION_ACCEPTED.getVal();

            if (!this.response) {
                start = LocaleType.MESSAGE_CONVERSATION_NOT_STARTED.getVal();
                reply = LocaleType.MESSAGE_CONVERSATION_DENIED.getVal();
            }

            MessageUtil.sendMessage(player, start.replace("%player", Parser.parsePlayerName(rUUID, rWorld)));
            MessageUtil.sendMessage(recipient, reply.replace("%player", Parser.parsePlayerName(pUUID, pWorld)));
        }

        return true;
    }
}