import java.util.ArrayList;
import java.util.Iterator;

import gate.AnnotationSet;
import gate.Document;

public class XLMdata {

	ArrayList<ReviewDocs> docCollection;
	ArrayList<Document> docs;
	int arrayLen;

	public XLMdata(ArrayList<ReviewDocs> docs, int length) {
		this.docCollection = docs;
		this.arrayLen = length;
	}

	public void extract() {
	}

	
	public void init() throws Exception {
		Iterator<ReviewDocs> iter = docCollection.iterator();
		while (iter.hasNext()) {
			ReviewDocs document = (ReviewDocs) iter.next();
			AnnotationSet as = document.revDoc.getAnnotations("reviews");
			System.out.println(document.URL);
			System.out.println(document.revDoc.getContent().getContent(
					as.get("RevScore").firstNode().getOffset(),
					as.get("RevScore").lastNode().getOffset()));
			System.out.println();
		}
	}
}
