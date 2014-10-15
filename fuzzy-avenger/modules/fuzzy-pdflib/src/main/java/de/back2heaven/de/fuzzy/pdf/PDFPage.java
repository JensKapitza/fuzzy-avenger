package de.back2heaven.de.fuzzy.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFPage {
	private PDPage page;

	private int pageNum;

	private PDFFile pdfFile;

	private int rotation;

	private boolean containsImagesOnly;
	private PDFRenderer renderer;
	private PDImage innerData;

	private float dpi = 300;

	public PDFPage(PDFFile file, PDFRenderer renderer, PDPage page, int pageNum)
			throws IOException {
		this.pdfFile = file;
		this.renderer = renderer;
		if (pdfFile == null) {
			throw new IOException("pdf file must be added for page def.");
		}

		// we need to load the page now
		this.page = page;
		this.pageNum = pageNum;

		if (page == null || pageNum < 0) {
			throw new IOException("the given page does not exist index:"
					+ pageNum);
		}

		// parse the page infos
		parse();
	}

	private void parse() throws IOException {
		rotation = page.findRotation();

		// nur images auf der seite?
		// ich muss diese extrahieren wenn kein text hier steht,
		// vom kopierer gebautes PDF?
		Map<String, PDXObject> allObj = page.getResources().getXObjects();

		List<PDXObject> liste = allObj.entrySet().stream()
				.map(e -> e.getValue()).collect(Collectors.toList());
		List<PDImage> images = liste.stream().filter(p -> p instanceof PDImage)
				.map(e -> (PDImage) e).collect(Collectors.toList());

		containsImagesOnly = liste.size() == images.size();

		if (containsImagesOnly && images.size() == 1) {
			innerData = images.get(0);
		}

	}

	public BufferedImage getImage() throws IOException {
		if (innerData != null) {
			return innerData.getImage();
		}
		return renderer.renderImageWithDPI(pageNum, dpi);
	}

	public int getRotation() {
		return rotation;
	}

	public PDFFile getParent() {
		return pdfFile;
	}

	public PDPage getPage() {
		if (innerData != null) {
			return page;
		}
		return null;
	}

	public boolean containsImagesOnly() {
		return containsImagesOnly;
	}

	public void setDPI(float dpi) {
		this.dpi = dpi;
	}

	public float getDPI() {
		return dpi;
	}

}
