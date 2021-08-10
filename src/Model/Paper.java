package Model;

import java.io.Serializable;

import Utils.PaperValue;
import Utils.Topic;

public class Paper extends LibraryItem implements Serializable{
	
	private static final long serialVersionUID = 2002L;
	private PaperValue papperValue;
	private String university;
	
	/**
     * Full constructor.
     *
     * @param name the Item name.
     * @param id the Item id.
     * @param ReleaseDate The release date of the Book. 
     * @param author The Item's author.
     * @param reviews The Item's reviews.
     * @param PaperValue
     * @param university
     */
    public Paper(String name, Topic topic, Author author, PaperValue papperValue,String university) {
    	super(name,topic,author);
    	this.papperValue=papperValue;
    	this.university=university;
    }
    
 // partial contractor
    public Paper(String name) {
    	super(name);
    }
    

    
    // geters and seters 
    
    public PaperValue getPaperValue() {
    	return this.papperValue;
    }
    
    public String getUniversity() {
    	return this.university;
    	
    }
    
    @Override
    public String toString() {
    	return getName(); 
    }

}
