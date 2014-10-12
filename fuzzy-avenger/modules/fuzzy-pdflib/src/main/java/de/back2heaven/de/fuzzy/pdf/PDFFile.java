package de.back2heaven.de.fuzzy.pdf;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFFile {
	private Path path;

	private PDDocument document;

	private int pages;

	public PDPage getPage(int pageNum) throws IOException {
		if (document == null) {
			throw new IOException("document not open");
		}
		PDPage page = null;
		return page;
	}

	public BufferedImage getPageImage(int pageNum) throws IOException {
		PDPage page = getPage(pageNum);
		if (page == null) {
			throw new IOException("page: " + pageNum + " not found");
		}
		return page.convertToImage();
	}
}
