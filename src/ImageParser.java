import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ImageParser {

	Image[] images;

	ArrayList<Image> horizontalImages;
	ArrayList<Image> verticleImages;

	public ImageParser() {
		horizontalImages = new ArrayList<Image>();
		verticleImages = new ArrayList<Image>();
	}

	public Image[] parse(String path) {
		File f = new File(path);
		if (!f.exists()) {
			System.err.println("Could not find file at " + f.getAbsolutePath());
			System.exit(1);
		}

		try (Scanner s = new Scanner(f)) {

			int numImages = Integer.valueOf(s.nextLine());
			Image[] images = new Image[numImages];
			for (int imgId = 0; imgId < numImages; imgId++) {
				String imgData = s.nextLine();
				String[] splitData = imgData.split(" ");
				int numTags = Integer.parseInt(splitData[1]);
				Set<String> tags = new HashSet<String>();
				for (int i = 0; i < numTags; i++) {
					tags.add(splitData[i + 2]);
				}
				boolean isHorizontal = splitData[0].equals("H");
				images[imgId] = new Image(isHorizontal, imgId, tags);
				(isHorizontal ? horizontalImages : verticleImages).add(images[imgId]);

			}

			this.images = images;
			return images;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	public int getSize() {
		return this.images.length;
	}

	public Image[] getImages() {
		return this.images;
	}
}