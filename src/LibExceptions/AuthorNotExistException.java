package LibExceptions;

import javax.swing.JOptionPane;

public class AuthorNotExistException extends Exception {
	
    public AuthorNotExistException(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE); 
    }

}
