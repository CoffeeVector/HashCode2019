import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Slide {

	private boolean isHorizontal;
	
	private Image img;
	private Image img2;
	private HashMap<Integer, Integer> transitionScoreCache; //todo

	
	
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
		output.addAll(img2.getTags());
		return output;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}
	
	
	
	
	
}
