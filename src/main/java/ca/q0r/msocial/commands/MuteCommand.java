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
    public MuteCommand() {
    }

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

        TreeMap<String, String> rMap = new TreeMap<String, String>();

        rMap.put("player", Parser.parsePlayerName(uuid, player.getWorld().getName()));

        if (SocialApi.isMuted(uuid)) {
            SocialApi.setMuted(uuid, false);

            rMap.put("muted", "unmuted");
            rMap.put("mute", "mute");

            MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_MUTE_TARGET.getVal(), rMap, IndicatorType.LOCALE_VAR));
            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_MUTE_RECIPIENT.getVal(), rMap, IndicatorType.LOCALE_VAR));
        } else {
            String unit = "\u221e";

            if (args.length < 2) {
                SocialApi.setMuted(uuid, true);
            } else {
                try {
                    String all = args[1];
                    Long time = Long.parseLong(all.substring(0, all.length() - 1));
                    String value = all.substring(all.length() - 1);

                    if (value.equalsIgnoreCase("s")) {
                        unit = time + " " + LocaleType.MESSAGE_MUTE_UNIT_SECONDS.getVal();
                        time = time * 1000;
                    } else if (value.equalsIgnoreCase("m")) {
                        unit = time + " " + LocaleType.MESSAGE_MUTE_UNIT_MINUTES.getVal();
                        time = time * 60000;
                    } else if (value.equalsIgnoreCase("h")) {
                        unit = time + " " + LocaleType.MESSAGE_MUTE_UNIT_HOURS.getVal();
                        time = time * 3600000;
                    }

                    SocialApi.setMuted(uuid, time + System.currentTimeMillis());
                } catch (Exception ignored) {
                    return false;
                }
            }

            rMap.put("muted", "muted");
            rMap.put("mute", "unmute");
            rMap.put("time", unit);

            MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_MUTE_TARGET.getVal(), rMap, IndicatorType.LOCALE_VAR));
            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_MUTE_RECIPIENT.getVal(), rMap, IndicatorType.LOCALE_VAR));
        }

        return true;
    }
}