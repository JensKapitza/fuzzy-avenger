package de.back2heaven.de.fuzzy.pdf;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.LayerUtility;

public class TargetPDF {
	private PDDocument document;

	public TargetPDF() {
		document = new PDDocument();
	}

	public PDPage importPage(PDFPage page) throws IOException {
		if (page.containsImagesOnly()) {
			return addPage(page.getImage());
		} else {

			LayerUtility layerUtility = new LayerUtility(document);

			ImageIO.write(page.getImage(), "png", new FileOutputStream(page
					.getParent().getPath().getFileName()
					+ "_" + page.getPageNumber() + ".png"));
			// this does not work!
			// font is missing
			PDPage px = document.importPage(page.getPage());
			px.setResources(page.getPage().findResources());

			PDXObjectForm form = layerUtility.importPageAsForm(page.getParent()
					.getDocument(), page.getPageNumber());

			// These things can easily be done in a loop, too
			AffineTransform affineTransform = new AffineTransform(); 
			layerUtility.appendFormAsLayer(px, form, affineTransform,
					"form"+page.getPageNumber());

			document.getDocumentCatalog()
					.getCOSDictionary()
					.mergeInto(
							page.getParent().getDocument().getDocumentCatalog()
									.getCOSDictionary());

			px.getResources().addFont(PDFontFactory.createDefaultFont());
			page.getPage().getResources().getFonts().values()
					.forEach(px.getResources()::addFont);
			return px;
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

		PDXObjectImage ximage = new PDJpeg(document, image);

		PDPageContentStream contentStream = new PDPageContentStream(document,
				xpage, true, true);

		contentStream.drawXObject(ximage, 0, 0, width * scale, height * scale);
		contentStream.close();

		return xpage;

	}

	public void saveToStream(OutputStream stream) throws IOException {
		try {
			document.save(stream);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		} finally {
			document.close();
		}
	}
}
