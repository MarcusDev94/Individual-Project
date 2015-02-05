package module7;

import gate.Resource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;

@SuppressWarnings("serial")
public class DocStats extends AbstractLanguageAnalyser {

	@Override
	public void execute() throws ExecutionException {
		int tokens = document.getAnnotations().get("Token").size();
		document.getFeatures().put("token_count", tokens);
	}

	@Override
	public Resource init() throws ResourceInstantiationException {
		System.out.println(getClass().getName() + " is initialising.");
		return this;
	}
}