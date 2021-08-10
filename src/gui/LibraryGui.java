package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import Model.Author;
import Model.Librarian;
import Model.Library;
import Model.Reader;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import Utils.UserType;



public class LibraryGui extends JFrame {


	private Library LibrarySys=null;

	private UserType userType = UserType.UNKNOWN;
	private String readerName="";
	
	//Readers's AND Admin's Panels
	private PanelReader panelReader;
	private PanelReaderChooseItems panelReaderChooseItems;
	private PanelReaderRecommender panelReaderRecommender;
	//Libririan's AND Admin's Panels
	private PanelLibrarian panelLibrarian;
	private PanelLibrarianGetBestK panelLibrarianGetBestK;	
	private PanelLibrarianCollections panelLibrarianCollections;	
	private PanelLibrarianReaders panelLibrarianReaders;	
	//Only Admin's Panels
	private PanelAdminMain panelAdminMain;
	private PanelAdminAddItems panelAdminAddItems;
	private PanelAdminAddAuthors panelAdminAddAuthors;
	private PanelAdminAddLibrarians panelAdminAddLibrarians;
	private PanelAdminAddCollections panelAdminAddCollections;
	private PanelLogIn panelLogIn;

	//A flag used to indicate if data was changed
	//Used when logging off or exiting the application to prompt the user to save the changes
	private boolean dataChanged;
	


	/**
	 * Create the frame.
	 */	
	public LibraryGui() {
		super("Library System");

		LoadLibraryData();

		PrintAllUsers();		


		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 986, 670);

		panelLogIn= new PanelLogIn(this);		
		add(panelLogIn);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileBtn = new JMenu("File");
		menuBar.add(fileBtn);

		JMenuItem mntmHome = new JMenuItem("Logout");
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// Set to a value that will not be returned by the Option Dialog, 
				//in order to do nothing if no data was change
				int input=JOptionPane.PLAIN_MESSAGE;  
				if (dataChanged)
				{
					input = ShowSaveDataOption(false);				
					if (input == JOptionPane.CANCEL_OPTION) return;
				}

				if (input==JOptionPane.YES_OPTION) SaveDataToFile();
				else if (input==JOptionPane.NO_OPTION) LoadLibraryData();

