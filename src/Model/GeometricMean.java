package Model;

import java.util.Set;

public class GeometricMean implements ScoreCalculator {

	@Override
	public float calculate(Set<Review> reviews) {


		// declare product variable and 
        // initialize it to 1. 
        float product = 1; 
  
        // Compute the product of all the 
        // elements in the array.
        int n = reviews.size();
		if(n== 0)
			return 0;
		for(Review review : reviews) 
			product = product * review.getRate();
		

  
        // compute geometric mean through  
        // formula pow(product, 1/n) and 
        // return the value to main function. 
        float gm = (float)Math.pow(product, (float)1 / n); 
        return gm; 
	}

}
