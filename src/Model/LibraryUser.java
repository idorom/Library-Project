package Model;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class LibraryUser  implements Serializable {
	private static final long serialVersionUID = 1000L;
	private String firstName;
	private String lastName;
	
	/*** list of items.*/
	private ArrayList<LibraryItem > itemList; 

	

	/**
	 * Contractor
	 * @param firstName
	 * @param lastName
	 * @param id
	 */
	LibraryUser(String firstName, String lastName) { 
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.itemList = new ArrayList<LibraryItem>();
	}
	

	
	  /**
     * This method adds item to the user list if the item doesn't
     * already exist and the details are valid.
     * @param book
     * @return true if the item was added successfully, false otherwise
     */	
	public boolean addItem(LibraryItem item) {
		if(item == null)
			return false;
		
		//check if the object available
		if(this.itemList.contains(item)) {
			return false;
		}
		
		 this.itemList.add(item);
		 return true;
	}
	

	public boolean removeItem(LibraryItem item) {
		if(item == null)
			return false;
		
		//check if the object available
		if(!this.itemList.contains(item)) {
			return false;
		}
		
		 this.itemList.remove(item);
		 return true;
	}
	


	
	/*** Geters ******/
	
	
	public ArrayList<LibraryItem> getItems(){
		return this.itemList;
	}
	

	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
//	public String getFullName() {
//		return this.firstName+ " " +this.lastName;
//	}
/******************************/
	
	@Override
	public String toString() {
		return firstName +" "+ lastName;
	}
	
	public ArrayList<Paper> getPapers(){
		ArrayList<Paper> papers = new ArrayList<Paper>();
		for( LibraryItem item : itemList) {
			if(item instanceof Paper)
				papers.add((Paper)item);
		}
		return papers;
	}
	
	public ArrayList<Book> getBooks(){
		ArrayList<Book> books = new ArrayList<Book>();
		for( LibraryItem item : itemList) {
			if(item instanceof Book)
				books.add((Book)item);
		}
		return books;
	}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	LibraryUser other = (LibraryUser) obj;
	if (firstName == null) {
		if (other.firstName != null)
			return false;
	} else if (!firstName.equals(other.firstName))
		return false;
	if (lastName == null) {
		if (other.lastName != null)
			return false;
	} else if (!lastName.equals(other.lastName))
		return false;
	return true;
}
	

}
