package de.back2heaven.de.fuzzy.pdf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.DecryptionMaterial;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;

public class PDFFile implements Iterable<PDFPage> {
	private Path path;

	private PDDocument document;

	private int pages;

	private String password;

	public PDFFile(Path path, String password) throws IOException {
		this.path = path;
		this.password = password;
		if (path == null || !Files.exists(path)) {
			throw new IOException("pdf file not found");
		}
		document = PDDocument.load(path.toFile());
		parse();
		this.pages = document.getNumberOfPages();
	}

	private void parse() throws IOException {
		if (document == null) {
			throw new IOException("document not open");
		}

		if (document.isEncrypted()) {
			if (password == null) {
				// hackO
				password = "";
			}
			DecryptionMaterial decryptionMaterial = new StandardDecryptionMaterial(
					password);
			try {
				document.openProtection(decryptionMaterial);
			} catch (Exception e) {
				throw new IOException("password invalid for file: " + path);
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
		return new PDFPage(this, pageNum);
	}

	public int getPages() {
		return pages;
	}

	public PDDocument getDocument() {
		return document;
	}

	public Path getPath() {
		return path;
	}

	public List<PDFPage> getPages(int... pageNums) throws IOException {
		ArrayList<PDFPage> pages = new ArrayList<>();
		for (int pageNum : pageNums) {
			pages.add(getPage(pageNum));
		}
		return pages;
	}

	public void close() throws IOException {
		document.close();
	}

	@Override
	public Iterator<PDFPage> iterator() {
		ArrayList<PDFPage> pages = new ArrayList<>();
		for (int pageNum = 0; pageNum < this.pages; pageNum++) {
			try {
				pages.add(getPage(pageNum));
			} catch (IOException e) {
				return null;
			}
		}
		return pages.iterator();
	}
}
