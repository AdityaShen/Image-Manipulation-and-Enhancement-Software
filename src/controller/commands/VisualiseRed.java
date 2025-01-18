package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * VisualiseRed class checks if the command to create red component of an image has required number
 * of arguments and subsequently passes control to model.
 */
public class VisualiseRed implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length < 3 || args.length > 4) {
      throw new IllegalArgumentException("Wrong number of arguments");
    } else if (args.length == 4) {
      handler.redComponentWithMask(args[1], args[2], args[3]);
    } else {
      handler.redComponent(args[1], args[2]);
    }
  }
}
