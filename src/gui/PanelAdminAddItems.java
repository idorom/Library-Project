package gui;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import LibExceptions.itemAddErrorExceptionGui;
import LibExceptions.itemAlreadyExistExceptionGui;
import LibExceptions.itemNotExistException;
import Model.ArithmeticMean;
import Model.Author;
import Model.Book;
import Model.LibraryItem;
import Model.Paper;
import Utils.AcademicBook;
import Utils.BookSize;
import Utils.PaperValue;
import Utils.Topic;



public class PanelAdminAddItems extends JPanel {

	private LibraryGui libraryGui;

	private DefaultTableModel itemsModel;

	private JTable table;

	private JRadioButton bookChoise;

	private JRadioButton paperChoise;

	private ButtonGroup itemChoise;

	private JLabel BookAuthor;

	private JLabel lblItemName;

	private JLabel BookTopic;

	private JLabel UniversityLabel;

	private JTextField txtItemName;

	private JLabel lblAllUsersAdmin;

	private  JComboBox<Author> cmbAuthor;

	private JComboBox<Topic> topicAddToBook;

	private JLabel lblItemtype;

	private JTextField txtUniversity;

	private JButton btnBack;

	private JLabel lblValueOrSize;

	private JComboBox<PaperValue> cmbValueOrSize;

	private JComboBox<AcademicBook> cmbAcademic;

	/**
	 * Create the panel.
	 */
	public PanelAdminAddItems( LibraryGui libraryGui) {

		this.libraryGui=libraryGui;
		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);

		CollectionHandler collectionHandler= new CollectionHandler();

		bookChoise = new JRadioButton("Book");
		bookChoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookChoise.setBounds(165, 51, 61, 23);
		bookChoise.setOpaque(false);
		bookChoise.addItemListener(collectionHandler);
		add(bookChoise);

		paperChoise = new JRadioButton("Paper");
		paperChoise.setFont(new Font("Tahoma", Font.PLAIN, 14));
		paperChoise.setBounds(240, 51, 81, 23);
		paperChoise.setOpaque(false);
		paperChoise.addItemListener(collectionHandler);
		add(paperChoise);

		itemChoise= new ButtonGroup();
		itemChoise.add(bookChoise);
		itemChoise.add(paperChoise);

		BookAuthor = new JLabel("Author");
		BookAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		BookAuthor.setBounds(60, 120, 100, 40);
		add(BookAuthor);

		lblItemName = new JLabel("Book name");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemName.setBounds(60, 91, 81, 30);
		add(lblItemName);

		BookTopic = new JLabel("Topic");
		BookTopic.setFont(new Font("Tahoma", Font.PLAIN, 14));
		BookTopic.setBounds(343, 95, 41, 23);
		add(BookTopic);

		UniversityLabel= new JLabel("University");
		UniversityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		UniversityLabel.setBounds(60, 155, 81, 40);
		UniversityLabel.setVisible(true);
		add(UniversityLabel);

		//Admin's TextFields
		txtItemName = new JTextField();
		txtItemName.setBounds(143, 96, 157, 20);
		add(txtItemName);
		txtItemName.setColumns(10);

		lblAllUsersAdmin = new JLabel("All LibraryItmes");
		lblAllUsersAdmin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAllUsersAdmin.setBounds(35, 8, 140, 29);
		add(lblAllUsersAdmin);

		itemsModel =  new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		table = new JTable(itemsModel);
		table.setLocation(74, 383);
		table.setEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		table.setSize(700, 100);
		table.setVisible(true);

		JScrollPane pane;

		pane= new JScrollPane(table);
		pane.setBounds(35, 246, 778, 342);		
		pane.setEnabled(false);

		add(pane);

		cmbAuthor = new JComboBox<Author>();
		cmbAuthor.setBounds(143, 130, 157, 20);		
		add(cmbAuthor);


		topicAddToBook = new JComboBox<Topic>();
		topicAddToBook.setModel(new DefaultComboBoxModel<Topic>(Topic.values()));
		topicAddToBook.setBounds(394, 96, 122, 20);
		add(topicAddToBook);

		lblItemtype = new JLabel("ItemType");
		lblItemtype.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemtype.setBounds(60, 42, 100, 40);
		add(lblItemtype);

		cmbAcademic = new JComboBox<AcademicBook>();
		cmbAcademic.setBounds(143, 165, 157, 20);
		cmbAcademic.setModel(new DefaultComboBoxModel<AcademicBook>(AcademicBook.values()));
		add(cmbAcademic);

