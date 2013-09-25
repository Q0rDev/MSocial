package ca.q0r.msocial.commands;

import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.configs.ConfigUtil;
import ca.q0r.msocial.configs.LocaleUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MSocialCommand implements CommandExecutor {
    public MSocialCommand() { }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("msocial")) {
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")
                || args[0].equalsIgnoreCase("r")) {
            if (!CommandUtil.hasCommandPerm(sender, "msocial.reload")) {
                return true;
            }

            ConfigUtil.initialize();
            LocaleUtil.initialize();

            MessageUtil.sendMessage(sender, "Config Reloaded.");
            return true;
        }

        return false;
    }
}
