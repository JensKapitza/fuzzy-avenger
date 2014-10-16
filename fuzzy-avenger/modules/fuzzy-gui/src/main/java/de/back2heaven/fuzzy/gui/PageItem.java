package de.back2heaven.fuzzy.gui;

import javafx.scene.image.Image;

public class PageItem {

	private String name;

	private Object data;

	private Image icon;

	public PageItem(String name, Object data, Image icon) {
		this.data = data;
		this.name = name;
		this.icon = icon;
	}

	public Object getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public Image getIcon() {
		return icon;
	}
}
