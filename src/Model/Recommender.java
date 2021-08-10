package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;

import java.util.Set;
import Model.Library.sortByScore;
import Utils.Topic;

public class Recommender implements Serializable {
	private static final long serialVersionUID = 5000L;
	
	
	private static Recommender instance;
	
	private HashMap<Reader, HashMap<Topic,Integer>> topicMap; //for every reader you have counter of topics
	private HashMap<Reader, HashMap<Author,Integer>> authorMap; //for every reader you have counter of Authors
	
	private Recommender()
	{
		topicMap = new HashMap<Reader, HashMap<Topic,Integer>>();
		authorMap = new HashMap<Reader, HashMap<Author,Integer>>();
		
	}
	
	public static Recommender getInstance() {
		if (instance == null) 
			instance = new Recommender();
		return instance;
	}
	

	
    /**
     * This method adds a reader to the library IF the reader doesn't
     * already exist and the details are valid.
     * @param reader
     * @return true if the reader was added successfully, false otherwise
     */
	public boolean addReader(Reader reader) {
		if(reader == null)
			return false;
		
		//check if the reader available
		if(this.topicMap.containsKey(reader) ||  this.authorMap.containsKey(reader))
			return false;
		
		 this.topicMap.put(reader, new HashMap<Topic,Integer>());
		 this.authorMap.put(reader, new HashMap<Author,Integer>());
		 return true;
	}
	
	
    /**
     * This method remove a reader from the library IF the reader doesn't
     * already exist and the details are valid.
     * @param reader
     * @return true if the reader was removed successfully, false otherwise
     */
	
	public boolean removeReader(Reader reader) {
		if(reader == null) 
			return false;
		

		this.topicMap.remove(reader);
		this.authorMap.remove(reader);
		return true;
						 
	
	}
	
	/**
	 * 
	 * @param reader
	 * @param item
	 */
	public void readUpdate(Reader reader, LibraryItem item) {
		int TopicUpdate = 0;
		int AuthorUpdate = 0;
		
		if(this.topicMap.get(reader).containsKey((item).getTopic()))
				TopicUpdate = this.topicMap.get(reader).get(item.getTopic());
		
		if(this.authorMap.get(reader).containsKey((item).getAuthor()))
			AuthorUpdate = this.authorMap.get(reader).get(item.getAuthor());
		
		this.authorMap.get(reader).put(item.getAuthor(), AuthorUpdate +1 ); // update author map
		this.topicMap.get(reader).put(item.getTopic(), TopicUpdate +1 ); // update topic map
	}
	


	
	/**
	 * 
	 * @param reader
	 * @param k
	 * @param calcualtor
	 * @return k best books with the relevant topics, if  k > books of the relevant topics ,go to the second topic and ...
	 */
	public ArrayList<Book> recommendBooksByTopic(Reader reader, int k, ScoreCalculator calcualtor,Set<LibraryItem> items  ){
		ArrayList<Book> returnBooks = new ArrayList<Book>();
		ArrayList<Book> tempBooks = new ArrayList<Book>();
		
		
		ArrayList<Topic>sortedTopics = sortedByTopicValue(topicMap.get(reader));// sort map by topic counter
		for (Topic topic : sortedTopics) {
			
		    tempBooks =  getbooksByTopic(reader,topic,items);

		    returnBooks.addAll(getBestBooks (tempBooks,  k, calcualtor));
		    if(returnBooks.size() >= k)
		    	break;
		}
		
		if (returnBooks.size()>=k+1)
		{
			int i=returnBooks.size();
			while(i-->k)
				returnBooks.remove(i);
		}
		return returnBooks;	
	}
	

	
	/**
	 * 
	 * @param reader
	 * @param k
	 * @param calcualtor
	 * @param items
	 * @return
	 */
	public ArrayList<Paper> recommendPapersByTopic(Reader reader, int k, ScoreCalculator calcualtor,Set<LibraryItem> items  ){
		ArrayList<Paper> returnPapers = new ArrayList<Paper>();
		ArrayList<Paper> tempPapers = new ArrayList<Paper>();
		
		
		ArrayList<Topic>sortedTopics = sortedByTopicValue(topicMap.get(reader));// sort map by topic counter
		for (Topic topic : sortedTopics) {
			
			tempPapers =  getPapersByTopic(reader,topic,items);
		    returnPapers.addAll(getBestPapers (tempPapers,  k, calcualtor));
		    if(returnPapers.size() >= k)
		    	break;
		}
		 
		if (returnPapers.size()>=k+1)
		{
			int i=returnPapers.size();
			while(i-->k)
				returnPapers.remove(i);
		}
		return returnPapers;	
	}
		
	
	
