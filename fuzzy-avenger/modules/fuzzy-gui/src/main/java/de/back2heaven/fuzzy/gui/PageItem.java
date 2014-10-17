package de.back2heaven.fuzzy.gui;

import java.io.Serializable;
import java.net.URL;

import javafx.scene.image.Image;

public class PageItem implements Serializable {

	private static final long serialVersionUID = 1848466658628544472L;

	private String name;

	private Serializable[] data;

	private URL iconPath;

	public PageItem(String name, URL icon, Serializable... data) {
		this.data = data;
		this.name = name;
		this.iconPath = icon;
	}

	public Object getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public Image getIcon() {
		if (iconPath == null) {
			return null;
		}
		return new Image(iconPath.toString());
	}
}
