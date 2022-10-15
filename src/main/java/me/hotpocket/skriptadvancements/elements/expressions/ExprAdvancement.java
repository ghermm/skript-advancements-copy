package me.hotpocket.skriptadvancements.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprAdvancement extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprAdvancement.class, Advancement.class, ExpressionType.SIMPLE,
                "[the] advancement %string%");
    }

    private Expression<String> advancementName;

    @Override
    protected @Nullable Advancement[] get(Event e) {
        if (advancementName.getSingle(e).contains(":")) {
            if (Bukkit.getAdvancement(new NamespacedKey(advancementName.getSingle(e).split(":")[0], advancementName.getSingle(e).split(":")[1])) != null)
                return new Advancement[]{Bukkit.getAdvancement(new NamespacedKey(advancementName.getSingle(e).split(":")[0], advancementName.getSingle(e).split(":")[1]))};
        } else {
            if (Bukkit.getAdvancement(NamespacedKey.minecraft(advancementName.getSingle(e))) != null)
                return new Advancement[]{Bukkit.getAdvancement(NamespacedKey.minecraft(advancementName.getSingle(e)))};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Advancement> getReturnType() {
        return Advancement.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the advancement " + advancementName.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancementName = (Expression<String>) exprs[0];
        return true;
    }
}
