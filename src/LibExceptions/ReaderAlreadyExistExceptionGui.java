package LibExceptions;

import javax.swing.JOptionPane;


public class ReaderAlreadyExistExceptionGui extends Exception {
	
    public ReaderAlreadyExistExceptionGui(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