	/**
	 * 
	 * @param reader
	 * @param k
	 * @param calcualtor
	 * @param items
	 * @return
	 */
	public ArrayList<Paper> recommendPapersByAuthor(Reader reader, int k, ScoreCalculator calcualtor,Set<LibraryItem> items  ){
		ArrayList<Paper> returnPapers = new ArrayList<Paper>();
		ArrayList<Paper> tempPapers = new ArrayList<Paper>();
		
		
		ArrayList<Author>sortedAuthors = sortedByAuthorValue(authorMap.get(reader));// sort map by topic counter
		for (Author author : sortedAuthors) {
			
			tempPapers =  getPapersByAuthor(reader,author,items);
		    returnPapers.addAll(getBestPapers (tempPapers,  k, calcualtor));
		    if(returnPapers.size() >= k)
		    	break;
		}
		
		if (returnPapers.size()>=k+1)
		{
			int i=returnPapers.size();
			while(i-->k)
				returnPapers.remove(i);
		}
		return returnPapers;	
		
	}
	
	/**
	 * 
	 * @param reader
	 * @param k
	 * @param calcualtor
	 * @return k best books with the relevant topics, if  k > books of the relevant author ,go to the second author and .. at the end
	 */
	public ArrayList<Book> recommendBooksByAuthor(Reader reader, int k, ScoreCalculator calcualtor,Set<LibraryItem> items  ){
		ArrayList<Book> returnBooks = new ArrayList<Book>();
		ArrayList<Book> tempBooks = new ArrayList<Book>();
		
		
		ArrayList<Author>sortedAuthors = sortedByAuthorValue(authorMap.get(reader));// sort map by topic counter
		for (Author author : sortedAuthors) {
			
		    tempBooks =  getbooksByAuthor(reader,author,items);

		    returnBooks.addAll(getBestBooks (tempBooks,  k, calcualtor));
		    if(returnBooks.size() >= k)
		    	break;
		}

		if (returnBooks.size()>=k+1)
		{
			int i=returnBooks.size();
			while(i-->k)
				returnBooks.remove(i);
		}
		return returnBooks;	

	}
	
	
	
	
	/******** private help function *************/
	
	
	/**
	 * 
	 * @param reader
	 * @param topic
	 * @param items
	 * @return
	 */
	private ArrayList<Book> getbooksByTopic (Reader reader, Topic topic, Set<LibraryItem> items ) {
		ArrayList<Book> returnBooks = new ArrayList<Book>();
		for(LibraryItem item : items) {
			if(item instanceof Book && !reader.getItems().contains(item)) {
				if(item.getTopic().equals(topic)) {
					returnBooks.add((Book)item);
				}
			}
		}
		return returnBooks;
	}
	
	
	/**
	 * 
	 * @param reader
	 * @param topic
	 * @param items
	 * @return
	 */
	private ArrayList<Paper> getPapersByTopic (Reader reader, Topic topic, Set<LibraryItem> items ) {
		ArrayList<Paper> returnPapers = new ArrayList<Paper>();
		for(LibraryItem item : items) {
			if(item instanceof Paper && !reader.getItems().contains(item)) {
				if(item.getTopic().equals(topic)) {
					returnPapers.add((Paper)item);
				}
			}
		}
		return returnPapers;
	}
	
	
	/**
	 * 
	 * @param reader
	 * @param topic
	 * @param items
	 * @return
	 */
	private ArrayList<Book> getbooksByAuthor (Reader reader, Author author, Set<LibraryItem> items ) {
		ArrayList<Book> returnBooks = new ArrayList<Book>();
		for(LibraryItem item : items) {
			if(item instanceof Book && !reader.getItems().contains(item)) {
				if(item.getAuthor().equals(author) ) {
					returnBooks.add((Book)item);
				}
			}
		}
		return returnBooks;
	}
	
