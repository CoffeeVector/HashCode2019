import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
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

	public int getId() {
		return id;
	}

	public Set getTags() {
		return tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
	}

	int hash = -1;

	@Override
	public int hashCode() {
		if (hash == -1) {
			hash = Objects.hash(id);
		}
		return hash;
	}

	@Override
	public String toString() {
		return "Image [horizontal=" + horizontal + ", id=" + id + ", tags=" + tags + ", hash=" + hash + "]";
	}

}