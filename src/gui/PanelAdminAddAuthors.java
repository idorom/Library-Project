package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import LibExceptions.AuthorAlreadyExistExceptionGui;
import LibExceptions.AuthorNotExistException;
import LibExceptions.addUserLibraryExceptionGui;
import Model.Author;


import javax.swing.DefaultComboBoxModel;
import Utils.Topic;
import View.ViewModelHelper;

public class PanelAdminAddAuthors extends JPanel {


	private JTextField firstNameField;

	private JTextField lastNameField;

	private LibraryGui libraryGui;

	private JTable authorsTable;

	private DefaultTableModel authorsTableModel;

	private JButton btnBack;

	private JComboBox<Topic> Topics;


	/**
	 * Create the panel. 
	 */
	public PanelAdminAddAuthors(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setVisible(true);
		setBackground(new Color(192, 192, 192));
		setLayout(null);


		JLabel lblAuhtors = new JLabel("Auhtors");
		lblAuhtors.setBounds(35, -1, 119, 46);
		lblAuhtors.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblAuhtors);

		JLabel lblTopic = new JLabel("Topic");
		lblTopic.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTopic.setBounds(35, 120, 61, 25);
		add(lblTopic);

		JLabel lblfirstName = new JLabel("First Name");
		lblfirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblfirstName.setBounds(35, 80, 89, 25);
		add(lblfirstName);

		JLabel lbllastName = new JLabel("Last Name");
		lbllastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbllastName.setBounds(306, 80, 89, 25);
		add(lbllastName);

		//--Libririan's TextFields---
		firstNameField = new JTextField();
		firstNameField.setBounds(127, 82, 140, 20);
		add(firstNameField);
		firstNameField.setColumns(10);

		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		lastNameField.setBounds(405, 82, 140, 20);
		add(lastNameField);


		String[] headersNames = new String[] {
				"First name" , "Last name" ,"Topic"
		};

		authorsTableModel = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		authorsTable = new JTable(authorsTableModel);
		authorsTable.setEnabled(true);
		authorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		authorsTable.setRowSelectionAllowed(true);	
		authorsTableModel.setColumnIdentifiers(headersNames);


		authorsTable.setSize(700, 100);
		authorsTable.setVisible(true);

		JScrollPane pane;

		pane= new JScrollPane(authorsTable);
		pane.setBounds(35, 246, 736, 342);
		pane.setEnabled(false);

		add(pane);


		Topics = new JComboBox<Topic>();
		Topics.setModel(new DefaultComboBoxModel<Topic>(Topic.values()));
		Topics.setBounds(127, 122, 140, 20);
		add(Topics);
		Topics.setEditable(true);


		JButton librarianAddNewUser = new JButton("Add Author");
		librarianAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Author a=null;
				try {
					//check if fields haven't been filled
					String ErrMsg="";
					if (firstNameField.getText().isEmpty()) ErrMsg="\nFirst Name";
					if (lastNameField.getText().isEmpty()) ErrMsg+="\nLast Name";

					if (!ErrMsg.isEmpty())
						throw new addUserLibraryExceptionGui("Missing or invalid data in the following field(s): " + ErrMsg);

					a= new Author(firstNameField.getText(), lastNameField.getText(), (Topic)Topics.getSelectedItem());

					//Add new author to Library's data
					libraryGui.getLibrarySys().addAuther(a); //throws AuthorAlreadyExistExceptionGui

					//update the system that change has been happened
					libraryGui.DataWasChanged();
					ClearFields();
					//reload again authorsTable
					ReloadeAuthors();

					//JOptionPane that tell if author added successfully
					JOptionPane.showConfirmDialog(null, "Author " + a.toString()+" was added successfully", "Add Author"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}catch (addUserLibraryExceptionGui exception1) {
					return;
				}catch(AuthorAlreadyExistExceptionGui exception2){
					return;
				}

			}
		});
		librarianAddNewUser.setBounds(643, 212, 128, 23);
		add(librarianAddNewUser);

		//Button to remove Author if it has been pushed
		JButton btRemoveReader = new JButton("Remove Author");
		btRemoveReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Author a=null;

				//notified that the user hasn't selected authorsTable's row
				if (authorsTable.getSelectedRow()<0)
				{
					JOptionPane.showConfirmDialog(null, "Please select a author to remove" 
							, "Remove selected author"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				try {
					//from the selected row take the full name of the author
					String firstName= authorsTable.getValueAt(authorsTable.getSelectedRow(), 0).toString();
					String lastName= authorsTable.getValueAt(authorsTable.getSelectedRow(), 1).toString();

					if(firstName==null || lastName==null)
						return;

					//JOptionPane that ask if the user really wants to removed the author
					int input = JOptionPane.showConfirmDialog(null, "Do you want to remove " + firstName +" " + lastName +"?" 
							, "Remove Selected author",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);				

					if (input == JOptionPane.NO_OPTION) return;	

					//find the real author in the Library's data					
					a=libraryGui.getLibrarySys().getAuthorByName(firstName, lastName); 

					//Remove the author from the system
					libraryGui.getLibrarySys().removeAuthor(a); //throws AuthorNotExistException

					//update the system that change has been happened
					libraryGui.DataWasChanged();

					//reload again authorsTable
					ReloadeAuthors();

					//JOptionPane that tell if author was removed successfully
					JOptionPane.showConfirmDialog(null, "Author " + a.toString()+" was removed successfully", "Remove author"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} catch (AuthorNotExistException exception)
				{
					return;
				}
			}
		});
		btRemoveReader.setBounds(35, 212, 128, 23);
		add(btRemoveReader);
		ReloadeAuthors();

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
		Topics.setSelectedIndex(0);		
	}


	//reload authors from Library class to the table
	private void ReloadeAuthors()
	{
		ClearModelAndSetHeaders(new String[] {"First name" , "Last name" ,"Topic"});
		int i=0;


		if (libraryGui.getLibrarySys().getAuthors().size()>0) 
		{
			for(Author a: libraryGui.getLibrarySys().getAuthors()) 
			{	
				Object[] CurrentUser = new Object[] {
						a.getFirstName(), a.getLastName() , a.gettopic()
				};
				authorsTableModel.insertRow(i++, CurrentUser);			
			}
		}
		ViewModelHelper.SortTableOn2Columns(authorsTable, 0, 1);
	}

	//Clear old rows and Headers from the table and give new Headers to the table
	private void ClearModelAndSetHeaders(String[] Headers)
	{
		while (authorsTableModel.getRowCount() > 0) {
			authorsTableModel.removeRow(0);
		}

		authorsTableModel.setColumnIdentifiers(Headers);
		authorsTable.getTableHeader().setReorderingAllowed(false);//for not allow to move columns by the headers
	}

}