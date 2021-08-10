package LibExceptions;

import javax.swing.JOptionPane;

public class addLibrarianExceptionGui extends Exception {

private String ErrMsg;
	
	public addLibrarianExceptionGui(String ErrMsg) {
		this.ErrMsg="Missing or invalid data in the following field(s): " +ErrMsg ;
		
		JOptionPane.showMessageDialog(null,this.ErrMsg ,"Error", JOptionPane.ERROR_MESSAGE);
	}
}
