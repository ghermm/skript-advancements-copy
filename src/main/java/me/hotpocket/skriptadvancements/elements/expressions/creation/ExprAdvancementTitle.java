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

@Name("Creation - Advancement Title")
@Description("Sets the title of a custom advancement to a string.")
@Examples("set title of advancement to \"Epic Advancement!\"")
@Since("1.4")

public class ExprAdvancementTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprAdvancementTitle.class, String.class, ExpressionType.SIMPLE,
                "[the] title [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] title");
    }

    @Override
    protected @Nullable String[] get(Event e) {
        if (e instanceof AdvancementCreateEvent event)
            return new String[]{event.getTempAdvancement().getDisplay().getTitle()};
        return new String[]{""};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the title of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET-> CollectionUtils.array(String.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        if (e instanceof AdvancementCreateEvent event)
            event.getTempAdvancement().setTitle((String) delta[0]);
    }
}
