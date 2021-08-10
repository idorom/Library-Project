package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import LibExceptions.userNotFoundGui;
import Model.Librarian;
import Model.Reader;
import Utils.UserType;
import javax.swing.SwingConstants;

public class PanelLogIn extends JPanel {

	private JButton btnLogin;
	private JPanel jpanelReturn;
	private LibraryGui libraryGui;
	private JTextField usertextField;
	private JPasswordField passwordField;
	private JLabel lblLogo;
	private JLabel lblNewLabel_1;


	/**
	 * Create the panel.
	 */
	public PanelLogIn(LibraryGui libraryGui) {

		this.libraryGui=libraryGui;


		setBounds(0, 0, 986, 694);
		setBackground(new Color(192, 192, 192));
		setVisible(true);
		setLayout(null);

		usertextField = new JTextField();
		usertextField.setBounds(477, 236, 112, 20);
		usertextField.addKeyListener(new MyKeyListener());

		add(usertextField);
		usertextField.setColumns(10);

		passwordField = new JPasswordField();	
		passwordField.addKeyListener(new MyKeyListener());
		passwordField.setBounds(477, 274, 112, 20);
		add(passwordField);

		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.addVetoableChangeListener(new VetoableChangeListener() {
			public void vetoableChange(PropertyChangeEvent arg0) {
			}
		});
		lblUserName.setForeground(Color.BLACK);
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserName.setBounds(372, 229, 89, 34);
		add(lblUserName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.addVetoableChangeListener(new VetoableChangeListener() {
			public void vetoableChange(PropertyChangeEvent arg0) {
			}
		});
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(372, 274, 86, 20);
		add(lblPassword);

		btnLogin = new JButton("LogIn");
		
		btnLogin.setBounds(440, 318, 118, 34);
		btnLogin.setVisible(true);
		add(btnLogin);
		
		lblLogo = new JLabel("L M S");
		lblLogo.setForeground(new Color(0, 0, 0));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 72));
		lblLogo.setBounds(387, 46, 191, 92);
		add(lblLogo);
		
		lblNewLabel_1 = new JLabel("Library Management System");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(364, 136, 236, 17);
		add(lblNewLabel_1);
		
		JLabel lblLogoShedow = new JLabel("L M S");
		lblLogoShedow.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogoShedow.setForeground(Color.WHITE);
		lblLogoShedow.setFont(new Font("Tahoma", Font.PLAIN, 72));
		lblLogoShedow.setBounds(389, 48, 191, 92);
		add(lblLogoShedow);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent eventvent) {
				TryLogIn();
			}
		});
	}

	public void ClearPasswordField()
	{
		
	}
	
	private class MyKeyListener  implements KeyListener {
		public void keyTyped(KeyEvent e) {	        
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==10) TryLogIn();
		}

		public void keyReleased(KeyEvent e) {	        
		}
	}



	private void TryLogIn()
	{
		UserType userType=UserType.UNKNOWN;
		String readerName="";
		Reader reader=null;
		Librarian librarian=null;
		boolean isAdmin=false;

		String p= passwordField.getText();
		try {
			if (usertextField.getText().equals("Admin"))
			{
				if (p.equals("Admin")) isAdmin=true;
			}
			else
			{
				reader=libraryGui.getLibrarySys().getReaderByIdAndPassword(usertextField.getText(), passwordField.getText());
				librarian=libraryGui.getLibrarySys().getLibrarianByIdAndPassword(usertextField.getText(), passwordField.getText());
			}

			if(!isAdmin && librarian==null && reader==null)
				throw new userNotFoundGui();


			if(isAdmin)
			{
				userType= UserType.ADMIN;
			} 


			if(reader!=null)
			{
				readerName=reader.getFirstName()+" " + reader.getLastName();
				userType= UserType.READER;						
			}


			if(librarian!=null)
			{
				userType= UserType.LIBRARIAN;						
			}

			if (!userType.equals(UserType.UNKNOWN))
			{
				passwordField.setText("");
				libraryGui.OnSucsesfullLogin(userType, readerName);
			}
		}
		catch(userNotFoundGui e) {
			return;
		}


	}
	/**
	 * @return the jpanelReturn
	 */
	public JPanel getJpanelReturn() {
		return jpanelReturn;
	}

	/**
	 * @param jpanelReturn the jpanelReturn to set
	 */
	public void setJpanelReturn(JPanel jpanelReturn) {
		this.jpanelReturn = jpanelReturn;
	}
}
