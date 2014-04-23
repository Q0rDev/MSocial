package ca.q0r.msocial.commands;

import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.yml.YmlManager;
import ca.q0r.msocial.yml.YmlType;
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

            YmlManager.reloadYml(YmlType.CONFIG_YML);
            YmlManager.reloadYml(YmlType.LOCALE_YML);

            MessageUtil.sendMessage(sender, "Config Reloaded.");
            return true;
        }

        return false;
    }
}