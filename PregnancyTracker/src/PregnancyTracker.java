import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Font;


public class PregnancyTracker {

	
	private JFrame frame;
	private JTextField patientNameSearch;
	private JTextField searchValue;
	private JTextField patientID;
	private JTextField patientName;
	private JTextField dueDate;
	private JTextField delDate;
	private JTextField pregID;
	private JTextField type;
	private JTextField docID;
	private JTextField date;
	private JTextField time;
	private JTextField PID;
	private JTextField DID;
	private JTextArea Sum;
	private JTextField AID;
	private BufferedImage image;

	
	//Launch the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PregnancyTracker window = new PregnancyTracker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//Create the application
	public PregnancyTracker() {
		initialize();
	}

	
	//Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1000, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Pregnancy Tracker");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new LineBorder(Color.BLACK, 2));
		menuPanel.setBounds(0, 3, 220, 609);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		menuPanel.setBackground(Color.PINK);
		
		try {
			image = ImageIO.read(new File("preg.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		JLabel picture = new JLabel(new ImageIcon(image));
		picture.setBounds(2, 318, 217, 350);
		menuPanel.add(picture);
		menuPanel.repaint();
		
		JButton searchButton = new JButton("Search Patients");
		searchButton.setBounds(10, 10, 200, 50);
		searchButton.setBackground(Color.WHITE);
		menuPanel.add(searchButton);

		JButton pregButton = new JButton("Search Pregnancies");
		pregButton.setBounds(10, 70, 200, 50);
		pregButton.setBackground(Color.WHITE);
		menuPanel.add(pregButton);
		
		JButton aptButton = new JButton("Search Appointments");
		aptButton.setBounds(10, 130, 200, 50);
		aptButton.setBackground(Color.WHITE);
		menuPanel.add(aptButton);

		JButton insertPatient = new JButton("New Patient");
		insertPatient.setBounds(10, 190, 200, 50);
		insertPatient.setBackground(Color.WHITE);
		menuPanel.add(insertPatient);
		
		JButton insertPregnancy = new JButton("New Pregnancy");
		insertPregnancy.setBounds(10, 250, 200, 50);
		insertPregnancy.setBackground(Color.WHITE);
		menuPanel.add(insertPregnancy);
		
		JButton insertDoctor = new JButton("New Appointment");
		insertDoctor.setBounds(10, 310, 200, 50);
		insertDoctor.setBackground(Color.WHITE);
		menuPanel.add(insertDoctor);

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(Color.BLACK, 2));
		mainPanel.setBounds(220, 3, 766, 609);
		mainPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPatient(mainPanel);
			}
		});
		
		insertPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPatientFunction(mainPanel);
			}
		});

		pregButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPreg(mainPanel);
			}
		});
		
		insertPregnancy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPregnancyFunction(mainPanel);
			}
		});
		
		insertDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertAptFunction(mainPanel);
			}
		});
		
		aptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchApt(mainPanel);
			}
		});
	}

	
	// Create the patient search interface
	public void searchPatient(JPanel panel) {

		panel.removeAll();
		JLabel lblPleaseTypeA = new JLabel("Patient Name:");
		lblPleaseTypeA.setBounds(58, 10, 200, 50);
		panel.add(lblPleaseTypeA);

		patientNameSearch = new JTextField();
		patientNameSearch.setBounds(50, 60, 200, 50);
		panel.add(patientNameSearch);
		patientNameSearch.setColumns(10);
		
		JRadioButton preg = new JRadioButton("Currently Pregnant");
		preg.setBounds(50, 120, 150, 50);
		preg.setBackground(Color.WHITE);
		panel.add(preg);

		JRadioButton del = new JRadioButton("Past Pregnancy");
		del.setBounds(210, 120, 140, 50);
		del.setBackground(Color.WHITE);
		panel.add(del);
		
		JRadioButton all = new JRadioButton("All");
		all.setBounds(350, 120, 150, 50);
		all.setBackground(Color.WHITE);
		panel.add(all);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(preg);
		bg.add(del);
		bg.add(all);

		JButton lookup = new JButton("SUBMIT");
		lookup.setBounds(270, 60, 200, 50);
		lookup.setBackground(Color.PINK);
		panel.add(lookup);
		frame.repaint();
		
		JTextPane displaypane = new JTextPane();
		displaypane.setBounds(50, 190, 670, 380);
		JScrollPane jsp = new JScrollPane(displaypane);
		jsp.setBounds(50, 190, 670, 380);

		lookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String column = "";

				if (preg.isSelected()) {
					column = "DueDate";
					
				} else if (del.isSelected()) {
					column = "DeliveryDate";
					
				} else if (all.isSelected()) {
					column = "PregnancyID";
				}
				
				JLabel hidden = new JLabel(column);

				displaypane.setText(searchPatient(hidden.getText(), patientNameSearch.getText()));
				panel.add(jsp);
				frame.repaint();
				}
		});
	}

	
	// create pregnancy search interface
		public void searchPreg(JPanel pan) {
			
			pan.removeAll();
			
			JLabel selectRadio = new JLabel("Select Search Item:");
			selectRadio.setBounds(58, 10, 200, 50);
			pan.add(selectRadio);
			
			JRadioButton due = new JRadioButton("Due Date");
			due.setBounds(50, 120, 100, 50);
			due.setBackground(Color.WHITE);
			pan.add(due);
			
			JRadioButton del = new JRadioButton("Delivery Date");
			del.setBounds(160, 120, 120, 50);
			del.setBackground(Color.WHITE);
			pan.add(del);

			JRadioButton pregID = new JRadioButton("Pregnancy ID");
			pregID.setBounds(290, 120, 120, 50);
			pregID.setBackground(Color.WHITE);
			pan.add(pregID);
			
			JRadioButton docID = new JRadioButton("Doctor ID");
			docID.setBounds(420, 120, 120, 50);
			docID.setBackground(Color.WHITE);
			pan.add(docID);

			searchValue = new JTextField();
			searchValue.setBounds(50, 60, 200, 50);
			pan.add(searchValue);
			searchValue.setColumns(10);

			JButton search = new JButton("SUBMIT");
			search.setBounds(270, 60, 200, 50);
			search.setBackground(Color.PINK);
			pan.add(search);

			ButtonGroup bg = new ButtonGroup();
			bg.add(due);
			bg.add(del);
			bg.add(pregID);
			bg.add(docID);

			frame.repaint();

			JTextPane displaypane = new JTextPane();
			displaypane.setBounds(50, 190, 670, 380);
			JScrollPane jsp = new JScrollPane(displaypane);
			jsp.setBounds(50, 190, 670, 380);

			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String column = "";

					if (due.isSelected()) {
						column = "DueDate";
						
					} else if (del.isSelected()) {
						column = "DeliveryDate";
						
					} else if (pregID.isSelected()) {
						column = "PregnancyID";

					} else if (docID.isSelected()) {
						column = "DoctorID";
					}
					
					JLabel hidden = new JLabel(column);

					displaypane.setText(searchPreg(hidden.getText(), searchValue.getText()));
					pan.add(jsp);
					frame.repaint();
				}
			});
		}
	
	
		//create the search appointment interface
		public void searchApt(JPanel panel) {
			
			panel.removeAll();

			JLabel selectRadio = new JLabel("Select Search Item:");
			selectRadio.setBounds(58, 10, 200, 50);
			panel.add(selectRadio);
			
			JRadioButton patID = new JRadioButton("Patient ID");
			patID.setBounds(50, 120, 100, 50);
			patID.setBackground(Color.WHITE);
			panel.add(patID);
			
			JRadioButton dID = new JRadioButton("Doctor ID");
			dID.setBounds(160, 120, 110, 50);
			dID.setBackground(Color.WHITE);
			panel.add(dID);
			
			JRadioButton radioDate = new JRadioButton("Date");
			radioDate.setBounds(270, 120, 120, 50);
			radioDate.setBackground(Color.WHITE);
			panel.add(radioDate);

			searchValue = new JTextField();
			searchValue.setBounds(50, 60, 200, 50);
			panel.add(searchValue);
			searchValue.setColumns(10);

			JButton search = new JButton("SUBMIT");
			search.setBounds(270, 60, 200, 50);
			search.setBackground(Color.PINK);
			panel.add(search);

			ButtonGroup bg = new ButtonGroup();
			bg.add(patID);
			bg.add(dID);
			bg.add(radioDate);

			frame.repaint();

			JTextPane displaypane = new JTextPane();
			displaypane.setBounds(50, 190, 670, 380);
			JScrollPane jsp = new JScrollPane(displaypane);
			jsp.setBounds(50, 190, 670, 380);

			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String column = "";

					if (patID.isSelected()) {
						column = "PatientID";
						
					} else if (dID.isSelected()) {
						column = "DoctorID";
						
					} else if (radioDate.isSelected()) {
						column = "Date";
					}
					
					JLabel hidden = new JLabel(column);

					displaypane.setText(searchApt(hidden.getText(), searchValue.getText()));
					panel.add(jsp);
					frame.repaint();
				}
			});
		}
		
		
	// create the insert patient interface
	public void insertPatientFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel lblIsbn = new JLabel("Patient ID");
		lblIsbn.setBounds(58, 10, 200, 50);
		mainPanel.add(lblIsbn);

		JLabel lblBookTitle = new JLabel("Patient Name");
		lblBookTitle.setBounds(58, 110, 200, 50);
		mainPanel.add(lblBookTitle);

		patientID = new JTextField();
		patientID.setBounds(50, 60, 200, 50);
		mainPanel.add(patientID);
		patientID.setColumns(10);

		patientName = new JTextField();
		patientName.setColumns(10);
		patientName.setBounds(50, 160, 200, 50);
		mainPanel.add(patientName);

		JButton btnInsertData = new JButton("CREATE PATIENT");
		btnInsertData.setBounds(50, 230, 200, 50);
		btnInsertData.setBackground(Color.PINK);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPatient(Integer.parseInt(patientID.getText()), patientName.getText());
				JOptionPane.showMessageDialog(null, "The patient was added!");
				patientID.setText("");
				patientName.setText("");
				frame.repaint();
			}
		});
	}
	
	
	//create the insert pregnancy interface
	public void insertPregnancyFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel pregIDL = new JLabel("Pregnancy ID");
		pregIDL.setBounds(58, 10, 200, 50);
		mainPanel.add(pregIDL);

		JLabel DueDateL = new JLabel("Due Date");
		DueDateL.setBounds(58, 110, 200, 50);
		mainPanel.add(DueDateL);
		
		JLabel typeL = new JLabel("Current Pregnancy?");
		typeL.setBounds(58, 210, 200, 50);
		mainPanel.add(typeL);
		
		JLabel docIDL = new JLabel("Doctor ID");
		docIDL.setBounds(58, 310, 200, 50);
		mainPanel.add(docIDL);
		
		JLabel patientIDL = new JLabel("Patient ID");
		patientIDL.setBounds(58, 410, 200, 50);
		mainPanel.add(patientIDL);

		pregID = new JTextField();
		pregID.setBounds(50, 60, 200, 50);
		mainPanel.add(pregID);
		pregID.setColumns(10);

		dueDate = new JTextField();
		dueDate.setColumns(10);
		dueDate.setBounds(50, 160, 200, 50);
		mainPanel.add(dueDate);
		
		type = new JTextField();
		type.setColumns(10);
		type.setBounds(50, 260, 200, 50);
		mainPanel.add(type);
		
		docID = new JTextField();
		docID.setColumns(10);
		docID.setBounds(50, 360, 200, 50);
		mainPanel.add(docID);
		
		patientID = new JTextField();
		patientID.setColumns(10);
		patientID.setBounds(50, 460, 200, 50);
		mainPanel.add(patientID);

		JButton btnInsertData = new JButton("CREATE PREGNANCY");
		btnInsertData.setBounds(50, 530, 200, 50);
		btnInsertData.setBackground(Color.PINK);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPregnancy(Integer.parseInt(pregID.getText()), dueDate.getText(), Integer.parseInt(type.getText()), Integer.parseInt(docID.getText()), Integer.parseInt(patientID.getText()));
				JOptionPane.showMessageDialog(null, "The pregnancy was added!");
				pregID.setText("");
				dueDate.setText("");
				type.setText("");
				docID.setText("");
				patientID.setText("");
				frame.repaint();
			}
		});
	}
	
	
	//create the insert appointment interface
	public void insertAptFunction(JPanel mainPanel) {

		mainPanel.removeAll();
		JLabel AIDL = new JLabel("Appointment ID");
		AIDL.setBounds(58, 10, 200, 50);
		mainPanel.add(AIDL);
		
		JLabel dateL = new JLabel("Date");
		dateL.setBounds(58, 110, 200, 50);
		mainPanel.add(dateL);

		JLabel timeL = new JLabel("Time");
		timeL.setBounds(58, 210, 200, 50);
		mainPanel.add(timeL);
		
		JLabel sumL = new JLabel("Summary");
		sumL.setBounds(278, 10, 200, 50);
		mainPanel.add(sumL);
		
		JLabel PIDL = new JLabel("Patient ID");
		PIDL.setBounds(58, 310, 200, 50);
		mainPanel.add(PIDL);
		
		JLabel DIDL = new JLabel("Doctor ID");
		DIDL.setBounds(58, 410, 200, 50);
		mainPanel.add(DIDL);

		AID = new JTextField();
		AID.setBounds(50, 60, 200, 50);
		mainPanel.add(AID);
		AID.setColumns(10);
		
		date = new JTextField();
		date.setBounds(50, 160, 200, 50);
		mainPanel.add(date);
		date.setColumns(10);

		time = new JTextField();
		time.setColumns(10);
		time.setBounds(50, 260, 200, 50);
		mainPanel.add(time);
		
		Sum = new JTextArea();
		Sum.setColumns(10);
		Sum.setBorder(new LineBorder(Color.BLACK, 1));
		Sum.setBounds(270, 60, 449, 449);
		mainPanel.add(Sum);
		
		PID = new JTextField();
		PID.setColumns(10);
		PID.setBounds(50, 360, 200, 50);
		mainPanel.add(PID);
		
		DID = new JTextField();
		DID.setColumns(10);
		DID.setBounds(50, 460, 200, 50);
		mainPanel.add(DID);

		JButton btnInsertData = new JButton("CREATE APPOINTMENT");
		btnInsertData.setBounds(50, 530, 200, 50);
		btnInsertData.setBackground(Color.PINK);
		mainPanel.add(btnInsertData);

		frame.repaint();
		
		btnInsertData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertApt(Integer.parseInt(AID.getText()), date.getText(), time.getText(), Sum.getText(), Integer.parseInt(PID.getText()), Integer.parseInt(DID.getText()));
				JOptionPane.showMessageDialog(null, "The appointment was added!");
				docID.setText("");
				time.setText("");
				PID.setText("");
				DID.setText("");
				frame.repaint();
			}
		});
	}
	
	
	//search patient methods
	public String searchPatient(String col, String s) {
		String Query;
		String mystr;
		if (s.equalsIgnoreCase("all")) {
			
			Query = "Select p.Name, pr.PregnancyID, d.Name, m.MedName, a.Date from patient p, pregnancy pr, doctor d, medication m, appointment a, prescription pre where p.PatientID = pr.PatientID and pr.DoctorID = d.DoctorID and pr.DoctorID = m.DoctorID and a.PatientID = p.PatientID and pre.PatientID = p.PatientID";
			
		} else if (col.equalsIgnoreCase("DueDate")) {
			
			Query = "Select p.Name, pr.PregnancyID, d.Name, m.MedName, a.Date from patient p, pregnancy pr, doctor d, medication m, appointment a, prescription pre where p.PatientID = pr.PatientID and pr.DoctorID = d.DoctorID and pr.DoctorID = m.DoctorID and a.PatientID = p.PatientID and pre.PatientID = p.PatientID and pr.type = '1' and p.Name like \"%" + s + "%\"";
			
		} else if (col.equalsIgnoreCase("DeliveryDate")) {
			
			Query = "Select p.Name, pr.PregnancyID, d.Name, m.MedName, a.Date from patient p, pregnancy pr, doctor d, medication m, appointment a, prescription pre where p.PatientID = pr.PatientID and pr.DoctorID = d.DoctorID and pr.DoctorID = m.DoctorID and a.PatientID = p.PatientID and pre.PatientID = p.PatientID and pr.type = '0' and p.Name like \"%" + s + "%\"";
		
		} else {
			
			Query = "Select p.Name, pr.PregnancyID, d.Name, m.MedName, a.Date from patient p, pregnancy pr, doctor d, medication m, appointment a, prescription pre where p.PatientID = pr.PatientID and pr.DoctorID = d.DoctorID and pr.DoctorID = m.DoctorID and a.PatientID = p.PatientID and pre.PatientID = p.PatientID and p.Name like \"%" + s + "%\"";
		}

		mystr = "Patients found in the database: \n \n";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy", "root", "password");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next())
				mystr += "Name: " + rs.getString(1) + "\n" + 
						 "PregnancyID: " + rs.getString(2) + "\n" + 
						 "Doctor: " + rs.getString(3) + "\n" +
						 "Medication: " + rs.getString(4) + "\n" +
						 "Appointment: " + rs.getString(5) + "\n\n";
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (mystr.equals("Patients found in the database: \n \n")) {
			mystr = "No patients found";
		} return mystr;
	}

	
	// Search pregnancy method
	public String searchPreg(String col, String s) {
		
		String Query;
		String mystr;
		
		if (s.equalsIgnoreCase("all")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		Order BY p.PatientID";
			
		} else if (col.equalsIgnoreCase("DueDate")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.DueDate = '" + s + "'\r\n" +
					"		AND " + s + " > CurDate()\r\n";

		} else if (col.equalsIgnoreCase("DeliveryDate")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.DueDate = '" + s + "'\r\n" +
					"		AND " + s + " < CurDate()\r\n";
		
		} else if (col.equalsIgnoreCase("PregnancyID")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND pr.PregnancyID = " + s;
			
		} else if (col.equalsIgnoreCase("DoctorID")) {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID\r\n" + 
					"		AND d.DoctorID = " + s;
		} else {
			Query = "SELECT p.Name, pr.PregnancyID, d.Name, pr.DueDate\r\n" + 
					"		FROM patient p, pregnancy pr, doctor d\r\n" + 
					"		WHERE p.PatientID = pr.PatientID\r\n" + 
					"		AND pr.DoctorId = d.doctorID";
		}

		mystr = "Pregnancies found in the database: \n \n";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy", "root", "password");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(Query);

			while (rs.next())
				mystr += "Name: " + rs.getString(1) + "\n" + 
						 "Pregnancy ID: " + rs.getString(2) + "\n" + 
						 "Doctor: " + rs.getString(3) + "\n" + 
						 "Due Date: " + rs.getString(4) + "\n\n";
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (mystr.equals("Pregnancies found in the database: \n \n")) {
			mystr = "No pregnancies found";
		} return mystr;
	}
	
	//search appointment method
	public String searchApt(String col, String s) {
		String Query;
		String mystr;
		if (s.equalsIgnoreCase("all")) {
			
			Query = "select a.Date, a.Time, p.Name, d.Name from appointment a, patient p, doctor d where a.PatientID = p.PatientID and a.DoctorID = d.DoctorID";
			
		} else if (col.equalsIgnoreCase("PatientID")) {
			
			Query = "select a.Date, a.Time, p.Name, d.Name from appointment a, patient p, doctor d where a.PatientID = p.PatientID and a.DoctorID = d.DoctorID and a.PatientID = " + s;
			
		} else if (col.equalsIgnoreCase("DoctorID")) {
			
			Query = "select a.Date, a.Time, p.Name, d.Name from appointment a, patient p, doctor d where a.PatientID = p.PatientID and a.DoctorID = d.DoctorID and a.DoctorID = " + s;
			
		} else if (col.equalsIgnoreCase("Date")){
			
			Query = "select a.Date, a.Time, p.Name, d.Name from appointment a, patient p, doctor d where a.PatientID = p.PatientID and a.DoctorID = d.DoctorID and a.Date = '" + s +"'";
			
		} else {
			
			Query = "select a.Date, a.Time, p.Name, d.Name from appointment a, patient p, doctor d where a.PatientID = p.PatientID and a.DoctorID = d.DoctorID";
		}

		mystr = "Appointments found in the database: \n \n";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy", "root", "password");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next())
				mystr += "Date: " + rs.getString(1) + "\n" + 
						 "Time: " + rs.getString(2) + "\n" + 
						 "Patient: " + rs.getString(3) + "\n" + 
						 "Doctor: " + rs.getString(4) + "\n\n";
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (mystr.equals("Appointments found in the database: \n \n")) {
			mystr = "No appointments found";
		} return mystr;
	}
	
	
	//insert patient query
	public void insertPatient(int patientID, String patientName) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","password");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into patient values('"+patientID+"', '"+patientName+"', '0')";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	
	//insert appointment query
	public void insertApt(int AID, String date, String time, String summary, int PID, int DID) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","password");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into appointment values('"+AID+"', '"+date+"', '"+time+"', '"+summary+"', '"+PID+"', '"+DID+"')";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	
	//insert pregnancy query
	public void insertPregnancy(int pregnancyID, String dueDate, int type, int doctorID, int patientID) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pregnancy","root","password");  
			
			Statement stmt=con.createStatement();  
			String Query = "insert into pregnancy values('"+pregnancyID+"', '"+dueDate+"', '"+type+"', '"+doctorID+"', '"+patientID+"')";
			
			stmt.executeUpdate(Query);
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
}