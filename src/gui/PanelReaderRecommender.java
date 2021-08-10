package gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import Model.ArithmeticMean;
import Model.GeometricMean;
import Model.HarmonicMean;
import Model.LibraryItem;
import Model.Reader;
import Model.ScoreCalculator;
import View.ViewModelHelper;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.DefaultTableModel;

import LibExceptions.IleagalNumberExceptionGui;

public class PanelReaderRecommender extends JPanel {

	private Reader reader;

	private LibraryGui libraryGui;
	
	private JButton btnBack;

	private JTextField txtK;

	private JComboBox<String> cmbCalculateScore;

	private JButton btnNewButton;

	private JComboBox<String> cmbChooseCommand;

	private JLabel lblReaderPicture;

	private DefaultTableModel itemsTableModel;

	private JTable itemsTable;

	private JComboBox<String> cmbReader;

	private ScoreCalculator score;
	private JLabel lblRecommendedForYou;
	private JLabel lblHello;

	/**
	 * Create the panel.
	 */
	public PanelReaderRecommender(LibraryGui libraryGui) { 

		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);


		lblReaderPicture = new JLabel("");
		lblReaderPicture.setBounds(35, 69, 165, 156);		
		Border border = BorderFactory.createLineBorder(Color.black, 1);		 
		// set the border of this component
		lblReaderPicture.setBorder(border);
		add(lblReaderPicture);

		cmbChooseCommand = new JComboBox<String>();
		cmbChooseCommand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbChooseCommand.setModel(new DefaultComboBoxModel<String>(new String[] {"What do you want to find?", "Books by author",
				"Paper by author", "Books by topic", "Paper by topic"}));
		cmbChooseCommand.setBounds(228, 104, 202, 40);
		add(cmbChooseCommand);


