package timetable.gui;

import timetable.objects.*;
import timetable.algorithms.*;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	static String timetableToCreateOption = "";
	private static final String DATABASE_URL = "https://timetable-5673f.firebaseio.com";
	private static FirebaseDatabase firebaseDatabase;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void firebaseSetup() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("C:/Users/Brian/Dropbox/timetableDB/timetable-5673f-firebase-adminsdk-ujdb6-fb9f4122b5.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(DATABASE_URL)
				.build();

		FirebaseApp.initializeApp(options);
		firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference ref = firebaseDatabase.getReference("classes");
		
	}
	
	public static String[] splitStringForTimetable(String timeslotData) {
		String[] splitData = new String[4];
		String[] data = timeslotData.split("\\|");
		splitData[0] = data[0];
		data = data[1].split("\\\\");
		splitData[1] = data[0];
		data = data[1].split("\\/");
		splitData[2] = data[0];
		splitData[3] = data[1];		
		return splitData;
	}
	
	public static void writeToFirebase(String[][] timetable) throws IOException//Make this method work with all time slots
	{
		int dayCounter = 0;//Max = 5
		int timeSlotCounter = 0;//Max = 9
		
//		String classes = "classes";
//		String classGroup = "SDH4";
//		String day = "Monday";
//		String timeSlot = "9";
//		String classAtTime = "module|room\\lecturer/classGroup";
		String[] splitStorage = new String[4];
		dayCounter = 0;
		timeSlotCounter = 0;
		DatabaseReference ref = firebaseDatabase.getReference("classes");
		DatabaseReference lecturerRef = firebaseDatabase.getReference("lecturers");
		while (dayCounter < 5)//Use this loop to split stuff and add to the database
		{
			while(timeSlotCounter < 9)
			{
				if(timetable[dayCounter][timeSlotCounter] != "empty") {
					splitStorage = splitStringForTimetable(timetable[dayCounter][timeSlotCounter]);
					//Upload to database here
					ref.child(splitStorage[3]).child(String.valueOf(dayCounter)).child(String.valueOf(timeSlotCounter)).child(splitStorage[0]).child(splitStorage[2]).setValueAsync(splitStorage[1]);
					lecturerRef.child(splitStorage[2]).child(String.valueOf(dayCounter)).child(String.valueOf(timeSlotCounter)).child(splitStorage[0]).child(splitStorage[3]).setValueAsync(splitStorage[1]);
				}
				//System.out.print(timetable[dayCounter][timeSlotCounter] + "\t");
				timeSlotCounter++;
			}
			dayCounter++;
			if (timeSlotCounter == 9)
			{
				timeSlotCounter = 0;
				//System.out.print("\n");
			}
		}

	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		firebaseSetup();
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		BorderPane root = new BorderPane();
		Button runAlgorithmButton, uploadAlgorithmToDatabaseButton, exit;
		VBox leftVerticalBox, rightVerticalBox;
		HBox bottomBox;
		
		double screenSizeMultiplier = .5;//Change this value to change the amount of screen taken by the GUI
		primaryStage.setTitle("Timetable Scheduler");
		runAlgorithmButton = new Button();
		runAlgorithmButton.setText("Run Algorithm");
		runAlgorithmButton.setOnAction(e -> algorithmStart());//Runs the method that runs the algorithm
		
		uploadAlgorithmToDatabaseButton = new Button();
		uploadAlgorithmToDatabaseButton.setText("Make Timetable Live");
		//uploadAlgorithmToDatabaseButton.setOnAction(e -> uploadTimetableToDatabase());
		
		exit = new Button();
		exit.setText("Exit");
		exit.setOnAction(e -> System.exit(0));
		
		bottomBox = new HBox(5);
		bottomBox.getChildren().addAll(runAlgorithmButton, uploadAlgorithmToDatabaseButton, exit);
		bottomBox.setAlignment(Pos.BASELINE_CENTER);
		bottomBox.setPadding(new Insets(20, 20, 20, 20));
		Canvas canvas = new Canvas(400, 300);

		leftVerticalBox = new VBox();//Side with all the buttons, etc.
		rightVerticalBox = new VBox();//Side that shows the timetable
		
		ComboBox<String> timetableToCreate = new ComboBox<>();
		timetableToCreate.getItems().addAll(
				"SDH4",
				"SDH",
				"Computing Department"
				);
		timetableToCreate.setPromptText("What groups you like to make a timetable for?");
		timetableToCreate.setOnAction(e -> timetableToCreateOption = timetableToCreate.getValue());
		
		FileInputStream inputstream = new FileInputStream("C:\\Users\\Brian\\Dropbox\\CitRGB.png");
		Image image = new Image(inputstream);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);

		leftVerticalBox.getChildren().addAll(timetableToCreate, canvas);
		leftVerticalBox.setAlignment(Pos.BASELINE_LEFT);
        leftVerticalBox.setMinWidth(primaryScreenBounds.getWidth() * (screenSizeMultiplier * .3));//Sets leftVerticalBox to half the size of the window
        leftVerticalBox.setMinHeight(0);
        leftVerticalBox.setPadding(new Insets(100,100,100,100));
        
		rightVerticalBox.getChildren().addAll(imageView, canvas);
		rightVerticalBox.setAlignment(Pos.BASELINE_RIGHT);
		rightVerticalBox.setMinWidth(primaryScreenBounds.getWidth() * (screenSizeMultiplier * .3));//Sets rightVerticalBox to half the size of the window
		rightVerticalBox.setMinHeight(0);
		rightVerticalBox.setPadding(new Insets(100,100,100,100));
		
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
		stage.setResizable(false);
		return stage;
	}
	
	/**Method which starts the algorithm**/
	public static void algorithmStart() {
		
		Lecturer lecturer1 = new Lecturer("Larkin Cunningham", assignEmptyTimetable());
		Lecturer lecturer2 = new Lecturer("Sean McSweeney", assignEmptyTimetable());
		Lecturer lecturer3 = new Lecturer("Ted Scully", assignEmptyTimetable());
		Lecturer lecturer4 = new Lecturer("Oonagh O'Brien", assignEmptyTimetable());
		Lecturer lecturer5 = new Lecturer("Paul Davern", assignEmptyTimetable());
		Lecturer lecturer6 = new Lecturer("Denis Long", assignEmptyTimetable());
		Lecturer lecturer7 = new Lecturer("Karl Grabe", assignEmptyTimetable());
		Lecturer lecturer8 = new Lecturer("Ignacio Castineiras", assignEmptyTimetable());
		Lecturer lecturer9 = new Lecturer("Paul Rothwell", assignEmptyTimetable());
		Lecturer lecturer10 = new Lecturer("Mary Davin", assignEmptyTimetable());
		Lecturer lecturer11 = new Lecturer("Michael Brennan", assignEmptyTimetable());
		Lecturer lecturer12 = new Lecturer("Cliona Mc Guane", assignEmptyTimetable());
		Lecturer lecturer13 = new Lecturer("Catherine Frehill", assignEmptyTimetable());
		Lecturer lecturer14 = new Lecturer("Irene Foley", assignEmptyTimetable());
		Lecturer lecturer15 = new Lecturer("John Creagh", assignEmptyTimetable());
		Lecturer lecturer16 = new Lecturer("Marie Nicholson", assignEmptyTimetable());
		
		ArrayList<Module> SDH4ModuleList = new ArrayList<Module>();
		SDH4ModuleList.add(new Module("SOFT8020", "App Development Framework Lab", lecturer1, true, "SDH4"));
		SDH4ModuleList.add(new Module("SOFT8020", "App Development Framework Lecture", lecturer1, false, "SDH4"));
		SDH4ModuleList.add(new Module("SOFT8020", "App Development Framework Lecture", lecturer1, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8050", "Security for Software Systems Lab", lecturer2, true, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8050", "Security for Software Systems Lecture", lecturer2, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8050", "Security for Software Systems Lecture", lecturer2, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8043", "Machine Learning Lab", lecturer3, true, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8043", "Machine Learning Lecture", lecturer3, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8043", "Machine Learning Lecture", lecturer3, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lab", lecturer4, true, "SDH4"));
		SDH4ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lecture", lecturer4, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lecture", lecturer4, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8049", "Embedded Systems Tools & Models Lab", lecturer5, true, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8049", "Embedded Systems Tools & Models Lecture", lecturer5, false, "SDH4"));
		SDH4ModuleList.add(new Module("COMP8049", "Embedded Systems Tools & Models Lecture", lecturer5, false, "SDH4"));

		ArrayList<Module> SDH3ModuleList = new ArrayList<Module>();
		SDH3ModuleList.add(new Module("SOFT8023", "Distributed Systems Programming Lab", lecturer6, true, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT8023", "Distributed Systems Programming Lecture", lecturer6, false, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT8023", "Distributed Systems Programming Lecture", lecturer6, false, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7035", "Programming Mobile Devices Lab", lecturer7, true, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7035", "Programming Mobile Devices Lecture", lecturer7, false, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7035", "Programming Mobile Devices Lecture", lecturer7, false, "SDH3"));
		SDH3ModuleList.add(new Module("COMP7039", "Programming Mobile Devices Lab", lecturer1, true, "SDH3"));
		SDH3ModuleList.add(new Module("COMP7039", "Programming Mobile Devices Lecture", lecturer1, false, "SDH3"));
		SDH3ModuleList.add(new Module("COMP7039", "Programming Mobile Devices Lecture", lecturer1, false, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7019", "C Programming Lab", lecturer8, true, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7019", "C Programming Lecture", lecturer8, false, "SDH3"));
		SDH3ModuleList.add(new Module("SOFT7019", "C Programming Lecture", lecturer8, false, "SDH3"));
		SDH3ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lab", lecturer4, true, "SDH4"));
		SDH3ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lecture", lecturer4, false, "SDH4"));
		SDH3ModuleList.add(new Module("COMP7037", "NoSQL Data Architectures Lecture", lecturer4, false, "SDH4"));
		
		ArrayList<Module> SDH2ModuleList = new ArrayList<Module>();
		SDH2ModuleList.add(new Module("SOFT7004", "Object Oriented Principles Lab", lecturer6, true, "SDH2"));
		SDH2ModuleList.add(new Module("SOFT7004", "Object Oriented Principles Lecture", lecturer6, false, "SDH2"));
		SDH2ModuleList.add(new Module("SOFT7004", "Object Oriented Principles Lecture", lecturer6, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6041", "Introduction to Databases Lab", lecturer4, true, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6041", "Introduction to Databases Lecture", lecturer4, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6041", "Introduction to Databases Lecture", lecturer4, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6042", "Operating Systems In Practice Lab", lecturer9, true, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6042", "Operating Systems In Practice Lecture", lecturer9, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP6042", "Operating Systems In Practice Lecture", lecturer9, false, "SDH2"));
		SDH2ModuleList.add(new Module("SOFT7007", "Requirements Engineering Lab", lecturer10, true, "SDH2"));
		SDH2ModuleList.add(new Module("SOFT7007", "Requirements Engineering Lecture", lecturer10, false, "SDH2"));
		SDH2ModuleList.add(new Module("SOFT7007", "Requirements Engineering Lecture", lecturer10, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP7035", "Linear Data Struct & Alg Lab", lecturer8, true, "SDH2"));
		SDH2ModuleList.add(new Module("COMP7035", "Linear Data Struct & Alg Lecture", lecturer8, false, "SDH2"));
		SDH2ModuleList.add(new Module("COMP7035", "Linear Data Struct & Alg Lecture", lecturer8, false, "SDH2"));
		SDH2ModuleList.add(new Module("MATH6004", "Discrete Maths Lab", lecturer11, true, "SDH2"));
		SDH2ModuleList.add(new Module("MATH6004", "Discrete Maths Lecture", lecturer11, false, "SDH2"));
		SDH2ModuleList.add(new Module("MATH6004", "Discrete Maths Lecture", lecturer11, false, "SDH2"));
		
		ArrayList<Module> SDH1ModuleList = new ArrayList<Module>();
		SDH1ModuleList.add(new Module("SOFT6018", "Programming Fundamentals Lab", lecturer12, true, "SDH1"));
		SDH1ModuleList.add(new Module("SOFT6018", "Programming Fundamentals Lecture", lecturer12, false, "SDH1"));
		SDH1ModuleList.add(new Module("SOFT6018", "Programming Fundamentals Lecture", lecturer12, false, "SDH1"));
		SDH1ModuleList.add(new Module("CMOD6001", "Creativity Innovation & Teamwork Lab", lecturer13, true, "SDH1"));
		SDH1ModuleList.add(new Module("CMOD6001", "Creativity Innovation & Teamwork Lecture", lecturer13, false, "SDH1"));
		SDH1ModuleList.add(new Module("CMOD6001", "Creativity Innovation & Teamwork Lecture", lecturer13, false, "SDH1"));
		SDH1ModuleList.add(new Module("SOFT6007", "Web Development Fundamentals Lab", lecturer14, true, "SDH1"));
		SDH1ModuleList.add(new Module("SOFT6007", "Web Development Fundamentals Lecture", lecturer14, false, "SDH1"));
		SDH1ModuleList.add(new Module("SOFT6007", "Web Development Fundamentals Lecture", lecturer14, false, "SDH1"));
		SDH1ModuleList.add(new Module("COMH6002", "Computer Architecture Lab", lecturer15, true, "SDH1"));
		SDH1ModuleList.add(new Module("COMH6002", "Computer Architecture Lecture", lecturer15, false, "SDH1"));
		SDH1ModuleList.add(new Module("COMH6002", "Computer Architecture Lecture", lecturer15, false, "SDH1"));
		SDH1ModuleList.add(new Module("COMP6035", "Computer Security Principles Lab", lecturer2, true, "SDH1"));
		SDH1ModuleList.add(new Module("COMP6035", "Computer Security Principles Lecture", lecturer2, false, "SDH1"));
		SDH1ModuleList.add(new Module("COMP6035", "Computer Security Principles Lecture", lecturer2, false, "SDH1"));
		SDH1ModuleList.add(new Module("MATH6055", "Maths For Computer Science Lab", lecturer16, true, "SDH1"));
		SDH1ModuleList.add(new Module("MATH6055", "Maths For Computer Science Lecture", lecturer16, false, "SDH1"));
		SDH1ModuleList.add(new Module("MATH6055", "Maths For Computer Science Lecture", lecturer16, false, "SDH1"));
		
		ArrayList<ClassGroup> classGroupList = new  ArrayList<ClassGroup>();
		ClassGroup classGroup1 = new ClassGroup("Software Development 4", "SDH4", 30, assignEmptyTimetable());
		ClassGroup classGroup2 = new ClassGroup("Software Development 3", "SDH3", 32, assignEmptyTimetable());
		ClassGroup classGroup3 = new ClassGroup("Software Development 2", "SDH2", 35, assignEmptyTimetable());
		ClassGroup classGroup4 = new ClassGroup("Software Development 1", "SDH1", 40, assignEmptyTimetable());
		classGroupList.add(classGroup1);
		classGroupList.add(classGroup2);
		classGroupList.add(classGroup3);
		classGroupList.add(classGroup4);
		

		ArrayList<Lecturer> lecturerList = new ArrayList<Lecturer>();//Contains all lecturers
		lecturerList.add(lecturer1);
		lecturerList.add(lecturer2);
		lecturerList.add(lecturer3);
		lecturerList.add(lecturer4);
		lecturerList.add(lecturer5);
		lecturerList.add(lecturer6);
		lecturerList.add(lecturer7);
		lecturerList.add(lecturer8);
		lecturerList.add(lecturer9);
		lecturerList.add(lecturer10);
		lecturerList.add(lecturer11);
		
		ArrayList<Room> listOfLectureRooms = new ArrayList<Room>();//All rooms to be added here
		ArrayList<Room> listOfLabRooms = new ArrayList<Room>();
		ArrayList<Room> allRooms = new ArrayList<Room>();
		
		Room it11 = new Room(27, true, "IT1.1", assignEmptyTimetable());
		Room it12 = new Room(36, true, "IT1.2", assignEmptyTimetable());
		Room it13 = new Room(38, true, "IT1.3", assignEmptyTimetable());
		Room it21 = new Room(37, true, "IT2.1", assignEmptyTimetable());
		Room it22 = new Room(37, true, "IT2.2", assignEmptyTimetable());
		Room it23 = new Room(37, true, "IT2.3", assignEmptyTimetable());
		Room it1 = new Room(50, false, "IT1", assignEmptyTimetable());
		Room it2 = new Room(60, false, "IT2", assignEmptyTimetable());
		Room it3 = new Room(30, false, "IT3", assignEmptyTimetable());
		Room lectureRoom4 = new Room(37, false, "lectureRoom4", assignEmptyTimetable());
		Room lectureRoom5 = new Room(50, false, "lectureRoom5", assignEmptyTimetable());
		Room lectureRoom6 = new Room(40, false, "lectureRoom6", assignEmptyTimetable());
		listOfLabRooms.add(it11);
		listOfLabRooms.add(it12);
		listOfLabRooms.add(it13);
		listOfLabRooms.add(it21);
		listOfLabRooms.add(it22);
		listOfLabRooms.add(it23);
		listOfLectureRooms.add(it1);
		listOfLectureRooms.add(it2);
		listOfLectureRooms.add(it3);
		listOfLectureRooms.add(lectureRoom4);
		listOfLectureRooms.add(lectureRoom5);
		listOfLectureRooms.add(lectureRoom6);
		allRooms.addAll(listOfLabRooms);
		allRooms.addAll(listOfLectureRooms);
		
		DatabaseReference ref = firebaseDatabase.getReference("classes");
		DatabaseReference lecturerRef = firebaseDatabase.getReference("lecturers");
		ref.removeValue();
		lecturerRef.removeValue();
		//greedyalgorithm call here for classes
		try {
			writeToFirebase(createTimetable(SDH4ModuleList, lecturerList, allRooms, classGroup1, listOfLabRooms, listOfLectureRooms));
			writeToFirebase(createTimetable(SDH3ModuleList, lecturerList, allRooms, classGroup2, listOfLabRooms, listOfLectureRooms));
			writeToFirebase(createTimetable(SDH2ModuleList, lecturerList, allRooms, classGroup3, listOfLabRooms, listOfLectureRooms));
			writeToFirebase(createTimetable(SDH1ModuleList, lecturerList, allRooms, classGroup4, listOfLabRooms, listOfLectureRooms));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public static String[][] createTimetable(ArrayList<Module> moduleList, ArrayList<Lecturer> lecturerList, ArrayList<Room> roomList, ClassGroup classGroup, ArrayList<Room> listOfLabRooms, ArrayList<Room> listOfLectureRooms) {
		String timetable[][] = GreedyAlgorithm.greedyAlgorithmSolver(moduleList, lecturerList, roomList, classGroup, listOfLabRooms, listOfLectureRooms);
		int dayCounter=0, timeslotCounter = 0;
		//System.out.print("9:00\t10:00\t11:00\t12:00\t13:00\t14:00\t15:00\t16:00\t17:00\n");
		while (dayCounter < 5)//Prints timetable contents
		{
			while(timeslotCounter < 9)
			{
				//System.out.print(timetable[dayCounter][timeslotCounter] + "\t");
				timeslotCounter++;
			}
			dayCounter++;
			if (timeslotCounter == 9)
			{
				timeslotCounter = 0;
				//System.out.print("\n");
			}
		}
		dayCounter = 0;
		timeslotCounter = 0;
		while (dayCounter < 5)//Prints classgroup timetable
		{
			while(timeslotCounter < 9)
			{
				//System.out.print(classGroup.gettimetable()[dayCounter][timeslotCounter] + "\t");
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
		//System.out.println("\n");
		return timetable;
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
