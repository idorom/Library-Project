package LibExceptions;

import javax.swing.JOptionPane;

public class IleagalNumberExceptionGui extends Exception {

	/**
	 * 
	 */
	public IleagalNumberExceptionGui(String KNumber) {
		super();
		JOptionPane.showMessageDialog(null, KNumber, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
