package de.back2heaven.de.fuzzy.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class TargetPDF {
	private PDDocument document;

	public TargetPDF() {
		document = new PDDocument();
	}

	public PDPage importPage(PDFPage page) throws IOException {
		PDPage xpage = document.importPage(page.getPage());
		return xpage;
	}

	public PDPage addPage(BufferedImage image) throws IOException {
		PDPage xpage = new PDPage(new PDRectangle(image.getWidth(),
				image.getHeight()));
		document.addPage(xpage);
		PDImageXObject ximage = JPEGFactory.createFromImage(document, image);

		PDPageContentStream contentStream = new PDPageContentStream(document,
				xpage);

		contentStream.drawXObject(ximage, 0, 0, image.getWidth(),
				image.getHeight());
		contentStream.close();

		return xpage;

	}

	public void saveToStream(OutputStream stream) throws IOException {
		document.save(stream);
	}
}
