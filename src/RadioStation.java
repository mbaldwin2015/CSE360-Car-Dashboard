import java.io.Serializable;

public class RadioStation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String frequency;
	private String channel;
	private String city;
	private String licenseName;
	
	public RadioStation(String frequency,String channel, String city, String licenseName){
		this.frequency = frequency;
		this.channel = channel;
		this.city = city;
		this.licenseName = licenseName;
	}
	
	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the licenseName
	 */
	public String getLicenseName() {
		return licenseName;
	}
	/**
	 * @param licenseName the licenseName to set
	 */
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	/**
	 * @return radio string
	 */
	public String getRadioAsString() {
		String str = this.frequency + " " + this.channel + " " + this.licenseName;
		return str;
	}
	
}