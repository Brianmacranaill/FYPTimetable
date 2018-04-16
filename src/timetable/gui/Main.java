package timetable.gui;

import timetable.objects.*;
import timetable.algorithms.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	static String timetableToCreateOption = "";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		BorderPane root = new BorderPane();
		Button runAlgorithmButton, uploadAlgorithmToDatabaseButton, exit;
		VBox leftVerticalBox, rightVerticalBox;
		HBox bottomBox;
		Label label1 = new Label("Left Box");
		Label label2 = new Label("Timetable shown here?");
		
		double screenSizeMultiplier = .5;//Change this value to change the amount of screen taken by the GUI
		
		primaryStage.setTitle("Timetable Scheduler");
		runAlgorithmButton = new Button();
		runAlgorithmButton.setText("Run Algorithm");
		runAlgorithmButton.setOnAction(e -> algorithmStart());//Runs the method that runs the algorithm
		
		uploadAlgorithmToDatabaseButton = new Button();
		uploadAlgorithmToDatabaseButton.setText("Make Timetable Live(NYI)");
		uploadAlgorithmToDatabaseButton.setOnAction(e -> uploadTimetableToDatabase());
		
		exit = new Button();
		exit.setText("Exit");
		exit.setOnAction(e -> System.exit(0));
		
		bottomBox = new HBox(5);
		bottomBox.getChildren().addAll(runAlgorithmButton, uploadAlgorithmToDatabaseButton, exit);
		bottomBox.setAlignment(Pos.BASELINE_CENTER);
		bottomBox.setPadding(new Insets(20, 20, 20, 20));
		bottomBox.setStyle("-fx-border-style: dashed;");
		Canvas canvas = new Canvas(400, 300);

		leftVerticalBox = new VBox();//Side with all the buttons, etc.
		rightVerticalBox = new VBox();//Side that shows the timetable
		
		ComboBox<String> timetableToCreate = new ComboBox<>();
		timetableToCreate.getItems().addAll(
				"SDH4",
				"SDH",
				"Computing Department"
				);
		timetableToCreate.setPromptText("What would you like to make a timetable for?");
		timetableToCreate.setOnAction(e -> timetableToCreateOption = timetableToCreate.getValue());
		
		leftVerticalBox.getChildren().addAll(label1, timetableToCreate, canvas);
		leftVerticalBox.setAlignment(Pos.BASELINE_LEFT);
        leftVerticalBox.setMinWidth(primaryScreenBounds.getWidth() * (screenSizeMultiplier * .5));//Sets leftVirticalBox to half the size of the window
        leftVerticalBox.setPadding(new Insets(100,100,100,100));
        leftVerticalBox.setStyle("-fx-border-style: dashed;");
        
		rightVerticalBox.getChildren().addAll(label2, canvas);
		rightVerticalBox.setAlignment(Pos.BASELINE_RIGHT);
		rightVerticalBox.setMinWidth(primaryScreenBounds.getWidth() * (screenSizeMultiplier * .5));//Sets rightVirticalBox to half the size of the window
		rightVerticalBox.setPadding(new Insets(100,100,100,100));
		rightVerticalBox.setStyle("-fx-border-style: dashed;");
		
		//"root" is the borderpane set up for positioning.
		root.setLeft(leftVerticalBox);
		root.setRight(rightVerticalBox);
		root.setBottom(bottomBox);
		root.setStyle("-fx-background-color: #D31145");

		StackPane layout = new StackPane();
		layout.getChildren().add(root);
		Scene scene = new Scene(layout);
		
		
		primaryStage.setScene(scene);
		setStageDimensions(primaryStage, primaryScreenBounds, screenSizeMultiplier, screenSizeMultiplier);
		primaryStage.show();
		
	}
	
	/**Sets stage dimensions. Default is full screen size. add in double value(max 1) to change size
	 * @param stage What stage to edit
	 * @param widthPercentage What percentage of the screen you want it to be. Eg, 0.5 for 50% of screen, 1 for max size
	 * @param heightPercentage What percentage of the screen you want it to be. Eg, 0.5 for 50% of screen, 1 for max size**/
	public static Stage setStageDimensions(Stage stage, Rectangle2D screenBounds , double widthPercentage, double heightPercentage) {
		stage.setX(screenBounds.getMinX());
		stage.setY(screenBounds.getMinY());
		stage.setWidth(screenBounds.getWidth() * widthPercentage);
		stage.setHeight(screenBounds.getHeight() * heightPercentage);
		return stage;
	}
	
	/**Method which starts the algorithm**/
	public static void algorithmStart() {
		ArrayList<Module> SDH4ModuleList = new ArrayList<Module>();
		Module AppDevLab = new Module("SOFT8020", "App Development Framework Lecture", "Larkin Cunningham", true, "SDH4");
		Module AppDevLecture = new Module("SOFT8020", "App Development Framework Lab", "Larkin Cunningham", false, "SDH4");
		Module SoftwareSecurityLab = new Module("COMP8050", "Security for Software Systems Lecture", "Sean McSweeney", true, "SDH4");
		Module SoftwareSecurityLecture = new Module("COMP8050", "Security for Software Systems Lab", "Sean McSweeney", false, "SDH4");
		Module MachineLearningLab = new Module("COMP8043", "Machine Learning Lecture", "Ted Scully", true, "SDH4");
		Module MachineLearningLecture = new Module("COMP8043", "Machine Learning Lab", "Ted Scully", false, "SDH4");
		Module NoSQLLab = new Module("COMP7037", "NoSQL Data Architectures Lecture", "Oonagh O'Brien", true, "SDH4");
		Module NoSQLLecture = new Module("COMP7037", "NoSQL Data Architectures Lab", "Oonagh O'Brien", false, "SDH4");
		Module EmbeddedSystemsLab = new Module("COMP8049", "Embedded Systems Tools & Models Lecture", "Paul Davern", true, "SDH4");
		Module EmbeddedSystemsLecture = new Module("COMP8049", "Embedded Systems Tools & Models Lab", "Paul Davern", false, "SDH4");
		SDH4ModuleList.add(AppDevLab);
		SDH4ModuleList.add(AppDevLecture);
		SDH4ModuleList.add(SoftwareSecurityLab);
		SDH4ModuleList.add(SoftwareSecurityLecture);
		SDH4ModuleList.add(MachineLearningLab);
		SDH4ModuleList.add(MachineLearningLecture);
		SDH4ModuleList.add(NoSQLLab);
		SDH4ModuleList.add(NoSQLLecture);
		SDH4ModuleList.add(EmbeddedSystemsLab);
		SDH4ModuleList.add(EmbeddedSystemsLecture);
//		System.out.println(SDH4ModuleList.get(0));
//		System.out.println(SDH4ModuleList.get(1));
//		System.out.println(SDH4ModuleList.get(9));
//		System.out.println(SDH4ModuleList.get(10));

		ArrayList<ClassGroup> classGroupList = new  ArrayList<ClassGroup>();
		ClassGroup classGroup1 = new ClassGroup("Software Development 4", "SDH4", 50, assignEmptyTimetable());
		classGroupList.add(classGroup1);
		
		ArrayList<Module> larkinModuleList = new ArrayList<Module>();
		larkinModuleList.add(AppDevLab);
		larkinModuleList.add(AppDevLecture);
		Lecturer lecturer1 = new Lecturer("Larkin Cunningham", larkinModuleList, assignEmptyTimetable());
		ArrayList<Module> seanModuleList = new ArrayList<Module>();
		seanModuleList.add(SoftwareSecurityLab);
		seanModuleList.add(SoftwareSecurityLecture);
		Lecturer lecturer2 = new Lecturer("Sean McSweeney", seanModuleList, assignEmptyTimetable());
		ArrayList<Module> tedModuleList = new ArrayList<Module>();
		tedModuleList.add(MachineLearningLab);
		tedModuleList.add(MachineLearningLecture);
		Lecturer lecturer3 = new Lecturer("Ted Scully", tedModuleList, assignEmptyTimetable());
		ArrayList<Module> oonaghModuleList = new ArrayList<Module>();
		oonaghModuleList.add(NoSQLLab);
		oonaghModuleList.add(NoSQLLecture);
		Lecturer lecturer4 = new Lecturer("Oonagh O'Brien", oonaghModuleList, assignEmptyTimetable());
		ArrayList<Module> paulModuleList = new ArrayList<Module>();
		paulModuleList.add(EmbeddedSystemsLab);
		paulModuleList.add(EmbeddedSystemsLecture);
		Lecturer lecturer5 = new Lecturer("Paul Davern", paulModuleList, assignEmptyTimetable());
		
		ArrayList<Lecturer> lecturerList = new ArrayList<Lecturer>();
		lecturerList.add(lecturer1);
		lecturerList.add(lecturer2);
		lecturerList.add(lecturer3);
		lecturerList.add(lecturer4);
		lecturerList.add(lecturer5);
		
		ArrayList<Room> listOfLectureRooms = new ArrayList<Room>();
		ArrayList<Room> listOfLabRooms = new ArrayList<Room>();
		ArrayList<Room> allRooms = new ArrayList<Room>();
		
		Room it11 = new Room(30, true, "IT1.1", assignEmptyTimetable());
		Room it12 = new Room(28, true, "IT1.2", assignEmptyTimetable());
		Room it13 = new Room(24, true, "IT1.3", assignEmptyTimetable());
		Room it1 = new Room(50, false, "IT1", assignEmptyTimetable());
		Room it2 = new Room(60, false, "IT2", assignEmptyTimetable());
		Room it3 = new Room(30, false, "IT3", assignEmptyTimetable());
		listOfLabRooms.add(it11);
		listOfLabRooms.add(it12);
		listOfLabRooms.add(it13);
		listOfLectureRooms.add(it1);
		listOfLectureRooms.add(it2);
		listOfLectureRooms.add(it3);
		allRooms.addAll(listOfLabRooms);
		allRooms.addAll(listOfLectureRooms);
		
		String timetable[][] = GreedyAlgorithm.greedyAlgorithmSolver(SDH4ModuleList, lecturerList, allRooms, classGroupList, listOfLabRooms, listOfLectureRooms);
		int dayCounter=0, timeslotCounter = 0;
		//System.out.print("9:00\t10:00\t11:00\t12:00\t13:00\t14:00\t15:00\t16:00\t17:00\n");
		while (dayCounter < 5)
		{
			while(timeslotCounter < 9)
			{
				System.out.print(timetable[dayCounter][timeslotCounter] + "\t");
				timeslotCounter++;
			}
			dayCounter++;
			if (timeslotCounter == 9)
			{
				timeslotCounter = 0;
				System.out.print("\n");
			}
		}
		//System.exit(0);
		System.out.println("\n");
	}
	
	/**When complete, will upload database to timetable**/
	public static void uploadTimetableToDatabase() {
		System.out.println("Pretending that the timetable is uploading to the database....");
		int i = 0;
		try {
			while(i <= 5000)
			{
				TimeUnit.MILLISECONDS.sleep(1);
				System.out.println("Pretend upload to database " + i);
				i++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem with pretend upload. Please try again.");
		}
		System.out.println("Pretend upload complete. Exit the application");
	}
	
	/**Fills roomTimetable with "false"**/
	public static boolean[][] assignEmptyTimetable()
	{
		boolean timetable[][] = new boolean[5][9];
		int a=0, b=0;
		while(a<5)
		{
			while(b<9)
			{
				timetable[a][b] = false;
				b++;
			}
			a++;
			if(b==9)
			{
				b = 0;
			}
		}
		return timetable;
	}
	
	
}
