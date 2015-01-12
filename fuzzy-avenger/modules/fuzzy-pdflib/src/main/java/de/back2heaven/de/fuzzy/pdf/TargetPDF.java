package de.back2heaven.de.fuzzy.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;

public class TargetPDF {
	private PDDocument document;

	public TargetPDF() {
		document = new PDDocument();
	}

	public PDPage importPage(PDFPage page) throws IOException {
		PDPage xpage = document.importPage(page.getPage());
		xpage.setResources(page.getPage().getResources());
		processAnnotations(xpage);
		return xpage;
	}

	private void processAnnotations(PDPage imported) throws IOException {
		List<PDAnnotation> annotations = imported.getAnnotations();
		for (PDAnnotation annotation : annotations) {
			if (annotation instanceof PDAnnotationLink) {
				PDAnnotationLink link = (PDAnnotationLink) annotation;
				PDDestination destination = link.getDestination();
				if (destination == null && link.getAction() != null) {
					PDAction action = link.getAction();
					if (action instanceof PDActionGoTo) {
						destination = ((PDActionGoTo) action).getDestination();
					}
				}
				if (destination instanceof PDPageDestination) {
					((PDPageDestination) destination).setPage(null);
				}
			} else {
				annotation.setPage(null);
			}
		}
	}

	public PDPage addPage(BufferedImage image) throws IOException {
		return addPage(image, 1f);
	}

	public PDPage addPage(BufferedImage image, float scale) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		PDPage xpage = new PDPage(new PDRectangle(width, height));
		document.addPage(xpage);
		PDImageXObject ximage = LosslessFactory
				.createFromImage(document, image);

		PDPageContentStream contentStream = new PDPageContentStream(document,
				xpage, true, true);

		contentStream.drawImage(ximage, 0, 0, width * scale, height * scale);
		contentStream.close();

		return xpage;

	}

	public void saveToStream(OutputStream stream) throws IOException {
		document.save(stream);
		document.close();
	}
}
