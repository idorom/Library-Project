package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import LibExceptions.LibrarianNotExistException;
import LibExceptions.addLibrarianExceptionGui;
import LibExceptions.addUserLibraryExceptionGui;
import Model.Librarian;
import View.ViewModelHelper;


public class PanelAdminAddLibrarians extends JPanel {

	private JTextField firstNameField;
	
	private JTextField lastNameField;
		
	private JTextField userIDField;
	
	private JPasswordField passWordField;
	
	private JPasswordField confirmPassWordField;

	private LibraryGui libraryGui;
	
	private JTable librariansTable;
	
	private DefaultTableModel librariansTableModel;

	private JButton btnBack;

	/**
	 * Create the panel. 
	 */
	public PanelAdminAddLibrarians(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setVisible(true);
		setBackground(new Color(192, 192, 192));
		setLayout(null);


		JLabel lblLibrarians = new JLabel("Librarians");
		lblLibrarians.setBounds(35, -1, 116, 46);
		lblLibrarians.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblLibrarians);

		JLabel lblIDNew = new JLabel("User ID");
		lblIDNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIDNew.setBounds(35, 112, 81, 40);
		add(lblIDNew);

		JLabel lblfirstName = new JLabel("First Name");
		lblfirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblfirstName.setBounds(35, 72, 100, 40);
		add(lblfirstName);

		JLabel lbllastName = new JLabel("Last Name");
		lbllastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbllastName.setBounds(310, 72, 100, 40);
		add(lbllastName);

		JLabel lblpassWord = new JLabel("Password");
		lblpassWord.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblpassWord.setBounds(310, 112, 100, 40);
		add(lblpassWord);

		JLabel lblconfirmPassword = new JLabel("Confirm Password");
		lblconfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblconfirmPassword.setBounds(286, 152, 116, 40);
		add(lblconfirmPassword);



		//--Libririan's TextFields---
		firstNameField = new JTextField();
		firstNameField.setBounds(127, 82, 140, 20);
		firstNameField.setColumns(10);
		add(firstNameField);


		userIDField = new JTextField();
		userIDField.setColumns(10);
		userIDField.setBounds(127, 122, 140, 20);
		add(userIDField);

		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		lastNameField.setBounds(405, 82, 140, 20);
		add(lastNameField);

		passWordField = new JPasswordField();
		passWordField.setColumns(10);
		passWordField.setBounds(405, 122, 140, 20);
		add(passWordField);

		confirmPassWordField = new JPasswordField();
		confirmPassWordField.setColumns(10);
		confirmPassWordField.setBounds(405, 162, 140, 20);
		add(confirmPassWordField);


		String[] headersNames = new String[] {
				"First name" , "Last name" ,"ID"
		};

		librariansTableModel = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		librariansTable = new JTable(librariansTableModel);
		librariansTable.setLocation(74, 383);
		librariansTable.setEnabled(true);
		librariansTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		librariansTable.setRowSelectionAllowed(true);	
		librariansTableModel.setColumnIdentifiers(headersNames);


		librariansTable.setSize(700, 100);
		librariansTable.setVisible(true);

		JScrollPane pane;

		pane= new JScrollPane(librariansTable);
		pane.setBounds(35, 246, 652, 342);
		pane.setEnabled(false);

		add(pane);



