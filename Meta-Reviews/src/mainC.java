import gate.Document;
import gate.Gate;
import gate.gui.MainFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

public class mainC {
	static int count;
	public static ArrayList<ReviewDocs> reviewDocs = new ArrayList<ReviewDocs>();
	public static ArrayList<String> revURL = new ArrayList<String>();
	static String[] textData;
	static int numberOfURLS = 5;
	protected ReviewDocs rd;
	protected static ReviewCorpus rc;
	protected static AnnieClass ac;
	protected static XLMdata xmlStuff;

	public mainC() {
	}

	//Reads a file and saves each line as a new URL to an array
	protected static void fileRead(String path) throws Exception {
		File file = new File(path);
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		// readFile(br);
		textData = new String[numberOfURLS];
		int i;

		for (i = 0; i < numberOfURLS; i++) {
			textData[i] = br.readLine();
		}
		br.close();
	}

	//reads URL's from one array and saves them into another array as ReviewDocs
	protected static void arrayToArray(String[] textData2) throws Exception {
		int id = 0;
		System.out.println(textData2[0]);
		for (int i = 0; i < textData2.length; i++) {
			ReviewDocs revD = new ReviewDocs(textData2[i], id);
			revD.createDoc();
			reviewDocs.add(revD);
			id++;
		}
	}

	//Adds all documents in an array to a corpus
	protected void docsToCorpus(ReviewCorpus first) {
		Iterator<ReviewDocs> iter = reviewDocs.iterator();
		while (iter.hasNext()) {
			ReviewDocs rd = (ReviewDocs) iter.next();
			Document d = rd.revDoc;
			rc.corpus.add(d);
		}
	}

	public static void main(String[] args) throws Exception {
		Gate.init();

		/*
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				MainFrame.getInstance().setVisible(true);
			}
		});
		*/

		mainC mc = new mainC();
		String path = "C:\\Users\\Marcus\\Documents\\University Of Southampton - Computer Science Bsc (3 Yr)\\Year 3 - Individual Project\\websitesimdb.txt";
		fileRead(path);
		arrayToArray(textData);
		// rc = new ReviewCorpus("Rotten Tomato Corpus", reviewDocs);
		rc = new ReviewCorpus("Rotten Tomato Corpus");
		mc.docsToCorpus(rc);
		ac = new AnnieClass(rc);
		ac.init();

		xmlStuff = new XLMdata(reviewDocs, numberOfURLS);
		xmlStuff.init();
	}
}
