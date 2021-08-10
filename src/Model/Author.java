package Model;

import java.io.Serializable;

import Utils.Topic;

public class Author extends LibraryUser  implements Serializable{
	
	private static final long serialVersionUID = 1001L;
	private Topic topic;
	

	public Author(String firstName, String lastName, Topic topic) { // this implementation because it without polymorphism.
		super(firstName, lastName);
		this.topic = topic;
		
	}
	
	// partial contractor
	public 	Author(String firstName, String lastName) {
		// TODO Auto-generated constructor stub
		super(firstName,lastName);
	}
	
	
	//get
	public Topic gettopic() {
		return this.topic;
	}
	
	 @Override
	    public String toString() {
	    	return getFirstName() + " " + getLastName() ; 
	    }
	    
	
	
	
	

}
