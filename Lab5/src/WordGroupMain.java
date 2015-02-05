import java.lang.reflect.Array;


public class WordGroupMain {
	
	
	public static void main(String[] args) {
		WordGroup string1 = new WordGroup( "You can discover more about a person in an hour of play than in a year of conversation" );
		WordGroup string2 = new WordGroup( "When you play play hard when you work dont play at all" );
		
		String[] array1 = string1.getWordArray();
		String[] array2 = string2.getWordArray();
		
		for(int i = 0; i<array1.length ; i++) {
			System.out.println(array1[i]);
		}
		
		System.out.println();
		
		for(int i = 0; i<array2.length ; i++) {
			System.out.println(array2[i]);
		}
	}
}
