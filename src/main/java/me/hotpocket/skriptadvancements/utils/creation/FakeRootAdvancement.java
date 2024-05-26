package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class FakeRootAdvancement extends FakeAdvancement {
	private String backgroundTexture;
	private Material background;

	public FakeRootAdvancement(String name, FakeAdvancementDisplay display, FakeAdvancementTab tab) {
		super(name, display, tab);
	}

	public String getBackgroundTexture() {
		return backgroundTexture;
	}

	public void setBackgroundTexture(String backgroundTexture) {
		this.backgroundTexture = backgroundTexture;
	}

	public Material getBackground() {
		return background;
	}

	public void setBackground(Material background) {
		this.background = background;
	}

}
