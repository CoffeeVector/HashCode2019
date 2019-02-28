import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Slide {

	private boolean isHorizontal;

	private Image img;
	private Image img2;
	private HashMap<Integer, Integer> transitionScoreCache; // todo

	public Slide(Image img) {
		transitionScoreCache = new HashMap<>();
		isHorizontal = true;
		this.img = img;
	}

	public Slide(Image img, Image img2) {
		transitionScoreCache = new HashMap<>();
		isHorizontal = false;
		this.img = img;
		this.img2 = img2;
	}

	public HashSet<String> getTags() {
		HashSet<String> output = new HashSet<String>();
		output.addAll(img.getTags());
		if (img2 != null) {
			output.addAll(img2.getTags());
		}
		return output;
	}

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

	public boolean isHorizontal() {
		return isHorizontal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(img, img2);
	}

	public Image getImg() {
		return img;
	}

	public Image getImg2() {
		return img2;
	}

}
