package annieTest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import gate.*;
import gate.creole.SerialAnalyserController;
import gate.creole.SerialController;
import gate.gui.MainFrame;

public abstract class Test1 {
	
	public static void main(String[] args) throws Exception {
		//Task 1 & 2
		Gate.init(); // Initiates Gate
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				MainFrame.getInstance().setVisible(true);
			}
		});
		
		
		Document doc = Factory.newDocument(new URL("https://gate.ac.uk/"),"UTF-8");
		doc.setName("This is Home");
		FeatureMap feats = Factory.newFeatureMap();
		feats.put("date", "04/11/2014");
		doc.setFeatures(feats);
		
		//Task 3
		/*
		System.out.println(doc.getAnnotationSetNames());
		Iterator iter = doc.getAnnotations("Original markups").getAllTypes().iterator();
		ArrayList ar = new ArrayList();
		/*while(iter.hasNext()) {
			ar.add(iter.next());
		}*/
		
		/*
		for(int i = 0; i < ar.size(); i++) {
			System.out.println(doc.getAnnotations("Original markups").get((String)ar.get(i)));
		}*/
		
		/*
		 * Feature map used to create a documents with the init param that
		 * it will be encoded UTF-8 and that it will have a resource feature
		 * called "date" and will contain todays date
		 */
		
		/*
		FeatureMap params = Factory.newFeatureMap();
		params.put(
		Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME,
		"");
		params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "UTF-8");
		FeatureMap feats = Factory.newFeatureMap();
		feats.put("date", "03/11/2014");
		Factory.createResource("gate.corpora.DocumentImpl",
				params, feats, "This is home");
		*/
		
		//Task 4
		/*
		Iterator iter  = doc.getAnnotations("Original markups").get("a").iterator();
		while(iter.hasNext()) {
			FeatureMap s = ((FeatureBearer) iter.next()).getFeatures();
			URL myURL = new URL("https://gate.ac.uk/");
			System.out.println(new URL(myURL,s.get("href").toString()));
		}
		*/
		
		//Task 5
		
		// get the root plugins dir
		File pluginsDir = Gate.getPluginsHome();
		// Let’s load the Tools plugin
		File aPluginDir = new File(pluginsDir, "ANNIE");
		// load the plugin.
		Gate.getCreoleRegister().registerDirectories(aPluginDir.toURI().toURL());
		
		LanguageAnalyser tok = (LanguageAnalyser)Factory.createResource(
				   "gate.creole.tokeniser.DefaultTokeniser");
		/*tok.setDocument(doc);
		tok.setCorpus(null);
		tok.execute();*/
		
		
		SerialAnalyserController controller =
				(SerialAnalyserController) Factory.createResource(
				"gate.creole.SerialAnalyserController");
		controller.add(0,tok);
		Corpus corpus = Factory.newCorpus("Young Corpus");
		corpus.add(doc);
		((SerialAnalyserController) controller).setCorpus(corpus);
		controller.execute();
		
		
		
	}
}
