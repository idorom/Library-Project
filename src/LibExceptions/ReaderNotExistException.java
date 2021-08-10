package LibExceptions;

import javax.swing.JOptionPane;

public class ReaderNotExistException extends Exception {
	
    public ReaderNotExistException(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
