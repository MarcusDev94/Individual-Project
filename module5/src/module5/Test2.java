package module5;
import gate.*;
import gate.gui.*;

public class Test2 {
	public static void main(String[] args)
	throws Exception{
		Gate.init();
		MainFrame.getInstance().setVisible(true);
		Factory.newDocument("This is a document");
	}
}
