package timetable.objects;

import java.util.Arrays;

public class Room {
	int numSeats;
	boolean isLabRoom;
	String location;
	String[] timetable;
	//String array to show timetable of room, filled with true/false to show if class is in slot.
	
	public Room(int numSeats, boolean isLabRoom, String location) {
		super();
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
	public String[] gettimetable() {
		return timetable;
	}
	public void settimetable(String[] timetable) {
		this.timetable = timetable;
	}
	

	@Override
	public String toString() {
		String room = "";
		if (isLabRoom = true) {
			room = "Lab";
		}
		else
		{
			room = "Lecture";
		}
		return "Room: \n\tLocation = " + location + "\n\tRoom Type = " + room + "\n\tAmount of seats = " + numSeats + "\n\ttimetable = " + Arrays.toString(timetable);
	}
	
	
}
