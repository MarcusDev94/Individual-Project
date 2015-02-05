import gate.Document;
import gate.Factory;
import java.net.URL;

public class ReviewDocs {

	String URL;
	int docID;
	Document revDoc;

	public ReviewDocs(String URL, int docID) {
		this.URL = URL;
		this.docID = docID;
	}

	protected void writeReviewName(String name) {
		// Use method from test class to writea method to extract the review
		// name
	}

	protected void createDoc() throws Exception {
		// System.out.println(URL);
		revDoc = Factory.newDocument(new URL(URL), "UTF-8");
	}

	protected void createDoc(String url, String encode) throws Exception {
		revDoc = Factory.newDocument(new URL(url), encode);
	}
}
