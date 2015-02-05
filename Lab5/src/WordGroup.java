
public class WordGroup {

	String words;
	
	public WordGroup(String string) {
		words = string.toLowerCase();
	}
	
	public String[] getWordArray() {
		return words.split(" ");
		
	}
}