		txtUniversity = new JTextField();
		txtUniversity.setText("University");
		txtUniversity.setColumns(10);
		txtUniversity.setBounds(143, 165, 157, 20);
		txtUniversity.setVisible(false);
		add(txtUniversity);

		lblValueOrSize = new JLabel("Value");
		lblValueOrSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValueOrSize.setBounds(343, 133, 46, 14);
		add(lblValueOrSize);

		cmbValueOrSize = new JComboBox<PaperValue>();
		cmbValueOrSize.setBounds(394, 130, 122, 20);
		add(cmbValueOrSize);

		JButton librarianAddNewUserAdmin = new JButton("Add Item");
		librarianAddNewUserAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String name;
				Topic t;
				Author a;
				BookSize bs;
				AcademicBook ab;
				PaperValue pv;

				try {
					if(bookChoise.isSelected()) {
						String errMsg="";
						if (txtItemName.getText().isEmpty()) errMsg+="\nBook name";
						if (cmbAuthor.getSelectedIndex()<0) errMsg+="\nAuthor";
						if (cmbAcademic.getSelectedIndex()<0) errMsg+="\nAcademic";						
						if (topicAddToBook.getSelectedIndex()<0) errMsg+="\nTopic";
						if (cmbValueOrSize.getSelectedIndex()<0) errMsg+="\nSize";
						if (!errMsg.isEmpty()) throw new itemAddErrorExceptionGui("Missing or invalid data in the following field(s): " +errMsg);

						name=txtItemName.getText();
						t= (Topic)topicAddToBook.getSelectedItem();
						a= (Author)cmbAuthor.getSelectedItem();
						bs=(BookSize)cmbValueOrSize.getSelectedItem();
						ab=(AcademicBook)cmbAcademic.getSelectedItem(); 

						Book b= new Book(name, t, a, bs, ab);
						libraryGui.getLibrarySys().addItem(b);
						libraryGui.DataWasChanged();
						ClearFields();
						ReloadeBooks();
						
						
						//JOptionPane that tell if book added successfully
						JOptionPane.showConfirmDialog(null, "Book " + b.toString()+" was added successfully", "Add book"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}
					if(paperChoise.isSelected()) {	
						//check if fields haven't been filled
						String errMsg="";
						if (txtItemName.getText().isEmpty()) errMsg+="\nPaper name";
						if (cmbAuthor.getSelectedIndex()<0) errMsg+="\nAuthor";
						if (txtUniversity.getText().isEmpty()) errMsg+="\nUniversity";						
						if (topicAddToBook.getSelectedIndex()<0) errMsg+="\nTopic";
						if (cmbValueOrSize.getSelectedIndex()<0) errMsg+="\nSize";
						if (!errMsg.isEmpty()) throw new itemAddErrorExceptionGui("Missing or invalid data in the following field(s): " +errMsg);

						name=txtItemName.getText();
						t= (Topic)topicAddToBook.getSelectedItem();
						a= (Author)cmbAuthor.getSelectedItem();
						pv=(PaperValue)cmbValueOrSize.getSelectedItem();

						Paper p= new Paper(name, t, a, pv, txtUniversity.getText());
						libraryGui.getLibrarySys().addItem(p);
						libraryGui.DataWasChanged();
						ClearFields();
						ReloadePapers();
						
						//JOptionPane that tell if paper added successfully
						JOptionPane.showConfirmDialog(null, "Paper "+p.toString()+" successfully", "Add paper"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (itemAlreadyExistExceptionGui e1) {
					return;
				}	
				catch (itemAddErrorExceptionGui e2) {
					return;
				}
			}
		});

		librarianAddNewUserAdmin.setBounds(681, 212, 132, 23);
		add(librarianAddNewUserAdmin);


		//Return to the main panel of the user 
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);

