package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;




public class PanelLibrarian extends JPanel{


	private LibraryGui libraryGui;
	
	private JButton Home; 
	/**
	 * Create the panel.
	 */
	public PanelLibrarian(LibraryGui libraryGui) {
		this.libraryGui=libraryGui;
			
		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);

		ButtonHandler handler= new ButtonHandler();
		JButton btnAddReader = new JButton("Readers");
		btnAddReader.setBounds(40, 308, 202, 102);
		btnAddReader.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddReader.addActionListener(handler);
		add(btnAddReader);



		JButton btnLibraryCollections = new JButton("Collections Info");
		btnLibraryCollections.setBounds(370, 308, 202, 102);
		btnLibraryCollections.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLibraryCollections.addActionListener(handler);
		add(btnLibraryCollections);

		JButton btnQueries = new JButton("Queries");
		btnQueries.setBounds(720, 308, 202, 102);
		btnQueries.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnQueries.addActionListener(handler);
		add(btnQueries);


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
			libraryGui.LibrarianMenuButtonClick(ButtonName);						
		}
	}
}
