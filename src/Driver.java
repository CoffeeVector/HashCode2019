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

	private static int evaluate(Image a, Image b, Image c) throws Exception {
		if (a.isHorizontal() && b.isHorizontal() && c.isHorizontal()) {
			return evaluate(new Slide(a), new Slide(b)) + evaluate(new Slide(b), new Slide(c));
		} else if (a.isHorizontal() && !b.isHorizontal() && !c.isHorizontal()) {
			return evaluate(new Slide(a), new Slide(b, c));
		} else if (!a.isHorizontal() && !b.isHorizontal() && c.isHorizontal()) {
			return evaluate(new Slide(a, b), new Slide(c));
		} else {
			throw new Exception("You can't have a vertical image alone");
		}
	}

	public static void main(String[] args) {
		FileParser r = new FileParser();
		Image[] output = new Image[r.getSize()];
		output[0] = r.getImages()[0];
		for (int i = 1; i < r.getSize(); i++) {
			Image bestImage = r.getImages()[i];
			int bestScore = evaluate(output[i - 1], bestImage);
			for (int j = i + 1; j < r.getSize(); j++) {
				int score = evaluate(output[i - 1], r.getImages()[j]);
				if (score > bestScore) {
					bestScore = score;
					bestImage = r.getImages()[j];
				}
			}
			output[i] = bestImage;
		}
	}
}