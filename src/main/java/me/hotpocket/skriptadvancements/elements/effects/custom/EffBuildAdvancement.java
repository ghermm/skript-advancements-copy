package me.hotpocket.skriptadvancements.elements.effects.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import me.hotpocket.skriptadvancements.utils.CustomAdvancement;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffBuildAdvancement extends Effect {

    static {
        Skript.registerEffect(EffBuildAdvancement.class, "build [[the] last (created|made)] [custom] advancement");
    }

    @Override
    protected void execute(Event e) {
        CustomAdvancement.build();
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
