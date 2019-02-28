import java.util.HashSet;

public class Main {

	public static void main(String[] args) {

		ImageParser r = new ImageParser();
		r.parse("./tests/a_example.txt");
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
