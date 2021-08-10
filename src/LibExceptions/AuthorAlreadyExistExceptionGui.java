package LibExceptions;

import javax.swing.JOptionPane;

public class AuthorAlreadyExistExceptionGui extends Exception {
	
    public AuthorAlreadyExistExceptionGui(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE); 
    }

}
