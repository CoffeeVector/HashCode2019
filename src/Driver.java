import java.util.ArrayList;
import java.util.Arrays;
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

	public static void main(String[] args) {
		ImageParser r = new ImageParser();
		r.parse("tests/a_example.txt");
		ArrayList<Image> images = new ArrayList<Image>();
		for (Image i : r.getImages()) {
			images.add(i);
		}
		Image[] output = new Image[images.size()];
		output[0] = images.get(0);
		images.remove(0);
		int cursor = 0;
		Image bestImage = null;
		Image bestImage2 = null;
		int bestScore = -1;
		while (images.size() > 2) {
			for (int i = 0; i < images.size(); i++) {
				for (int j = i + 1; j < images.size(); j++) {
					try {
						System.out.println(output[cursor].isHorizontal());
						System.out.println(images.get(i).isHorizontal());
						System.out.println(images.get(j).isHorizontal());
						int score = evaluate(output[cursor], images.get(i), images.get(j));
						System.out.println("Score: " + score);
						if (score > bestScore) {
							bestImage = images.get(i);
							bestImage2 = images.get(j);
							images.remove(i);
							images.remove(j - 1);// Because the array list shifted, and I know that j is after i
							score = bestScore;
						}
					} catch (Zhengception e) {
						System.out.println("BAD");
						// moving on...
					}
				}
			}
			if (bestImage == null || bestImage2 == null) {
				System.out.println("PANIC! THE BEST POSSIBLE IMAGES ARE NULL");
			}
			output[++cursor] = bestImage;
			output[++cursor] = bestImage2;
		}
		if(images.size() == 2) {
			output[output.length - 2] = images.get(0);
			images.remove(0);
			output[output.length - 1] = images.get(0);
		} else if (images.size() == 1) {
			output[output.length - 1] = images.get(0);
			images.remove(0);
		}
		System.out.println(Arrays.toString(output));
	}
}
