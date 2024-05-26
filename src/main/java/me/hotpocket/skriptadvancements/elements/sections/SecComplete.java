package me.hotpocket.skriptadvancements.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SecComplete extends Section {

	static {
		Skript.registerSection(SecComplete.class, "[on] [advancement] complete[d]");
	}

	@Nullable
	private Trigger trigger;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
		if (!getParser().isCurrentSection(SecAdvancementTab.class)) {
			Skript.error("The advancement complete section needs to be inside of an advancement creation section.");
			return false;
		}
		trigger = loadCode(sectionNode, "complete", AdvancementCompleteEvent.class);
		return true;
	}

	@Override
	protected @Nullable TriggerItem walk(Event e) {
		if (trigger != null) {
			Object localVars = Variables.copyLocalVariables(e);
			Creator.lastCreatedAdvancement.setConsumer((advancementEvent) -> {
				Variables.setLocalVariables(advancementEvent, localVars);
				trigger.execute(advancementEvent);
			});
		}
		return walk(e, false);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on advancement complete";
	}

}
