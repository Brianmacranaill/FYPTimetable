package timetable.objects;

public class Module {
	String moduleCode;
	String moduleName;
	Lecturer lecturer;
	Boolean lectureOrLab;
	String className;
	

	public Module(String moduleCode, String moduleName, Boolean lectureOrLab, String className) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.lectureOrLab = lectureOrLab;
		this.className = className;
	}

	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public Boolean getLectureOrLab() {
		return lectureOrLab;
	}
	public void setLectureOrLab(Boolean lectureOrLab) {
		this.lectureOrLab = lectureOrLab;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return "Module Details: \n\tModule Code = " + moduleCode + "\n\tModule Name = " + moduleName + "\n\tLecturer Name = " + lecturer + "\n\tClass Type = " + lectureOrLab + "\n\tClass Name = " + className + "\n";
	}
	
}
