package LibExceptions;

import javax.swing.JOptionPane;

public class itemNotExistException extends Exception {
	
	public itemNotExistException(String message) {
		JOptionPane.showMessageDialog(null, this.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

}
