package favorite_dailies.db;

public class StickyHist {
	private final Long id;
	private final String stickyText;
	
	public StickyHist(Long id, String stickyText) {
		this.id = id;
		this.stickyText =stickyText;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getStickyText() {
		return stickyText;
	}
}
