package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Model.ArithmeticMean;
import Model.Author;
import Model.Encyclopedia;
import Model.LibraryCollection;
import Model.LibraryItem;
import Model.Magazine;
import Utils.Topic;
import View.ViewModelHelper;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;

public class PanelLibrarianCollections extends JPanel {

	private LibraryGui libraryGui;

	private DefaultTableModel itemsTableModel;

	private JTable itemsTable;

	private JScrollPane itemPane;

	private JRadioButton rbCollectionMagazine;

	private JRadioButton rbCollectionEncyclopedia;

	private JComboBox<String> actionComboBox;

	private JComboBox<String> CollectionComboBox;

	private ButtonGroup buttonGroup;

	private JButton btnBack;

	private JLabel lblObject;
	private JLabel lblTitle;

	/**
	 * Create the panel.
	 */
	public PanelLibrarianCollections(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setVisible(true);
		setBackground(new Color(192, 192, 192));
		setLayout(null);


		itemsTableModel = new DefaultTableModel(0,6);
		itemsTable = new JTable(itemsTableModel);
		itemsTable.setLocation(74, 383);
		itemsTable.setEnabled(true);
		itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		

		String[] headersNames = new String[] {
				"Item name" , "Author" , "Current score" , "Topic"
		};

		itemsTableModel.setColumnIdentifiers(headersNames);

		itemsTable.setSize(700, 100);
		itemsTable.setVisible(true);


		itemPane= new JScrollPane(itemsTable);
		itemPane.setBounds(35, 172, 630, 258);
		itemPane.setEnabled(true);
		add(itemPane);

		//Return to the main panel of the user
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);


		CollectionHandler collectionHandler= new CollectionHandler();

		rbCollectionMagazine = new JRadioButton("Magazines");
		rbCollectionMagazine.setBounds(270, 49, 109, 23);
		rbCollectionMagazine.setOpaque(false);
		rbCollectionMagazine.addItemListener(collectionHandler);
		add(rbCollectionMagazine);

		rbCollectionEncyclopedia = new JRadioButton("Encyclopedias");
		rbCollectionEncyclopedia.setBounds(100, 49, 109, 23);
		rbCollectionEncyclopedia.setOpaque(false);
		rbCollectionEncyclopedia.addItemListener(collectionHandler);
		add(rbCollectionEncyclopedia);

		buttonGroup=new ButtonGroup();
		buttonGroup.add(rbCollectionMagazine);
		buttonGroup.add(rbCollectionEncyclopedia);

