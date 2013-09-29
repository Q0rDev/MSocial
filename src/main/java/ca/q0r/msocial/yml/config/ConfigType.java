package ca.q0r.msocial.yml.config;

import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.msocial.yml.YmlManager;
import ca.q0r.msocial.yml.YmlType;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
    OPTION_SPOUT("option.spoutPM");

    private final String option;

    ConfigType(String option) {
        this.option = option;
    }

    public Boolean getBoolean() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getBoolean(option, false);
    }

    public String getString() {
        return MessageUtil.addColour(YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getString(option, ""));
    }

    public Integer getInteger() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getInt(option, 0);
    }

    public Double getDouble() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getDouble(option, 0.0);
    }

    public List<String> getList() {
        List<String> list = YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getStringList(option);
        ArrayList<String> l = new ArrayList<String>();

        for (String s : list) {
            l.add(MessageUtil.addColour(s));
        }

        return l;
    }
}
