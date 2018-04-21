package timetable.objects;

import java.util.Arrays;

public class Room {
	int numSeats;
	boolean isLabRoom;
	String location;
	boolean[][] timetable;
	
	public Room(int numSeats, boolean isLabRoom, String location, boolean[][] timetable) {
		this.numSeats = numSeats;
		this.isLabRoom = isLabRoom;
		this.location = location;
		this.timetable = timetable;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public boolean isLabRoom() {
		return isLabRoom;
	}
	public void setLabRoom(boolean isLabRoom) {
		this.isLabRoom = isLabRoom;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean[][] getTimetable() {
		return timetable;
	}
	public void setTimetable(boolean[][] timetable) {
		this.timetable = timetable;
	}
	

	@Override
	public String toString() {
		return "Room: \n\tLocation = " + location + "\n\tRoom Type = " + isLabRoom + "\n\tAmount of seats = " + numSeats + "\n\tTimetable Availability(true = taken) = " + Arrays.toString(timetable);
	}
	
	
}
