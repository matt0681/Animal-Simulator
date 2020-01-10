package utilities;

import java.util.Random;

/**
 * Provides methods for generating numbers.
 * @author Matthew
 */
public class RandomNumber {
    
    // Inclusively generates some numbers.
    public static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
                }
		Random r = new Random();
                int out = r.nextInt((max - min) + 1) + min;
		return out;
    }
}
