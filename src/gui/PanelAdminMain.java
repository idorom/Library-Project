package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



import javax.swing.JLabel;
import java.awt.Font;



public class PanelAdminMain extends JPanel {


	private LibraryGui libraryGui;

	private JButton Home; 

	/**
	 * Create the panel.
	 */
	public PanelAdminMain(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;

		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);
		
		ButtonHandler handler= new ButtonHandler();
		
		JButton btnAddReader = new JButton("Readers");
		btnAddReader.setBounds(110, 272, 202, 102);
		btnAddReader.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddReader.addActionListener(handler);
		add(btnAddReader);


		JButton btnLibraryCollections = new JButton("Collections Info");
		btnLibraryCollections.setBounds(402, 272, 202, 102);
		btnLibraryCollections.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLibraryCollections.addActionListener(handler);
		add(btnLibraryCollections);

		JButton btnQueries = new JButton("Queries");
		btnQueries.setBounds(694, 272, 202, 102);
		btnQueries.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnQueries.addActionListener(handler);
		add(btnQueries);
		
		
		JButton btnColections = new JButton("Collections");
		btnColections.setBounds(58, 73, 202, 102);
		btnColections.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnColections.addActionListener(handler);
		add(btnColections);
		
		JButton btnLibrarians = new JButton("Librarians");
		btnLibrarians.setBounds(520, 73, 202, 102);
		btnLibrarians.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLibrarians.addActionListener(handler);
		add(btnLibrarians);
		
		JButton btnAuthors = new JButton("Authors");		
		btnAuthors.setBounds(751, 73, 202, 102);
		btnAuthors.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAuthors.addActionListener(handler);
		add(btnAuthors);
		
		JButton btnReadAndReviewItems = new JButton("Read And Review");
		btnReadAndReviewItems.setBounds(166, 471, 202, 102);
		btnReadAndReviewItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReadAndReviewItems.addActionListener(handler);
		add(btnReadAndReviewItems);

		JButton btnRecommendItems = new JButton("Your recommended");
		btnRecommendItems.setBounds(567, 471, 202, 102);
		btnRecommendItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRecommendItems.addActionListener(handler);
		add(btnRecommendItems);
		
		JButton btnItems = new JButton("Items");
		btnItems.setBounds(289, 73, 202, 102);
		btnItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnItems.addActionListener(handler);
		add(btnItems);
		
		JLabel lblAdminActions = new JLabel("Admin Actions");
		lblAdminActions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAdminActions.setBounds(51, 24, 136, 52);
		add(lblAdminActions);
		
		JLabel lblLibrarianActions = new JLabel("Librarian Actions");
		lblLibrarianActions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLibrarianActions.setBounds(51, 212, 136, 52);
		add(lblLibrarianActions);
		
		JLabel lblReaderActions = new JLabel("Reader Actions");
		lblReaderActions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblReaderActions.setBounds(51, 409, 136, 52);
		add(lblReaderActions);		
		

		Home = new JButton("Home");
		Home.setBounds(853, 0, 112, 40);
		Home.addActionListener(handler);

	}


	private class ButtonHandler implements ActionListener 
	{
		String ButtonName=null;
		public void actionPerformed( ActionEvent event )
		{
			ButtonName=event.getActionCommand();
			libraryGui.AdminMenuButtonClick(ButtonName);						
		}
	}
}
