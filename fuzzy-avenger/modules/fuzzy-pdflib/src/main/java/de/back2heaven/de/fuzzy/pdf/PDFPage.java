package de.back2heaven.de.fuzzy.pdf;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFPage {
	private PDPage page;
	private Image prev;
	private BufferedImage printable;

	private int pageNum;

	private PDFFile pdfFile;

	private int rotation;

	private boolean containsImagesOnly;

	public PDFPage(PDFFile file,PDPage page, int pageNum) throws IOException {
		this.pdfFile = file;
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
		printable = page.convertToImage(BufferedImage.TYPE_INT_ARGB, 300);
		// die prev. ist immer 200px groß
		prev = printable.getScaledInstance(200, -1, Image.SCALE_FAST);

		// nur images auf der seite?
		// ich muss diese extrahieren wenn kein text hier steht, 
		// vom kopierer gebautes PDF?
		
		containsImagesOnly = false;
		// sollte das der Fall sein, dann muss ich die prev. nicht berechnen, kann das bild einfach nutzen.
		
	
	}
}