		JButton librarianAddNewUser = new JButton("Add Librarian");
		librarianAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Librarian l=null;
				try {
					//check if fields haven't been filled
					String ErrMsg="";
					if (firstNameField.getText().isEmpty()) ErrMsg="\nFirst Name";
					if (lastNameField.getText().isEmpty()) ErrMsg+="\nLast Name";					
					if (userIDField.getText().isEmpty()) ErrMsg+="\nID";
					if (passWordField.getText().isEmpty()) ErrMsg+="\nPassword";
					if (confirmPassWordField.getText().isEmpty()) ErrMsg+="\nConfirm Password";
					if (!ErrMsg.isEmpty())
						throw new addUserLibraryExceptionGui("Missing or invalid data in the following field(s): " + ErrMsg);
					
					//check if passWordField field not equals to confirmPassWordField
					if (!passWordField.getText().equals(confirmPassWordField.getText()))
						throw new addUserLibraryExceptionGui("Passwords dose not match.\nPlease enter same password in both fields");
					
					//valid that librarian coudn't have ID: "Admin" in all forms, and not already in the Library data
					if(libraryGui.getLibrarySys().getUserByID(userIDField.getText())!=null 
							|| userIDField.getText().toLowerCase().equals("admin"))
						throw new addUserLibraryExceptionGui("User with the same ID already exists");

					l= new Librarian(firstNameField.getText(),  lastNameField.getText(), userIDField.getText(), passWordField.getText());
					libraryGui.getLibrarySys().addLibrarian(l); //throws addLibrarianExceptionGui.java
					libraryGui.DataWasChanged();
					ClearFields();
					Reloadelibrarians();
					
					//JOptionPane that tell if librarian added successfully
					JOptionPane.showConfirmDialog(null, "Librarian " + l.toString()+" was added successfully", "Add librarian"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}catch (addUserLibraryExceptionGui exception1) {
					return;
				}catch(addLibrarianExceptionGui exception2){
					return;
				}

			}
		});
		librarianAddNewUser.setBounds(552, 212, 135, 23);
		add(librarianAddNewUser);


		JButton btRemoveReader = new JButton("Remove Librarian");
		btRemoveReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Librarian l=null;

				if (librariansTable.getSelectedRow()<0)
				{
					JOptionPane.showConfirmDialog(null, "Please select a librarian to remove" 
							, "Remove selected librarian"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				try {
					String firstName= librariansTable.getValueAt(librariansTable.getSelectedRow(), 0).toString();
					String lastName= librariansTable.getValueAt(librariansTable.getSelectedRow(), 1).toString();
					String ID= librariansTable.getValueAt(librariansTable.getSelectedRow(), 2).toString();

					if(firstName==null || lastName==null || ID==null)
						return;

					int input = JOptionPane.showConfirmDialog(null, "Do you want to remove " + firstName +" " + lastName +"?" 
							, "Remove Selected librarian",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);				

					if (input == JOptionPane.NO_OPTION) return;

					l= new Librarian(firstName, lastName, ID, ""); 
					libraryGui.getLibrarySys().removeLibrarian(l); //throws LibrarianNotExistException
					libraryGui.DataWasChanged();
					Reloadelibrarians();
					
					//JOptionPane that tell if librarian was removed successfully
					JOptionPane.showConfirmDialog(null, "Librarian " + l.toString()+" was removed successfully", "Remove librarian"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} catch (LibrarianNotExistException exception)
				{
					return;
				}
			}
		});

		btRemoveReader.setBounds(35, 212, 135, 23);
		add(btRemoveReader);	
		Reloadelibrarians();

		//Return to the main panel of the user 
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);
	}

	
	//Initialize all the components when the user enter to the panel
	public void InitPanel()
	{	
		ClearFields();
	}
	
	
	//Clear All the Fields in the panel
	private void ClearFields()
	{  
		firstNameField.setText("");
		lastNameField.setText("");	
		userIDField.setText("");						
		passWordField.setText("");
		confirmPassWordField.setText("");	
	}

	//reload librarians from Library class to the table
	private void Reloadelibrarians()
	{
		
		ClearModelAndSetHeaders(new String[] {"First name" , "Last name" ,"ID"});
		int i=0;
		for(Librarian l: libraryGui.getLibrarySys().getLibrarians())
		{	
			Object[] CurrentUser = new Object[] {
					l.getFirstName(), l.getLastName(), l.getId() 
			};
			librariansTableModel.insertRow(i++, CurrentUser);			
		}
		
		ViewModelHelper.SortTableOn2Columns(librariansTable, 0, 1);		
	}
	
	//Clear old rows and Headers from the table and give new Headers to the table
	private void ClearModelAndSetHeaders(String[] Headers)
	{
		while (librariansTableModel.getRowCount() > 0) {
			librariansTableModel.removeRow(0);
		}
		librariansTableModel.setColumnIdentifiers(Headers);
		librariansTable.getTableHeader().setReorderingAllowed(false);//for not allow to move columns by the headers
	}
}