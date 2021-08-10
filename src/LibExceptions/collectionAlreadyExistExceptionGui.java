package LibExceptions;

import javax.swing.JOptionPane;

public class collectionAlreadyExistExceptionGui extends Exception {
	
	  public collectionAlreadyExistExceptionGui(String message) {
	        super(message);
	        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE); 
	    }
}
