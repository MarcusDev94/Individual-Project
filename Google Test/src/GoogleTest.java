import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleTest {

	// String URLname;
	ArrayList<String> unrawList = new ArrayList<String>();

	/*
	 * public GoogleTest(String searchTerm) { String query = searchTerm +
	 * "+Review"; URLname = "http://www.google.com/search?q=\""+query+"\""; }
	 */

	public void listConversion(ArrayList<String> list) {
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String l = (String) itr.next();
			int i = l.indexOf("&amp");
			if (i == -1) {
			} else {
				String newL = l.substring(0, i);
				System.out.println(i);
				unrawList.add(newL);
			}
		}
		Iterator itr2 = unrawList.iterator();
		while (itr2.hasNext()) {
			System.out.println(itr2.next());
		}
	}

	public void reviewList() throws IOException {
		ArrayList<String> rawList = new ArrayList<String>();
		String query = "Interstellar+Review";
		String URLname = "http://www.duckduckgo.com/?q=\"" + query + "\"";
		URL url = new URL(URLname);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		// "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern pattern = Pattern
				.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		String line;
		while ((line = in.readLine()) != null) {
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				rawList.add(m.group());
				//System.out.println(m.group());
			}
		}
		listConversion(rawList);
		in.close();
	}

	public static void main(String[] args) throws IOException {
		GoogleTest gt = new GoogleTest();
		gt.reviewList();
	}
}
