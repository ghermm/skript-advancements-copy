package me.hotpocket.skriptadvancements.customevent;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.events.advancement.AdvancementProgressionUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AdvancementCompleteEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Advancement advancement;

    public AdvancementCompleteEvent(Player player, Advancement advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    public Player getPlayer() {
        return player;
    }
    public Advancement getAdvancement() {
        return advancement;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}