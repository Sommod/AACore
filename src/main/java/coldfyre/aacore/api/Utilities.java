package coldfyre.aacore.api;

public class Utilities {
	
	public static String wordWarp(String input, int words) {
		String[] split = input.trim().split(" ");
		String result = split[0];
		
		for(int i = 1; i < split.length; i++) {
			if((i + 1) % 13 == 0)
				result = result.concat("\n");
			
			if(!result.endsWith("\n"))
				result = result.concat(" ".concat(split[i]));
			else
				result = result.concat(split[i]);
		}
		
		return result;
	}
	
	public static String[] wordWarpAsArray(String input, int words) {
		return wordWarp(input, words).split("\n");
	}
	
	public static String[] addToArray(String[] input, String value) {
		return addToArray(input.length, input, value);
	}
	
	public static String[] addToArray(int element, String[] input, String value) {
		if(element < 0 || element >= (input.length + 1))
			throw new ArrayIndexOutOfBoundsException("Error, tried to edit String array by inserting value into element beyond array size.");
		
		String[] result = new String[input.length + 1];
		
		for(int i = 0, j = 0; i < result.length; i++) {
			if(i == element) {
				result[i] = value;
				j++;
				continue;
			}
			
			result[i] = input[i - j];
		}
		
		return result;
	}
	
	public static String[] removeFromArray(String[] input, int element) {
		if(element < 0 || element >= input.length)
			throw new ArrayIndexOutOfBoundsException("Error, tried to edit String array by removing value from element beyond array size.");
		
		String[] result = new String[input.length - 1];
		
		for(int i = 0, j = 0; i < result.length; i++) {
			if(i == element)
				j++;
			
			result[i] = input[i + j];
		}
		
		return result;
	}

}
