package ca.q0r.msocial.types;

import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.configs.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
    OPTION_SPOUT("option.spoutPM");

    private final String option;

    ConfigType(String option) {
        this.option = option;
    }

    public Boolean getBoolean() {
        return ConfigUtil.getConfig().getBoolean(option, false);
    }

    public String getString() {
        return MessageUtil.addColour(ConfigUtil.getConfig().getString(option, ""));
    }

    public Integer getInteger() {
        return ConfigUtil.getConfig().getInt(option, 0);
    }

    public Double getDouble() {
        return ConfigUtil.getConfig().getDouble(option, 0.0);
    }

    public List<String> getList() {
        List<String> list = ConfigUtil.getConfig().getStringList(option);
        ArrayList<String> l = new ArrayList<String>();

        for (String s : list) {
            l.add(MessageUtil.addColour(s));
        }

        return l;
    }
}
