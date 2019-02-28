import java.util.HashSet;

public class TestSlide {
	public static void main(String[] args) {
		HashSet<String> t1 = new HashSet<String>();
		t1.add("hello");
		HashSet<String> t2 = new HashSet<String>();
		t2.add("world");
		Image a = new Image(true, 0, t1);
		Image b = new Image(true, 1, t2);
		Slide s = new Slide(a, b);
		System.out.println(a);
		System.out.println(b);
		System.out.println(s.getTags());
	}
}
