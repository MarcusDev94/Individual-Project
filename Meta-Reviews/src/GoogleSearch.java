import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import textfiles.WriteFile;

public class GoogleSearch {

	// String URLname;
	ArrayList<String> unrawList = new ArrayList<String>();

	String query;
	static String URLname;
	WriteFile googleURLs = new WriteFile("C:\\Users\\Marcus\\Documents\\GitHub\\Individual-Project\\MetaReviews\\src\\googleURLs.txt", true);
	
	public GoogleSearch(String searchTerm) {
		this.query = this.termConv(searchTerm) + "+Review";
		URLname = "http://www.google.com/search?q=\"" + this.query + "\"";
	}

	public String termConv(String s) {
		String newS = s.replaceAll("\\s", "+");
		return newS;
	}

	public void listConversion(ArrayList<String> list) {
		unrawList.clear();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String l = (String) itr.next();
			int i = l.indexOf("&amp");
			if (i == -1) {
			} else {
				String newL = l.substring(0, i);
				//System.out.println(i);
				unrawList.add(newL);
			}
		}
		Iterator itr2 = unrawList.iterator();
		while (itr2.hasNext()) {
			System.out.println(itr2.next());
		}
	}

	public void reviewList(String page) throws IOException {
		ArrayList<String> rawList = new ArrayList<String>();
		String URLname = this.URLname;
		if (!page.isEmpty()) {
			URLname = this.URLname + page;
		}
		URL url = new URL(URLname);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
		conn.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		Pattern pattern = Pattern
				.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
			googleURLs.writeToFile(line);
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				//System.out.println(this.query);
				rawList.add(m.group());
				//System.out.println(m.group());
			}
		}
		in.close();
		listConversion(rawList);
	}

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out
				.printf("Please enter the movie for which you wish to attain review scores:\n ");
		String sTerm = scan.nextLine();
		GoogleSearch gt = new GoogleSearch(sTerm);
		System.out.println(gt.query);
		System.out.println("******** Fetching Review Websites ********");
		gt.reviewList("");
		gt.reviewList("&start=10");
		//gt.reviewList("&start=20");
		//gt.reviewList("&start=30");
		//gt.reviewList("&start=40");
		System.out.println("******** Fetch Complete ********");
	}
}
