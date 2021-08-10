package LibExceptions;

import javax.swing.JOptionPane;

public class addUserLibraryExceptionGui extends Exception {

	private String ErrMsg;
	
	public addUserLibraryExceptionGui(String ErrMsg) {
		this.ErrMsg=ErrMsg ;
		
		JOptionPane.showMessageDialog(null,this.ErrMsg ,"Error", JOptionPane.ERROR_MESSAGE);
	}
}
