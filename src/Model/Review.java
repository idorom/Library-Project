package Model;

import java.io.Serializable;

public class Review implements Serializable {
	private static final long serialVersionUID = 6000L;
	/** the  review Paragraph.**/
    private String readerFirstName;
    
	/** the  review Paragraph.**/
    private String readerLastName;
 
	/** the  review Paragraph.**/
    private String bookName;
    
	/** the  review Paragraph.**/
    private String reviewSentence;
    
    /*** the Item review score.*/
    private int  rate;

    
    /**
     * Full constructor.
     *
     * @param reviewParagraph review Paragraph.
     * @param score the Item review score.
     * @param reviewerId the reviewer Id. 
     */
    public Review(String readerFirstName, String readerLastName, String bookName, String reviewSentence, int rate) {
    	this.readerFirstName=readerFirstName;
    	this.readerLastName=readerLastName;
    	this.bookName=bookName;
    	this.reviewSentence = reviewSentence;
    	this.rate = rate;
    }
    
	/**
	 * return the score review
	 */
    public int getRate() {
    	return this.rate;
    }
    
	/**
	 * return the review Paragraph
	 */
    public String getSentence() {
    	return this.reviewSentence;
    }
    
	/**
	 * return the reviewer Id
	 */
    public String getReaderFirstName() {
    	return this.readerFirstName;
    }
	/**
	 * return the reviewer Id
	 */
    public String getReaderLaststName() {
    	return this.readerLastName;
    }
    
	/**
	 * return the reviewer Id
	 */
    public String BookName() {
    	return this.bookName;
    }

	
	@Override
	public String toString() {
		return "review - item name: " + bookName + ", reader name: "+ readerFirstName +" " + readerLastName + ", rate: " + rate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((readerFirstName == null) ? 0 : readerFirstName.hashCode());
		result = prime * result + ((readerLastName == null) ? 0 : readerLastName.hashCode());
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
		Review other = (Review) obj;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (readerFirstName == null) {
			if (other.readerFirstName != null)
				return false;
		} else if (!readerFirstName.equals(other.readerFirstName))
			return false;
		if (readerLastName == null) {
			if (other.readerLastName != null)
				return false;
		} else if (!readerLastName.equals(other.readerLastName))
			return false;
		return true;
	}
    

}
