package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand implements CommandExecutor {
    public SayCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatsay")
                || !CommandUtil.hasCommandPerm(sender, "mchat.say")) {
            return true;
        }

        if (sender instanceof Player && SocialApi.isMuted(((Player) sender).getUniqueId())) {
            return true;
        }

        if (args.length > 0) {
            String message = "";

            for (String arg : args) {
                message += " " + arg;
            }

            message = message.trim();

            Bukkit.getServer().broadcastMessage(API.replace(LocaleType.FORMAT_SAY.getVal(), "msg", message, IndicatorType.LOCALE_VAR));
            return true;
        }

        return false;
    }
}