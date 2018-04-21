package timetable.algorithms;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import timetable.objects.ClassGroup;
import timetable.objects.Lecturer;
import timetable.objects.Module;
import timetable.objects.Room;

public class GreedyAlgorithm {
	public static String[][] greedyAlgorithmSolver(ArrayList<Module> moduleList, ArrayList<Lecturer> lecturerList, ArrayList<Room> roomList, ArrayList<ClassGroup> classGroupList, ArrayList<Room> listOfLabRooms, ArrayList<Room> listOfLectureRooms){
		int n = 9;//Amount of hours in each day.
		int days = 5;//5 days of the week for classes
		int timeSlot = 0;
		int[] timeSlots = new int[n];
		int i = 0;
		while (timeSlot < n)
		{
			timeSlots[i] = timeSlot;
			i++;
			timeSlot++;
		}
		String[][] timetable = new String[days][timeSlot];
		int a = 0, b = 0;
		while(a<days) {//Fills each time slot with "empty"
			while(b<timeSlot) {
				timetable[a][b] = "empty";
				b++;
			}
			b=0;
			a++;
		}
		a=0;
		b=0;
		Stack<Module> moduleListStack = new Stack<Module>();//Timetables added into stack, simple to manage
		moduleListStack.addAll(moduleList);
		//String[][] createdTimetable = basicTimetableEntry(days, timeSlot, timetable, moduleListStack);
		String[][] createdTimetable = randomTimetableEntry(days, timeSlot, timetable, moduleListStack, listOfLectureRooms, listOfLabRooms, classGroupList, lecturerList);
		//System.out.println(createdTimetable[0][8]);//[1][2] = empty(without moving around)
		return createdTimetable;
	}
	
	public static String[][] randomTimetableEntry(int days, int timeSlot, String[][] timetable, Stack<Module> moduleList, ArrayList<Room> listOfLectureRooms, ArrayList<Room> listOfLabRooms, ArrayList<ClassGroup> classGroupList, ArrayList<Lecturer> lecturerList){//Creates timetable with modules entered at random positions
		Module temporaryModule = moduleList.get(0);
		Random ran = new Random();
		int a=ran.nextInt(days);
		int b=ran.nextInt(timeSlot);
		int classGroupCounter = 0;
		int roomCounter = 0;
		
		while(!moduleList.isEmpty())
		{
			a = ran.nextInt(days);
			b = ran.nextInt(timeSlot);
			
			if(timetable[a][b] == "empty")
			{
				if(temporaryModule.getLectureOrLab() == true)//Lecture, 1 hour timeslot
				{
					timetable[a][b] = temporaryModule.getModuleName();
				}
				else if(temporaryModule.getLectureOrLab() == false)//Lab, 2 hour.
				{
					while(timetable[a][b] == "empty") {
						if (b != 8 && timetable[a][b+1] == "empty") //If timeslot+1 is equal to final timeslot of the day and is empty, lab can be allocated here
						{//b can't be last time slot, otherwise second part of the lab is outside of time range
							timetable[a][b] = temporaryModule.getModuleName();
							timetable[a][b+1] = temporaryModule.getModuleName();
						}
						else//If none of the above are possible, generate a new day + timeslot
						{
							a = ran.nextInt(days);
							b = ran.nextInt(timeSlot);
						}
					}
				}
				temporaryModule = moduleList.pop();
				assignRoom(a, b, temporaryModule, listOfLectureRooms, listOfLabRooms, classGroupList, lecturerList, timetable);//Room allocation. Sometimes doesn't give a timeslot a room. Fix this
				
			}
		}
		return timetable;
	}
	
	public static String[][] basicTimetableEntry(int days, int timeSlot, String[][] timetable, Stack<Module> moduleList) {//Enters modules into timetable one directly after another
		Module temporaryModule = moduleList.pop();
		int a=0;
		int b=0;
		//Below loop populates timetable(Puts them in one after another)
		while(a<days) {
			if (moduleList.isEmpty())
			{
				break;
			}
			while(b<timeSlot) {
				timetable[a][b] = temporaryModule.getModuleName();
				if (moduleList.isEmpty())
				{
					break;
				}
				temporaryModule = moduleList.pop();
				b++;
			}
			b=0;
			a++;
		}
		return timetable;
	}
	
