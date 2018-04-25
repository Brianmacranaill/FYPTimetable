package timetable.objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Lecturer {
	String name;
	boolean[][] timetable;
	Module module;
	ArrayList<Module> lecturerModuleList = new ArrayList<Module>();
	
	public Lecturer(String name, boolean[][] timetable) {
		this.name = name;
		this.timetable = timetable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean[][] gettimetable() {
		return timetable;
	}
	public void settimetable(int a, int b, boolean timetable) {
		this.timetable[a][b] = timetable;
	}
	
	@Override
	public String toString() {
		return "Lecturer Details: \n\tName = " + name + "\n\tTimetable Availability(true = taken) = " + Arrays.toString(timetable);
	}
}
