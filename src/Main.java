import controller.Controller;
import gui.GUIMutex;




public class Main 
{
	public static void main(String[] args)
	{
		GUIMutex guiFrame = new GUIMutex();
		Controller controller = new Controller(guiFrame);
		guiFrame.Start(controller);
	}
}
