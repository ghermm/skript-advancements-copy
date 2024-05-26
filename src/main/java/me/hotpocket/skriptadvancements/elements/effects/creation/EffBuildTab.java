package me.hotpocket.skriptadvancements.elements.effects.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancementTab;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Build Advancement Tab")
@Description({"Builds the last created advancement tab.",
        "This can only be used inside of the advancement tab section."})
@Examples("build advancement tab")
@Since("1.4")

public class EffBuildTab extends Effect {

    static {
        Skript.registerEffect(EffBuildTab.class, "build [[the] last (created|made)] [advancement] tab");
    }

    @Override
    protected void execute(Event e) {
        Creator.lastCreatedTab.buildTab();
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "build the advancement tab";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancementTab.class);
    }
}
