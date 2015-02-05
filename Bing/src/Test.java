import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
	
	public static String termConv(String s) {
		String newS = s.replaceAll("\\s", "+");
		return newS;
	}
	
    public static void main(String[] args) throws IOException {
    	System.out.println();
    	Scanner scan = new Scanner(System.in);
    	String searchTerm = scan.nextLine();
    	String query = termConv(searchTerm) + "+Review";
		String URLname = "http://www.google.com/search?q=\"" + query + "\"";
    	URL url = new URL(URLname);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		Pattern pattern = Pattern
				.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		String line;
		while ((line = in.readLine()) != null) {
			Matcher m = pattern.matcher(line);
			System.out.println(line);
			/*if (m.find()) {
				System.out.println(line);
				System.out.println(query);
				//rawList.add(m.group());
				System.out.println(m.group());
			}*/
		}
   }
}
