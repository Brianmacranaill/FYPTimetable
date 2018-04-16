package timetable.objects;

import java.util.ArrayList;
import java.util.Arrays;

public class Lecturer {
	String name;
	boolean[][] timetable;
	Module module;
	ArrayList<Module> lecturerModuleList = new ArrayList<Module>();
	
	public Lecturer(String name, ArrayList<Module> lecturerModuleList, boolean[][] timetable) {
		this.name = name;
		this.timetable = timetable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Module> getModuleList(){
		return lecturerModuleList;
	}
	public void setModuleList(ArrayList<Module> lecturerModuleList) {
		this.lecturerModuleList = lecturerModuleList;
	}
	public boolean[][] gettimetable() {
		return timetable;
	}
	public void settimetable(boolean[][] timetable) {
		this.timetable = timetable;
	}
	
	@Override
	public String toString() {
		return "Lecturer Details: \n\tName = " + name + "\n\tTimetable Availability(true = taken) = " + Arrays.toString(timetable);
	}
}
