package Model;

import java.io.Serializable;
import java.util.Date;
import javax.swing.ImageIcon;
import Utils.Gender;

public class Reader extends LibraryUser implements Serializable {
	private static final long serialVersionUID = 1003L;

	private Gender gender;


	private Date joiningDate;

	private String id;

	private String Password;

	private ImageIcon userIcon;
	
	public Reader(String firstName, String lastName) { // this implementation because it without polymorphism.
		super(firstName, lastName);

	}

	public Reader(String firstName, String lastName, String Password, String id) { // this implementation because it without polymorphism.
		super(firstName, lastName);

	}
	public Reader(String firstName, String lastName, String Password, String id,Gender gender) { // this implementation because it without polymorphism.
		super(firstName, lastName);
		this.id = id;
		this.gender = gender;
		this.joiningDate = new Date();
		this.Password=	Password;

	}


	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return joiningDate;
	}

	/**
	 * @param joiningDate the joiningDate to set
	 */
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() ; 
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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return the userIcon
	 */
	public ImageIcon getUserIcon() {
		return userIcon;
	}
	
	

	/**
	 * @param userIcon the userIcon to set
	 */
	public void setUserIcon(ImageIcon userIcon) {
		this.userIcon = userIcon;
	}
	



}