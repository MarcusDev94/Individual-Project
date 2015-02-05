import java.io.File;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.util.Out;

public class AnnieClass {

	ReviewCorpus revCorpus;

	protected AnnieClass(ReviewCorpus corpus) {
		this.revCorpus = corpus;
	}

	public AnnieClass() {
	}

	// Create params for the resources, Will be used later
	public void createParams(FeatureMap p, String obName, String path) {
		Object ob = (Object) obName;
		p.put(ob, path);
	}

	//Loads the ANNIE application with defaults
	protected void loadAnnie() throws Exception {
		File pluginsDir = Gate.getPluginsHome();
		File aPluginDir = new File(pluginsDir, "ANNIE");
		Gate.getCreoleRegister()
				.registerDirectories(aPluginDir.toURI().toURL());
	}

	//Initiates the loading of ANNIE and the processing resources
	public void init() throws Exception {
		Out.prln("Initialising ANNIE...");
		this.loadAnnie();

		LanguageAnalyser one = (LanguageAnalyser) Factory
				.createResource("gate.creole.annotdelete.AnnotationDeletePR");
		one.setParameterValue("keepOriginalMarkupsAS", false);

		FeatureMap params = Factory.newFeatureMap();
		this.createParams(
				params,
				"grammarURL",
				"file:/C:/Users/Marcus/Documents/GATE_Developer_8.0/plugins/ANNIE/resources/NE/imdb.jape");

		LanguageAnalyser two = (LanguageAnalyser) Factory
				.createResource("gate.creole.tokeniser.DefaultTokeniser");
		two.setParameterValue("annotationSetName", "Tokens");

		LanguageAnalyser three = (LanguageAnalyser) Factory.createResource(
				"gate.creole.ANNIETransducer", params);
		three.setParameterValue("inputASName", "Tokens");
		three.setParameterValue("outputASName", "reviews");

		SerialAnalyserController controller = (SerialAnalyserController) Factory
				.createResource("gate.creole.SerialAnalyserController");

		controller.add(one);
		controller.add(two);
		controller.add(three);

		controller.setCorpus(revCorpus.corpus);
		controller.execute();
		Out.prln("...ANNIE loaded");
	}

}
