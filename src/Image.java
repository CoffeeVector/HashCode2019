import java.util.Objects;

public class Image {

	private boolean horizontal;
	private int id;
	private String tags;
	private int tagCount;

	public Image(boolean horizontal, int id, String tags, int tagCount) {
		this.horizontal = horizontal;
		this.id = id;
		this.tags = tags;
		this.tagCount = tagCount;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public int getId() {
		return id;
	}

	public String getTags() {
		return tags;
	}
	
	public int getTagCount() {
		return tagCount;
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