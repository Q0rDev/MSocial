package ca.q0r.msocial.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.config.ConfigType;
import ca.q0r.mchat.yml.locale.LocaleType;
import ca.q0r.msocial.api.SocialApi;

import java.util.UUID;

public class MSocialVars extends VarAddon {
    public static void addVars() {
        VariableManager.addVar(new DistanceType());
    }

    private static class DistanceType extends Var {
        @Var.Keys(keys = {"distancetype", "dtype"})
        public String getValue(UUID uuid) {
            String dType = "";

            if (SocialApi.isShouting(uuid)) {
                dType = ca.q0r.msocial.yml.locale.LocaleType.FORMAT_SHOUT.getRaw();
            } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
                dType = LocaleType.FORMAT_LOCAL.getVal();
            }

            return dType;
        }
    }
}