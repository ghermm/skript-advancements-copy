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
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import me.hotpocket.skriptadvancements.utils.AdvancementUtils;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import me.hotpocket.skriptadvancements.utils.creation.FakeAdvancementTab;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Advancement Tab Section")
@Description("Creates a custom advancement tab.")
@Since("1.4")

public class SecAdvancementTab extends EffectSection {

    static {
        Skript.registerSection(SecAdvancementTab.class, "create [a[n]] [new] advancement tab named %string%");
    }

    private Expression<String> name;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (getParser().isCurrentSection(SecAdvancementTab.class)) {
            Skript.error("The advancement tab creation section is not meant to be put inside of another advancement tab creation section.");
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
        String n = name.getSingle(event).toLowerCase().replaceAll(" ", "_");
        Creator.lastCreatedTab = new FakeAdvancementTab(n);
        // Remove all previous advancement tabs with the same name.
        if (AdvancementUtils.getAPI().getAdvancementTab(n) != null && !AdvancementUtils.getAPI().getAdvancementTab(n).isInitialised()) {
            RootAdvancement root = new RootAdvancement(AdvancementUtils.getAPI().getAdvancementTab(n), "temp_root_advancement_name_1289587", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), AdvancementUtils.getTexture(Material.DIAMOND_BLOCK));
            BaseAdvancement tempBase = new BaseAdvancement("name1", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), root);
            AdvancementUtils.getAPI().getAdvancementTab(n).registerAdvancements(root, tempBase);
            AdvancementUtils.getAPI().unregisterAdvancementTab(n);
        }
        if (AdvancementUtils.getAPI().getAdvancementTab(n) != null && (AdvancementUtils.getAPI().getAdvancementTab(n).isInitialised() || AdvancementUtils.getAPI().getAdvancementTab(n).isActive())) {
            if (AdvancementUtils.getAPI().getAdvancementTab(n).getAdvancements() != null) {
                for (Advancement advancement : AdvancementUtils.getAPI().getAdvancementTab(n).getAdvancements()) {
                    SkriptAdvancements.consumers.remove(advancement);
                }
            }
            AdvancementUtils.getAPI().unregisterAdvancementTab(n);
        }
        return walk(event, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create an advancement tab with the name " + name.toString(event, debug);
    }
}
