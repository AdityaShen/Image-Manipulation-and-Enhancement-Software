package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * RGBSplit class checks if the command to split an image into red, green and blue components has
 * required number of arguments and subsequently passes control to model.
 */
public class RGBSplit implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    handler.rgbSplit(args[1], args[2], args[3], args[4]);
  }
}