		cmbReader = new JComboBox<String>();
		cmbReader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbReader.setBounds(274, 69, 188, 20);
		cmbReader.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				ReaderSelected();
			}				
		});	
		add(cmbReader);


		JLabel lblHowMany = new JLabel("  How many?");
		lblHowMany.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHowMany.setBounds(440, 104, 91, 40);
		add(lblHowMany);

		txtK = new JTextField();
		txtK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtK.setBounds(541, 114, 86, 20);
		add(txtK);
		txtK.setColumns(10);

		cmbCalculateScore = new JComboBox<String>();
		cmbCalculateScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbCalculateScore.setModel(new DefaultComboBoxModel<String>(new String[] {"Please choose calculating way", 
				"ArithmaticMean", "HarmonicMean", "GeometricMean"}));
		cmbCalculateScore.setBounds(643, 104, 221, 40);
		cmbCalculateScore.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(cmbCalculateScore.getModel().getElementAt(1).toString().equals(e.getItem().toString()))
					score= new ArithmeticMean();

				else if(cmbCalculateScore.getModel().getElementAt(2).toString().equals(e.getItem().toString()))
					score= new HarmonicMean();

				else if(cmbCalculateScore.getModel().getElementAt(3).toString().equals(e.getItem().toString()))
					score= new GeometricMean();
				else 
					return;

			}
		});
		add(cmbCalculateScore);

		//Return to the main panel of the user
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);

		//Button that choose reader, a number and ScoreCalculator and 
		//reload to the table the recommender LibraryItems for the selected reader 
		btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ArrayList<LibraryItem> help = new ArrayList<LibraryItem>();
					score = null;
					
					//check witch ScoreCalculator the user has chosen
					if(cmbCalculateScore.getSelectedIndex() == 1) 
						score = new ArithmeticMean();
					if(cmbCalculateScore.getSelectedIndex() == 2) 
						score = new HarmonicMean();
					if(cmbCalculateScore.getSelectedIndex() == 3) 
						score = new GeometricMean();

					//check if the user has typed a positive number or not
					if(txtK.getText()==null || txtK.getText().isEmpty())
						throw new IleagalNumberExceptionGui("You need to enter a number");					
					int k= ViewModelHelper.IntK(txtK.getText());	

					//get the command 
					int mission =  cmbChooseCommand.getSelectedIndex();
					
					if(score==null|| mission == 0)
						throw new IleagalNumberExceptionGui("You need to choose \nCommand/ScoreCalculator way");

					
					//reload to the table LibraryItems by the index of command that was chosen
					if(mission == 1 ) {
						help.addAll(libraryGui.getLibrarySys().recommendBooksByAuthor(reader, k, score));
					}
					if(mission == 2) {
						help.addAll(libraryGui.getLibrarySys().recommendPapersByAuthor(reader, k, score));
					}
					if(mission == 3) {
						help.addAll(libraryGui.getLibrarySys().recommendBooksByTopic(reader, k, score));
					}
					if(mission == 4) {
						help.addAll(libraryGui.getLibrarySys().recommendPapersByTopic(reader, k, score));						
					}					
					
					ReloadeItems(help, score);
					
					//if there no result JOptionPane promotes the user 
					if (help.size()==0)
					{
						JOptionPane.showConfirmDialog(null, "No matching result found" 
								, "Recommended"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}										
				} catch (NumberFormatException  arg) {
					return;
				}catch (IleagalNumberExceptionGui arg) {
					return;
				}	
			}
		});
		btnNewButton.setBounds(35, 242, 97, 23);
		add(btnNewButton);
		

		
		String[] headersNames = new String[] {
				"Item name" , "Author" , "Current score" , "Topic", "Read check box"
		};			
		itemsTableModel = new DefaultTableModel(0,6){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		itemsTableModel.setColumnIdentifiers(headersNames);
		itemsTable = new JTable(itemsTableModel);
		itemsTable.setEnabled(true);
		itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		itemsTable.setRowSelectionAllowed(true);

		
		itemsTable.setSize(700, 100);
		itemsTable.setVisible(true);
		JScrollPane pane;

		pane= new JScrollPane(itemsTable);
		pane.setBounds(35, 276, 829, 327);
		pane.setEnabled(false);
		add(pane);
		
		lblRecommendedForYou = new JLabel("Recommended for you");
		lblRecommendedForYou.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRecommendedForYou.setBounds(35, 11, 241, 23);
		add(lblRecommendedForYou);
		
		lblHello = new JLabel("Hello!");
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHello.setBounds(228, 74, 46, 14);
		add(lblHello);
		InitPanel();
	}

	
	//reload LibraryItems from 'itemsTemp' to the table in order by using ScoreCalculator each time
	private void ReloadeItems(ArrayList<LibraryItem> itemsTemp, ScoreCalculator calculator)
	{
		ClearModelAndSetHeaders(new String[] {"Item name" , "Author" , "Current score" , "Topic" });

		int counter=0;

		for(LibraryItem i: itemsTemp) 
		{	
			Object[] CurrentUser = new Object[] {
					i.getName(), i.getAuthor() , i.GetScore(calculator), i.getTopic()
			};
			itemsTableModel.insertRow(counter++, CurrentUser);			
		}
	}

	//Clear old rows and Headers from the table and give new Headers to the table
	private void ClearModelAndSetHeaders(String[] Headers)
	{
		while (itemsTableModel.getRowCount() > 0) {
			itemsTableModel.removeRow(0);
		}
		itemsTableModel.setColumnIdentifiers(Headers);
		itemsTable.getTableHeader().setReorderingAllowed(false); //for not allow to move columns by the headers
	}

	
	//if CurrentReader isn't empty the method reload only the specific Reader to cmbReader
	//else reload all the readers from Library class to cmbReader
	public void ReloadReaders(String CurrentReader)
	{		
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
		if (readersList.size()>0) this.reader=readersList.get(0);
		else this.reader=null;
		SetReaderPicture();
	}

	//Initialize and set reader image
	private void ReaderSelected()
	{
		InitPanel();
		SetReaderPicture();
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
	
	//Initialize all the components when the user enter to the panel and select reader
	public void InitPanel()
	{
		//Clear All the Fields in the panel		
		txtK.setText("");
		cmbChooseCommand.setSelectedIndex(0);
		cmbCalculateScore.setSelectedIndex(0);
		ReloadeItems(libraryGui.getLibrarySys().getItemsSorted(),new ArithmeticMean()); //DEFUALT
		this.reader=(Reader)cmbReader.getSelectedItem();
	}

}