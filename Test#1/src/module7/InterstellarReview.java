package module7;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.creole.gazetteer.Gazetteer;
import gate.creole.gazetteer.GazetteerNode;
import gate.gui.MainFrame;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;

import javax.swing.SwingUtilities;

public class InterstellarReview {

	public void initAnnie(Corpus c) throws GateException, IOException {
		Out.prln("Initialising ANNIE...");

		// load the ANNIE application from the saved state in plugins/ANNIE
		File pluginsDir = Gate.getPluginsHome();
		File aPluginDir = new File(pluginsDir, "ANNIE");
		Gate.getCreoleRegister().registerDirectories(aPluginDir.toURI().toURL());
		
		FeatureMap params = Factory.newFeatureMap();
		Object s = (Object) "tokeniserRulesURL";
		params.put( s, "file:/C:/Program%20Files/GATE_Developer_8.0/plugins/ANNIE/resources/tokeniser/NewTokeniser.rules");
		
		//Need to come back and finish!!
		LanguageAnalyser one = (LanguageAnalyser) 
				Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR");
		//one.cleanup();
		
		LanguageAnalyser two = (LanguageAnalyser) 
				Factory.createResource("gate.creole.tokeniser.DefaultTokeniser", params);
		//two.cleanup();
		//two.setParameterValue( "tokeniserRulesURL" ,  "file:/C:/Program%20Files/GATE_Developer_8.0/plugins/ANNIE/resources/tokeniser/NewTokeniser.def");

		LanguageAnalyser three = (LanguageAnalyser) 
				Factory.createResource("gate.creole.gazetteer.DefaultGazetteer");
		//three.cleanup();
		three.setParameterValue( "listsURL" ,  "file:/C:/Program%20Files/GATE_Developer_8.0/plugins/ANNIE/resources/gazetteer/newLists.def");
		
		SerialAnalyserController controller = (SerialAnalyserController) 
				Factory.createResource("gate.creole.SerialAnalyserController");
		
		controller.add(one);
		controller.add(two);
		controller.add(three);
		
		controller.setCorpus(c);
		controller.execute();
		Out.prln("...ANNIE loaded");
	} // initAnnie()

	public static void main(String[] args) throws Exception {
		/*
		String fileName = "C:\\Users\\Marcus\\Documents\\University Of Southampton - Computer Science Bsc (3 Yr)\\Year 3 - Individual Project\\gazetteer\\movieTitle.lst";
		WriteFile newFile = new WriteFile(fileName);
		newFile.writeToFile("Interstellar");
		newFile.writeToFile("interstellar");
		*/
		
		InterstellarReview ir = new InterstellarReview();
		
		Gate.init(); // Initiates Gate
		/*SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				MainFrame.getInstance().setVisible(true);
			}
		});*/

		Document doc1 = Factory.newDocument(new URL(
				"http://www.imdb.com/title/tt0816692/"), "UTF-8");
		Document doc2 = Factory.newDocument(new URL(
				"http://www.rottentomatoes.com/m/interstellar_2014/"), "UTF-8");
		/*
		 * Document doc3 = Factory.newDocument(new URL(
		 * "https://www.metacritic.com/movie/interstellar"), "UTF-8");
		 */
		Document doc4 = Factory
				.newDocument(
						new URL(
								"http://www.theguardian.com/film/2014/nov/09/interstellar-review-sci-fi-spectacle-delivers"),
						"UTF-8");
		Document doc5 = Factory
				.newDocument(
						new URL(
								"http://www.telegraph.co.uk/culture/film/filmreviews/11207268/Interstellar-review-a-feast-of-extraordinary-ideas.html"),
						"UTF-8");

		doc1.setName("IMDB Review");
		doc2.setName("RottenTomatoes Review");
		// doc3.setName("Metacritic Review");
		doc4.setName("The Guardian Review");
		doc5.setName("The Telegraph Review");

		Corpus corpus1 = Factory.newCorpus("Interstellar Reviews Corpus");
		corpus1.add(doc1);
		corpus1.add(doc2);
		// corpus1.add(doc3);
		corpus1.add(doc4);
		corpus1.add(doc5);

		ir.initAnnie(corpus1);
	}

}
