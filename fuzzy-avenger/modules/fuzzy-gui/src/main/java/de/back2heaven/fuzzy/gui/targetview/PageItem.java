package de.back2heaven.fuzzy.gui.targetview;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.image.Image;

public class PageItem implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageItem other = (PageItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1848466658628544472L;

	private String name;

	private Serializable[] data;

	private URL iconPath;
	private transient volatile Object cache;

	public PageItem(String name, URL icon, Serializable... data) {
		this.data = data;
		this.name = name;
		this.iconPath = icon;
	}

	public void setCache(Object cache) {
		this.cache = cache;
	}

	public Object getCache() {
		return cache;
	}

	public Serializable[] getData() {
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

	public void countUp(List<PageItem> items) {
		String base = name.split("#")[0].trim();
		List<String> names = items.stream()
				.filter(i -> i != this && i.getName().startsWith(base))
				.map(i -> i.getName()).collect(Collectors.toList());

		if (names.size() > 0) {
			Optional<Integer> intx = names.stream()
					.filter(i -> i.contains("#")).map(p -> p.split("#")[1])
					.map(i -> Integer.parseInt(i)).max(Integer::compare);

			intx.orElse(1);
			int num = intx.isPresent() ? intx.get() : 1;
			name = base + " #" + (num + 1);
		}

	}
}
