import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Image {

	private boolean horizontal;
	private int id;
	private Set tags;
	

	public Image(boolean horizontal, int id, Set tags) {
		this.horizontal = horizontal;
		this.id = id;
		this.tags = tags;
	}
	

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set getTags() {
		return tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
	}






}
