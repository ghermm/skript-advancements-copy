package me.hotpocket.skriptadvancements.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.events.advancement.AdvancementProgressionUpdateEvent;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import me.hotpocket.skriptadvancements.utils.creation.TempAdvancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SecComplete extends Section {

	static {
		Skript.registerSection(SecComplete.class, "[on] [advancement] complete[d]");
	}

	@Nullable
	private Trigger trigger;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
		if (!getParser().isCurrentSection(SecAdvancement.class)) {
			Skript.error("The advancement complete section needs to be inside of an advancement creation section.");
			return false;
		}
		trigger = loadCode(sectionNode, "advancement complete", AdvancementCompleteEvent.class);
		return true;
	}

	@Override
	protected @Nullable TriggerItem walk(Event event) {
		BiConsumer<Player,Advancement> consumer;
		if (trigger != null) {
			consumer = (p, a) -> {
				if (Creator.currentEvent != null) {
					Event currentEvent = Creator.currentEvent;
					AdvancementCompleteEvent advancementEvent = new AdvancementCompleteEvent(p, a);
					Variables.setLocalVariables(advancementEvent, Variables.copyLocalVariables(currentEvent));
					TriggerItem.walk(trigger, advancementEvent);
					Variables.setLocalVariables(currentEvent, Variables.copyLocalVariables(advancementEvent));
					Variables.removeLocals(advancementEvent);
				}
			};
			Creator.lastCreatedAdvancement.consumer = consumer;
		}
		return walk(event, false);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on advancement complete";
	}

}
