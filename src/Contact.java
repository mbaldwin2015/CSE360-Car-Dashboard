import java.io.Serializable;

public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private boolean isFavorite;
	
	public Contact(String name, String phoneNumber, boolean isFavorite){
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isFavorite = isFavorite;
	}
	
	public String getName() { 
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	public String getContactAsString(){
		String contact = this.name + " " + this.phoneNumber;
		return contact;
	}
}