package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class FakeAdvancement {

	private final String name;
	private final FakeAdvancementDisplay display;
	private FakeAdvancement parent;
	private int maxProgression = 1;
	private AdvancementTab tab;
	private FakeAdvancementTab fakeTab;
	private Consumer<AdvancementCompleteEvent> consumer;
	private Collection<FakeAdvancement> children = new ArrayList<>();

	public FakeAdvancement(String name, FakeAdvancementDisplay display, FakeAdvancementTab tab) {
		this.name = name;
		this.display = display;
		this.fakeTab = tab;
	}

	public String getName() {
		return name;
	}

	public FakeAdvancementDisplay getDisplay() {
		return display;
	}

	public FakeAdvancement getParent() {
		return parent;
	}

	public void setParent(FakeAdvancement parent) {
		this.parent = parent;
		parent.addChild(this);
	}

	public int getMaxProgression() {
		return maxProgression;
	}

	public void setMaxProgression(int maxProgression) {
		this.maxProgression = maxProgression;
	}

	public FakeAdvancementTab getFakeTab() {
		return fakeTab;
	}

	public void setFakeTab(FakeAdvancementTab fakeTab) {
		this.fakeTab = fakeTab;
	}

	public Consumer<AdvancementCompleteEvent> getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer<AdvancementCompleteEvent> consumer) {
		this.consumer = consumer;
	}

	public Collection<FakeAdvancement> getChildren() {
		return children;
	}

	public void addChild(FakeAdvancement child) {
		children.add(child);
	}

	public void send() {
		if (this instanceof FakeRootAdvancement rootAdvancement) {
			getFakeTab().setFakeRootAdvancement(rootAdvancement);
			return;
		}
		getFakeTab().addFakeAdvancement(this);
	}

}
