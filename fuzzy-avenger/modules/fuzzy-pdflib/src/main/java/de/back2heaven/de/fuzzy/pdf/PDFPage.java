package de.back2heaven.de.fuzzy.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PDFPage {
	private PDPage page;

	private int pageNum;

	private PDFFile pdfFile;

	private int rotation;

	private boolean containsImagesOnly;
	private PDXObjectImage innerData;

	private int dpi = 300;

	private PDResources res;

	public PDFPage(PDFFile file, int pageNum) throws IOException {
		this.pdfFile = file;
		page = (PDPage) file.getDocument().getDocumentCatalog().getAllPages()
				.get(pageNum);
		if (pdfFile == null) {
			throw new IOException("pdf file must be added for page def.");
		}

		// we need to load the page now
		this.pageNum = pageNum;

		if (page == null || pageNum < 0) {
			throw new IOException("the given page does not exist index:"
					+ pageNum);
		}

		// parse the page infos
		parse();
	}

	private void parse() throws IOException {
		Integer r = page.getRotation();
		rotation = r == null ? 0 : r.intValue();
		// nur images auf der seite?
		// ich muss diese extrahieren wenn kein text hier steht,
		// vom kopierer gebautes PDF?
		res = page.getResources();

		List<PDXObjectImage> images = res.getXObjects().values().stream()
				.filter(p -> p instanceof PDXObjectImage)
				.map(p -> (PDXObjectImage) p).collect(Collectors.toList());
		containsImagesOnly = images.size() == 1
				&& res.getXObjects().values().size() == 1;

		if (containsImagesOnly) {
			innerData = images.get(0);
		}

	}

	public BufferedImage getImage() throws IOException {
		if (innerData != null) {
			return innerData.getRGBImage();
		}
		return page.convertToImage(BufferedImage.TYPE_INT_RGB,dpi);
	}
	
	

	public int getRotation() {
		return rotation;
	}

	public PDFFile getParent() {
		return pdfFile;
	}

	public PDPage getPage() {
		return page;
	}

	public boolean containsImagesOnly() {
		return containsImagesOnly;
	}

	public void setDPI(int dpi) {
		this.dpi = dpi;
	}

	public int getDPI() {
		return dpi;
	}

	public PDResources getResources() {
		return res;
	}

	public int getPageNumber() {
		return pageNum;
	}

}
