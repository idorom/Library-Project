package LibExceptions;

import javax.swing.JOptionPane;

public class userNotFoundGui extends Exception {
	public userNotFoundGui() {
		
		String message="User is not found in the system,\nor incorrect password\nPlease cheack your ID And Password";
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
