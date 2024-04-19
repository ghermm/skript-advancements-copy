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
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import me.hotpocket.skriptadvancements.customevent.AdvancementTabCreateEvent;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import me.hotpocket.skriptadvancements.utils.creation.Creator;
import me.hotpocket.skriptadvancements.utils.creation.TempAdvancement;
import org.bukkit.Bukkit;
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
        String lastCreatedTab = name.getSingle(event).toLowerCase().replaceAll(" ", "_");
        Bukkit.getPluginManager().callEvent(new AdvancementTabCreateEvent(lastCreatedTab));
        if (CustomUtils.getAPI().getAdvancementTab(lastCreatedTab) != null && !CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).isInitialised()) {
            RootAdvancement root = new RootAdvancement(CustomUtils.getAPI().getAdvancementTab(lastCreatedTab), "temp_root_advancement_name_1289587", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), TempAdvancement.getTexture(Material.DIAMOND_BLOCK));
            BaseAdvancement tempBase = new BaseAdvancement("name1", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), root);
            CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).registerAdvancements(root, tempBase);
            CustomUtils.getAPI().unregisterAdvancementTab(lastCreatedTab);
        }
        if (CustomUtils.getAPI().getAdvancementTab(lastCreatedTab) != null && (CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).isInitialised() || CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).isActive())) {
            if (CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).getAdvancements() != null) {
                for (Advancement advancement : CustomUtils.getAPI().getAdvancementTab(lastCreatedTab).getAdvancements()) {
                    SkriptAdvancements.consumers.remove(advancement);
                }
            }
            CustomUtils.getAPI().unregisterAdvancementTab(lastCreatedTab);
        }
        CustomUtils.getAPI().createAdvancementTab(lastCreatedTab);
        return walk(event, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create an advancement tab with the name " + name.toString(event, debug);
    }
}
