package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * BrightenImage class checks if the command to brighten an image has required number of arguments
 * and subsequently passes control to model.
 */
public class BrightenImage implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 4) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    try {
      handler.brighten(args[2], args[3], Integer.parseInt(args[1]));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid brightness");
    }
  }
}
