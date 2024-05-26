package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;
import me.hotpocket.skriptadvancements.utils.AdvancementUtils;
import me.hotpocket.skriptadvancements.utils.AdvancementPositioner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FakeAdvancementTab {

	private final String name;

	private FakeRootAdvancement rootAdvancement;
	private RootAdvancement root;
	private List<FakeAdvancement> fakeAdvancements = new ArrayList<>();
	private Collection<Advancement> builtAdvancements = new ArrayList<>();
	private boolean automaticPositioning;

	public FakeAdvancementTab(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public FakeRootAdvancement getFakeRootAdvancement() {
		return rootAdvancement;
	}

	public void setFakeRootAdvancement(FakeRootAdvancement rootAdvancement) {
		this.rootAdvancement = rootAdvancement;
	}

	public RootAdvancement getRootAdvancement() {
		return root;
	}

	public void setRootAdvancement(RootAdvancement advancement) {
		root = advancement;
	}

	public FakeAdvancement getFakeAdvancementByName(String name) {
		for (FakeAdvancement advancement : getFakeAdvancements()) {
			if (advancement.getName().equals(name)) {
				return advancement;
			}
		}
		return getFakeRootAdvancement();
	}

	public List<FakeAdvancement> getFakeAdvancements() {
		return fakeAdvancements;
	}

	public void addFakeAdvancement(FakeAdvancement advancement) {
		if (!getFakeAdvancements().contains(advancement))
			getFakeAdvancements().add(advancement);
	}

	public void removeFakeAdvancement(FakeAdvancement advancement) {
		getFakeAdvancements().remove(advancement);
	}

	public void setFakeAdvancements(List<FakeAdvancement> fakeAdvancements) {
		this.fakeAdvancements = fakeAdvancements;
	}

	public Collection<Advancement> getBuiltAdvancements() {
		return builtAdvancements;
	}

	public void addBuiltAdvancements(Advancement advancement) {
		if (!getBuiltAdvancements().contains(advancement))
			getBuiltAdvancements().add(advancement);
	}

	public void removeBuiltAdvancements(Advancement advancement) {
		getBuiltAdvancements().remove(advancement);
	}

	public void setBuiltAdvancements(Collection<Advancement> builtAdvancements) {
		this.builtAdvancements = builtAdvancements;
	}

	public boolean isAutomaticPositioning() {
		return automaticPositioning;
	}

	public void setAutomaticPositioning(boolean automaticPositioning) {
		this.automaticPositioning = automaticPositioning;
	}

	public Advancement getBuiltAdvancementByName(String name) {
		for (Advancement advancement : builtAdvancements)
			if (advancement.getKey().getKey().equals(name))
				return advancement;
		return null;
	}

	private final Set<BaseAdvancement> baseAdvancements = new HashSet<>();

	public void buildTab() {
		removeOldTabs();
		AdvancementPositioner.arrangeForTree(getFakeRootAdvancement());
		AdvancementTab tab = AdvancementUtils.getAPI().createAdvancementTab(name);
		setRootAdvancement(buildRoot(getFakeRootAdvancement(), tab));
		for (FakeAdvancement advancement : getFakeAdvancements()) {
			if (getBuiltAdvancements().contains(getBuiltAdvancementByName(advancement.getParent().getName()))) {
				baseAdvancements.add(buildAdvancement(advancement, tab));
			}
		}
		tab.registerAdvancements(getRootAdvancement(), baseAdvancements);
		AdvancementUtils.updateTabs();
	}

	private void removeOldTabs() {
		if (AdvancementUtils.getAPI().getAdvancementTab(name) != null && AdvancementUtils.getAPI().getAdvancementTab(name).isInitialised())
			AdvancementUtils.getAPI().unregisterAdvancementTab(name);
	}

	private RootAdvancement buildRoot(FakeRootAdvancement advancement, AdvancementTab tab) {
		RootAdvancement root = new RootAdvancement(tab, advancement.getName(), advancement.getDisplay().getDisplay(), advancement.getBackgroundTexture(), advancement.getMaxProgression()) {
			@Override
			public void giveReward(@NotNull Player player) {
				super.giveReward(player);
				callEvent(player, this);
			}
		};
		addBuiltAdvancements(root);
		SkriptAdvancements.consumers.put(root, advancement.getConsumer());
		return root;
	}

	private BaseAdvancement buildAdvancement(FakeAdvancement advancement, AdvancementTab tab) {
		BaseAdvancement base = new BaseAdvancement(advancement.getName(), advancement.getDisplay().getDisplay(), getBuiltAdvancementByName(advancement.getParent().getName()), advancement.getMaxProgression()) {
			@Override
			public void giveReward(@NotNull Player player) {
				super.giveReward(player);
				callEvent(player, this);
			}
		};
		addBuiltAdvancements(base);
		SkriptAdvancements.consumers.put(base, advancement.getConsumer());
		return base;
	}

	private void callEvent(Player player, Advancement advancement) {
		AdvancementCompleteEvent event = new AdvancementCompleteEvent(player, advancement);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (SkriptAdvancements.consumers.get(advancement) != null) {
			SkriptAdvancements.consumers.get(advancement).accept(event);
		}
	}

}
