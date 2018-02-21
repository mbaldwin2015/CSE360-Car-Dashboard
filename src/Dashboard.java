/* TEAM 4
 * Class: Dashboard
 * Description: This class is the primary user interface of the car.  It houses all of the buttons etc and gets all values 
 * for the functionality from the Driver and Car classes (which this class initializes objects of).
 */

// All and only the swing and awt libraries needed
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JList;

public class Dashboard extends JFrame implements ActionListener{
	// Initialization
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel login;
	private JPanel mainDash;
	private JPanel phoneTab;
	private JPanel exit;
	private JTabbedPane tabbedPane;
	
	//Phone Components
	private JLabel lblCallInformation;
	private JLabel lblMessage;
	private ContactList listOfContacts;
	
	//Dashboard Components
	private String gear = "P";
	private Driver theDriver = new Driver("test", "password"); // initialize driver
	private Car theCar = new Car(75, 51798); //initialize car
	private int fuelBefore = theCar.getFuel();
	private boolean onCall = false;

	//Radio Components
	private RadioList listOfAMRadioStations;
	private RadioList listOfFMRadioStations;
	private JTextField fuelUsedDisplay;
	private JTextField fuelFilledDisplay;
	private JTextField callTimeDisplay;
	private JTextField driveTimeDisplay;
	private JTextField avgSpeedDisplay;
	private JTextField maxSpeedDisplay;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public Dashboard() {
		// Main frame settings
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		setTitle("Car Interface - Team 4");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
		    	login.setVisible(false);
				mainDash.setVisible(false);
				exit.setVisible(true);
		    }
		});
		
		/*
		 * Sets up all the components in the Login GUI
		 * */
		loginGUI();

		/*
		 * Sets up all the components in the Main Dashboard GUI
		 * */
		mainDashGUI();
		
		/*
		 * Sets up all the components in the Phone GUI
		 * */
		this.listOfContacts = new ContactList();
		phoneGUI();
		
		/*
		 * Sets up all the components in the Radio GUI
		 * */
		this.listOfAMRadioStations = new RadioList();
		this.listOfFMRadioStations = new RadioList();
		radioGUI();
		
		/*
		 * Sets up all the components in the Exit GUI
		 */
		exitGUI();
	}
	
	// this panel is displayed when the user goes to exit the program
	private void exitGUI() {
		exit = new JPanel();
		contentPane.add(exit);
		exit.setLayout(null);
		
		JButton btnYes = new JButton("YES");
		btnYes.setBounds(284, 389, 89, 39);
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exit.add(btnYes);
		
		JLabel lblAreYouSure = new JLabel("Are you sure you want to exit?");
		lblAreYouSure.setForeground(Color.RED);
		lblAreYouSure.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAreYouSure.setBounds(291, 350, 211, 28);
		exit.add(lblAreYouSure);
		
		JButton btnNo = new JButton("NO");
		btnNo.setBounds(397, 389, 89, 39);
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit.setVisible(false);
				login.setVisible(true);
			}
		});
		exit.add(btnNo);
		
		JLabel lblUserStatsFor = new JLabel("User Stats for this session");
		lblUserStatsFor.setFont(new Font("Arial", Font.BOLD, 16));
		lblUserStatsFor.setBounds(284, 28, 211, 39);
		exit.add(lblUserStatsFor);
		
		JLabel lblOfFuel = new JLabel("% of Fuel Used:");
		lblOfFuel.setFont(new Font("Arial", Font.PLAIN, 11));
		lblOfFuel.setBounds(284, 225, 89, 14);
		exit.add(lblOfFuel);
		
		fuelUsedDisplay = new JTextField();
		fuelUsedDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		fuelUsedDisplay.setText(Integer.toString(theDriver.getFuelUsed()));
		fuelUsedDisplay.setBounds(458, 222, 27, 20);
		fuelUsedDisplay.setEditable(false);
		exit.add(fuelUsedDisplay);
		fuelUsedDisplay.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("# of times Fuel was filled:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(285, 113, 132, 14);
		exit.add(lblNewLabel_1);
		
		fuelFilledDisplay = new JTextField();
		fuelFilledDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		fuelFilledDisplay.setText(Integer.toString(theDriver.getFillCount()));
		fuelFilledDisplay.setBounds(458, 111, 27, 20);
		fuelFilledDisplay.setEditable(false);
		exit.add(fuelFilledDisplay);
		fuelFilledDisplay.setColumns(10);
		
		JLabel lblTotalTimeOn = new JLabel("Time spent on Phone (seconds):");
		lblTotalTimeOn.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTotalTimeOn.setBounds(284, 149, 165, 14);
		exit.add(lblTotalTimeOn);
		
		callTimeDisplay = new JTextField();
		callTimeDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		callTimeDisplay.setText(Integer.toString(theDriver.getCallTime()));
		callTimeDisplay.setBounds(458, 147, 27, 20);
		callTimeDisplay.setEditable(false);
		exit.add(callTimeDisplay);
		callTimeDisplay.setColumns(10);
		
		JLabel lblTotalDriveTime = new JLabel("Total Drive Time (seconds):");
		lblTotalDriveTime.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTotalDriveTime.setBounds(284, 78, 152, 14);
		exit.add(lblTotalDriveTime);
		
		driveTimeDisplay = new JTextField();
		driveTimeDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		driveTimeDisplay.setText(Integer.toString(theDriver.getDriveTime()));
		driveTimeDisplay.setBounds(458, 74, 27, 20);
		driveTimeDisplay.setEditable(false);
		exit.add(driveTimeDisplay);
		driveTimeDisplay.setColumns(10);
		
		JLabel lblAverageSpeed = new JLabel("Average Speed:");
		lblAverageSpeed.setFont(new Font("Arial", Font.PLAIN, 11));
		lblAverageSpeed.setBounds(284, 187, 89, 14);
		exit.add(lblAverageSpeed);
		
		JLabel lblMaxSpeed = new JLabel("Max Speed:");
		lblMaxSpeed.setFont(new Font("Arial", Font.PLAIN, 11));
		lblMaxSpeed.setBounds(284, 267, 89, 14);
		exit.add(lblMaxSpeed);
		
		avgSpeedDisplay = new JTextField();
		avgSpeedDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		avgSpeedDisplay.setText(Double.toString(theDriver.getAvgSpeed()));
		avgSpeedDisplay.setBounds(458, 183, 27, 20);
		avgSpeedDisplay.setEditable(false);
		exit.add(avgSpeedDisplay);
		avgSpeedDisplay.setColumns(10);
		
		maxSpeedDisplay = new JTextField();
		maxSpeedDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		maxSpeedDisplay.setText(Integer.toString(theDriver.getMaxSpeed()));
		maxSpeedDisplay.setBounds(458, 261, 27, 20);
		maxSpeedDisplay.setEditable(false);
		exit.add(maxSpeedDisplay);
		maxSpeedDisplay.setColumns(10);
		
		Timer exitTimer = new Timer();
		TimerTask exitTask = new TimerTask() {
			public void run() {
				fuelUsedDisplay.setText(Integer.toString(theDriver.getFuelUsed()));
				fuelFilledDisplay.setText(Integer.toString(theDriver.getFillCount()));
				callTimeDisplay.setText(Integer.toString(theDriver.getCallTime()));
				driveTimeDisplay.setText(Integer.toString(theDriver.getDriveTime()));
				maxSpeedDisplay.setText(Integer.toString(theDriver.getMaxSpeed()));
				avgSpeedDisplay.setText(Double.toString(theDriver.getAvgSpeed()));
			}
		};
		exitTimer.scheduleAtFixedRate(exitTask, 1000, 1000);  // runs task every second
	}
	
	private void mainDashGUI(){
		// mainDash JPanel.  Houses all of the Dashboard functionality
		mainDash = new JPanel();
		contentPane.add(mainDash, "name_341074754740188");
		mainDash.setLayout(null);
		
		// Park Gear Display (current gear is highlighted by setting the background to RED)
		JTextPane txtpnP = new JTextPane();
		txtpnP.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnP.setEditable(false);
		txtpnP.setBackground(Color.RED);
		txtpnP.setForeground(Color.BLACK);
		txtpnP.setText("P");
		txtpnP.setBounds(157, 171, 15, 26);
		mainDash.add(txtpnP);
		
		// Drive Gear Display
		JTextPane txtpnD = new JTextPane();
		txtpnD.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnD.setEditable(false);
		txtpnD.setText("D");
		txtpnD.setForeground(Color.BLACK);
		txtpnD.setBackground(Color.WHITE);
		txtpnD.setBounds(187, 171, 15, 26);
		mainDash.add(txtpnD);
		
		// Neutral Gear Display
		JTextPane txtpnN = new JTextPane();
		txtpnN.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnN.setEditable(false);
		txtpnN.setText("N");
		txtpnN.setForeground(Color.BLACK);
		txtpnN.setBackground(Color.WHITE);
		txtpnN.setBounds(217, 171, 15, 26);
		mainDash.add(txtpnN);
		
		// Reverse Gear Display
		JTextPane txtpnR = new JTextPane();
		txtpnR.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnR.setEditable(false);
		txtpnR.setText("R");
		txtpnR.setForeground(Color.BLACK);
		txtpnR.setBackground(Color.WHITE);
		txtpnR.setBounds(127, 171, 15, 26);
		mainDash.add(txtpnR);
		
		// Speed TextPane Display
		JTextPane Speed = new JTextPane();
		Speed.setEditable(false);
		Speed.setFont(new Font("Arial", Font.PLAIN, 68));
		Speed.setText("000");
		Speed.setBounds(124, 0, 120, 91);
		mainDash.add(Speed);
		
		// "Speed" Label
		JLabel lblSpeed = new JLabel("SPEED");
		lblSpeed.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSpeed.setBounds(155, 92, 56, 20);
		mainDash.add(lblSpeed);
		
		// "Fuel" Label
		JLabel lblFuelLevel = new JLabel("FUEL");
		lblFuelLevel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFuelLevel.setBounds(22, 374, 42, 20);
		mainDash.add(lblFuelLevel);
		
		// "Volume" Label
		JLabel lblVol = new JLabel("VOLUME");
		lblVol.setFont(new Font("Arial", Font.PLAIN, 16));
		lblVol.setBounds(295, 171, 81, 20);
		mainDash.add(lblVol);
		
		// "Location" Label
		JLabel lblLocation = new JLabel("LOCATION");
		lblLocation.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLocation.setBounds(275, 2, 99, 20);
		mainDash.add(lblLocation);
		
		// "Trip" Label
		JLabel lblTrip = new JLabel("TRIP");
		lblTrip.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTrip.setBounds(173, 374, 38, 20);
		mainDash.add(lblTrip);
		
		// "Odometer" Label
		JLabel lblOdometer = new JLabel("ODOMETER");
		lblOdometer.setFont(new Font("Arial", Font.PLAIN, 16));
		lblOdometer.setBounds(119, 401, 93, 20);
		mainDash.add(lblOdometer);
		
		// "DATE & TIME" Label
		JLabel lblDateTime = new JLabel("DATE & TIME");
		lblDateTime.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDateTime.setBounds(0, 0, 112, 20);
		mainDash.add(lblDateTime);
		
		// Fuel display as a progress bar, value is saved into a file
		JProgressBar fuel = new JProgressBar();
		fuel.setForeground(new Color(0, 150, 0));
		fuel.setOrientation(SwingConstants.VERTICAL);
		fuel.setValue(75);
		fuel.setBounds(20, 185, 44, 190);
		mainDash.add(fuel);
		
		// Odometer display, value is saved into a file
		JTextPane odoDisplay = new JTextPane();
		odoDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
		odoDisplay.setEditable(false);
		odoDisplay.setText("000000");
		odoDisplay.setBounds(217, 401, 60, 26);
		mainDash.add(odoDisplay);
		
		// Trip display, initializes to 0 every time the car is started
		JTextPane tripDisplay = new JTextPane();
		tripDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
		tripDisplay.setEditable(false);
		tripDisplay.setText("000");
		tripDisplay.setBounds(243, 374, 33, 26);
		mainDash.add(tripDisplay);
		
		// Location display, gets location from Car class object theCar
		JTextPane locDisplay = new JTextPane();
		locDisplay.setBackground(SystemColor.window);
		locDisplay.setFont(new Font("Arial", Font.BOLD, 16));
		locDisplay.setEditable(false);
		locDisplay.setText(theCar.getLocation());
		locDisplay.setBounds(267, 21, 93, 26);
		mainDash.add(locDisplay);
		
		// Date Display
		JTextPane dateDisplay = new JTextPane();
		dateDisplay.setBackground(SystemColor.window);
		dateDisplay.setEditable(false);
		dateDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
		dateDisplay.setText(theCar.getDate());
		dateDisplay.setBounds(0, 27, 117, 20);
		mainDash.add(dateDisplay);
		
		// Accelerate button, increases the speed of the car when pressed and held (if there is fuel)
		JButton btnAccelerate = new JButton("Accelerate");
		btnAccelerate.setBounds(127, 253, 115, 29);
		javax.swing.Timer accelTimer = new javax.swing.Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (theCar.getFuel() > 0)
					Speed.setText(String.format("%03d", theCar.changeSpeed(1)));
				if (theCar.getSpeed() == 100)
					Speed.setForeground(Color.RED);
			}
		});
		ButtonModel accelModel = btnAccelerate.getModel();
		accelModel.addChangeListener(new ChangeListener() {
	         @Override
	         public void stateChanged(ChangeEvent cEvt) {
	        	 if (accelModel.isPressed() && !accelTimer.isRunning()) {
	                 accelTimer.start();
	              } else if (!accelModel.isPressed() && accelTimer.isRunning()) {
	                 accelTimer.stop();
	              }
	         }
	      });
		mainDash.add(btnAccelerate);
		
		// Brake button, decreases the speed of the car when pressed and held
		JButton btnBrake = new JButton("Brake");
		btnBrake.setBounds(127, 280, 115, 29);
		javax.swing.Timer brakeTimer = new javax.swing.Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Speed.setText(String.format("%03d", theCar.changeSpeed(-1)));
				if (theCar.getSpeed() < 100)
					Speed.setForeground(Color.BLACK);
			}
		});
		ButtonModel brakeModel = btnBrake.getModel();
		brakeModel.addChangeListener(new ChangeListener() {
	         @Override
	         public void stateChanged(ChangeEvent cEvt) {
	        	 if (brakeModel.isPressed() && !brakeTimer.isRunning()) {
	                 brakeTimer.start();
	              } else if (!brakeModel.isPressed() && brakeTimer.isRunning()) {
	                 brakeTimer.stop();
	              }
	         }
	      });
		mainDash.add(btnBrake);
		
		// TabbedPane for the Phone and Radio interfaces to reside
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(375, 0, 393, 434);
		
		mainDash.add(tabbedPane);
		
		// Changes to the gear on the left of the current gear, assuming certain conditions pass
		JButton gearLeft = new JButton("<");
		gearLeft.setBounds(108, 134, 48, 29);
		gearLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gear = theCar.changeGear(-1);
				if (gear.equals("D")) {
					txtpnN.setBackground(Color.WHITE);
					txtpnD.setBackground(Color.RED);
				}
				else if (gear.equals("P")) {
					txtpnD.setBackground(Color.WHITE);
					txtpnP.setBackground(Color.RED);
				}
				else if (gear.equals("R")) {
					txtpnP.setBackground(Color.WHITE);
					txtpnR.setBackground(Color.RED);
				}
			}
		});
		mainDash.add(gearLeft);
		
		// Changes to the gear on the right of the current gear, assuming certain conditions pass
		JButton gearRight = new JButton(">");
		gearRight.setBounds(205, 134, 48, 29);
		gearRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gear = theCar.changeGear(1);
				if (gear.equals("P")) {
					txtpnR.setBackground(Color.WHITE);
					txtpnP.setBackground(Color.RED);
				}
				else if (gear.equals("D")) {
					txtpnP.setBackground(Color.WHITE);
					txtpnD.setBackground(Color.RED);
				}
				else if (gear.equals("N")) {
					txtpnD.setBackground(Color.WHITE);
					txtpnN.setBackground(Color.RED);
				}
			}
		});
		mainDash.add(gearRight);
		
		// Gear label
		JLabel lblGear = new JLabel("GEAR");
		lblGear.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGear.setBounds(161, 140, 44, 20);
		mainDash.add(lblGear);
		
		// Volume Display, value changes based on the volume slider
		JTextPane volDisplay = new JTextPane();
		volDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
		volDisplay.setEditable(false);
		volDisplay.setText("050");
		volDisplay.setBounds(309, 190, 36, 26);
		mainDash.add(volDisplay);
		
		// Volume slider
		JSlider volumeSlider = new JSlider();
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setMajorTickSpacing(10);
		volumeSlider.setMinorTickSpacing(1);
		volumeSlider.setOrientation(SwingConstants.VERTICAL);
		volumeSlider.setBounds(307, 217, 44, 183);
		volumeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				theCar.setSpeakerVolume(volumeSlider.getValue());
				volDisplay.setText(String.format("%03d", theCar.getSpeakerVolume()));
			}
		});
		mainDash.add(volumeSlider);
		
		JButton volMute = new JButton("Mute");
		volMute.setFont(new Font("Arial", Font.PLAIN, 10));
		volMute.setBounds(294, 402, 66, 22);
		volMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theCar.setSpeakerVolume(0);
				volumeSlider.setValue(0);
				volDisplay.setText(String.format("%03d", 0));
			}
		});
		mainDash.add(volMute);
		
		// Fill Fuel Button, fills fuel to 100%
		JButton setFuel = new JButton("FILL");
		setFuel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (theCar.getGear().equals("P")) {
					theCar.updateFuel(100);
					fuel.setValue(theCar.getFuel());
					fuel.setForeground(new Color(0, 150, 0));
				
					// User Stats
					theDriver.fillCount();
					fuelBefore = 100;
				}
			}
		});
		setFuel.setFont(new Font("Arial", Font.PLAIN, 12));
		setFuel.setBounds(13, 400, 59, 29);
		mainDash.add(setFuel);
		
		/*
		 * TIMERS
		 */
		
		// This TimerTask task object will update the date, trip, odometer, and fuel level when run
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				dateDisplay.setText(theCar.getDate());
				tripDisplay.setText(String.format("%03d", theCar.updateMileage()));
				odoDisplay.setText(String.format("%06d", theCar.getOdo()));
				fuel.setValue(theCar.getFuel());
				if (theCar.getFuel() < 20)
					fuel.setForeground(new Color(150, 0, 0));
				else fuel.setForeground(new Color(0, 150, 0));
				theCar.updateLocation();
				locDisplay.setText(theCar.getLocation());
				
				/*
				 * User Stats
				 */
				theDriver.updateDriveTime();
				theDriver.updateAvgSpeed(Integer.parseInt(Speed.getText()));
				theDriver.updateMaxSpeed(Integer.parseInt(Speed.getText()));
				theDriver.updateFuelUsed(fuelBefore-theCar.getFuel());
				fuelBefore = theCar.getFuel();
				if (onCall) theDriver.updateCallTime();
			}
		};
		// the speedDepreciate object lowers the speed when run
		TimerTask speedDepreciate = new TimerTask() {
			public void run() {
				if (theCar.getSpeed() > 0) {
					Speed.setText(String.format("%03d", theCar.changeSpeed(-1)));
					if (theCar.getSpeed() < 100)
						Speed.setForeground(Color.BLACK);
				}
			}
		};
		
		timer.scheduleAtFixedRate(task, 1000, 1000);  // runs task every second
		timer.scheduleAtFixedRate(speedDepreciate, 5000, 5000);  // runs speedDepreciate every 5 seconds

	}
	
	private void loginGUI(){
		
		// JPanel for login screen
		login = new JPanel();
		contentPane.add(login, "name_341072449551435");
		login.setLayout(null);
		
		// Welcome Label
		JLabel lblWelcomePleaseEnter = new JLabel("Welcome! Please enter your username and password.", SwingConstants.CENTER);
		lblWelcomePleaseEnter.setFont(new Font("Arial", Font.PLAIN, 26));
		lblWelcomePleaseEnter.setBounds(80, 16, 620, 52);
		login.add(lblWelcomePleaseEnter);
		
		// Username Text Field
		usernameField = new JTextField("");
		usernameField.setBounds(367, 274, 146, 26);
		login.add(usernameField);
		usernameField.setColumns(10);
		
		// Password Text Field
		passwordField = new JPasswordField("");
		passwordField.setBounds(367, 316, 146, 26);
		login.add(passwordField);
		
		// Error Message if invalid username and/or password was entered
		JLabel invalid = new JLabel("Invalid username and/or password entered.", SwingConstants.CENTER);
		invalid.setForeground(Color.RED);
		invalid.setBounds(77, 238, 623, 20);
		login.add(invalid);
		invalid.setVisible(false);
		
		// Starts the car if the correct username and password was entered
		// Switches the CardLayout from the Login Panel to the mainDash panel
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Arial", Font.PLAIN, 16));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = usernameField.getText();
				@SuppressWarnings("deprecation")
				String pword = passwordField.getText();
				
				if (theDriver.validLogin(uname, pword)) {
					login.setVisible(false);
					mainDash.setVisible(true);
					invalid.setVisible(false);
				}
				else {
					invalid.setVisible(true);
				}
				usernameField.setText("");
				passwordField.setText("");
			}
		});
		btnStart.setBounds(333, 376, 115, 29);
		login.add(btnStart);
		
		// Username text label
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsername.setBounds(276, 277, 76, 20);
		login.add(lblUsername);
		
		// Password text label
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(276, 319, 74, 20);
		login.add(lblPassword);
		
		// Label that creates the image for the car on the login screen
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/car.fw.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(276, 51, 237, 191);
		login.add(label);
	}
	
	private void phoneGUI(){
		phoneTab = new JPanel();
		phoneTab.setToolTipText("");
		tabbedPane.addTab("Phone", null, phoneTab, null);
		phoneTab.setLayout(null);
		
		/* Keyboard Buttons*/
		JButton btnDial = new JButton("DIAL");
		btnDial.setForeground(new Color(0, 204, 102));
		btnDial.setEnabled(true);
		btnDial.setBounds(232, 220, 75, 43);
		addButton(btnDial);
		
		JButton btnCancel = new JButton("END");
		btnCancel.setForeground(new Color(204, 0, 0));
		btnCancel.setEnabled(true);
		btnCancel.setBounds(78, 220, 72, 43);
		addButton(btnCancel);

		JButton button_1 = new JButton("1");
		button_1.setBounds(96, 79, 54, 35);
		addButton(button_1);
		
		JButton button_2 = new JButton("2");
		button_2.setBounds(167, 79, 54, 35);
		addButton(button_2);
		
		JButton button_3 = new JButton("3");
		button_3.setBounds(232, 79, 54, 35);
		addButton(button_3);
		
		JButton button_4 = new JButton("4");
		button_4.setBounds(96, 126, 54, 35);
		addButton(button_4);
		
		JButton button_5 = new JButton("5");
		button_5.setBounds(167, 126, 54, 35);
		addButton(button_5);
		
		JButton button_6 = new JButton("6");
		button_6.setBounds(232, 126, 54, 35);
		addButton(button_6);
		
		JButton button_7 = new JButton("7");
		button_7.setBounds(96, 173, 54, 35);
		addButton(button_7);
		
		JButton button_8 = new JButton("8");
		button_8.setBounds(167, 173, 54, 35);
		addButton(button_8);
		
		JButton button_9 = new JButton("9");
		button_9.setBounds(232, 173, 54, 35);
		addButton(button_9);
		
		JButton button_0 = new JButton("0");
		button_0.setBounds(167, 217, 54, 35);
		addButton(button_0);
		/*End Keyboard Buttons*/
		/*
		 * 
		 */
		//JList<String> listContacts = new JList<String>(listOfContacts.contactsAsString());
		JList<String> listContacts = new JList<String>();

		listContacts.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listContacts.setBounds(32, 312, 319, 72);		
		phoneTab.add(listContacts);
		
		listContacts.addListSelectionListener(
				new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						lblMessage.setText("");
						lblCallInformation.setText("");
						lblCallInformation.setText(listContacts.getSelectedValue());
					}
				}
		);
		
		JLabel lblAgenda = new JLabel("Agenda");
		lblAgenda.setBounds(167, 285, 61, 16);
		phoneTab.add(lblAgenda);
		
		JSlider micVolumeSlider = new JSlider();
		micVolumeSlider.setValue(theCar.getMicVolume());
		micVolumeSlider.setSnapToTicks(true);
		micVolumeSlider.setPaintTicks(true);
		micVolumeSlider.setOrientation(SwingConstants.VERTICAL);
		micVolumeSlider.setMinorTickSpacing(1);
		micVolumeSlider.setMajorTickSpacing(10);
		micVolumeSlider.setBounds(20, 126, 44, 155);
		phoneTab.add(micVolumeSlider);
		
		JLabel lblMic = new JLabel("MIC");
		lblMic.setBounds(32, 87, 29, 16);
		phoneTab.add(lblMic);
		
		JTextPane micVolDisplay = new JTextPane();
		micVolDisplay.setText(String.format("%03d",theCar.getMicVolume()));
		micVolDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		micVolDisplay.setEditable(false);
		micVolDisplay.setBounds(32, 104, 29, 23);
		phoneTab.add(micVolDisplay);
		
		lblCallInformation = new JLabel();
		lblCallInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCallInformation.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblCallInformation.setForeground(new Color(0, 102, 204));
		lblCallInformation.setBounds(32, 27, 309, 27);
		lblCallInformation.setBorder(new LineBorder(new Color(153, 153, 153)));
		phoneTab.add(lblCallInformation);
		
		lblMessage = new JLabel();
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(new Color(153, 51, 51));
		lblMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblMessage.setBorder(new LineBorder(new Color(153, 153, 153)));
		lblMessage.setBounds(32, 27, 309, 27);
		phoneTab.add(lblMessage);
		
		tabbedPane.setEnabledAt(0, true);

		micVolumeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				theCar.setMicVolume(micVolumeSlider.getValue());
				micVolDisplay.setText(String.format("%03d", theCar.getMicVolume()));
			}
		});
		
		JButton micMute = new JButton("Mute");
		micMute.setFont(new Font("Arial", Font.PLAIN, 10));
		micMute.setBounds(32, 283, 66, 22);
		micMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				micVolumeSlider.setValue(0);
				micVolDisplay.setText(String.format("%03d", 0));
			}
			
		});
		phoneTab.add(micMute);
	}
	
	/* addButton function add the keyboard buttons to the GUI and set the event listener*/
    private void addButton(JButton button) {
        button.addActionListener(this);
        phoneTab.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	if (command.equals("DIAL")){
    		onCall = true;
    		String phoneNumber = lblCallInformation.getText();
			phoneNumber = phoneNumber.replaceAll("Calling ","");
    		//verify number is complete
    		String pattern = "(\\d\\d\\d)*";
    		// Create a Pattern object
    		Pattern r = Pattern.compile(pattern);
    		// Now create matcher object.
    		Matcher m = r.matcher(phoneNumber);
    		if(m.find()){ 
    			lblCallInformation.setText("");
    			lblCallInformation.setText("Calling " + phoneNumber);
    		}else{
    			lblCallInformation.setText("");
    			lblMessage.setText("Please insert a valid number.");
    		}
    		//then call
    		//if it isn't complete then
    		//trigger a message
    	}else if (command.equals("END")){
    		//if it is an outgoing call
    		//cancel call and clear the field
    		onCall = false;
    		lblCallInformation.setText("");
    		lblMessage.setText("");
    	}else{
    		lblMessage.setText("");
 		
    		String phoneNumber = lblCallInformation.getText();
    		phoneNumber += command;
    		lblCallInformation.setText(phoneNumber);
    	}
    }
	
	private void radioGUI(){
		JPanel radioTab = new JPanel();
		tabbedPane.addTab("Radio", null, radioTab, null);
		radioTab.setLayout(null);
		
		JToggleButton tglbtnAM = new JToggleButton("AM");
		tglbtnAM.setSelected(true);
		tglbtnAM.setBounds(6, 6, 57, 29);
		radioTab.add(tglbtnAM);
		
		JToggleButton tglbtnFM = new JToggleButton("FM");
		tglbtnFM.setBounds(65, 6, 57, 29);
		radioTab.add(tglbtnFM);
		
		JButton buttonNext = new JButton("NEXT >");
		buttonNext.setBounds(246, 104, 91, 29);
		radioTab.add(buttonNext);
		
		JButton buttonPrevious = new JButton("< PREV");
		buttonPrevious.setBounds(31, 104, 91, 29);
		radioTab.add(buttonPrevious);
		
		JTextPane txtActiveStation = new JTextPane();
		txtActiveStation.setForeground(new Color(0, 102, 102));
		txtActiveStation.setBackground(SystemColor.window);
		txtActiveStation.setText("930 AM Phoenix Radio, Inc.");
		txtActiveStation.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		txtActiveStation.setEditable(false);
		txtActiveStation.setBounds(40, 72, 297, 20);
		radioTab.add(txtActiveStation);
		
		JLabel lblNewLabel = new JLabel("Favorites");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel.setBounds(138, 143, 61, 16);
		radioTab.add(lblNewLabel);
		/*
		 * 
		 */
		//JList<String> listRadioAM = new JList<String>(listOfAMRadioStations.radioAMAsString());
		JList<String> listRadioAM = new JList<String>();
		
		listRadioAM.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listRadioAM.setLayoutOrientation(JList.VERTICAL_WRAP);
		listRadioAM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listRadioAM.setVisible(true);
		listRadioAM.setSelectedIndex(0);
		listRadioAM.setBounds(43, 161, 281, 111);
		radioTab.add(listRadioAM);
		/*
		 * 
		 */
		//JList<String> listRadioFM = new JList<String>(listOfFMRadioStations.radioFMAsString());
		JList<String> listRadioFM = new JList<String>();		
		
		listRadioFM.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listRadioFM.setLayoutOrientation(JList.VERTICAL_WRAP);
		listRadioFM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listRadioFM.setVisible(false);
		listRadioFM.setBounds(43, 161, 281, 111);
		radioTab.add(listRadioFM);
		
		JLabel noSignal = new JLabel("NO SIGNAL");
		noSignal.setFont(new Font("Tahoma", Font.BOLD, 14));
		noSignal.setForeground(Color.RED);
		noSignal.setBounds(154, 32, 97, 29);
		noSignal.setVisible(false);
		radioTab.add(noSignal);
		
		tglbtnAM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tglbtnAM.setSelected(true);
				tglbtnFM.setSelected(false);
				listRadioAM.setVisible(true);
				listRadioFM.setVisible(false);
			}
		});
		
		tglbtnFM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tglbtnFM.setSelected(true);
				tglbtnAM.setSelected(false);
				listRadioFM.setVisible(true);
				listRadioAM.setVisible(false);
			}
		});
		
		listRadioAM.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int i = listRadioAM.getSelectedIndex();
						// TODO Auto-generated method stub
						txtActiveStation.setText("");
						txtActiveStation.setText(listRadioAM.getSelectedValue());
						if (listOfAMRadioStations.getLocation(i, "AM").equals(theCar.getLocation())) noSignal.setVisible(false);
						else noSignal.setVisible(true);
					}
				}
		);
		
		listRadioFM.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int i = listRadioFM.getSelectedIndex();
						// TODO Auto-generated method stub
						txtActiveStation.setText("");
						txtActiveStation.setText(listRadioFM.getSelectedValue());
						if (listOfFMRadioStations.getLocation(i, "FM").equals(theCar.getLocation())) noSignal.setVisible(false);
						else noSignal.setVisible(true);
					}
				}
		);
		
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnAM.isSelected()) {
					int i = listRadioAM.getSelectedIndex();
					if (i<5) i++; 
					listRadioAM.setSelectedIndex(i);
					txtActiveStation.setText(listRadioAM.getSelectedValue());
					if (listOfAMRadioStations.getLocation(i, "AM").equals(theCar.getLocation())) noSignal.setVisible(false);
					else noSignal.setVisible(true);
				} else {
					int i = listRadioFM.getSelectedIndex();
					if (i<5) i++; 
					listRadioFM.setSelectedIndex(i);
					txtActiveStation.setText(listRadioFM.getSelectedValue());
					if (listOfFMRadioStations.getLocation(i, "FM").equals(theCar.getLocation())) noSignal.setVisible(false);
					else noSignal.setVisible(true);
				}
			}
		});
		
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnAM.isSelected()) {
					int i = listRadioAM.getSelectedIndex();
					if (i>0) i--; 
					listRadioAM.setSelectedIndex(i);
					txtActiveStation.setText(listRadioAM.getSelectedValue());
					if (listOfAMRadioStations.getLocation(i, "AM").equals(theCar.getLocation())) noSignal.setVisible(false);
					else noSignal.setVisible(true);
				} else {
					int i = listRadioFM.getSelectedIndex();
					if (i>0) i--; 
					listRadioFM.setSelectedIndex(i);
					txtActiveStation.setText(listRadioFM.getSelectedValue());
					if (listOfFMRadioStations.getLocation(i, "FM").equals(theCar.getLocation())) noSignal.setVisible(false);
					else noSignal.setVisible(true);
				}
			}
		});
	}
}