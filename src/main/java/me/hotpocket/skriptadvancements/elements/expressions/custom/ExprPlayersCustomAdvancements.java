package me.hotpocket.skriptadvancements.elements.expressions.custom;

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
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.utils.AdvancementUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Custom Advancements of Player")
@Description("Creates a custom advancement.")
@Examples("broadcast player's custom advancements")
@Since("1.4")

public class ExprPlayersCustomAdvancements extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprPlayersCustomAdvancements.class, Advancement.class, ExpressionType.SIMPLE,
                "[all [[of] the]] custom advancements of %players%",
                "[all [[of] the]] %players%'[s] custom advancements");
    }

    private Expression<Player> players;

    @Override
    protected @Nullable Advancement[] get(Event e) {
        for (Player player : players.getAll(e))
            return AdvancementUtils.getPlayerAdvancements(player).toArray(new Advancement[AdvancementUtils.getPlayerAdvancements(player).size()]);
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Advancement> getReturnType() {
        return Advancement.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the custom advancements of " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        players = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET, REMOVE, RESET, DELETE, ADD -> CollectionUtils.array(Advancement[].class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        List<Advancement> advancements = new ArrayList<>(Arrays.asList((Advancement[]) delta));
        for (Player player : players.getAll(e)) {
            switch (mode) {
                case SET:
                    for (Advancement advancement : AdvancementUtils.getPlayerAdvancements(player)) {
                        advancement.revoke(player);
                    }
                    for (Advancement advancement : advancements) {
                        advancement.grant(player);
                    }
                    break;
                case ADD:
                    for (Advancement advancement : advancements) {
                        if (!advancement.isGranted(player)) {
                            advancement.grant(player);
                        }
                    }
                    break;
                case REMOVE:
                    for (Advancement advancement : advancements) {
                        if (advancement.isGranted(player)) {
                            advancement.revoke(player);
                        }
                    }
                    break;
                case RESET, DELETE:
                    for (Advancement advancement : AdvancementUtils.getPlayerAdvancements(player)) {
                        advancement.revoke(player);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
