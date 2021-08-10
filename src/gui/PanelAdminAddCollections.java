package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import LibExceptions.IleagalNumberExceptionGui;
import LibExceptions.addUserLibraryExceptionGui;
import LibExceptions.collectionAlreadyExistExceptionGui;
import Model.Book;
import Model.Encyclopedia;
import Model.LibraryCollection;
import Model.LibraryItem;
import Model.Magazine;
import Model.Paper;
import View.ViewModelHelper;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PanelAdminAddCollections extends JPanel {

	private JButton btnBack;

	private LibraryGui libraryGui;

	private DefaultTableModel collectionsTableModel;

	private JTable collectionsTable;

	private JScrollPane itemPane;

	private JRadioButton rbCollectionMagazine;

	private JRadioButton rbCollectionEncyclopedia;

	private ButtonGroup buttonGroup;

	private JTextField txtName;

	private JTextField textId;

	private JList lstAllItmes;

	private DefaultListModel allItmesModel;

	private JList lstCollectionItmes;

	private DefaultListModel collectionItmesModel;
	
	private final Lock queueLock = new ReentrantLock();

	/**
	 * Create the panel.
	 */
	public PanelAdminAddCollections(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setVisible(true);
		setBackground(new Color(230, 230, 250));
		setLayout(null);

		
		String[] headersNames1 = new String[] {
				"Name", "ID" };

		JLabel lblTitle = new JLabel("Library collections");
		lblTitle.setBounds(35, -1, 182, 46);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblTitle);

		
		collectionsTableModel = new DefaultTableModel();
		collectionsTableModel.setColumnIdentifiers(headersNames1);
		collectionsTable = new JTable(collectionsTableModel);		
		collectionsTable.setEnabled(true);
		collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		collectionsTable.getTableHeader().setReorderingAllowed(false);
		collectionsTable.setSize(700, 100);
		collectionsTable.setVisible(true);

		collectionsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				FillCollectionItems();
			}
		});




		itemPane= new JScrollPane(collectionsTable);
		itemPane.setBounds(35, 164, 389, 143);
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
		rbCollectionMagazine.setBounds(224, 67, 109, 23);
		rbCollectionMagazine.setOpaque(false);
		rbCollectionMagazine.addItemListener(collectionHandler);
		add(rbCollectionMagazine);

		rbCollectionEncyclopedia = new JRadioButton("Encyclopedias");
		rbCollectionEncyclopedia.setBounds(57, 67, 109, 23);
		rbCollectionEncyclopedia.setOpaque(false);
		rbCollectionEncyclopedia.addItemListener(collectionHandler);
		add(rbCollectionEncyclopedia);

		buttonGroup=new ButtonGroup();
		buttonGroup.add(rbCollectionMagazine);
		buttonGroup.add(rbCollectionEncyclopedia);

		txtName = new JTextField();
		txtName.setBounds(87, 101, 140, 20);
		add(txtName);
		txtName.setColumns(10);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(271, 101, 140, 20);
		add(textId);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(35, 102, 39, 19);
		add(lblName);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(237, 104, 30, 14);
		add(lblId);

		JButton btnRemove = new JButton("Remove collection");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tp=(rbCollectionEncyclopedia.isSelected())?"Encyclopedia":"Magazine";

				if (collectionsTable.getSelectedRow()<0) {

					JOptionPane.showConfirmDialog(null, "Please select " +tp +" to remove" 
							, "Remove selected " + tp
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					String colName=collectionsTable.getValueAt(collectionsTable.getSelectedRow(), 0).toString();

					if (libraryGui.getLibrarySys().getCollectionByName(colName)==null)
						throw new collectionAlreadyExistExceptionGui(colName);

					LibraryCollection col=libraryGui.getLibrarySys().getCollectionByName(colName);				


					int input = JOptionPane.showConfirmDialog(null, "Do you want to remove " + colName +"?" 
							, "Remove Selected " +tp,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (input == JOptionPane.NO_OPTION) return;					

					libraryGui.getLibrarySys().removeCollection(col);				
					libraryGui.DataWasChanged();

					ReloadCollection("");	
					
					//JOptionPane that tell if collection  was removed successfully
					JOptionPane.showConfirmDialog(null, colName+" was removed successfully", "Remove collection"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}catch (collectionAlreadyExistExceptionGui arg) {
					return;
				}
			}
		});
		btnRemove.setBounds(35, 130, 140, 23);
		add(btnRemove);

		JButton btnAddCollection = new JButton("Add collection");
		btnAddCollection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String name= txtName.getText();
					int id= ViewModelHelper.IntK(textId.getText());

					String ErrMsg="";	
					if (name.isEmpty()) ErrMsg="\nName";					
					if(id<0)ErrMsg+="\nID";
					if (!ErrMsg.isEmpty())
						throw new addUserLibraryExceptionGui("Missing or invalid data in the following field(s): " + ErrMsg);

					if (libraryGui.getLibrarySys().getCollectionByName(name)!=null)
						throw new collectionAlreadyExistExceptionGui(name);


					if(rbCollectionEncyclopedia.isSelected())										
						libraryGui.getLibrarySys().addCollection(new Encyclopedia(name, id));

					if(rbCollectionMagazine.isSelected())											
						libraryGui.getLibrarySys().addCollection(new Magazine(name, id));

					libraryGui.DataWasChanged();
					
					ReloadCollection(txtName.getText());
					
					txtName.setText("");
					textId.setText("");
					
					
					//JOptionPane that tell if collection added successfully
					JOptionPane.showConfirmDialog(null, "Collection " +name+" was added successfully", "Add collection"
							,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}catch (collectionAlreadyExistExceptionGui arg) {
					return;
				} catch (NumberFormatException arg) {
					return;
				} catch (IleagalNumberExceptionGui arg) {
					return;
				} catch (addUserLibraryExceptionGui e1) {
					return;
				}
			}
		});
		btnAddCollection.setBounds(284, 132, 140, 23);
		add(btnAddCollection);

		JButton btnAddItems = new JButton("Add >");
		btnAddItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (collectionsTable.getSelectedRow()<0) return;
				String colName=collectionsTable.getValueAt(collectionsTable.getSelectedRow(), 0).toString();
				LibraryCollection col=libraryGui.getLibrarySys().getCollectionByName(colName);
				if (col==null) return;

				List<Object> selected=lstAllItmes.getSelectedValuesList();				
				if (selected.isEmpty()) return;

				for (Object i: selected)
				{
					AddItemTotListModel(collectionItmesModel,(LibraryItem)i); 					
					allItmesModel.removeElement(i);
				}

				SetCollectionItems(col);
			}
		});
		btnAddItems.setBounds(181, 378, 94, 23);
		add(btnAddItems);

		JButton btnRemoveItems = new JButton("< Remove");
		btnRemoveItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (collectionsTable.getSelectedRow()<0) return;
				String colName=collectionsTable.getValueAt(collectionsTable.getSelectedRow(), 0).toString();
				LibraryCollection col=libraryGui.getLibrarySys().getCollectionByName(colName);
				if (col==null) return;

				List<Object> selected=lstCollectionItmes.getSelectedValuesList();
				if (selected.isEmpty()) return;

				for (Object i: selected)
				{
					AddItemTotListModel(allItmesModel,(LibraryItem)i);					
					collectionItmesModel.removeElement(i);
				}	

				SetCollectionItems(col);
			}
		});
		btnRemoveItems.setBounds(181, 435, 94, 23);
		add(btnRemoveItems);

		allItmesModel=new DefaultListModel<LibraryItem>();
		lstAllItmes = new JList(allItmesModel);			
		lstAllItmes.setLocation(35, 0);
		JScrollPane spAllItems= new JScrollPane(lstAllItmes);
		spAllItems.setBounds(35, 350, 123, 163);	    
		add(spAllItems);

		collectionItmesModel =new DefaultListModel<LibraryItem>();
		lstCollectionItmes = new JList(collectionItmesModel);	  
		JScrollPane spColIyems = new JScrollPane(lstCollectionItmes);
		spColIyems.setBounds(300, 350, 123, 163);	    
		add(spColIyems);
		
		JLabel lblNewLabel = new JLabel("Available Items");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(35, 329, 126, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Items in Collection");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(300, 331, 140, 14);
		add(lblNewLabel_1);
	}

	
	//Initialize all the components when the user enter to the panel
	public void InitPanel()
	{
		txtName.setText("");
		textId.setText("");
		rbCollectionEncyclopedia.setSelected(true);
		ReloadCollection("");
	}

	// Update the Collection object (in the Model) with the items selected for it 
	private void SetCollectionItems(LibraryCollection col)
	{
		col.getItems().clear();
		for (Object o : collectionItmesModel.toArray())
			libraryGui.getLibrarySys().addCollectionItem(col, (LibraryItem)o);
		libraryGui.DataWasChanged();
	}

	protected void ClearModelAndSetHeaders(String[] Headers)
	{
		while (collectionsTableModel.getRowCount() > 0) {
			collectionsTableModel.removeRow(0);
		}
		collectionsTableModel.setColumnIdentifiers(Headers);
	}



	//Reload encyclopedias or magazines
	protected void ReloadCollection(String collectionToSelect)
	{	
		ClearModelAndSetHeaders(new String[] {"Name", "ID"});
		if(rbCollectionEncyclopedia.isSelected())
		{
			if (libraryGui.getLibrarySys().getEncyclopedias()==null ) return;
			int counter=0;

			for(Encyclopedia e: libraryGui.getLibrarySys().getEncyclopedias()) 
			{	
				Object[] CurrentUser = new Object[] {
						e.toString(), e.getId()
				};
				collectionsTableModel.insertRow(counter++, CurrentUser);
			}
		}
		else
		{
			if (libraryGui.getLibrarySys().getMagazines()==null) return;
			int counter=0;

			for(Magazine m: libraryGui.getLibrarySys().getMagazines()) 
			{	
				Object[] CurrentUser = new Object[] {
						m.toString(), m.getId()
				};
				collectionsTableModel.insertRow(counter++, CurrentUser);
			}
		}
		
		ViewModelHelper.SortTableOn2Columns(collectionsTable, 0, 0);
		
		// Select the recently add collection so the user can start adding items to it
		if (!collectionToSelect.isEmpty())
		{
			int i=0;
			while (i<collectionsTable.getRowCount())
			{
				String cName= collectionsTable.getValueAt(i, 0).toString();
				if (cName.equals(collectionToSelect)) 
				{
					collectionsTable.setRowSelectionInterval(i, i);
					break;
				}
				i++;
			}
		} 
		else if (collectionsTable.getRowCount()>0) collectionsTable.setRowSelectionInterval(0, 0);
	}


	// Fill the list of available items - not in selected collection
	//And the list of the items that are in the selected collection
	private void FillCollectionItems()
	{
		allItmesModel.clear();
		collectionItmesModel.clear();

		if (collectionsTable.getSelectedRow()<0) return;
		String colName=collectionsTable.getValueAt(collectionsTable.getSelectedRow(), 0).toString();
		LibraryCollection col=libraryGui.getLibrarySys().getCollectionByName(colName);
		
		// col will not be found when removing item from Library collections
		if (col==null) return; 
		
		boolean LoadBooks=(col instanceof Encyclopedia);

		for (LibraryItem i : libraryGui.getLibrarySys().getItems())
		{
			if ((i instanceof Book && LoadBooks) || (i instanceof Paper && !LoadBooks))
			{
				if (col.getItems().contains(i))
					AddItemTotListModel(collectionItmesModel,i);
				else AddItemTotListModel(allItmesModel,i); 					
			}
		}		
	}

	// Add a LibraryItem to a DefaultListModel in alphabetical order
	private void AddItemTotListModel(DefaultListModel mdl, LibraryItem itm)
	{
		String neItemName=itm.getName().toLowerCase();
		int i=0;
		while (i< mdl.getSize())
		{
			if (neItemName.compareTo( ((LibraryItem)mdl.getElementAt(i)).getName().toLowerCase())<=0)
			{
				mdl.add(i, itm);
				return;
			}
			i++;
		}
		mdl.addElement(itm);
	}

	// Load the Encyclopedias or Magazines collection according to the user selection 
	private class CollectionHandler implements ItemListener 
	{		
		@Override
		public void itemStateChanged(ItemEvent e) {		
			ReloadCollection("");
		}
	}
}