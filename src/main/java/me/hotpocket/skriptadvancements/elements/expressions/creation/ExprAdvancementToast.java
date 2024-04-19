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

@Name("Creation - Advancement Toast")
@Description("Sets the toast of a custom advancement to true or false.")
@Examples("set toast of advancement to true")
@Since("1.4")

public class ExprAdvancementToast extends SimpleExpression<Boolean> {

    static {
        Skript.registerExpression(ExprAdvancementToast.class, Boolean.class, ExpressionType.SIMPLE,
                "[the] toast [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] toast");
    }

    @Override
    protected @Nullable Boolean[] get(Event e) {
        if (e instanceof AdvancementCreateEvent event)
            return new Boolean[]{event.getTempAdvancement().getDisplay().doesShowToast()};
        return new Boolean[]{false};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the toast of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> CollectionUtils.array(Boolean.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        if (e instanceof AdvancementCreateEvent event)
            event.getTempAdvancement().setToast((Boolean) delta[0]);
    }
}
