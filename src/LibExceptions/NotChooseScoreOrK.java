package LibExceptions;

import javax.swing.JOptionPane;

public class NotChooseScoreOrK extends Exception {

	/**
	 * 
	 */
	public NotChooseScoreOrK(String message) {
		super(message);
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
