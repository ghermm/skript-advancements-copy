package me.hotpocket.skriptadvancements.customevent;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.events.advancement.AdvancementProgressionUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public final class AdvancementCompleteEvent extends AdvancementProgressionUpdateEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean cancelled;

    public AdvancementCompleteEvent(TeamProgression team, int oldProgression, int newProgression, Advancement advancement) {
        super(team, oldProgression, newProgression, advancement);
        if (team.getAMember() != null)
            player = Bukkit.getPlayer(team.getAMember());
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}