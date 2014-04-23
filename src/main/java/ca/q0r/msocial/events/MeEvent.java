package ca.q0r.msocial.events;

import ca.q0r.msocial.api.SocialApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MeEvent implements Listener {
    public MeEvent() { }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMe(ca.q0r.mchat.events.custom.MeEvent event) {
        if (SocialApi.isMuted(event.getUuid())) {
            event.setCancelled(true);
        }
    }
}