				HideAllPanels();				
				panelLogIn.setVisible(true);
			}

		});
		fileBtn.add(mntmHome);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Set to a value that will not be returned by the Option Dialog, 
				//in order to do mothing if no data was change
				int input=JOptionPane.PLAIN_MESSAGE;
				if (dataChanged)
				{
					input=ShowSaveDataOption(true);
					if (input==JOptionPane.CANCEL_OPTION) return;
				}

				if (input==JOptionPane.YES_OPTION) SaveDataToFile();
				System.exit(0);
			}
		});
		fileBtn.add(mntmExit);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				int input=JOptionPane.NO_OPTION;
				if (dataChanged)
				{
					input = ShowSaveDataOption(true);				
					if (input == JOptionPane.CANCEL_OPTION) return;
				}

				if (input==JOptionPane.YES_OPTION) SaveDataToFile();						            	              
				System.exit(0);
			}
		});

		this.setLocationRelativeTo(null);
		dataChanged=false;
	}

	// Load the saved data from the serialized files 
	private void LoadLibraryData()
	{
		File file = new File("library.ser");
		if(file.exists())
			LibrarySys=Library.deserialize();
		else
			LibrarySys=Library.getInstance();

		dataChanged=false;
	}
	
	// Prompt the user to save or discard changes in the data
	private int ShowSaveDataOption(Boolean ForExit)
	{

		String msg="Library data was changed.\r\n" + 
				"Do you want to save the changes?\r\n" + 
				"Press \"Yes\" to save, \"No\" to discard changes and continue\r\n" + 
				((ForExit)? "\"Cancel\" to go back to the application"
						:"\"Cancel\" to continue without login out.");

		return JOptionPane.showConfirmDialog(null, msg 
				, "Log-off",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);						
	}

	// Hide all the panels in order to show only on panel at a time
	private void HideAllPanels()
	{
		if(panelLogIn!=null) panelLogIn.setVisible(false);	
		if(panelReader!=null) panelReader.setVisible(false);
		if(panelReaderRecommender!=null) panelReaderRecommender.setVisible(false);
		if(panelReaderChooseItems!=null) panelReaderChooseItems.setVisible(false);
		if(panelLibrarian!=null) panelLibrarian.setVisible(false);
		if(panelLibrarianGetBestK!=null) panelLibrarianGetBestK.setVisible(false);
		if(panelLibrarianCollections!=null) panelLibrarianCollections.setVisible(false);
		if(panelLibrarianReaders!=null) panelLibrarianReaders.setVisible(false);				
		if(panelAdminMain!=null) panelAdminMain.setVisible(false);
		if(panelAdminAddItems!=null) panelAdminAddItems.setVisible(false);  
		if(panelAdminAddAuthors!=null) panelAdminAddAuthors.setVisible(false);
		if(panelAdminAddLibrarians!=null) panelAdminAddLibrarians.setVisible(false);	
		if(panelAdminAddCollections!=null) panelAdminAddCollections.setVisible(false);
	}

	//Set local variables with the current logged in user
	public void OnSucsesfullLogin(UserType userType, String readerName)
	{
		this.userType=userType;
		this.readerName=readerName;
		System.out.println("Login button pressed");
		Home();		
	}


	//Hide current panel and show the user relevant "home" panel
	public void Home()
	{
		if (userType.equals(UserType.UNKNOWN)) {
			panelLogIn= new PanelLogIn(this);
			panelLogIn.setVisible(true);
			JOptionPane.showMessageDialog(null,"User Doesn't exit at all" ,"Error", JOptionPane.ERROR_MESSAGE);
			return; 
		}

		HideAllPanels();

		if (userType.equals(UserType.LIBRARIAN))
		{
			if (panelLibrarian==null) {
				panelLibrarian=new PanelLibrarian(this);
				add(panelLibrarian);
			}	
			panelLibrarian.setVisible(true);
		}
		if (userType.equals(UserType.READER))
		{
			if (panelReader==null) {
				panelReader=new PanelReader(this);				
				add(panelReader);
			}	
			panelReader.setReader(readerName);
			panelReader.setVisible(true);			
		}
		if (userType.equals(UserType.ADMIN))
		{
			if (panelAdminMain==null) {
				panelAdminMain=new PanelAdminMain(this);
				add(panelAdminMain);
			}	
			panelAdminMain.setVisible(true);
		}

	}

	// Display the relevant panel for a librarian according 
	// to his selection from his main panel
	public void LibrarianMenuButtonClick(String ButtonName)
	{
		HideAllPanels();
		if (ButtonName.equals("Readers"))
		{
			if (panelLibrarianReaders==null) {
				panelLibrarianReaders=new PanelLibrarianReaders(this);
				add(panelLibrarianReaders);
			}			
			panelLibrarianReaders.InitPanel();
			panelLibrarianReaders.setVisible(true);
		}

		if (ButtonName.equals("Collections Info"))
		{
			if (panelLibrarianCollections==null) {
				panelLibrarianCollections=new PanelLibrarianCollections(this);
				add(panelLibrarianCollections);
			}
			panelLibrarianCollections.InitPanel();
			panelLibrarianCollections.setVisible(true);
		}

		if (ButtonName.equals("Queries"))
		{
			if (panelLibrarianGetBestK==null) {
				panelLibrarianGetBestK=new  PanelLibrarianGetBestK(this);
				add(panelLibrarianGetBestK);
			}
			panelLibrarianGetBestK.InitPanel();
			panelLibrarianGetBestK.setVisible(true);
		}		
	}

	// Display the relevant panel for a reader according 
	// to his selection from his main panel
	public void ReaderMenuButtonClick(String ButtonName)
	{
		HideAllPanels();
		if (ButtonName.equals("Read And Review"))
		{
			if (panelReaderChooseItems==null) {
				panelReaderChooseItems= new PanelReaderChooseItems(this);
				add(panelReaderChooseItems);
			}	
			panelReaderChooseItems.ReloadReaders(readerName);
			panelReaderChooseItems.setVisible(true);
		}

		if (ButtonName.equals("Your recommended"))
		{
			if (panelReaderRecommender==null) {
				panelReaderRecommender=new PanelReaderRecommender(this);
				add(panelReaderRecommender);
			}
			panelReaderRecommender.ReloadReaders(readerName);
			panelReaderRecommender.InitPanel();
			panelReaderRecommender.setVisible(true);
		}

	}

	// Display the relevant panel for a Admin according 
	// to his selection from his main panel
	public void AdminMenuButtonClick(String ButtonName)
	{
		HideAllPanels();
		if (ButtonName.equals("Read And Review"))
		{
			if (panelReaderChooseItems==null) {
				panelReaderChooseItems= new PanelReaderChooseItems(this);
				add(panelReaderChooseItems);
			}
			panelReaderChooseItems.ReloadReaders("");
			panelReaderChooseItems.setVisible(true);
		}

		if (ButtonName.equals("Your recommended"))
		{
			if (panelReaderRecommender==null) {
				panelReaderRecommender=new PanelReaderRecommender(this);
				add(panelReaderRecommender);
			}
			panelReaderRecommender.ReloadReaders("");
			panelReaderRecommender.InitPanel();
			panelReaderRecommender.setVisible(true);
		}

		if (ButtonName.equals("Readers"))
		{
			if (panelLibrarianReaders==null) {
				panelLibrarianReaders=new PanelLibrarianReaders(this);
				add(panelLibrarianReaders);
			}	
			panelLibrarianReaders.InitPanel();
			panelLibrarianReaders.setVisible(true);
		}

		if (ButtonName.equals("Collections Info"))
		{
			if (panelLibrarianCollections==null) {
				panelLibrarianCollections=new PanelLibrarianCollections(this);
				add(panelLibrarianCollections);
			}
			panelLibrarianCollections.InitPanel();
			panelLibrarianCollections.setVisible(true);
		}		

		if (ButtonName.equals("Queries"))
		{
			if (panelLibrarianGetBestK==null) {
				panelLibrarianGetBestK=new  PanelLibrarianGetBestK(this);
				add(panelLibrarianGetBestK);
			}
			panelLibrarianGetBestK.InitPanel();
			panelLibrarianGetBestK.setVisible(true);
		}	

		
		if (ButtonName.equals("Items"))
		{
			if (panelAdminAddItems==null) {
				panelAdminAddItems=new  PanelAdminAddItems(this);
				add(panelAdminAddItems);
			}
			panelAdminAddItems.InitPanel();
			panelAdminAddItems.setVisible(true);
		}

		if (ButtonName.equals("Librarians"))
		{
			if (panelAdminAddLibrarians==null) {
				panelAdminAddLibrarians=new  PanelAdminAddLibrarians(this);
				add(panelAdminAddLibrarians);
			}
			panelAdminAddLibrarians.InitPanel();
			panelAdminAddLibrarians.setVisible(true);
		}

		if (ButtonName.equals("Authors"))  
		{
			if (panelAdminAddAuthors==null) {
				panelAdminAddAuthors=new  PanelAdminAddAuthors(this);
				add(panelAdminAddAuthors);
			}
			panelAdminAddAuthors.InitPanel();
			panelAdminAddAuthors.setVisible(true);
		}

		if (ButtonName.equals("Collections"))
		{
			if (panelAdminAddCollections==null) {
				panelAdminAddCollections=new  PanelAdminAddCollections(this);
				add(panelAdminAddCollections);
			}
			panelAdminAddCollections.InitPanel();
			panelAdminAddCollections.setVisible(true);
		}

	}


	// return the Library class (main model class)
	public Library getLibrarySys() {
		return LibrarySys;
	}


	// Return a sorted list of the Authors 
	public List<Author> getAuthorsSorted() {
		ArrayList<Author> lst= new ArrayList<Author>();

		for(Author a: LibrarySys.getAuthors()) 	lst.add(a);

		Collections.sort(lst, new Comparator<Author>() {
			public int compare(Author o1, Author o2) {
				return o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
			}
		}); 

		return lst;
	}

	
	// Print all users and passwords for TESTING ONLY
	public void PrintAllUsers()
	{
		
		//------------Print All Users ID And Password---------------
		for(Librarian l: LibrarySys.getLibrarians())
		{	
			System.out.println(l.getId() + " "+ l.getPassword());
		}
		for(Reader r: LibrarySys.getReaders())
		{

			System.out.println(r.getFirstName()+" "+ r.getId() + " "+ r.getPassword());
		}
	}

	
	// Set the data was changed flag. 
	//Used by the different panels when data is changed
	public void DataWasChanged()
	{
		dataChanged=true;
	}

	// Serialize the data to file
	private void SaveDataToFile()
	{
		Library.serialize(LibrarySys);
		dataChanged=false;
	}
	
}