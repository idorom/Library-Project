package gui;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Model.Reader;

public class PhotoButton extends JButton
{
	private ImageIcon imageIcon;
	private Reader reader;
	
	public PhotoButton()
	{
		super("Choose Photo");
		setToolTipText("Click to change the photo");		
	}
	
	private ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	
	
	public Reader getReader()
	{
		return reader;
	}
	
	public void  setReader(Reader reader)
	{
		this.reader =reader;
	}
	
	public int SelectPicture(Reader reader) {			
		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new File(System.getProperty("user.home")));
		//filter the files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		file.addChoosableFileFilter(filter);
		file.setAcceptAllFileFilterUsed(false);
		int result = file.showOpenDialog(null);
		//if the user click on open in Jfilechooser
		if(result == JFileChooser.APPROVE_OPTION){
			File selectedFile = file.getSelectedFile();
			String path = selectedFile.getAbsolutePath();

			imageIcon=ResizeImage(path);
			setIcon(imageIcon);
			if (reader!=null) {
				reader.setUserIcon(imageIcon);
				JOptionPane.showConfirmDialog(null, "The photo was change successfully" 
						, "Reader Photo"
						,JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		}		
		return result;
	}

}