		actionComboBox = new JComboBox<String>();
		actionComboBox.setBounds(126, 90, 256, 20);
		actionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				LoadItemData();
			}				
		});		
		add(actionComboBox);

		CollectionComboBox = new JComboBox<String>();
		CollectionComboBox.setBounds(126, 123, 256, 20);
		CollectionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				LoadItemData();
			}				
		});
		add(CollectionComboBox);

		JLabel lblAction = new JLabel("Action");
		lblAction.setBounds(35, 93, 46, 14);
		add(lblAction);

		lblObject = new JLabel("Object");
		lblObject.setBounds(35, 126, 89, 14);
		add(lblObject);

		lblTitle = new JLabel("Collections Info");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitle.setBounds(35, -1, 182, 46);
		add(lblTitle);
		InitPanel();
	}

	private void LoadItemData()
	{

		if (rbCollectionEncyclopedia.isSelected() && actionComboBox.getSelectedIndex()>=0 && CollectionComboBox.getSelectedIndex()>=0)
		{
			Encyclopedia encyclopedia=(Encyclopedia) libraryGui.getLibrarySys().getEncyclopidea(CollectionComboBox.getSelectedItem().toString());
			if(encyclopedia!= null) {
				if (actionComboBox.getSelectedIndex()==0) ReloadAllItems(null);
				if (actionComboBox.getSelectedIndex()==1) ReloadAllItems(new ArrayList<LibraryItem>(encyclopedia.getBooks()));
				if (actionComboBox.getSelectedIndex()==2) ReloadTopics(new ArrayList<Topic>(encyclopedia.getTopics()));
				if (actionComboBox.getSelectedIndex()==3) ReloadAuthors(new ArrayList<Author>(encyclopedia.getAuthors()));
			}
		}

		if (rbCollectionMagazine.isSelected() && actionComboBox.getSelectedIndex()>=0 && CollectionComboBox.getSelectedIndex()>=0)
		{
			Magazine magazine=(Magazine) libraryGui.getLibrarySys().getMagazine(CollectionComboBox.getSelectedItem().toString());
			if (magazine != null)
			{
				if (actionComboBox.getSelectedIndex()==0) ReloadAllItems(null);
				if (actionComboBox.getSelectedIndex()==1) ReloadAllItems(new ArrayList<LibraryItem>(magazine.getPapers()));
				if (actionComboBox.getSelectedIndex()==2) ReloadTopics(new ArrayList<Topic>(magazine.getTopics()));
				if (actionComboBox.getSelectedIndex()==3) ReloadAuthors(new ArrayList<Author>(magazine.getAuthors()));
			}
		}
	}	

	//reload LibraryItems from 'itemsTemp' to the table
	private void ReloadAllItems(ArrayList<LibraryItem> itemsTemp)
	{
		ClearModelAndSetHeaders(new String[] {"Item name" , "Author" , "Current score" , "Topic"});

		if (itemsTemp==null) return;

		int counter=0;

		for(LibraryItem i: itemsTemp) 
		{	
			Object[] CurrentUser = new Object[] {
					i.getName(), i.getAuthor() , i.GetScore(new ArithmeticMean()), i.getTopic() 
			};
			itemsTableModel.insertRow(counter++, CurrentUser);
		}
		ViewModelHelper.SortTableOn2Columns(itemsTable, 0, 0);
	}


	//reload topics from 'topicsTemp' to the table
	private void ReloadTopics(ArrayList<Topic> topicsTemp)
	{
		ClearModelAndSetHeaders(new String[] {"Topic"});

		if (topicsTemp==null) return;

		int counter=0;

		for(Topic t: topicsTemp) 
		{	
			Object[] CurrentUser = new Object[] {
					t.toString()
			};
			itemsTableModel.insertRow(counter++, CurrentUser);
		}	

		ViewModelHelper.SortTableOn2Columns(itemsTable, 0, 0);
	}


	//reload authors from 'authorsTemp' to the table
	private void ReloadAuthors(ArrayList<Author> authorsTemp)
	{
		ClearModelAndSetHeaders(new String[] {"First Name" , "Last Name", "Topic"});

		if (authorsTemp==null) return;

		int counter=0;

		for(Author a: authorsTemp) 
		{	
			Object[] CurrentUser = new Object[] {
					a.getFirstName(), a.getLastName(), a.gettopic()
			};
			itemsTableModel.insertRow(counter++, CurrentUser);
		}	

		ViewModelHelper.SortTableOn2Columns(itemsTable, 0, 1);
	}


	//Clear old rows and Headers from the table and give new Headers to the table
	private void ClearModelAndSetHeaders(String[] Headers)
	{
		while (itemsTableModel.getRowCount() > 0) {
			itemsTableModel.removeRow(0);
		}
		itemsTableModel.setColumnIdentifiers(Headers);
		itemsTable.getTableHeader().setReorderingAllowed(false);//for not allow to move columns by the headers
	}


	private class CollectionHandler implements ItemListener 
	{
		@Override
		public void itemStateChanged(ItemEvent e) {		

			FillComboBoxes();
		}
	}

	public void FillComboBoxes() {		

		ArrayList<LibraryCollection> lst= new ArrayList<LibraryCollection>();

		//Remove CollectionComboBox items
		CollectionComboBox.removeAllItems();

		if (rbCollectionMagazine.isSelected())
		{	
			lblObject.setText("Magazine");
			actionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Get me all magazine papers", 
					"What are this magazine topics?", "Who are this magazine authors?"}));

			//Add magazines names 
			for(LibraryCollection a: libraryGui.getLibrarySys().getMagazines())	lst.add(a);				
		}

		if(rbCollectionEncyclopedia.isSelected())
		{
			lblObject.setText("Encyclopedia");
			actionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Get me all encycolpedia books", 
					"What are this encycolpedia topics?", "Who are this encycolpedia authors?"}));

			//Remove CollectionComboBox items
			CollectionComboBox.removeAllItems();

			//Add encyclopedias names 
			for(LibraryCollection a: libraryGui.getLibrarySys().getEncyclopedias())	lst.add(a);				
		}

		Collections.sort(lst, new Comparator<LibraryCollection>() {
			public int compare(LibraryCollection o1, LibraryCollection o2) {
				return o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
			}
		}); 

		for(LibraryCollection c: lst) 
			CollectionComboBox.addItem(c.getName());
	}

	//Initialize all the components when the user enter to the panel
	public void InitPanel()
	{
		//Clear All the Fields in the panel
		rbCollectionEncyclopedia.setSelected(true);
		FillComboBoxes();
		actionComboBox.setSelectedIndex(0);
		if (CollectionComboBox.getItemCount()>0) CollectionComboBox.setSelectedIndex(0);
	}

}
