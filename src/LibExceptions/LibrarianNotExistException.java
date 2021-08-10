package LibExceptions;

import javax.swing.JOptionPane;

public class LibrarianNotExistException extends Exception {

	public LibrarianNotExistException(String message) {
		super(message);
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}