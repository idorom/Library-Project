package Model;


import java.util.Set;

public class HarmonicMean implements ScoreCalculator{

	@Override
	public float calculate(Set<Review> reviews) {
		float count=0;
		float H;
		if(reviews.isEmpty())		
			return (float)0.0;
		
		for(Review r: reviews)
			count+=Math.pow(r.getRate(),-1);

		H= (float)Math.pow((count/reviews.size()),-1);
		
		return H;

	}

}
