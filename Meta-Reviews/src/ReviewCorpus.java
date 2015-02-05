import gate.Corpus;
import gate.Factory;

import java.util.ArrayList;
import java.util.Iterator;

public class ReviewCorpus {

	Corpus corpus;

	// Class ReviewCorpus takes 2 arguments; A corpus name and an array of docs
	// The array of docs are iterated through and added to said corpus
	public ReviewCorpus(String corpName, ArrayList<ReviewDocs> docs)
			throws Exception {
		corpus = Factory.newCorpus(corpName);
		Iterator<ReviewDocs> iter = docs.iterator();
		while (iter.hasNext()) {
			ReviewDocs rd = (ReviewDocs) iter.next();
			corpus.add(rd.revDoc);
		}
	}

	// Create a corpus without passing an array
	public ReviewCorpus(String corpName) throws Exception {
		corpus = Factory.newCorpus(corpName);
	}
}
