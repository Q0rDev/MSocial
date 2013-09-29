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

public class ShoutCommand implements CommandExecutor {
    MSocial plugin;

    public ShoutCommand(MSocial instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatshout")
                || !CommandUtil.hasCommandPerm(sender, "mchat.shout")) {
            return true;
        }

        String message = "";

        for (String arg : args) {
            message += " " + arg;
        }

        message = message.trim();

        if (!(sender instanceof Player)) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "say " + message);
            return true;
        }

        if (message.length() < 1) {
            MessageUtil.sendMessage(sender, LocaleType.MESSAGE_SHOUT_NO_INPUT.getVal());
            return true;
        }

        Player player = (Player) sender;

        MSocial.isShouting.put(sender.getName(), true);

        plugin.getServer().broadcastMessage(Parser.parseChatMessage(player.getName(), player.getWorld().getName(), message));

        MSocial.isShouting.put(sender.getName(), false);

        return true;
    }
}
