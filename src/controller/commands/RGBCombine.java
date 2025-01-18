package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * RGBCombine class checks if the command to combine red, green and blue components of an image has
 * required number of arguments and subsequently passes control to model.
 */
public class RGBCombine implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    handler.rgbCombine(args[1], args[2], args[3], args[4]);
  }
}
