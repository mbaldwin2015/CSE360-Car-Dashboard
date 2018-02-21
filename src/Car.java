/* TEAM 4
 * Class: Car
 * Description: This class stores all important car functionality information including fuel, current gear, current
 * speed, current trip distance, total odometer reading, location, date & time, and makes calculations to update
 * a few variables
 */

import java.text.*;
import java.util.*;

public class Car {
	// Declare needed variables and objects
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	private Date currentDate;
	private int speed, trip, odo, speakerVolume, micVolume, fuel;
	private double realTrip;
	private String location, gear;

	// Constructor instantiates the class
	public Car (int lastFuel, int lastOdo) {
		currentDate = Calendar.getInstance().getTime();
		speed = 0;
		trip = 0;
		realTrip = 0.0;
		speakerVolume = 50;
		micVolume = 25;
		gear = "P";
		location = "Phoenix";
		
		fuel = lastFuel;
		odo = lastOdo;
	}
	
	// updates date information to CurrentDate then formats it and stores it as a string in theDate
	// theDate is returned
	public String getDate () {
		currentDate = Calendar.getInstance().getTime();
		String theDate = dateFormat.format(currentDate);
		return theDate;
	}
	
	// changes location
	public void updateLocation() {
		int i = getMileage() % 100;
		if (i >= 0 && i < 25) location = "Phoenix";
		else if (i >= 25 && i < 50) location = "Tempe";
		else if (i >= 50 && i < 75) location = "Scottsdale";
		else location = "Glendale";
	}
	
	// returns location
	public String getLocation () {
		return location;
	}
	
	// This method changes the speed of the car.
	public int changeSpeed (int change) {
		if (speed == 0 && change == -1) // Speed can't go below 0 or above 100
			return 000;
		else if (speed == 100 && change == 1)
			return 100;
		else if (gear.equals("D") || gear.equals("R")) {  // if the car is in the correct gear
			speed += change*5;
			return speed;
		}
		else if (gear.equals("N") && change == -1) { // if the car is in neutral, only allow deceleration
			speed += change*5;
			return speed;
		}
		else return speed;
	}
	
	// Changes the gear based on conditions
	public String changeGear (int changeG) {
		if (gear.equals("R") && changeG == -1) // can't change gear below Reverse
			return "R";
		else if (gear.equals("N") && changeG == 1) // or above Neutral
			return "N";
		else if (gear.equals("R") && speed == 0) // Only allows switching to Park if the car is stopped
			gear = "P";
		else if (gear.equals("P")) {
			if (changeG == 1) gear = "D";
			else gear = "R";
		}
		else if (gear.equals("D")) {  // Allows switching between Drive and Neutral
			if (changeG == 1) gear = "N";
			else if (speed == 0) gear = "P"; // Only switches to Park if car is stopped
		}
		else if (gear.equals("N")) gear = "D";
		return gear;
	}
	
	// updates the cars mileage
	public int updateMileage() {
		realTrip += ((double)getSpeed() / 90); // Store the current distance as a double
		if ((int) Math.round(realTrip) > trip) { // if the car has increased a mile
			trip++;                              // increase trip, odo, and decrease fuel
			odo++;
			if (fuel > 0)
				fuel--;
			else fuel = 0;
		}
		return trip;
	}
	
	public int getMileage() {
		return trip;
	}
	
	// Returns speed
	public int getSpeed() {
		return speed;
	}
	
	// returns odometer
	public int getOdo() { 
		return odo;
	}
	
	// Returns fuel
	public int getFuel() {
		return fuel;
	}
	
	// changes fuel level
	public void updateFuel(int incFuel) {
		fuel = incFuel;
	}
	
	// changes speakers volume level
	public void setSpeakerVolume (int vol) {
		speakerVolume = vol;
	}
	
	// returns speakers volume
	public int getSpeakerVolume () {
		return speakerVolume;
	}
	
	//changes mic volume level
	public void setMicVolume(int volume){
		micVolume = volume;
	}
	
	//returns mic volume level
	public int getMicVolume(){
		return micVolume;
	}
	
	// returns gear
	public String getGear() {
		return gear;
	}
}