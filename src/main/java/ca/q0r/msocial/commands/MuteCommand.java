package ca.q0r.msocial.commands;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.api.SocialApi;
import ca.q0r.msocial.yml.locale.LocaleType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.TreeMap;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {
    public MuteCommand() { }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatmute")
                || !CommandUtil.hasCommandPerm(sender, "mchat.mute")) {
            return true;
        }

        if (args.length < 1) {
            return false;
        }


        Player player = API.getPlayer(args[0]);

        if (!CommandUtil.isOnlineForCommand(sender, player)) {
            return true;
        }

        UUID uuid = player.getUniqueId();

        TreeMap<String, String> rMap = new TreeMap<>();

        rMap.put("player", Parser.parsePlayerName(uuid, player.getWorld().getName()));

        if (SocialApi.isMuted(uuid)) {
            SocialApi.setMuted(uuid, false);

            rMap.put("muted", "unmuted");
            rMap.put("mute", "mute");

            MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_MUTE_MISC.getVal(), rMap, IndicatorType.LOCALE_VAR));
        } else {
            SocialApi.setMuted(uuid, true);

            rMap.put("muted", "muted");
            rMap.put("mute", "unmute");

            MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_MUTE_MISC.getVal(), rMap, IndicatorType.LOCALE_VAR));
        }

        return true;
    }
}