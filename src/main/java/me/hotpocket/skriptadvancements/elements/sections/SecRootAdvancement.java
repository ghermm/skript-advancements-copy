package me.hotpocket.skriptadvancements.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import me.hotpocket.skriptadvancements.utils.creation.FakeAdvancement;
import me.hotpocket.skriptadvancements.utils.creation.FakeAdvancementDisplay;
import me.hotpocket.skriptadvancements.utils.creation.FakeRootAdvancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Advancement Section")
@Description("Creates a custom advancement.")

public class SecRootAdvancement extends EffectSection {

    static {
        Skript.registerSection(SecRootAdvancement.class, "create [a[n]] [new] root advancement named %string%");
    }

    private Expression<String> name;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (getParser().isCurrentSection(SecRootAdvancement.class)) {
            Skript.error("The advancement creation section is not meant to be put inside of another advancement creation section.");
            return false;
        }
        if (!getParser().isCurrentSection(SecAdvancementTab.class)) {
            Skript.error("The advancement creation section needs to be inside of an advancement tab creation section.");
            return false;
        }
        if (sectionNode != null) {
            loadOptionalCode(sectionNode);
        }
        name = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected TriggerItem walk(Event event) {
        String n = name.getSingle(event).toLowerCase().replaceAll(" ", "_").replaceAll("[^a-z0-9/._-]", "");
        Creator.lastCreatedAdvancement = new FakeRootAdvancement(n, new FakeAdvancementDisplay(), Creator.lastCreatedTab);
        return walk(event, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create an advancement with the name " + name.toString(event, debug);
    }
}
