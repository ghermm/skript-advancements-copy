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
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Name("Creation - Advancement Row")
@Description("Sets the row of a custom advancement to a number.")
@Examples("set row of advancement to 5")
@Since("1.4")

public class ExprAdvancementRow extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprAdvancementRow.class, Number.class, ExpressionType.SIMPLE,
                "[the] (row|y[-coord[inate]]) [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] (row|y[-coord[inate]])");
    }

    @Override
    protected @Nullable Number[] get(Event e) {
        return new Number[]{Creator.lastCreatedAdvancement.getDisplay().getY()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the row of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET-> CollectionUtils.array(Number.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        Creator.lastCreatedAdvancement.getDisplay().setY(((Number) delta[0]).floatValue());
    }
}
