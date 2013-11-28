package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.MSocial;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        String pName = player.getName();

        String rName = plugin.chatPartner.get(pName);

        Boolean isConv = plugin.isConv.get(pName);

        if (rName == null || isConv == null) {
            plugin.isConv.put(pName, false);
            plugin.chatPartner.remove(pName);

            return true;
        }

        if (isConv) {
            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_LEFT.getVal(), "player", rName, IndicatorType.LOCALE_VAR));

            Player recipient = plugin.getServer().getPlayer(rName);

            if (recipient != null) {
                MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_ENDED.getVal(), "player", pName, IndicatorType.LOCALE_VAR));
            }

            plugin.isConv.put(pName, false);
            plugin.isConv.put(rName, false);

            plugin.chatPartner.remove(rName);
            plugin.chatPartner.remove(pName);
        } else {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NOT_IN.getVal());
        }

        return true;
    }
}