package me.hotpocket.skriptadvancements.utils;

import ch.njol.skript.Skript;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdvancementUtils {

    private static UltimateAdvancementAPI api = UltimateAdvancementAPI.getInstance(SkriptAdvancements.getInstance());

    public static List<Advancement> getPlayerAdvancements(Player player) {
        List<Advancement> advancementList = new ArrayList<>();
        for (Advancement advancement : getAdvancements()) {
            if (advancement.isGranted(player)) {
                advancementList.add(advancement);
            }
        }
        return advancementList;
    }

    public static List<Advancement> getAdvancements() {
        List<Advancement> advancementList = new ArrayList<>();
        for (AdvancementTab tab : api.getTabs()) {
            if (tab.isInitialised())
                advancementList.addAll(tab.getAdvancements());
        }
        return advancementList;
    }

    public static UltimateAdvancementAPI getAPI() {
        return api;
    }

    public static boolean exists(String tab, String name) {
        return api.getAdvancement(tab, name) != null;
    }

    public static String getTexture(Material block) {
        if (block.isBlock() && block.isSolid()) {
            if (Skript.methodExists(Material.class, "getTranslationKey")) {
                return "textures/block/" + block.getTranslationKey().split("minecraft\\.")[1] + ".png";
            } else {
                return "textures/block/" + getTranslationKey(block).split("minecraft\\.")[1] + ".png";
            }
        }
        return "texture/block/dirt.png";
    }

    public static void updateTabs() {
        for (AdvancementTab tab : AdvancementUtils.getAPI().getTabs()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (tab.isInitialised()) {
                    tab.updateAdvancementsToTeam(player);
                }
            }
        }
    }

    private static String getTranslationKey(Material block) {
        return "minecraft." + block.name().toLowerCase();
    }
}
