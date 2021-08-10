package Model;


import java.util.Set;

public class ArithmeticMean implements ScoreCalculator {

	@Override
	public float calculate(Set<Review> reviews) {
		float count=0;
		if(reviews.size() == 0)
			return 0;
		for(Review review : reviews) {
			count+=review.getRate();
		}
		return count/(reviews.size());
	}

}