	public static String[][] assignRoom(int a, int b, Module module, ArrayList<Room> lectureRooms, ArrayList<Room> labRooms, ArrayList<ClassGroup> classGroupList, ArrayList<Lecturer> lecturerList, String[][] timetable)
	{	//Check if module is lab or lecture
		//Check if selected timeslot is free for class group, room and lecturer
		//Check if the amount of seats fits the classgroup
		int numLectureRooms = lectureRooms.size();
		int numLabRooms = labRooms.size();
		Random ran = new Random();
		int randomNumber = 0;
		int classGroupListCounter = 0;
		int lecturerListCounter = 0;
		String temporaryLecturerName = "";
		boolean tempRoomTimetable[][] = new boolean[5][9];
		boolean tempLecturerTimetable[][] = new boolean[5][9];
		boolean tempClassGroupTimetable[][] = new boolean[5][9];
		while(!timetable[a][b].contains("|") && !timetable[a][b].contains("\\"))//"|" checks that a room is assigned, "\\" checks if the lecturer name is assigned
		{
			if(module.getLectureOrLab() == false)//Lecture
			{
				
				randomNumber = ran.nextInt(numLectureRooms);
				if(lectureRooms.get(randomNumber).getNumSeats() <= classGroupList.get(classGroupListCounter).getNumStudents())//Compares number of seats to number of students in class group 0 here is for SDH4(Before adding multiple class groups)
				{
					//Assign lecture room to time slot for class group. Change class timetable from boolean to string, then store module there?
					while(classGroupList.get(classGroupListCounter).gettimetable()[a][b] == false)//Checks if the class group's timetable is free
					{
						if(lectureRooms.get(randomNumber).getTimetable()[a][b] == false)//Checks if room timetable is free
						{
							tempRoomTimetable = lectureRooms.get(randomNumber).getTimetable();
							tempRoomTimetable[a][b] = true;
							lectureRooms.get(randomNumber).setTimetable(tempRoomTimetable);//This and above 3 lines assigns class room's timetable to a temporary timetable, then sets the room's timetable as temp timetable's data
							
							tempClassGroupTimetable = classGroupList.get(classGroupListCounter).gettimetable();
							tempClassGroupTimetable[a][b] = true;
							classGroupList.get(classGroupListCounter).settimetable(tempClassGroupTimetable);
							lecturerListCounter=0;
							while(lecturerListCounter < lecturerList.size())//Finding the correct lecturer for the module
							{
								if(module.getLecturerName() == lecturerList.get(lecturerListCounter).getName()) {
									temporaryLecturerName = lecturerList.get(lecturerListCounter).getName();
									break;
								}
								lecturerListCounter+=1;
							}
							if(lecturerList.get(lecturerListCounter).gettimetable()[a][b] == false) {//Lecturer Timetable Checking
								tempLecturerTimetable = lecturerList.get(lecturerListCounter).gettimetable();
								tempLecturerTimetable[a][b] = true;
								lecturerList.get(lecturerListCounter).settimetable(tempLecturerTimetable);
								timetable[a][b]+= "|" +  lectureRooms.get(randomNumber).getLocation() + "\\" + temporaryLecturerName;
							}
						}
						else
						{
							randomNumber = ran.nextInt(numLectureRooms);
						}
					}
				}
			}
			else//Lab
			{
				randomNumber = ran.nextInt(numLabRooms);
				if(labRooms.get(randomNumber).getNumSeats() <= classGroupList.get(classGroupListCounter).getNumStudents())
				{
					//Assign lecture room to time slot for class group. Change class timetable from boolean to string, then store module there?
					while(classGroupList.get(classGroupListCounter).gettimetable()[a][b] == false)//Currently can't get into this loop. Always true
					{
						if(labRooms.get(randomNumber).getTimetable()[a][b] == false)
						{
							tempRoomTimetable = labRooms.get(randomNumber).getTimetable();
							tempRoomTimetable[a][b] = true;
							labRooms.get(randomNumber).setTimetable(tempRoomTimetable);//This and above 3 lines assigns class room's timetable to a temporary timetable, then sets the room's timetable as temp timetable's data
							
							tempClassGroupTimetable = classGroupList.get(classGroupListCounter).gettimetable();
							tempClassGroupTimetable[a][b] = true;
							classGroupList.get(classGroupListCounter).settimetable(tempClassGroupTimetable);
							lecturerListCounter=0;
							while(lecturerListCounter < lecturerList.size())//Finding the correct lecturer for the module
							{
								if(module.getLecturerName() == lecturerList.get(lecturerListCounter).getName()) {
									temporaryLecturerName = lecturerList.get(lecturerListCounter).getName();
									break;
								}
								lecturerListCounter+=1;
							}
							if(lecturerList.get(lecturerListCounter).gettimetable()[a][b] == false) {//Lecturer Timetable Checking
								tempLecturerTimetable = lecturerList.get(lecturerListCounter).gettimetable();
								tempLecturerTimetable[a][b] = true;
								lecturerList.get(lecturerListCounter).settimetable(tempLecturerTimetable);
								timetable[a][b] += "|" +  labRooms.get(randomNumber).getLocation() + "\\" + temporaryLecturerName;
								timetable[a][b+1] += "|" +  labRooms.get(randomNumber).getLocation() + "\\" + temporaryLecturerName;
							}
						}
						else
						{
							randomNumber = ran.nextInt(numLabRooms);
						}
					}
				}
			}
			
		}
		return timetable;
	}
}
