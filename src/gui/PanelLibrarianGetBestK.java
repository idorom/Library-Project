package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import LibExceptions.IleagalNumberExceptionGui;
import Model.ArithmeticMean;
import Model.Author;
import Model.Book;
import Model.GeometricMean;
import Model.HarmonicMean;
import Model.LibraryItem;
import Model.Paper;
import Model.Reader;
import Model.ScoreCalculator;
import Utils.Topic;
import View.ViewModelHelper;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class PanelLibrarianGetBestK extends JPanel {

	private LibraryGui libraryGui; 

	private JTable table;

	private DefaultTableModel itemsModel;

	private JTextField txtK;

	private JComboBox<String> cmbCalculateScore;

	private JComboBox<String> chooseProgram;

	private JComboBox<String> comboBoxAuthors;

	private JComboBox<String> comboBoxTopics;

	private JLabel lblAuthorORTopic;
	
	private JButton btnBack;
	private JLabel lblNewLabel;
	
	private DecimalFormat decimalFormat;


	//Create the panel.
	public PanelLibrarianGetBestK(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;
		setBounds(0, 0, 986, 694);
		setVisible(false);
		setBackground(new Color(192, 192, 192));
		setLayout(null);

		decimalFormat = new DecimalFormat("#0.0 ");

		JLabel lblPleaseEnterA = new JLabel("Please enter a number");
		lblPleaseEnterA.setBounds(391, 54, 157, 26);
		lblPleaseEnterA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblPleaseEnterA);

		txtK = new JTextField();
		txtK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtK.setBounds(537, 57, 56, 20);
		add(txtK);
		txtK.setColumns(10);

		JLabel lblChooseCalculateScore = new JLabel("choose a calculate form");
		lblChooseCalculateScore.setBounds(615, 54, 157, 26);
		lblChooseCalculateScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblChooseCalculateScore);
		cmbCalculateScore = new JComboBox<String>();
		cmbCalculateScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbCalculateScore.setBounds(776, 57, 116, 20);
		cmbCalculateScore.setModel(new DefaultComboBoxModel<String>(new String[] {"", "ArithmeticMean", "GeometricMean", "HarmonicMean"}));
		cmbCalculateScore.setMaximumRowCount(4);
		cmbCalculateScore.setSelectedIndex(0);
		add(cmbCalculateScore);

		JLabel whatWouldYou = new JLabel("what would you like to find");
		whatWouldYou.setBounds(35, 59, 185, 16);
		whatWouldYou.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(whatWouldYou);

		comboBoxAuthors = new JComboBox<String>();
		comboBoxAuthors.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxAuthors.setModel(new DefaultComboBoxModel(libraryGui.getLibrarySys().getAuthors().toArray()));
		comboBoxAuthors.setMaximumRowCount(4);
		comboBoxAuthors.setBounds(211, 91, 170, 20);
		comboBoxAuthors.setVisible(false);
		add(comboBoxAuthors);

		comboBoxTopics = new JComboBox<String>();
		comboBoxTopics.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxTopics.setModel(new DefaultComboBoxModel(Topic.values()));
		comboBoxTopics.setMaximumRowCount(4);
		comboBoxTopics.setBounds(211, 91, 170, 20);
		comboBoxTopics.setVisible(false);
		add(comboBoxTopics);

		chooseProgram = new JComboBox<String>();
		chooseProgram.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseProgram.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Best book(1)", "Best Paper(1)", "Best book(s)", "Best paper(s)", 
				"Best Readers", "Best Authors", "Best books of author", "Best papers of author", "Best books of topic", "Best papers of topic"}));
		chooseProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				to set Enabled or not calculateScore every time the user choose command
				setCalculateScoreEnabled();
				txtK.setText("");
				cmbCalculateScore.setSelectedIndex(0);
				
				if(chooseProgram.getSelectedIndex() == 1) {
					txtK.setText("1");
					setLable("");
					txtK.setEditable(false);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 2) {
					txtK.setText("1");
					setLable("");
					txtK.setEditable(false);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 3) {
					setLable("");
					txtK.setEditable(true);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 4) {
					setLable("");
					txtK.setEditable(true);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() ==5 ) {
					setLable("");
					txtK.setEditable(true);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 6) {
					setLable("");	
					txtK.setEditable(true);
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 7) {
					txtK.setEditable(true);
					setLable("Author");
					comboBoxAuthors.setVisible(true);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 8) {
					txtK.setEditable(true);
					setLable("Author");
					comboBoxAuthors.setVisible(true);
					comboBoxTopics.setVisible(false);
				}
				if(chooseProgram.getSelectedIndex() == 9) {
					txtK.setEditable(true);
					setLable("Topic");
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(true);
				}
				if(chooseProgram.getSelectedIndex() == 10) {
					txtK.setEditable(true);
					setLable("Topic");
					comboBoxAuthors.setVisible(false);
					comboBoxTopics.setVisible(true);
				}
			}
		});
		chooseProgram.setBounds(211, 57, 170, 20);
		chooseProgram.setMaximumRowCount(4);
		add(chooseProgram);
		cmbCalculateScore.setSelectedIndex(0);
		


		lblAuthorORTopic = new JLabel();
		lblAuthorORTopic.setHorizontalAlignment(SwingConstants.RIGHT);
		setLable("");
		lblAuthorORTopic.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAuthorORTopic.setBounds(112, 93, 89, 16);
		lblAuthorORTopic.setVisible(true);
		add(lblAuthorORTopic);


		//Return to the main panel of the user 
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				libraryGui.Home();
			}
		});
		btnBack.setBounds(845, 11, 89, 23);
		add(btnBack);


		itemsModel =  new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		table = new JTable(itemsModel);
		table.setEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		table.setSize(700, 100);
		table.setVisible(true);

		JScrollPane pane;
		pane= new JScrollPane(table);
		pane.setBounds(35, 201, 736, 342);		
		pane.setEnabled(false);
		add(pane);

		JButton librarianAddNewUserAdmin = new JButton("Show");
		librarianAddNewUserAdmin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		librarianAddNewUserAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					comboBoxAuthors.setVisible(false);
					
					//check if the user has typed a positive number or not
					if(txtK.getText()==null || txtK.getText().isEmpty())
						throw new IleagalNumberExceptionGui("You need to enter a number");					
					int k= ViewModelHelper.IntK(txtK.getText());
					
					//check witch ScoreCalculator the user has chosen
					ScoreCalculator score = null; 
					if(cmbCalculateScore.getSelectedIndex() == 1)
						score = new ArithmeticMean();
					if(cmbCalculateScore.getSelectedIndex() == 2)
						score = new GeometricMean();
					if(cmbCalculateScore.getSelectedIndex() == 3)
						score = new HarmonicMean();

					
					//Command 0, 5 And 6 Not need need score to calculate
					//reload to the table data by the index of command that was chosen
					if (chooseProgram.getSelectedIndex()==0) {
						return;
					}
					else if (chooseProgram.getSelectedIndex()==5) {
						ArrayList<Reader> help = new ArrayList<Reader>(libraryGui.getLibrarySys().getBestReaders(k));
						ReloadeReaders(help);
					}					
					else if (chooseProgram.getSelectedIndex()==6) {
						ArrayList<Author> help = new ArrayList<Author>(libraryGui.getLibrarySys().getBestAuthors(k));
						ReloadeAuthors(help);
					}					
					//All the commands that need score to calculate
					else
					{ 		
						//to make sure that without choosing a score in commands that needed a score, an exception will throws
						if(score==null) 
							throw new IleagalNumberExceptionGui("You need to choose \na ScoreCalculator way");
						
						//reload to the table LibraryItems by the index of command that was chosen
						//indexes: 1-4, 7-10
						if (chooseProgram.getSelectedIndex()==1) {
							ArrayList<Book> help = new ArrayList<Book>();
							help.add((Book) libraryGui.getLibrarySys().getBestBook(score));
							ReloadeBooks(help,score);
						}
						if (chooseProgram.getSelectedIndex()==2) {
							ArrayList<Paper> help1 = new ArrayList<Paper>();
							help1.add((Paper) libraryGui.getLibrarySys().getBestPaper(score));
							ReloadePapers(help1,score);			
						}
						if (chooseProgram.getSelectedIndex()==3) {
							ArrayList<Book> help = new ArrayList<Book>(libraryGui.getLibrarySys().getBestBooks(k, score));
							ReloadeBooks(help,score);
						}						
						if (chooseProgram.getSelectedIndex()==4) {
							ArrayList<Paper> help = new ArrayList<Paper>(libraryGui.getLibrarySys().getBestPapers(k, score));
							ReloadePapers(help,score);					
						}
						
						if (chooseProgram.getSelectedIndex()==7) {
							comboBoxAuthors.setVisible(true);
							comboBoxTopics.setVisible(false);
							String s = comboBoxAuthors.getSelectedItem().toString();
							Author author=null;

							for(Author a: libraryGui.getLibrarySys().getAuthors())
							{
								if(s.equals(a.toString()))
									author=a;
							}
							if(author==null)
								return;

							ArrayList<Book> help = new ArrayList<Book>(libraryGui.getLibrarySys().getBestAuthorBooks(author , k, score));
							ReloadeBooks(help, score);				
						}
						if (chooseProgram.getSelectedIndex()==8) {
							comboBoxAuthors.setVisible(true);
							comboBoxTopics.setVisible(false);
							String s = comboBoxAuthors.getSelectedItem().toString();
							Author author=null;

							for(Author a: libraryGui.getLibrarySys().getAuthors())
							{
								if(s.equals(a.toString()))
									author=a;
							}
							if(author==null)
								return;

							ArrayList<Paper> help = new ArrayList<Paper>(libraryGui.getLibrarySys().getBestAuthorPapers(author , k, score));
							ReloadePapers(help, score);		
						}
						if (chooseProgram.getSelectedIndex()==9) {
							comboBoxAuthors.setVisible(false);
							comboBoxTopics.setVisible(true);
							String s = comboBoxTopics.getSelectedItem().toString();
							Topic topic = null;

							for(Topic t: Topic.values())
							{
								if(s.equals(t.toString()))
									topic= t;
							}
							if(topic==null)
								return;

							ArrayList<Book> help = new ArrayList<Book>(libraryGui.getLibrarySys().getBestTopicBooks(topic , k, score));
							ReloadeBooks(help, score);
						}
						if (chooseProgram.getSelectedIndex()== 10) {
							comboBoxAuthors.setVisible(false);
							comboBoxTopics.setVisible(true);
							String s = comboBoxTopics.getSelectedItem().toString();
							Topic topic = null;

							for(Topic t: Topic.values())
							{
								if(s.equals(t.toString()))
									topic= t;
							}
							if(topic==null)
								return;

							ArrayList<Paper> help = new ArrayList<Paper>(libraryGui.getLibrarySys().getBestTopicPapers(topic , k, score));
							ReloadePapers(help, score);
						}
					}
					
					//if there no result JOptionPane promotes the user
					if (itemsModel.getRowCount()==0)
					{						
						JOptionPane.showConfirmDialog(null, "No matching result found" 
								, "Find best things"
								,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);					
					}
				} catch (NumberFormatException  arg) {
					return;
				}catch(IleagalNumberExceptionGui arg1) {
					return;
				}
			}
		});
		librarianAddNewUserAdmin.setBounds(35,158,116,30);
		add(librarianAddNewUserAdmin);
		
		lblNewLabel = new JLabel("Find best things");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(35, 11, 194, 22);
		add(lblNewLabel);
		InitPanel();
	}

	//reload books from 'books' to the table in order by using ScoreCalculator each time 
	private void ReloadeBooks(ArrayList<Book> books , ScoreCalculator score)
	{
		ClearModelAndSetHeaders(new String[]{"Item name" , "Author" , "Current score" , "Topic", "Size", "Acadmemic"});
		ViewModelHelper.alignTableColumnRight(table, 2);
		
		int counter=0;
		for(Book b: books) 
		{	
			Object[] CurrentItem = new Object[] {
					b.getName(), b.getAuthor() , decimalFormat.format(b.GetScore(score))
							, b.getTopic(), b.getBookSize() ,b.getAcadmemicBook()
			};
			itemsModel.insertRow(counter++, CurrentItem);			
		}
	}

	//reload papers from 'papers' to the table in order by using ScoreCalculator each time
	private void ReloadePapers(ArrayList<Paper> papers, ScoreCalculator score)
	{
		ClearModelAndSetHeaders(new String[]{"Item name" , "Author" , "Current score" , "Topic", "Value", "University"});
		ViewModelHelper.alignTableColumnRight(table, 2);
		
		int counter=0;
		for(Paper p: papers) 
		{	
			Object[] CurrentItem = new Object[] {
					p.getName(), p.getAuthor() , decimalFormat.format(p.GetScore(score))
						, p.getTopic(), p.getPaperValue(), p.getUniversity() 
			};
			itemsModel.insertRow(counter++, CurrentItem);			
		}
	}

	//reload authors from 'authors' to the table
	private void ReloadeAuthors(ArrayList<Author> authors)
	{
		ClearModelAndSetHeaders(new String[]{"First name" , "Last name" , "Author topic", "Items Read"});

		int counter=0;
		for(Author a: authors) 
		{	
			int count = 0;
			for(LibraryItem item : a.getItems()) {
				count += item.getReaders().size();
			}

			Object[] CurrentUser = new Object[] {
					a.getFirstName(), a.getLastName() , a.gettopic(), count
			};
			itemsModel.insertRow(counter++, CurrentUser);			
		}
	}
	
	//reload readers from 'readers' to the table
	private void ReloadeReaders(ArrayList<Reader> readers){

		ClearModelAndSetHeaders(new String[]{"ID" , "First name" , "Last name" , "Gender", "Joining date", "items read"});
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

		int counter = 0;
		for(Reader rb: readers) 
		{	
			Object[] CurrentUser = new Object[] {
					rb.getId() , rb.getFirstName() , rb.getLastName() , rb.getGender().toString(), sdfr.format(rb.getJoiningDate()).toString() ,rb.getItems().size()
			};	
			itemsModel.insertRow(counter++, CurrentUser);			
		}
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


	//change the label text from "" or "Author" or "Topic"
	//for reuse of the label
	private void setLable(String s)
	{
		if(s.equals("Author"))
		{
			lblAuthorORTopic.setText("Author");
			comboBoxAuthors.setSelectedIndex(0);
		}
		else if(s.equals("Topic"))
		{
			lblAuthorORTopic.setText("Topic");
			comboBoxTopics.setSelectedIndex(0);
		}
		else
			lblAuthorORTopic.setText("");
		lblAuthorORTopic.setVisible(true);
	}
	
	
	//set visible false if selected command 5 OR 6 else true, because the order of "best readers" And "best Authors" is 
	//order by number of readers who read LibraryItems and the other need a ScoreCalculator		
	private void setCalculateScoreEnabled(){
		if(chooseProgram.getSelectedIndex()==5 || chooseProgram.getSelectedIndex()==6)
		{
			cmbCalculateScore.setSelectedIndex(0);
			cmbCalculateScore.setEnabled(false);
		}			
		else
			cmbCalculateScore.setEnabled(true);
	}
	
	//Initialize all the components when the user enter to the panel
	public void InitPanel()
	{
		//Clear All the Fields in the panel
		txtK.setText("");
		chooseProgram.setSelectedIndex(0);
		cmbCalculateScore.setSelectedIndex(0);
		ClearModelAndSetHeaders(null);
	}
}