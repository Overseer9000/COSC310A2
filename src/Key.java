//	Stores some data abouts Keys
//		Used to easily pass around information

public class Key {

	private String primary;
	private String secondary;
	
	
	public Key(String primary, String secondary) {
		setPrimary(primary);
		setSecondary(secondary);
	}

	
	
	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public String getSecondary() {
		return secondary;
	}

	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	
	
	public String toString() {
		return primary + "-" + secondary;
	}
}
