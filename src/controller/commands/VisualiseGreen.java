package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * VisualiseGreen class checks if the command to create green component of an image has required
 * number of arguments and subsequently passes control to model.
 */
public class VisualiseGreen implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length < 3 || args.length > 4) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    if (args.length == 3) {
      handler.greenComponent(args[1], args[2]);
    } else {
      handler.greenComponentWithMask(args[1], args[2], args[3]);
    }
  }
}
