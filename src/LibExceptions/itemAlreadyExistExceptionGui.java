package LibExceptions;

import javax.swing.JOptionPane;


public class itemAlreadyExistExceptionGui extends Exception {
	
	public itemAlreadyExistExceptionGui(String message)  {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
