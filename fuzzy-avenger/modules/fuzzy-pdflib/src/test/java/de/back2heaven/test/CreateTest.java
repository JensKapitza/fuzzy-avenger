package de.back2heaven.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import de.back2heaven.de.fuzzy.pdf.PDFFile;
import de.back2heaven.de.fuzzy.pdf.PDFPage;
import de.back2heaven.de.fuzzy.pdf.TargetPDF;

public class CreateTest {
	@Test
	public void testCreateTarget() throws IOException {
		String target = "target_doc.pdf";
		File testDir = new File("testdata");
		try (FileOutputStream fos = new FileOutputStream(target)) {
			TargetPDF pdf = new TargetPDF();
			for (File f : testDir.listFiles()) {
				System.out.println(f.getName());
				if (f.getName().endsWith("pdf")) {
					PDFFile x = new PDFFile(f.toPath(), null);
					for (PDFPage page : x) {
						pdf.importPage(page);
					}
					x.close();
				} else {
					// try imageio
					BufferedImage image = ImageIO.read(f);
					pdf.addPage(image);
				}
			}

			pdf.saveToStream(fos);
		}

	}
}
