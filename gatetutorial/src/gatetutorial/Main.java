package gatetutorial;
import javax.swing.SwingUtilities;

import gate.*;
import gate.gui.*;

public class Main {
	public static void main(String[] args)
	throws Exception{
		Gate.init();
		SwingUtilities.invokeAndWait (new Runnable() {
			public void run() {
			MainFrame.getInstance().setVisible(true) ;
			        }
			}) ;
		Factory.newDocument("This is a document");
	}
}