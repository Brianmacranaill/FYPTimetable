package timetable.objects;

import java.util.Arrays;

public class Lecturer {
	String name;
	String[] timetable;
	
	/**Requires String name, String[] timetable*/
	public Lecturer(String name, String[] timetable) {
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
	public String[] gettimetable() {
		return timetable;
	}
	public void settimetable(String[] timetable) {
		this.timetable = timetable;
	}
	
	@Override
	public String toString() {
		return "Lecturer Details: \n\tName = " + name + "\n\ttimetable = " + Arrays.toString(timetable);
	}
}
