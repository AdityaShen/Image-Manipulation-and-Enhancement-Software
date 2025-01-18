package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * FlipHorizontal class checks if the command to flip an image horizontally has required number of
 * arguments and subsequently passes control to model.
 */
public class FlipHorizontal implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    handler.horizontalFlip(args[1], args[2]);
  }
}
