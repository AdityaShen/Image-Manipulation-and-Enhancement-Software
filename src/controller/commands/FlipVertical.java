package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * FlipVertical class checks if the command to flip an image vertically has required number of
 * arguments and subsequently passes control to model.
 */
public class FlipVertical implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    handler.verticalFlip(args[1], args[2]);
  }
}
