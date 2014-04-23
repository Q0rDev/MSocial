package ca.q0r.msocial.events.custom;


import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessagingEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Player from, to;
    private String message;
    private Boolean cancelled;

    public MessagingEvent(Player from, Player to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;

        this.cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}