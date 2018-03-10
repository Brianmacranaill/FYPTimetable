package timetable.objects;

import java.util.Arrays;

public class Lecturer {
	String name;
	Boolean[] timetable;
	
	public Lecturer(String name, Boolean[] timetable) {
		super();
		this.name = name;
		this.timetable = timetable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean[] gettimetable() {
		return timetable;
	}
	public void settimetable(Boolean[] timetable) {
		this.timetable = timetable;
	}
	
	@Override
	public String toString() {
		return "Lecturer Details: \n\tName = " + name + "\n\tTimetable Availability(true = taken) = " + Arrays.toString(timetable);
	}
}
