package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import LibExceptions.ReaderAlreadyExistExceptionGui;
import LibExceptions.ReaderNotExistException;
import LibExceptions.addUserLibraryExceptionGui;
import Model.Reader;
import Utils.Gender;
import View.ViewModelHelper;


public class PanelLibrarianReaders extends JPanel {


	private JTextField firstNameField;
	private JTextField userIDField;
	private JPasswordField passWordField;
	private JTextField lastNameField;
	private JPasswordField confirmPassWordField;
	
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JButton btnLibrarianUpdateReader;
	private ButtonGroup genderContainer;
	//private JButton ChoosePhoto;
	private PhotoButton ChoosePhoto;
	private JButton btnBack;

	//private DefaultTableModel model;
	private LibraryGui libraryGui;

	private JTable readersTable;

	private DefaultTableModel readersTableModel;
	/**
	 * Create the panel. 
	 */
	public PanelLibrarianReaders(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setVisible(false);
		setBackground(new Color(192, 192, 192));
		setLayout(null);

		//Button for choosing an image
		
		ChoosePhoto=new PhotoButton();
		ChoosePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reader reader=null;
				
				int row=readersTable.getSelectedRow();
				if (row>=0) reader=libraryGui.getLibrarySys().getReaderByName(
						readersTable.getValueAt(row, 1).toString(), readersTable.getValueAt(row, 2).toString());
				
				if (ChoosePhoto.SelectPicture(reader)== JFileChooser.APPROVE_OPTION)
					libraryGui.DataWasChanged();
			}
		});		
		ChoosePhoto.setBounds(35, 43, 165, 156);
		add(ChoosePhoto);
		

		JButton btNewReader = new JButton("Clear Fields for Adding New Reader");
		btNewReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields();
			}
		});
		btNewReader.setBounds(301, 182, 254, 23);
		add(btNewReader);


		JLabel newReaderAdd = new JLabel("Readers");
		newReaderAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		newReaderAdd.setBounds(35, 8, 81, 29);
		add(newReaderAdd);

		JLabel lblIDNew = new JLabel("ID");
		lblIDNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIDNew.setBounds(216, 111, 44, 20);
		add(lblIDNew);

		JLabel lblfirstName = new JLabel("First Name");
		lblfirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblfirstName.setBounds(216, 73, 69, 29);
		add(lblfirstName);

		JLabel lbllastName = new JLabel("Last Name");
		lbllastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbllastName.setBounds(469, 67, 100, 40);
		add(lbllastName);

		JLabel lblpassWord = new JLabel("Password");
		lblpassWord.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblpassWord.setBounds(469, 101, 100, 40);
		add(lblpassWord);

		JLabel lblconfirmPassword = new JLabel("Confirm Password");
		lblconfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblconfirmPassword.setBounds(469, 142, 127, 26);
		add(lblconfirmPassword);


		genderContainer = new ButtonGroup();
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnMale.setBounds(291, 141, 63, 29);
		rdbtnMale.setOpaque(false);
		add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnFemale.setBounds(362, 141, 69, 29);
		rdbtnFemale.setOpaque(false);
		add(rdbtnFemale);

		genderContainer.add(rdbtnFemale);
		genderContainer.add(rdbtnMale);
		JLabel GenderNew = new JLabel("Gender");
		GenderNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GenderNew.setBounds(216, 145, 69, 20);
		add(GenderNew);


		//--Libririan's TextFields---
		firstNameField = new JTextField();
		firstNameField.setBounds(291, 77, 140, 20);
		add(firstNameField);
		firstNameField.setColumns(10);

		userIDField = new JTextField();
		userIDField.setColumns(10);
		userIDField.setBounds(291, 111, 140, 20);
		add(userIDField);

		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		lastNameField.setBounds(594, 77, 140, 20);
		add(lastNameField);

		passWordField = new JPasswordField();
		passWordField.setColumns(10);
		passWordField.setBounds(594, 111, 140, 20);
		add(passWordField);

		confirmPassWordField = new JPasswordField();
		confirmPassWordField.setColumns(10);
		confirmPassWordField.setBounds(594, 145, 140, 20);
		add(confirmPassWordField);


		String[] headersNames = new String[] {
				"ID" , "First name" , "Last name" , "Gender", "Register date", "Items Read"
		};

		readersTableModel = new DefaultTableModel(0,6){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		readersTable = new JTable(readersTableModel);
		readersTable.setLocation(74, 383);
		readersTable.setEnabled(true);
		readersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		readersTable.setRowSelectionAllowed(true);
		readersTableModel.setColumnIdentifiers(headersNames);

		readersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			String firstName=null;
			String lastName=null;
			Reader reader=null;
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int r=readersTable.getSelectedRow();

				if (r<0) return;				
				btnLibrarianUpdateReader.setText("Update Selected Reader");

				firstName=(readersTable.getValueAt(r, 1).toString());
				lastName=(readersTable.getValueAt(r, 2).toString());
				reader=libraryGui.getLibrarySys().getReaderByName(firstName, lastName);

				userIDField.setText(reader.getId());
				firstNameField.setText(reader.getFirstName());
				lastNameField.setText(reader.getLastName());	
				passWordField.setText(reader.getPassword());

				if(reader.getUserIcon()!=null) {
					ChoosePhoto.setIcon(reader.getUserIcon());					
				}
				else ChoosePhoto.setIcon(null);

				confirmPassWordField.setText(reader.getPassword());

				if(reader.getGender().equals(Gender.Male))
					rdbtnMale.setSelected(true);
				else
					rdbtnFemale.setSelected(true);

				firstNameField.setEditable(false);
				lastNameField.setEditable(false);
				userIDField.setEditable(false);

				System.out.println("selected row " + r);
				System.out.println(userIDField.getText());
				System.out.println("selected row " + r);
				System.out.println(userIDField.getText());

			}
		});

		readersTable.getTableHeader().setReorderingAllowed(false);
		readersTable.setSize(700, 100);
		readersTable.setVisible(true);

		JScrollPane pane;

		pane= new JScrollPane(readersTable);
		pane.setBounds(35, 271, 736, 328);
		//		pane.setLocation(74, 383);
		pane.setEnabled(false);

		add(pane);

		//Button to add new OR update reader
		btnLibrarianUpdateReader = new JButton("Create New Reader");
		btnLibrarianUpdateReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reader r=null;
				try {
					//check if fields haven't been filled
					String ErrMsg="";
					
					if (firstNameField.getText().isEmpty()) ErrMsg="\nFirst Name";
					if (lastNameField.getText().isEmpty()) ErrMsg+="\nLast Name";
					if(userIDField.getText().isEmpty())ErrMsg+="\nID";
					if(passWordField.getText().isEmpty())ErrMsg+="\nPassword";
					if(confirmPassWordField.getText().isEmpty())ErrMsg+="\nConfirm Password";
					if(!rdbtnFemale.isSelected() && !rdbtnMale.isSelected()) ErrMsg+="\nGender";
					if (!ErrMsg.isEmpty())
						throw new addUserLibraryExceptionGui("Missing or invalid data in the following field(s): " + ErrMsg);

					//check if userIDField have 9 digit, also make sure reader coudn't has ID: "Admin" in all forms 
					if (!userIDField.getText().matches("^\\d\\d\\d\\d\\d\\d\\d\\d\\d$"))  
						throw new addUserLibraryExceptionGui("Invalid ID.\nMust be 9 digits number.");
					
					//check if passWordField field not equals to confirmPassWordField
					if (!passWordField.getText().equals(confirmPassWordField.getText()))
						throw new addUserLibraryExceptionGui("Passwords dose not match.\nPlease enter same password in both fields");

					
					r= libraryGui.getLibrarySys().getReaderByName(firstNameField.getText(), lastNameField.getText());

					//to update existed reader
					if(!userIDField.isEditable()) 
					{						
						Gender gender= Gender.Male;
						if (rdbtnFemale.isSelected()) gender=Gender.Female;	

						r.setGender(gender);
						r.setPassword(passWordField.getText());
						r.setUserIcon((ImageIcon) ChoosePhoto.getIcon());
						ReloadeReaders();
						libraryGui.DataWasChanged();
						
						//JOptionPane that tell if reader updated successfully						
						JOptionPane.showConfirmDialog(null, "Reader " + r.toString()+" was updated successfully", "Update reader"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					// check that the that a reader with the same name or ID does not exists
					if (r!=null) throw new addUserLibraryExceptionGui("User with the same name already exists"); 							
					else if(libraryGui.getLibrarySys().getUserByID(userIDField.getText())!=null)
						throw new addUserLibraryExceptionGui("User with the same ID already exists");
					
					// Add the new reader
					Gender gender= Gender.Male;
					if (rdbtnFemale.isSelected()) gender=Gender.Female;

					r = new Reader(firstNameField.getText(), lastNameField.getText(), passWordField.getText(), userIDField.getText(),  gender);
					r.setUserIcon((ImageIcon) ChoosePhoto.getIcon());

					//to add a new reader
					libraryGui.getLibrarySys().addReader(r); //Throws ReaderAlreadyExistExceptionGui									
					libraryGui.DataWasChanged();
					ReloadeReaders();
					clearFields();
					
					//JOptionPane that tell if reader added successfully
					JOptionPane.showConfirmDialog(null, "Reader " + r.toString()+" was added successfully", "Add reader"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					
				}catch (addUserLibraryExceptionGui exception1) {
					return;
				}catch(ReaderAlreadyExistExceptionGui exception2){
					return;
				}

			}
		});
		btnLibrarianUpdateReader.setBounds(600, 234, 171, 23);
		add(btnLibrarianUpdateReader);

		//Button to remove selected Reader from Library data
		JButton btRemoveReader = new JButton("Remove Selected Reader");
		btRemoveReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reader r=null;

				if (readersTable.getSelectedRow()<0)
				{
					JOptionPane.showConfirmDialog(null, "Please select a reader to remove" 
							, "Remove selected reader"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				String firstName= readersTable.getValueAt(readersTable.getSelectedRow(), 1).toString();
				String lastName= readersTable.getValueAt(readersTable.getSelectedRow(), 2).toString();

				if(firstName==null || lastName==null)
					return;

				int input = JOptionPane.showConfirmDialog(null, "Do you want to remove " + firstName +" " + lastName +"?" 
						, "Remove Selected Reader",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);				

				if (input == JOptionPane.NO_OPTION) return;

				try {
					r=libraryGui.getLibrarySys().getReaderByName(firstName, lastName);	
					
					//remove 'r' from Library data
					libraryGui.getLibrarySys().removeReader(r); //throw ReaderNotExistException
					libraryGui.DataWasChanged();
					ReloadeReaders();
					clearFields();
					
					//JOptionPane that tell if author remove successfully
					JOptionPane.showConfirmDialog(null, "Reader " + r.toString()+" was removed successfully", "Remove reader"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} catch (ReaderNotExistException e) {
					return;
				}
			}
		});
		btRemoveReader.setBounds(35, 234, 186, 23);
		add(btRemoveReader);

		
		//Return to the main panel of the user 
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);

		ReloadeReaders();
	}
	
	public void InitPanel()
	{
		clearFields();
		ReloadeReaders();
	}

	//reload readers from Library class to the table
	private void ReloadeReaders()
	{
		readersTable.clearSelection();
		while (readersTableModel.getRowCount() > 0) {
			readersTableModel.removeRow(0);
		}

		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

		int i=0;
				
		for(Reader rb: libraryGui.getLibrarySys().getReaders()) 
		{	
			JButton button= new JButton();

			Object[] CurrentUser = new Object[] {
					rb.getId() , rb.getFirstName() , rb.getLastName() , rb.getGender().toString()
					, sdfr.format(rb.getJoiningDate()).toString(), rb.getItems().size() 
			};
			readersTableModel.insertRow(i++, CurrentUser);			
		}
		ViewModelHelper.SortTableOn2Columns(readersTable, 1, 2);
	}
	

	//Clear All the Fields in the panel
	public void clearFields()
	{
		btnLibrarianUpdateReader.setText("Create New Reader");
		userIDField.setText("");
		firstNameField.setText("");
		lastNameField.setText("");	
		passWordField.setText("");				
		confirmPassWordField.setText("");
		rdbtnMale.setSelected(false);
		rdbtnFemale.setSelected(false);
		genderContainer.clearSelection();
		
		ChoosePhoto.setIcon(null);

		if(!userIDField.isEditable())
		{
			userIDField.setEditable(true);
			firstNameField.setEditable(true);
			lastNameField.setEditable(true);	
			passWordField.setEditable(true);				
			confirmPassWordField.setEditable(true);

		}
		readersTable.clearSelection();
	}
}