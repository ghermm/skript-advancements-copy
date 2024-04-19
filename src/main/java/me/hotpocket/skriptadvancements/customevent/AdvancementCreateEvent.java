package me.hotpocket.skriptadvancements.customevent;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.utils.creation.TempAdvancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AdvancementCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String tab;
    private String advancement;
    private TempAdvancement tempAdvancement;

    public AdvancementCreateEvent(String tab, String advancement, TempAdvancement tempAdvancement) {
        this.tab = tab;
        this.advancement = advancement;
        this.tempAdvancement = tempAdvancement;
    }

    public String getTabName() {
        return tab;
    }

    public String getAdvancementName() {
        return advancement;
    }

    public TempAdvancement getTempAdvancement() {
        return tempAdvancement;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}