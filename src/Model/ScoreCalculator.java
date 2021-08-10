package Model;

import java.util.Set;

/**
 * Interface that defines the weight calculator for the image processor.
 *
 */
public interface ScoreCalculator {
	
	/** Calculates the weight between the given two pixels. */
	float calculate(Set<Review> reviews);

}
