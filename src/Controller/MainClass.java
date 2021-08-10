package Controller;

import gui.LibraryGui;

public class MainClass {

	public static void main(String[] args) {

		try {
			LibraryGui frame = new LibraryGui();

			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}