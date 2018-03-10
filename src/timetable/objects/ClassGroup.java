package timetable.objects;

import java.util.Arrays;

public class ClassGroup {
	String className;
	String classCode;
	int numStudents;
	String[] timetable;
	
	/**Requires String className, String classCode, int numStudents*/
	public ClassGroup(String className, String classCode, int numStudents, String[] timetable) {
		super();
		this.className = className;
		this.classCode = classCode;
		this.numStudents = numStudents;
		this.timetable = timetable;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public int getNumStudents() {
		return numStudents;
	}
	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}
	public String[] gettimetable() {
		return timetable;
	}
	public void settimetable(String[] timetable) {
		this.timetable = timetable;
	}
	@Override
	public String toString() {
		return "Class Group Details: \n\tClass Name = " + className + "\n\tClass Code = " + classCode + "\n\tAmount of Students = " + numStudents + "\n\tList of timetable = " + Arrays.toString(timetable);
	}
	
	
}
