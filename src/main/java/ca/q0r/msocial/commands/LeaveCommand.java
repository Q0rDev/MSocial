package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LeaveCommand implements CommandExecutor {
    public LeaveCommand() { }

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

        if (SocialApi.isConvo(pUUID)) {
            UUID rUUID = SocialApi.getConvoPartner(pUUID);
            Player recipient = Bukkit.getServer().getPlayer(rUUID);
            String rWorld = recipient.getWorld().getName();

            if (recipient != null) {
                MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_LEFT.getVal(), "player", Parser.parsePlayerName(rUUID, rWorld), IndicatorType.LOCALE_VAR));
                MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_ENDED.getVal(), "player", Parser.parsePlayerName(pUUID, pWorld), IndicatorType.LOCALE_VAR));
            }

            SocialApi.setConvo(pUUID, rUUID, false);
        } else {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NOT_IN.getVal());
        }

        return true;
    }
}