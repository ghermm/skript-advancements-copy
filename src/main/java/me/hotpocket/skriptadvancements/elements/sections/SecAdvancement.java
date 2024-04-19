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
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.customevent.AdvancementCreateEvent;
import me.hotpocket.skriptadvancements.customevent.AdvancementTabCreateEvent;
import me.hotpocket.skriptadvancements.utils.advancement.VisibilityType;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import me.hotpocket.skriptadvancements.utils.creation.TempAdvancement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Advancement Section")
@Description("Creates a custom advancement.")
@Since("1.4")

public class SecAdvancement extends EffectSection {

    static {
        Skript.registerSection(SecAdvancement.class, "create [a[n]] [new] advancement named %string%");
    }

    private Expression<String> name;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (getParser().isCurrentSection(SecAdvancement.class)) {
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
        if (event instanceof AdvancementTabCreateEvent evt) {
            String n = name.getSingle(event).toLowerCase().replaceAll(" ", "_").replaceAll("[^a-z0-9/._-]", "");
            TempAdvancement advancement = new TempAdvancement(n,
                    evt.getTabName(),
                    new AdvancementDisplay(Material.STICK, "", AdvancementFrameType.TASK, true, true, 0, 0, List.of("")),
                    List.of(""), 0, false, Material.STONE, VisibilityType.VISIBLE);
            Creator.tempAdvancements.add(advancement);
            Bukkit.getPluginManager().callEvent(new AdvancementCreateEvent(evt.getTabName(), n, advancement));
            return walk(event, true);
        }
        return walk(event, false);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create an advancement with the name " + name.toString(event, debug);
    }
}
