package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class FakeAdvancementDisplay {

	private String title = "New Title";
	private List<String> description = Collections.singletonList("New Description");
	private ItemStack icon = new ItemStack(Material.STICK);
	private AdvancementFrameType frame = AdvancementFrameType.TASK;
	private float x = 0f;
	private float y = 0f;
	private boolean toast;
	private boolean announcement;

	public FakeAdvancementDisplay() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public AdvancementFrameType getFrame() {
		return frame;
	}

	public void setFrame(AdvancementFrameType frame) {
		this.frame = frame;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean doesShowToast() {
		return toast;
	}

	public void setToast(boolean toast) {
		this.toast = toast;
	}

	public boolean doesAnnounce() {
		return announcement;
	}

	public void setAnnouncement(boolean announcement) {
		this.announcement = announcement;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public AdvancementDisplay getDisplay() {
		return new AdvancementDisplay(getIcon(), getTitle(), getFrame(), doesShowToast(), doesAnnounce(), getX(), getY(), getDescription());
	}

}
