package me.hotpocket.skriptadvancements.customevent;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AdvancementTabCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String tab;

    public AdvancementTabCreateEvent(String tab) {
        this.tab = tab;
    }

    public String getTabName() {
        return tab;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}