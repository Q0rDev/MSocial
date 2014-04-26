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

public class ShoutCommand implements CommandExecutor {
    public ShoutCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatshout")
                || !CommandUtil.hasCommandPerm(sender, "mchat.shout")) {
            return true;
        }

        if (sender instanceof Player && SocialApi.isMuted(((Player) sender).getUniqueId())) {
            return true;
        }

        String message = "";

        for (String arg : args) {
            message += " " + arg;
        }

        message = message.trim();

        if (!(sender instanceof Player)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "say " + message);
            return true;
        }

        if (message.length() < 1) {
            MessageUtil.sendMessage(sender, LocaleType.MESSAGE_SHOUT_NO_INPUT.getVal());
            return true;
        }

        Player player = (Player) sender;

        SocialApi.setShouting(player.getUniqueId(), true);

        Bukkit.getServer().broadcastMessage(Parser.parseChatMessage(player.getUniqueId(), player.getWorld().getName(), message));

        SocialApi.setShouting(player.getUniqueId(), false);

        return true;
    }
}