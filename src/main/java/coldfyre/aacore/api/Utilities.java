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

}
