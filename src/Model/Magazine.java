package Model;

import java.io.Serializable;
import java.util.ArrayList;


public class Magazine extends LibraryCollection implements Serializable{

	private static final long serialVersionUID = 3002L;
	public Magazine(String name, int id) {
		super(name,id);

	}

	// partial contractor
	public Magazine(String name) {
		super(name);

	}

	/**
	 * 
	 * @return books
	 */
	public ArrayList<Paper> getPapers(){
		ArrayList<Paper> papers = new ArrayList<Paper>();
		for(LibraryItem item : items ) {
			if(item instanceof Paper) {
				papers.add((Paper)item);
			}
		}
		return papers;
	}

	@Override
	public String toString() {
		return getName();
	}
}





/**
 * 
 * @return best paper (with the biggest score)
 */
/*	public Paper getBestPaper() { 
		double score = 0;
		int currPaperId = 0;
		for(Paper paper : this.papers) {

			if(paper.getReviews().size() != 0) {
			double scoreItem=paper.GetScore();
			System.out.println(scoreItem);
			if(scoreItem > score) {
				score = scoreItem;
				currPaperId = paper.getId();
				}
			}
		}	
		return getPaperById(currPaperId);
	}

	// help function that return book by id.
	private Paper getPaperById(int paperId) {
		for(Paper paper : papers) {
			if(paper.getId() == paperId)
				return paper;
		}
		return null;
	}

}*/
