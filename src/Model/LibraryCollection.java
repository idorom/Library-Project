package Model;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import Utils.Topic;

public abstract class LibraryCollection implements Serializable {
	private static final long serialVersionUID = 3000L;

    /*** the Item name.*/
    private String name;
    
    /***  the Item id.*/
    private int  id;
    
    /*** list of items*/
    protected Set<LibraryItem> items = new HashSet<LibraryItem>();

	/**
     * Full constructor.
     *
     * @param name the collection name.
     * @param id the collection number
     * */
	
	public LibraryCollection(String name, int id) {
		this.name = name;
		this.id=id;
	}
	
	
	// partial contractor
	public LibraryCollection(String name) {
		this.name = name;

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
		if(this.items.contains(item)) {
			return false;
		}
		
		 this.items.add(item);
		 return true;
	}
	
	
	
	public Set<LibraryItem> getItems(){
		return items;
	}
	
	/**
	 * 
	 * @return all the authors
	 */
	public Set<Author> getAuthors() {
		Set<Author> authors = new HashSet<Author>(); 
		//ArrayList<Author>authors = new ArrayList<Author>();
		for(LibraryItem item : this.items) {
			authors.add(item.getAuthor());
		}
		return authors;
	}
	
	/**
	 * 
	 * @return books topics
	 */
	public Set<Topic> getTopics() {
		Set<Topic>topics = new HashSet<Topic>();
		for(LibraryItem item : this.items) {
			topics.add(item.getTopic());
		}
		return topics;
	}
	
	//get
	public String getName() {
		return this.name;
	}
	
	//get
	public int getId() {
		return this.id;
	}
	
	 @Override
	    public String toString() {
	    	return " collection name: " + name ; 
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
		LibraryCollection other = (LibraryCollection) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	    


}
