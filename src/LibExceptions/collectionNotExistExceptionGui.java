package LibExceptions;

import javax.swing.JOptionPane;

public class collectionNotExistExceptionGui extends Exception {
	/**
	 * 
	 */
	public collectionNotExistExceptionGui(String KNumber) {
		super();
		JOptionPane.showMessageDialog(null, KNumber, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
