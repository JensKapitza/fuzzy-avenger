package de.back2heaven.de.fuzzy.pdf;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFFile {
	private Path path;

	private PDDocument document;

	private int pages;

	private String password;

	private void parse() throws IOException {
		if (document == null) {
			throw new IOException("document not open");
		}

		if (document.isEncrypted()) {
			try {
				if (password == null) {
					document.decrypt("");
				} else {
					document.decrypt(password);
				}
			} catch (CryptographyException e) {
				throw new IOException("problems with decrypting document");
			}
		}

	}

	public PDFPage getPage(int pageNum) throws IOException {
		if (document == null) {
			throw new IOException("document not open");
		}
		if (pageNum > document.getNumberOfPages()) {
			throw new IOException("page index is out of range");
		}
		PDPage page = (PDPage) document.getDocumentCatalog().getAllPages().get(pageNum);
		return new PDFPage(this, page, pageNum);
	}

}