	/**
	 * 
	 * @param reader
	 * @param author
	 * @param items
	 * @return
	 */
	private ArrayList<Paper> getPapersByAuthor (Reader reader, Author author, Set<LibraryItem> items ) {
		ArrayList<Paper> returnBooks = new ArrayList<Paper>();
		for(LibraryItem item : items) {
			if(item instanceof Paper && !reader.getItems().contains(item)) {
				if(item.getAuthor().equals(author) ) {
					returnBooks.add((Paper)item);
				}
			}
		}
		return returnBooks;
	}
	
	
	/**
	 * 
	 * @param books
	 * @param k
	 * @param calculator
	 * @return
	 */
	private ArrayList<Book> getBestBooks (ArrayList<Book>books, int k, ScoreCalculator calculator){
		ArrayList<Book> returnList = new ArrayList<Book>();
        // Sort the list 
		 Collections.sort(books, new sortByScore(calculator));
		 Collections.reverse(books);
		 if(k > books.size()) // if the k is biggest the all books
			 k = books.size();
        for(int i=0; i < k; i++) {
        	returnList.add(books.get(i)); 
        }
        
        return returnList;    
	}
	
	
	private ArrayList<Paper> getBestPapers(ArrayList<Paper>papers, int k, ScoreCalculator calculator){
		ArrayList<Paper> returnList = new ArrayList<Paper>();
        // Sort the list 
		 Collections.sort(papers, new sortByScore(calculator));
		 Collections.reverse(papers);
		 if(k > papers.size()) // if the k is biggest the all books
			 k = papers.size();
        for(int i=0; i < k; i++) {
        	returnList.add(papers.get(i)); 
        }
        
        return returnList;    
	}
	
	

	

	
	 private static ArrayList<Author> sortedByAuthorValue(HashMap<Author, Integer> hm){
		 
	        // Create a list from elements of HashMap 
		 LinkedList<Map.Entry<Author, Integer> > list = 
	               new LinkedList<Map.Entry<Author, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<Author, Integer> >() { 
	            public int compare(Map.Entry<Author, Integer> o1,  
	                               Map.Entry<Author, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	        
	     // put data from sorted list of  entry map to list of authors   
	        ArrayList<Author> temp = new ArrayList<Author>(); 
	        for (Map.Entry<Author, Integer> it : list) { 
	            temp.add(it.getKey()); 
	        }
	        Collections.reverse(temp);
	        return temp; 
	 }
	 
	 private static ArrayList<Topic> sortedByTopicValue(HashMap<Topic, Integer> hm){
		 
	        // Create a list from elements of HashMap 
		 LinkedList<Map.Entry<Topic, Integer> > list = 
	               new LinkedList<Map.Entry<Topic, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<Topic, Integer> >() { 
	            public int compare(Map.Entry<Topic, Integer> o1,  
	                               Map.Entry<Topic, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	        
	     // put data from sorted list of  entry map to list of authors   
	        ArrayList<Topic> temp = new ArrayList<Topic>(); 
	        for (Map.Entry<Topic, Integer> it : list) { 
	            temp.add(it.getKey()); 
	        }
	        Collections.reverse(temp);
	        return temp; 
	 }
	
	 public static Recommender deserialize()
		{
		 Recommender temp=null;

			try {
				
				File f = new File("library.ser");
				if(!f.exists()) return null;
				
				FileInputStream file = new FileInputStream("recommender.ser");
				ObjectInputStream in = new ObjectInputStream(file);

				temp = (Recommender) in.readObject();			
				in.close();
				file.close();
//				Library.recommender = Recommender.getInstance();
				
				
				System.out.println("The Recommender was deserialized successfully");
				return temp;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}	
		}
	 
		
		public static void serialize(Recommender recommender)
		{
			try {
				FileOutputStream file = new FileOutputStream("recommender.ser");
				ObjectOutputStream out = new ObjectOutputStream(file);
				out.writeObject(recommender);
				out.close();
				file.close();
				System.out.println("The Recommender list was serialized successfully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	
}
