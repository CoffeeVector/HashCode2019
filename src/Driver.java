import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Driver {
	private static int evaluate(Slide a, Slide b) {
		HashSet<String> aHS = a.getTags();
		HashSet<String> bHS = b.getTags();
		HashSet<String> intersection = (HashSet<String>) aHS.clone();
		intersection.retainAll(bHS);
		aHS.removeAll(intersection);
		bHS.removeAll(intersection);
		int sizeA = aHS.size();
		int sizeB = bHS.size();
		int sizeI = intersection.size();

		return Integer.min(Integer.min(sizeA, sizeB), sizeI);
	}

	private static int evaluate(Image a, Image b, Image c) throws Zhengception {
		if (a.isHorizontal() && b.isHorizontal() && c.isHorizontal()) {
			return evaluate(new Slide(a), new Slide(b)) + evaluate(new Slide(b), new Slide(c));
		} else if (a.isHorizontal() && !b.isHorizontal() && !c.isHorizontal()) {
			return evaluate(new Slide(a), new Slide(b, c));
		} else if (!a.isHorizontal() && !b.isHorizontal() && c.isHorizontal()) {
			return evaluate(new Slide(a, b), new Slide(c));
		} else {
			throw new Zhengception("You can't have a vertical image alone");
		}
	}

	public static void main(String[] args) throws Exception {
		ImageParser r = new ImageParser();
		r.parse("tests/c_memorable_moments.txt");
		String outputFile = "cSubmit.txt";
		ArrayList<Slide> slides = new ArrayList<Slide>();
		for (int i = 0; i < r.horizontalImages.size(); i++) {
			slides.add(new Slide(r.horizontalImages.get(i)));
		}
		for (int i = 0; i < r.verticleImages.size(); i += 2) {
			slides.add(new Slide(r.verticleImages.get(i), r.verticleImages.get(i + 1)));
		}

		HashMap<String, HashSet<Slide>> t2s = new HashMap<String, HashSet<Slide>>();
		for (Slide s : slides) {
			for (String t : (HashSet<String>) (s.getTags())) {
				if (t2s.get(t) == null) {
					t2s.put(t, new HashSet<Slide>());
				}
				t2s.get(t).add(s);
			}
		}

		ArrayList<Slide> output = new ArrayList<Slide>();
		output.add(slides.get(0));
		for (String tag : (HashSet<String>) output.get(0).getTags()) {
			// System.out.println(tag);
			t2s.get(tag).remove(output.get(0));
			// System.out.println(t2s.get(tag));
		}
		while (output.size() < slides.size()) {
			int bestScore = -1;
			Slide bestSlide = null;
			outer: for (String tag : (HashSet<String>) output.get(output.size() - 1).getTags()) {
				try {
					for (Slide i : t2s.get(tag)) {
						int score = evaluate(output.get(output.size() - 1), i);
						if (score > bestScore) {
							bestScore = score;
							bestSlide = i;
							if (bestScore == output.get(output.size() - 1).getTags().size() / 2) {
								break outer;
							}
						}
					}
				} catch (NullPointerException e) {
				}
			}
			if (bestSlide == null) {
				defSearch: for (HashSet<Slide> hs : t2s.values()) {
					for (Slide i : hs) {
						if (i != null) {
							bestSlide = i;
							break defSearch;
						}
					}
				}
			}
			output.add(bestSlide);
			if (output.size() % 1000 == 0) {
				System.out.println(output.size());
			}
			for (String tag : (HashSet<String>) bestSlide.getTags()) {
				t2s.get(tag).remove(bestSlide);
				if (t2s.get(tag).size() == 0) {
					t2s.remove(tag);
				}
			}
		}
		System.out.println("Writing to output");
		writeToOutput(output, outputFile);
	}

	public static void writeToOutput(Image[] out, String path) {
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
			String s = out.length + "\n";
			for (int i = 0; i < out.length; i++) {
				s += out[i].getId();
				if (out[i].isHorizontal()) {
					s += "\n";
				} else {
					s += " " + out[++i].getId() + "\n";
				}
			}

			writer.write(s.trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToOutput(Slide[] out, String path) {
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
			String s = out.length + "\n";
			for (Slide slide : out) {
				s += slide.getImg().getId();
				if (slide.isHorizontal()) {
					s += "\n";
				} else {
					s += " " + slide.getImg2().getId() + "\n";
				}
			}
			writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeToOutput(ArrayList<Slide> out, String path) {
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
			for (Slide slide : out) {
				s += slide.getImg().getId();
				if (slide.isHorizontal()) {
					s += "\n";
				} else {
					s += " " + slide.getImg2().getId() + "\n";
				}
			}
			writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}