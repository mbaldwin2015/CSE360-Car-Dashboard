/* TEAM 4
 * Class: Driver
 * Description: This class stores driver username and password, and will also store the users
 * phone contacts list and settings and the radio station list and settings
 */

public class Driver {
	// Initialize
	private String username, password;
	private int fuelUsed, fuelFilled, callTime, driveTime, maxSpeed, totalSpeed;
	private double avgSpeed;

	// Constructor stores username and password information
	public Driver (String uname, String pword) {
		username = uname;
		password = pword;
	}
	
	// validLogin method verifies that the username and password entered match
	public boolean validLogin (String uname, String pword) {
		if (uname.equals(username) && pword.equals(password))
			return true;
		else
			return false;
	}
	
	public void updateFuelUsed(int difference) {
		fuelUsed += difference;
	}
	
	public int getFuelUsed() {
		return fuelUsed;
	}
	
	public void fillCount() {
		fuelFilled++;
	}
	
	public int getFillCount() {
		return fuelFilled;
	}
	
	public void updateCallTime() {
		callTime++;
	}
	
	public int getCallTime() {
		return callTime;
	}
	
	public void updateDriveTime() {
		driveTime++;
	}
	
	public int getDriveTime() {
		return driveTime;
	}
	
	public void updateMaxSpeed(int currentSpeed) {
		if (currentSpeed > maxSpeed) maxSpeed = currentSpeed;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public void updateAvgSpeed(int currentSpeed) {
		totalSpeed += currentSpeed;
		avgSpeed = totalSpeed / driveTime;
	}
	
	public double getAvgSpeed() {
		return avgSpeed;
	}
}

