package de.back2heaven.fuzzy.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class FileAccepter {

	public static boolean accepts(Path file) throws IOException {
		// Get the reader
		Iterator<ImageReader> readers = ImageIO.getImageReaders(file.toFile());

		if (readers.hasNext()) {
			return true;
		}

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

	public static List<BufferedImage> readImages(InputStream imageStream)
			throws IOException {
		// Create input stream
		ImageInputStream input = ImageIO.createImageInputStream(imageStream);
		ArrayList<BufferedImage> imgs = new ArrayList<>();
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
				}
			} finally {
				// Dispose reader in finally block to avoid memory leaks
				reader.dispose();
			}
		} finally {
			// Close stream in finally block to avoid resource leaks
			input.close();
		}
		return imgs;
	}

}
