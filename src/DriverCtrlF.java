import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DriverCtrlF {

	public static void main(String[] args) throws Exception {
		String test = "b";
		Scanner s = new Scanner(new File("tests/" + test + ".txt"));
		ArrayList<Image> images = new ArrayList<Image>();
		String fileOutput = test + "Submit.txt";
		s.nextLine();
		int id = -1;
		while (s.hasNextLine()) {
			id++;
			String line = s.nextLine();
			Scanner scanLine = new Scanner(line);
			String orientation = scanLine.next();
			int tagCount = Integer.parseInt(scanLine.next());
			String[] tags = new String[tagCount];
			for (int i = 0; i < tagCount; i++) {
				tags[i] = scanLine.next();
			}
			Arrays.sort(tags);
			String out = "";
			for (String tag : tags) {
				out += tag += " ";
			}
			out.substring(0, out.length() - 2);// remove the last space
			// System.out.println(out);
			images.add(new Image(orientation.contentEquals("H"), id, out, tagCount));
		}
		System.out.println("Tags Sorted");
		ArrayList<Image> out = new ArrayList<Image>();
		out.add(images.get(images.size() - 1));
		images.remove(images.size() - 1);
		while (images.size() != 0) {
			int targetSize = out.get(out.size() - 1).getTagCount() / 2;
			targetSize = (int) (0.2 * targetSize);// Worse, but faster
			// System.out.println("targetSize: " + targetSize);
			boolean foundAnything = false;
			for (int size = targetSize; size >= 0; size--) {
				for (int offset = 0; offset < out.get(out.size() - 1).getTagCount() - size; offset++) {
					String firstHalf = "";
					{
						Scanner firstHalfScanner = new Scanner(out.get(out.size() - 1).getTags());
						for (int i = 0; i < offset; i++) {
							firstHalfScanner.next();// curn the first offset amount
						}
						for (int i = 0; i < size; i++) {
							firstHalf += firstHalfScanner.next() + " ";
						}
						if (firstHalf.length() != 0) {
							firstHalf = firstHalf.substring(0, firstHalf.length() - 2);// remove last space
						}
					}
					// System.out.println("firstHalf: " + firstHalf);
					boolean found = false;
					for (int i = 0; i < images.size(); i++) {
						if (images.get(i).getTags().indexOf(firstHalf) != -1) {
							out.add(images.get(i));
							if (out.size() % 100 == 0) {
								System.out.println("out.size(): " + out.size());
							}
							images.remove(i);
							found = true;
							break;
						}
					}
					if (found) {
						foundAnything = true;
						break;
					}
				}
			}
			if (!foundAnything) {
				out.add(images.get(images.size() - 1));
				System.out.println("out.size(): " + out.size() + " went to default");
			}
		}
		writeToOutput(out, fileOutput);

	}

	public static void writeToOutput(ArrayList<Image> out, String path) {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("Could not find file at " + f.getAbsolutePath() + ", creating it");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (FileWriter writer = new FileWriter(f)) {
			String s = out.size() + "\n";
			for (Image i : out) {
				s += i.getId();
				s += "\n";
			}
			writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}