package Model;
import Utils.Topic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;




public abstract class LibraryItem implements Serializable{
	
	private static final long serialVersionUID = 2000L;
	
    /*** the Item name.*/
    private String name;
    
    /***  the Item id.*/
    private int  id;
    
    /*** The topic of the Item.*/
    private Topic topic;
    
    
    /*** The Item's author.*/
    private Author author;
    
    /*** The Item's reviews.*/    
    private  Set<Review> reviews;
    
    /*** The Item's reader.*/    
    private  ArrayList<Reader> readers;
   
    
    
    /**
     * Full constructor.
     *
     * @param name the Item name.
     * @param id the Item id.
     * @param ReleaseDate The release date of the Book. 
     * @param author The Item's author.
     * @param reviews The Item's reviews.
     */
    public LibraryItem(String name, Topic topic, Author author) {
        this.name = name;
        this.topic=topic;
        this.author = author;
        this.reviews =  new HashSet<Review>();
        this.readers = new ArrayList<Reader>();
    }
    
    

    
    // partial contractor
    public LibraryItem(String name) {
    	this.name = name;
    }
    
	/**
	 * Add new Item review
	 * @param review
	 * @return
	 */
	public boolean addRewiew(Review review) {
		if(review == null)
			return false;
		if(reviews.contains(review))
			return false;
		
		this.reviews.add(review);
		return true;
	}
	
	/**
	 * Calculate and get the Item score
	 * @
	 * @return score [1-10] average reviews rates
	 */	
	public double GetScore(ScoreCalculator calculator) {
		return calculator.calculate(this.reviews);
		
	}
	
	
	/**
	 *  add reader
	 */
    public boolean  addReader(Reader reader) {
    	
		if(reader == null)
			return false;
		
		//check if the reader available
		if(this.readers.contains(reader)) {
			return false;
		}
		this.readers.add(reader);	
		//System.out.println("---" + name + "--" +readers);
		return true;
    	
    }
    
	/**
	 *  return readers Book
	 */
    public ArrayList<Reader> getReaders(){
    	return this.readers;
    }
	
	
	/**
	 * return the score review
	 */
    public String getName() {
    	return this.name;
    }
    

    
    
	/**
	 * return the reviewer Id
	 */
    public Topic getTopic() {
    	return this.topic;
    }
    /**
	 * return the score review
	 */
    public Author getAuthor() {
    	return this.author;
    }
    
	/**
	 * return the review Paragraph
	 */
    public Set<Review>  getReviews() {
    	return this.reviews;
    }
    
    
    @Override
    public String toString() {
    	return name; 
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		LibraryItem other = (LibraryItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    

}
