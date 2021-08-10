package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Model.Reader;
import javax.swing.SwingConstants;



public class PanelReader extends JPanel {

	private Reader reader;

	private JButton Home;

	private PhotoButton ChoosePhoto; 

	private LibraryGui libraryGui;
	
	private JLabel lblHello;
	
	public PanelReader( LibraryGui libraryGui) { 

		this.libraryGui=libraryGui;
		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);


		ChoosePhoto=new PhotoButton();
		ChoosePhoto.setForeground(Color.GRAY);
		ChoosePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ChoosePhoto.SelectPicture(reader)== JFileChooser.APPROVE_OPTION)
					libraryGui.DataWasChanged();
			}
		});		
		ChoosePhoto.setBounds(28, 69, 165, 156);		
		add(ChoosePhoto);

		ButtonHandler handler= new ButtonHandler();

		JButton btnReadAndReviewItems = new JButton("Read And Review");
		btnReadAndReviewItems.setBounds(199, 310, 202, 102);
		btnReadAndReviewItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReadAndReviewItems.addActionListener(handler);
		add(btnReadAndReviewItems);

		JButton btnRecommendItems = new JButton("Your recommended");
		btnRecommendItems.setBounds(571, 310, 202, 102);
		btnRecommendItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRecommendItems.addActionListener(handler);
		add(btnRecommendItems);

		JLabel lblNewLabel = new JLabel("Click to change the photo");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(28, 232, 165, 14);
		add(lblNewLabel);
		
		lblHello = new JLabel("Hello");
		lblHello.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHello.setBounds(254, 129, 398, 31);
		add(lblHello);

		Home = new JButton("Home");
		Home.setBounds(853, 0, 112, 40);
		Home.addActionListener(handler);
	}


	public void setReader(String CurrentReader)
	{
		reader=null;
		ChoosePhoto.setIcon(null);
		lblHello.setText("");

		for(Reader r: libraryGui.getLibrarySys().getReaders()) 
			if (r.toString().equals(CurrentReader))
			{
				reader=r;
				lblHello.setText("Hello " +reader.toString());
				ChoosePhoto.setIcon(this.reader.getUserIcon());
				break;
			}

	}


	private class ButtonHandler implements ActionListener 
	{
		String ButtonName=null;
		public void actionPerformed( ActionEvent event )
		{
			ButtonName=event.getActionCommand();
			libraryGui.ReaderMenuButtonClick(ButtonName);						
		}
	}
}
