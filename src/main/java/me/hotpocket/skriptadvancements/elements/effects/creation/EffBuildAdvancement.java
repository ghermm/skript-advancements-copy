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
import me.hotpocket.skriptadvancements.customevent.AdvancementCreateEvent;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Build Advancement")
@Description({"Builds the last created advancement.",
"This can only be used inside of the advancement section."})
@Examples("build advancement")
@Since("1.4")

public class EffBuildAdvancement extends Effect {

    static {
        Skript.registerEffect(EffBuildAdvancement.class, "build [[the] last (created|made)] [custom] advancement");
    }

    @Override
    protected void execute(Event e) {
        if (e instanceof AdvancementCreateEvent event)
            event.getTempAdvancement().build(event);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "build the custom advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }
}
