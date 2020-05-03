/**
 * This program ...
 * 
 * @author Yulia Thonippara (A20411313)
 * Final project for ITMD 411 Spring 2020
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Tickets extends JFrame implements ActionListener {
	
	// Class level member objects
	Dao dao = new Dao(); // for CRUD operations
	Boolean chkIfAdmin = null;

	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;

	public Tickets(Boolean isAdmin) {

		chkIfAdmin = isAdmin;
		createMenu();
		prepareGUI();

	}

	private void createMenu() {

		/* Initialize sub menu items */

		// Initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// Add to File main menu item
		mnuFile.add(mnuItemExit);

		// Initialize first sub menu items for Admin main menu
		mnuItemUpdate = new JMenuItem("Update Ticket");
		// Add to Admin main menu item
		mnuAdmin.add(mnuItemUpdate);

		// Initialize second sub menu items for Admin main menu
		mnuItemDelete = new JMenuItem("Delete Ticket");
		// Add to Admin main menu item
		mnuAdmin.add(mnuItemDelete);

		// Initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// Add to Ticket Main menu item
		mnuTickets.add(mnuItemOpenTicket);

		// Initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// Add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);

		// Initialize any more desired sub menu items below

		/* Add action listeners for each desired menu item */
		mnuItemExit.addActionListener(this);
		mnuItemUpdate.addActionListener(this);
		mnuItemDelete.addActionListener(this);
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		
		/*
		 * Continue implementing any other desired sub menu items (like 
		 * for update and delete sub menus for example) with similar 
		 * syntax & logic as shown above*
		 */
	}

	private void prepareGUI() {

		// Create JMenu bar
		JMenuBar bar = new JMenuBar();
		bar.add(mnuFile); // Add main menu items in order, to JMenuBar
		bar.add(mnuAdmin);
		bar.add(mnuTickets);
		// Add menu bar components to frame
		setJMenuBar(bar);

		addWindowListener(new WindowAdapter() {
			// Define a window close operation
			public void windowClosing(WindowEvent wE) {
				System.exit(0);
			}
		});
		
		// Set frame options
		setSize(400, 400);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.exit(0);
		} else if (e.getSource() == mnuItemOpenTicket) {
			
			// Get ticket information
			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");

			// Insert ticket information to database
			int id = dao.insertRecords(ticketName, ticketDesc);

			// Display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("Ticket ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
			} else
				System.out.println("Ticket cannot be created!!!");
		}

		else if (e.getSource() == mnuItemViewTicket) {
			
			// Retrieve all tickets details for viewing in JTable
			try {

				/* Use JTable built in functionality to build a table model and
				 * display the table model off your result set!!! 
				 */
				JTable jt = new JTable(TicketsJTable.buildTableModel(dao.readRecords()));
				jt.setBounds(30, 40, 200, 400);
				JScrollPane sp = new JScrollPane(jt);
				add(sp);
				setVisible(true); // Refreshes or repaints frame on screen

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		/*
		 * Continue implementing any other desired sub menu items (like for update and
		 * delete sub menus for example) with similar syntax & logic as shown above
		 */
	}
}