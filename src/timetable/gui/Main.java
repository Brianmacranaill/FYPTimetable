package timetable.gui;

import timetable.objects.*;

import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	Button button;
	
	public static void main(String[] args) {
        //launch(args);
		int[] timeSlots = new int[9];
		int a = 0;
		int b = 9;
		while (a<9) {
			timeSlots[a] = b-8;
			a++;
			b++;
		}
		System.out.println("Available timeslots: " + Arrays.toString(timeSlots));
		
		String[] listOfRooms = new String[6];
		listOfRooms[0] = "IT1.1";
		listOfRooms[1] = "IT1.2";
		listOfRooms[2] = "IT1.3";
		listOfRooms[3] = "IT2.1";
		listOfRooms[4] = "IT2.2";
		listOfRooms[5] = "IT2.3";
		System.out.println("\nAvailable Rooms: " + Arrays.toString(listOfRooms) + "\n");
		
		Module module = new Module("TestCode", "TestName", "TestLecturer", true, "test");
				
		Boolean[] classGroupAvailability = new Boolean[9];
		for (int i=0; i<9; i++)
		{
			classGroupAvailability[i] = false;
		}		
		ClassGroup classGroup = new ClassGroup("Software Development", "SDH4", 35, classGroupAvailability);
		
		Boolean[] roomAvailability = new Boolean[9];
		for (int i=0; i<9; i++)
		{
			roomAvailability[i] = false;
		}
		Room room = new Room(47, true, "C127", roomAvailability);
		
		Boolean[] lecturerAvailability = new Boolean[9];
		for (int i=0; i<9; i++)
		{
			lecturerAvailability[i] = false;
		}
		Lecturer lecturer = new Lecturer("Nacho", lecturerAvailability);
		System.out.println(module.toString() + "\n");
		System.out.println(classGroup.toString() + "\n");
		System.out.println(room.toString() + "\n");
		System.out.println(lecturer.toString() + "\n");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		primaryStage.setTitle("Timetable Scheduler");
		button = new Button();
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		Scene scene = new Scene(layout, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
		//primaryStage.setFullScreen(true);
		
	}
}
