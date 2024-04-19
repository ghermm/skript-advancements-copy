package me.hotpocket.skriptadvancements.elements.expressions.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.hotpocket.skriptadvancements.customevent.AdvancementCreateEvent;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Creation - Advancement Parent")
@Description({"Sets the parent of a custom advancement to any custom advancement represented as a string.",
"Remember to NOT use the custom advancement expression, use a string instead!"})
@Examples("set parent of advancement to \"tabName/advancementName\"")
@Since("1.4")

public class ExprAdvancementParent extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprAdvancementParent.class, String.class, ExpressionType.SIMPLE,
                "[the] parent[s] [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] parent[s]");
    }

    @Override
    protected @Nullable String[] get(Event e) {
        if (e instanceof AdvancementCreateEvent event)
            return event.getTempAdvancement().getParents().toArray(new String[event.getTempAdvancement().getParents().size()]);
        return new String[]{""};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the parent of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> CollectionUtils.array(String[].class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        if (e instanceof AdvancementCreateEvent event) {
            List<String> parents = new ArrayList<>();
            for (String parent : Arrays.copyOf(delta, delta.length, String[].class)) {
                parents.add(parent.toLowerCase().replaceAll(" ", "_"));
            }
            event.getTempAdvancement().setParents(parents);
        }
    }
}
