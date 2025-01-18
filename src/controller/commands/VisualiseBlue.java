package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * VisualiseBlue class checks if the command to create blue component of an image has required
 * number of arguments and subsequently passes control to model.
 */
public class VisualiseBlue implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length < 3 || args.length > 4) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    if (args.length == 3) {
      handler.blueComponent(args[1], args[2]);
    } else {
      handler.blueComponentWithMask(args[1], args[2], args[3]);
    }
  }
}
