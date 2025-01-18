import src.controller.ScriptController;
import src.controller.SimpleScriptController;
import src.controller.GUIController;
import src.model.ExtendedImageHandlerAdapter;
import src.model.SimpleExtendedImageHandlerAdapter;
import src.view.GUIInterface;
import src.view.SimpleGUI;
import src.view.SimpleView;
import src.view.View;

/**
 * This class drives the entire application and it's Image processing capabilties. It creates
 * objects of the view and model and passes it to the controller to process commands from a script
 * file. The application requires a script file as an argument, which contains the sequence of image
 * processing operations. The script is then processed line by line by the controller.
 */
public class Main {

  /**
   * The main method of the application. Runs the application and validates script process functions
   * are executed as expected.
   *
   * @param args the command line arguments, where the first argument should be the script file
   *             path.
   */
  public static void main(String[] args) {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    View v = new SimpleView();
    if (args.length > 0) {
      ScriptController scriptController = SimpleScriptController.create(args, handler, v);
      if (scriptController != null) {
        scriptController.processInput();
      }
    } else {
      GUIInterface view = new SimpleGUI("Image Manipulation and Enhancement");
      new GUIController(handler, view);
    }
  }
}
