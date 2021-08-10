package Model;

import java.io.Serializable;

public class Librarian extends LibraryUser implements Serializable {
	
	private static final long serialVersionUID = 1002L;
	private String id;
	
	private String Password;

	public Librarian(String firstName, String lastName, String id, String password) {
		super(firstName, lastName);
		this.id = id;
		this.Password = password;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	
}
