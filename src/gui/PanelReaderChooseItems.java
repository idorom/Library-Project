package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Model.ArithmeticMean;
import Model.Author;
import Model.Book;
import Model.LibraryItem;
import Model.Paper;
import Model.Reader;
import Model.Review;
import View.ViewModelHelper;



public class PanelReaderChooseItems extends JPanel {

	//private Reader reader;

	private LibraryGui libraryGui;
	
	private JButton btnBack;

	private DefaultTableModel itemsTableModel;

	private JTable itemsTable;

	private JComboBox<String> cmbReader;
	
	private JComboBox cmbSortBy;

	private JComboBox cmbTypeFilter;
	
	private JComboBox cmbReadStatusFilter;
	
	private JComboBox cmbAuthorFilter;	
	
	private JComboBox cmbTopicFilter;
	
	private JLabel lblReaderPicture;


	/**
	 * Create the panel.
	 */
	public PanelReaderChooseItems( LibraryGui libraryGui) {
		this.libraryGui=libraryGui;
		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);

		cmbReader = new JComboBox();
		cmbReader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbReader.setBounds(96, 31, 188, 20);
		cmbReader.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				ReaderSelected();
			}				
		});	
		add(cmbReader);


		JLabel paperOrBook = new JLabel("What are you looking for?");
		paperOrBook.setFont(new Font("Tahoma", Font.PLAIN, 18));
		paperOrBook.setBounds(306, 30, 227, 22);
		add(paperOrBook);


		itemsTableModel = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				if (mColIndex==5) 
				{
					if (itemsTableModel.getValueAt(rowIndex, 5).toString().equals("Mark as Read"))
						return true;
				}
				if (mColIndex==6)
				{
					if (itemsTableModel.getValueAt(rowIndex, 6).toString().equals("Add Review"))
						return true;
				}
				return false;
			}

			public Class getColumnClass(int c) {
				if (getRowCount()>0)
				{
					//if (c==3) return Double.class;
					return getValueAt(0, c).getClass();
				}
				return String.class;
			}
		};
		String[] headersNames = new String[] {
				"Item name" , "Type",  "Author" , "Score" , "Topic", "Read Status", "Review"
		};	
		itemsTableModel.setColumnIdentifiers(headersNames);
		itemsTable = new JTable(itemsTableModel);
		itemsTable.setLocation(74, 383);
		itemsTable.setEnabled(true);
		itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		itemsTable.setRowSelectionAllowed(true);
		itemsTable.getTableHeader().setReorderingAllowed(false);
		
		ViewModelHelper.alignTableColumnRight(itemsTable, 3);
		itemsTable.setSize(700, 100);
		itemsTable.setVisible(true);
		JScrollPane pane;

		pane= new JScrollPane(itemsTable);
		pane.setBounds(35, 276, 829, 320);
		pane.setEnabled(false);
		add(pane);


		//Return to the main panel of the user 
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);


		JButton randomButton = new JButton("Feeling lucky?(random)");
		randomButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
				ArrayList<LibraryItem> items;
				LibraryItem i=null;
				Reader reader=(Reader)cmbReader.getSelectedItem();
				if(cmbTypeFilter.getSelectedIndex()==0)
				{
					items = new ArrayList<LibraryItem>(libraryGui.getLibrarySys().getItems());
					int random = (int)(Math.random() * items.size());
					itemsTable.setSelectionBackground(Color.green);

					i= items.get(random);
				}
				if(cmbTypeFilter.getSelectedIndex()==2)
				{
					items = new ArrayList<LibraryItem>(libraryGui.getLibrarySys().getPapers());
					int random = (int)(Math.random() * items.size());
					itemsTable.setSelectionMode(random);
					i= items.get(random);
				}
				if(cmbTypeFilter.getSelectedIndex()==1)
				{
					items = new ArrayList<LibraryItem>(libraryGui.getLibrarySys().getBooks());
					int random = (int)(Math.random() * items.size());
					itemsTable.setSelectionBackground(Color.green);
					i= items.get(random);
				}
				if(i!=null) {
					final String[] options = { "Yes", "no"};
					JFrame frame = new JFrame();
					String temp = (String) JOptionPane.showInputDialog(frame, 
							"Are you sure you want to read " + i.getName() + "?",
							"Reading confirmation",
							JOptionPane.QUESTION_MESSAGE, 
							null, 
							options, 
							options[0]);
					if(temp != null) {
						if(temp.equals("Yes")) {
							if(!(reader.getItems().contains(i))) {
								libraryGui.getLibrarySys().readItem(reader, i);
								ReloadeItems();
								libraryGui.DataWasChanged();
							}
							else {
								if(i instanceof Book)
									JOptionPane.showMessageDialog(frame, "You've already read this book!!");
								else
									JOptionPane.showMessageDialog(frame, "You've already read this paper!!");

							}
						}
					}
				}
			}

		});
		
		randomButton.setBounds(676, 236, 188, 35);
		add(randomButton);

		ButtonColumn btnMarkAsread = new ButtonColumn(itemsTable, 5);
		ButtonColumn btnAddReview = new ButtonColumn(itemsTable, 6);

		

		JLabel lblSortBy = new JLabel("Sort By");
		lblSortBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSortBy.setBounds(35, 248, 46, 14);
		add(lblSortBy);

		cmbSortBy = new JComboBox(new String[] {"Item Name","Score"});
		cmbSortBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbSortBy.setBounds(105, 245, 120, 20);		
		cmbSortBy.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){				
				if (cmbSortBy.getSelectedIndex()!=1) SortColumn(0, SortOrder.ASCENDING); 
				else SortColumn(3, SortOrder.DESCENDING);	
			}				
		});	
		add(cmbSortBy);

		JLabel lblNewLabel = new JLabel("Filters");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(431, 103, 100, 14);
		add(lblNewLabel);

		JLabel lblTypeFilter = new JLabel("Type");
		lblTypeFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTypeFilter.setBounds(230, 140, 46, 14);
		add(lblTypeFilter);

		cmbTypeFilter = new JComboBox(new String[] {"All","Books","Papers"});
		cmbTypeFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbTypeFilter.setBounds(311, 137, 137, 20);
		cmbTypeFilter.addActionListener(new filterJbuttonListener());
		add(cmbTypeFilter);

		JLabel lblReadStatusFilter = new JLabel("Read Status");
		lblReadStatusFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReadStatusFilter.setBounds(230, 171, 82, 14);
		add(lblReadStatusFilter);

		cmbReadStatusFilter = new JComboBox(new String[] {"All","Unread","Read"});
		cmbReadStatusFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbReadStatusFilter.setBounds(311, 168, 137, 20);
		cmbReadStatusFilter.addActionListener(new filterJbuttonListener());
		add(cmbReadStatusFilter);

		JLabel lblAuthorFilter = new JLabel("Author");
		lblAuthorFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAuthorFilter.setBounds(485, 140, 46, 14);
		add(lblAuthorFilter);

		cmbAuthorFilter = new JComboBox();
		cmbAuthorFilter.setBounds(541, 137, 137, 20);
		cmbAuthorFilter.setEditable(false);
		cmbAuthorFilter.addActionListener(new filterJbuttonListener());
		add(cmbAuthorFilter);

		JLabel lblTopicFilter = new JLabel("Topic");
		lblTopicFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTopicFilter.setBounds(485, 171, 46, 14);
		add(lblTopicFilter);

		cmbTopicFilter = new JComboBox();
		cmbTopicFilter.setEditable(false);
		cmbTopicFilter.setBounds(541, 168, 137, 20);
		cmbTopicFilter.addActionListener(new filterJbuttonListener());
		add(cmbTopicFilter);

		JButton btnClearFilters = new JButton("Clear Filters");
		btnClearFilters.setBounds(431, 210, 113, 23);
		btnClearFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearFilters();
				ReloadeItems();
			}
		});
		add(btnClearFilters);
		
		lblReaderPicture = new JLabel("");
		lblReaderPicture.setBounds(35, 69, 165, 156);		
		Border border = BorderFactory.createLineBorder(Color.black, 1);		 
        // set the border of this component
		lblReaderPicture.setBorder(border);		
		add(lblReaderPicture);
		
		JLabel lblHello = new JLabel("Hello!");
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHello.setBounds(35, 30, 227, 22);
		add(lblHello);			
		
		
	}

	//clear all the filters
	private void ClearFilters()
	{
		cmbTypeFilter.setSelectedIndex(0);
		cmbReadStatusFilter.setSelectedIndex(0);
		cmbAuthorFilter.setSelectedIndex(0);
		cmbTopicFilter.setSelectedIndex(0);
	}

	// Display the image of the selected reader
	private void SetReaderPicture()
	{
		if (cmbReader.getSelectedItem()!=null)
		{
			lblReaderPicture.setIcon(((Reader)cmbReader.getSelectedItem()).getUserIcon());
		}
		else lblReaderPicture.setIcon(null); 
	}
	

	// Fill the Authors and Topics combo boxes
	private void FillAuthorAndTopicSearchCombo()
	{
		TreeSet<String> data=new TreeSet<String>();

		cmbAuthorFilter.removeAllItems();

		cmbAuthorFilter.addItem("All");
		for(Author a: libraryGui.getAuthorsSorted()) cmbAuthorFilter.addItem(a.toString());

		data.clear();		
		cmbTopicFilter.removeAllItems();

		for(LibraryItem i: libraryGui.getLibrarySys().getItems())
			data.add(i.getTopic().toString());

		cmbTopicFilter.addItem("All");
		for(String s: data) cmbTopicFilter.addItem(s);
	}


	//if CurrentReader isn't empty the method reload only the specific Reader to cmbReader
	//else reload all the readers from Library class to cmbReader
	public void ReloadReaders(String CurrentReader)
	{
		while (itemsTableModel.getRowCount() > 0) {
			itemsTableModel.removeRow(0);
		}
		
		FillAuthorAndTopicSearchCombo();
		ClearFilters();
		
		ArrayList<Reader> readersList= new ArrayList<Reader>();

		for(Reader r: libraryGui.getLibrarySys().getReaders()) 
			if (CurrentReader.isEmpty() || r.toString().equals(CurrentReader)) 
				readersList.add(r);

		//sort by reader's Name
		Collections.sort(readersList, new Comparator<Reader>() {
			public int compare(Reader r1, Reader r2) {
				return r1.toString().compareTo(r2.toString());
			}
		});
		
		cmbReader.setModel(new DefaultComboBoxModel(readersList.toArray()));		
		ReaderSelected();		
	}
	
	//Clear filter and show all items when a different reader is selected in the combo box
	private void ReaderSelected()
	{
		ClearFilters();		
		SetReaderPicture();
		ReloadeItems();
	}

	//reload libraryItems from Library class to the table 
	private void ReloadeItems()
	{	
		DecimalFormat decimalFormat = new DecimalFormat("#0.0 ");

		while (itemsTableModel.getRowCount() > 0) {
			itemsTableModel.removeRow(0);
		}

		int counter=0;
		
		Reader reader=(Reader)cmbReader.getSelectedItem();

		String TypeFilter="";
		String ReadStatusFilter="";
		String AuthorsFilter="";
		String TopicsFilter="";

		if (cmbTypeFilter.getSelectedIndex()>0) TypeFilter=cmbTypeFilter.getSelectedItem().toString();
		if (cmbReadStatusFilter.getSelectedIndex()>0) ReadStatusFilter=cmbReadStatusFilter.getSelectedItem().toString();
		if ( cmbAuthorFilter.getSelectedIndex()>0) AuthorsFilter=cmbAuthorFilter.getSelectedItem().toString();
		if ( cmbTopicFilter.getSelectedIndex()>0) TopicsFilter=cmbTopicFilter.getSelectedItem().toString();

		for(LibraryItem i: libraryGui.getLibrarySys().getItems()) 
		{	
			System.out.print(i.getName() + " ");
			String AuthorName= i.getAuthor().getFirstName() +" " + i.getAuthor().getLastName();
			boolean itemIsPaper=(i instanceof Paper);
			boolean wasReadByUser= false;

			boolean ReaderReviewedThisItem=false;
			if (reader!=null)
			{
				
				for (Review v: i.getReviews())
				{
					if (v.getReaderFirstName().equals(reader.getFirstName()) && 
							v.getReaderLaststName().equals(reader.getLastName()))
					{
						ReaderReviewedThisItem=true;
						break;
					}
				}

				//wasReadByUser=ReaderHasReadTheItem(reader, i);
				wasReadByUser=reader.getItems().contains(i);
			}

			if ((TypeFilter.equals("") || (itemIsPaper && TypeFilter.equals("Papers")) || (!itemIsPaper && TypeFilter.equals("Books")))
					&& (ReadStatusFilter.equals("") || (wasReadByUser && ReadStatusFilter.equals("Read")) || (!wasReadByUser && ReadStatusFilter.equals("Unread")))	
					&& (AuthorsFilter.equals("") || AuthorsFilter.equals(AuthorName)) && (TopicsFilter.equals("") || TopicsFilter.equals(i.getTopic().toString())))
			{
				Object[] CurrentItem = new Object[] {
						i.getName(), (itemIsPaper) ? "Paper":"Book", 
								AuthorName , decimalFormat.format( i.GetScore(new ArithmeticMean())), i.getTopic() 
								, wasReadByUser? "Was Read": "Mark as Read"
									,ReaderReviewedThisItem? "Reviewed": "Add Review"  
				};
				itemsTableModel.insertRow(counter++, CurrentItem);
			}
		}

		if (cmbSortBy.getSelectedIndex()!=1) SortColumn(0, SortOrder.ASCENDING); 
		else SortColumn(3, SortOrder.DESCENDING);		
	}
	

	private void SortColumn(int columnIndexToSort, SortOrder order)
	{
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(itemsTable.getModel());
		itemsTable.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();		 		
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, order));		 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	// Show dialog for creating a review
	private Review AddReview(Reader reader, String itemName) {

		if (reader==null || itemName.isEmpty()) return null;
		Integer[] options = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };	    

		JPanel panel = new JPanel(); 

		// Added GridBagLayout to be able to set controls layout in panel
		GridBagLayout layout = new GridBagLayout();

		panel.setLayout(layout);
		panel.setSize(300,300);

		// Added GridBagConstraints to be able to control  component location in the panel
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lblRate=new JLabel("Please Rate This Item");
		JComboBox revScore= new JComboBox(options);
		JLabel lblImpression = new JLabel("Please write your impression");
		JTextArea txtImpression = new JTextArea(10,10);
		txtImpression.setSize(200,200);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady=20;	      
		panel.add(lblRate ,gbc);


		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady=0;
		panel.add(revScore,gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady=20;
		panel.add(lblImpression,gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady=20;
		panel.add(txtImpression,gbc);

		int result = JOptionPane.showConfirmDialog(this, panel, "Review - ", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return new Review(reader.getFirstName(),reader.getLastName(),itemName,txtImpression.getText(),10-(int)revScore.getSelectedIndex());
		}
		return null;
	}

	// Help in sorting LibraryItem's
	class Compareid implements Comparator<LibraryItem> {
		public int compare(LibraryItem i1, LibraryItem i2) {		
			return (i1.getName().compareTo(i2.getName()));

		}
	}


	// Update the items table when a filter is changed
	private class filterJbuttonListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			ReloadeItems();				
		}
	}

	// Update an item review or item was read
	private double SetItemReadAndReview(Reader reader, String itemName, boolean wasRead, Review review)
	{
		LibraryItem item =libraryGui.getLibrarySys().getItemByName(itemName);
		if (item!=null)
		{
			if (wasRead) {
				libraryGui.getLibrarySys().readItem(reader, item);
				libraryGui.DataWasChanged();
			}
		
			if (review !=null) {
				libraryGui.getLibrarySys().makeReview(item, review);
				libraryGui.DataWasChanged();
				return item.GetScore(new ArithmeticMean());
			}
		}

		return -1;
	}


	// Class used to display button in the JTable
	class ButtonColumn extends AbstractCellEditor
	implements TableCellRenderer, TableCellEditor, ActionListener
	{
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public ButtonColumn(JTable table, int column)
		{
			super();
			this.table = table;
			renderButton = new JButton();

			editButton = new JButton();
			editButton.setFocusPainted( false );
			editButton.addActionListener( this );

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer( this );
			columnModel.getColumn(column).setCellEditor( this );
		}

		public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{

			// Disable the button if the action was already performed
			//To prevent "undoing" of the action
			if (value !=null) {
				// Review 
				if (column==6)
					renderButton.setEnabled(value.toString().equals("Add Review"));
				// Item read
				if (column==5)
					renderButton.setEnabled(value.toString().equals("Mark as Read"));
			}

			if (hasFocus)
			{
				renderButton.setForeground(table.getForeground());
				renderButton.setBackground(UIManager.getColor("Button.background"));
			}
			else if (isSelected)
			{
				renderButton.setForeground(table.getSelectionForeground());
				renderButton.setBackground(table.getSelectionBackground());
			}
			else
			{
				renderButton.setForeground(table.getForeground());
				renderButton.setBackground(UIManager.getColor("Button.background"));
			}

			renderButton.setText( (value == null) ? "" : value.toString() );
			return renderButton;
		}

		// set button text
		public Component getTableCellEditorComponent(
				JTable table, Object value, boolean isSelected, int row, int column)
		{
			// Column 6 - Review, 5 - Item Read
			if (column==6) text = "Add Review";
			else text="Mark as Read";

			editButton.setText( text );
			return editButton;			
		}

		public Object getCellEditorValue()
		{
			return text;
		}

		// Update the item when a button in the JTable is pressed
		public void actionPerformed(ActionEvent e)
		{		
			fireEditingStopped();
			Reader reader=(Reader)cmbReader.getSelectedItem();
			int column=table.getSelectedColumn();
			int row=table.getSelectedRow();
			int modelRow =table.convertRowIndexToModel(row);
			String itemName=table.getValueAt(row, 0).toString();

			// Item Read
			if (column==5)
			{
				itemsTableModel.setValueAt("Was Read",modelRow,5);				
				SetItemReadAndReview(  reader,itemName,true,null);
			}

			// Review
			if (column==6)
			{
				Review rev =AddReview(reader, itemName);
				if (rev!=null)
				{
					itemsTableModel.setValueAt("Was Read",modelRow,5);									
					itemsTableModel.setValueAt("Reviewed",modelRow,6);
					double ns=SetItemReadAndReview(reader,itemName,true,rev);
					itemsTableModel.setValueAt(ns,modelRow,3);
				}
			}

		}
	}
}


