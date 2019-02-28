import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Driver2 {
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
		r.parse("tests/b_lovely_landscapes.txt");
		String outputFile = "bSubmit.txt";
		HashMap<String, HashSet<Image>> t2iH = new HashMap<String, HashSet<Image>>();
		for (Image i : r.horizontalImages) {
			for (String t : (HashSet<String>) (i.getTags())) {
				if (t2iH.get(t) == null) {
					t2iH.put(t, new HashSet<Image>());
				}
				t2iH.get(t).add(i);
			}
		}

		ArrayList<Image> output = new ArrayList<Image>();
		output.add((Image) r.horizontalImages.get(0));
		for (String tag : (HashSet<String>) output.get(output.size() - 1).getTags()) {
			t2iH.get(tag).remove(output.get(output.size() - 1));
		}
		while (output.size() < r.horizontalImages.size()) {
			int bestScore = -1;
			Image bestImage = null;
			outer: for (String tag : (HashSet<String>) output.get(output.size() - 1).getTags()) {
				try {
					for (Image i : t2iH.get(tag)) {
						int score = evaluate(new Slide(output.get(output.size() - 1)), new Slide(i));
						if (score > bestScore) {
							bestScore = score;
							bestImage = i;
							if (bestScore == output.get(output.size() - 1).getTags().size() / 2) {
								break outer;
							}
						}
					}
				} catch (NullPointerException e) {
				}
			}
			if (bestImage == null) {
				defSearch: for (HashSet<Image> hs : t2iH.values()) {
					for (Image i : hs) {
						if (i != null) {
							bestImage = i;
							break defSearch;
						}
					}
				}
			}
			output.add(bestImage);
			System.out.println(output.size());
			for (String tag : (HashSet<String>) bestImage.getTags()) {
				t2iH.get(tag).remove(bestImage);
				if (t2iH.get(tag).size() == 0) {
					t2iH.remove(tag);
				}
			}
		}
		HashMap<String, HashSet<Slide>> t2iV = new HashMap<String, HashSet<Slide>>();
		for (int i = 0; i < r.verticleImages.size(); i += 2) {
			for (String t : (HashSet<String>) (r.verticleImages.get(i).getTags())) {
				if (t2iV.get(t) == null) {
					t2iV.put(t, new HashSet<Slide>());
				}
				t2iV.get(t).add(new Slide(r.verticleImages.get(i), r.verticleImages.get(i + 1)));
			}
		}
		System.out.println("Starting vertical");
		while (output.size() < r.getImages().length) {
			int bestScore = -1;
			Slide bestSlide = null;
			outer: for (String tag : (HashSet<String>) output.get(output.size() - 1).getTags()) {
				try {
					for (Slide i : t2iV.get(tag)) {
						int score = evaluate(new Slide(output.get(output.size() - 1)), i);
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
				defSearch: for (HashSet<Slide> hs : t2iV.values()) {
					for (Slide i : hs) {
						if (i != null) {
							bestSlide = i;
							break defSearch;
						}
					}
				}
			}
			output.add(bestSlide.getImg());
			output.add(bestSlide.getImg2());
			System.out.println(output.size());
			for (String tag : (HashSet<String>) bestSlide.getTags()) {
				try {
					t2iV.get(tag).remove(bestSlide);
					if (t2iV.get(tag).size() == 0) {
						t2iV.remove(tag);
					}
				} catch (NullPointerException e) {
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
			for (int i = 0; i < out.size(); i++) {
				s += out.get(i).getId();
				if (out.get(i).isHorizontal()) {
					s += "\n";
				} else {
					s += " " + out.get(++i).getId() + "\n";
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
}