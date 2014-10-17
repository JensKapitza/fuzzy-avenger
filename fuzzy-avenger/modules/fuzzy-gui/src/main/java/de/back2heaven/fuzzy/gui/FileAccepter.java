package de.back2heaven.fuzzy.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class FileAccepter {

	public static boolean isImage(Path file) throws IOException {
		ImageInputStream input = ImageIO.createImageInputStream(file.toFile());
		Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

		if (readers.hasNext()) {
			return true;
		}

		return false;
	}

	public static boolean isPDF(Path file) throws IOException {
		// PDF erlauben
		byte[] buffer = new byte[4];
		InputStream in = Files.newInputStream(file);
		in.read(buffer);
		in.close();

		// check if it is pdf
		if (buffer[0] == '%' && buffer[1] == 'P' && buffer[2] == 'D'
				&& buffer[3] == 'F') {
			return true;
		}
		return false;
	}

	public static boolean accepts(Path file) throws IOException {
		// Get the reader
		if (isImage(file) || isPDF(file)) {
			return true;
		}

		return false;
	}

	public static List<Image> readImages(Object inputx, boolean onePageOnly)
			throws IOException {
		// Create input stream
		ImageInputStream input = ImageIO.createImageInputStream(inputx);
		ArrayList<BufferedImage> imgs = new ArrayList<>();
		ArrayList<Image> results = new ArrayList<>();

		try {
			// Get the reader
			Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

			if (!readers.hasNext()) {
				throw new IllegalArgumentException("No reader found");
			}

			ImageReader reader = readers.next();

			try {
				reader.setInput(input);

				ImageReadParam param = reader.getDefaultReadParam();
				int nums = reader.getNumImages(true);
				for (int i = 0; i < nums; i++) {
					BufferedImage image = reader.read(i, param);
					imgs.add(image);
					if (onePageOnly) {
						break;
					}
				}
			} finally {
				// Dispose reader in finally block to avoid memory leaks
				reader.dispose();
			}
		} finally {
			// Close stream in finally block to avoid resource leaks
			input.close();
		}

		for (BufferedImage bf : imgs) {
			results.add(convertImage(bf));
		}

		return results;
	}

	public static Image convertImage(BufferedImage bf) {
		WritableImage wr = new WritableImage(bf.getWidth(), bf.getHeight());
		PixelWriter pw = wr.getPixelWriter();
		for (int x = 0; x < bf.getWidth(); x++) {
			for (int y = 0; y < bf.getHeight(); y++) {
				pw.setArgb(x, y, bf.getRGB(x, y));
			}
		}
		return wr;
	}

	public static List<Image> readImages(Path file, boolean onePageOnly)
			throws IOException {
		return readImages(file.toFile(), onePageOnly);
	}
}