		JButton btnRemove = new JButton("Remove Item");
		btnRemove.addActionListener(new ActionListener() {						
			public void actionPerformed(ActionEvent e) {
				boolean BooksMode=bookChoise.isSelected();

				String ItemType=(BooksMode) ? "book": "paper";

				if (table.getSelectedRow()<0) {
					JOptionPane.showConfirmDialog(null, "Please select a " + ItemType + "  to remove" 
							, "Remove selected " +  ItemType
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return;				
				}

				int row=table.getSelectedRow();

				if (row>=0)
				{					
					String name=(String)table.getValueAt(row, 0);
					int input = JOptionPane.showConfirmDialog(null, "Do you want to remove " + name +"?" 
							, "Remove selected " +  ItemType
							,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);				

					if (input == JOptionPane.NO_OPTION) return;

					try {
						LibraryItem item= libraryGui.getLibrarySys().getItemByName(name);
						libraryGui.getLibrarySys().removeItem(item);
						libraryGui.DataWasChanged();

						if (BooksMode) ReloadeBooks();
						else ReloadePapers(); 
						
						//JOptionPane that tell if item was removed successfully
						JOptionPane.showConfirmDialog(null, "Remove "+item.toString()+" was removed successfully", "Remove item"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}
					catch (itemNotExistException nex) {
						return;
					}	
				}
				else JOptionPane.showConfirmDialog(null, "Please select a " + ItemType + "  to remove" 
						, "Remove selected " +  ItemType
						,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

			}
		});		
		btnRemove.setBounds(35, 212, 132, 23);
		add(btnRemove);

		InitPanel();	
	}

	//Initialize all the components when the user enter to the panel
	public void InitPanel()
	{	
		bookChoise.setSelected(true);
		cmbAuthor.setModel(new DefaultComboBoxModel(libraryGui.getAuthorsSorted().toArray()));	
		ClearFields();
	}

	//Clear All the Fields in the panel
	private void ClearFields()
	{
		//Clear all fields
		txtItemName.setText("");
		cmbAuthor.setSelectedIndex(-1);
		cmbAcademic.setSelectedIndex(-1);
		txtUniversity.setText("");						
		topicAddToBook.setSelectedIndex(-1);
		cmbValueOrSize.setSelectedIndex(-1);
	}

	//Sort the all table by 'columnIndexToSort' order 
	private void SortColumn(int columnIndexToSort, SortOrder order)
	{
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();		 		
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, order));		 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	//reload books from Library class to the table
	private void ReloadeBooks()
	{
		ClearModelAndSetHeaders(new String[]{"Item name" , "Author" , "Current score" , "Topic", "Size", "Acadmemic"});

		int counter=0;

		for(Book b: libraryGui.getLibrarySys().getBooks()) 
		{	

			Object[] CurrentItem = new Object[] {
					b.getName(), b.getAuthor() , b.GetScore(new ArithmeticMean()), b.getTopic(), b.getBookSize() ,b.getAcadmemicBook() 
			};
			itemsModel.insertRow(counter++, CurrentItem);			
		}

		SortColumn(0, SortOrder.ASCENDING);
	}

	//reload papers from Library class to the table
	private void ReloadePapers()
	{
		ClearModelAndSetHeaders(new String[]{"Item name" , "Author" , "Current score" , "Topic", "Value", "University"});

		int counter=0;

		for(Paper p: libraryGui.getLibrarySys().getPapers()) 
		{	

			Object[] CurrentItem = new Object[] {
					p.getName(), p.getAuthor() , p.GetScore(new ArithmeticMean()), p.getTopic(), p.getPaperValue(), p.getUniversity() 
			};
			itemsModel.insertRow(counter++, CurrentItem);			
		}
		SortColumn(0, SortOrder.ASCENDING);
	}

	
	//Clear old rows and Headers from the table and give new Headers to the table
	private void ClearModelAndSetHeaders(String[] Headers)
	{
		while (itemsModel.getRowCount() > 0) {
			itemsModel.removeRow(0);
		}
		itemsModel.setColumnIdentifiers(Headers);
		table.getTableHeader().setReorderingAllowed(false);//for not allow to move columns by the headers
	}


	private class CollectionHandler implements ItemListener 
	{
		@Override
		public void itemStateChanged(ItemEvent e) {			
			if (bookChoise.isSelected())
			{
				setVisibleCompanne(true);

				ReloadeBooks();
			}

			if(paperChoise.isSelected())
			{
				setVisibleCompanne(false);

				ReloadePapers();				
			}
		}
	}

	//change the components by text or visibility or value for books or papers
	//for reuse of the components
	public void setVisibleCompanne(Boolean forBooks)
	{
		if(forBooks) 
		{
			lblItemName.setText("Book Name");
			lblValueOrSize.setText("Size");
			cmbValueOrSize.setModel(new DefaultComboBoxModel(BookSize.values()));
			cmbValueOrSize.setSelectedIndex(-1);
		}
		else
		{
			lblItemName.setText("Paper Name");
			lblValueOrSize.setText("Value");
			cmbValueOrSize.setModel(new DefaultComboBoxModel(PaperValue.values()));
			cmbValueOrSize.setSelectedIndex(-1);
		}

		cmbAcademic.setVisible(forBooks);
		UniversityLabel.setText(forBooks?"Academic":"University");
		txtUniversity.setVisible(!forBooks);
	}
}
