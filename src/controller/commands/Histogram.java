package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * Histogram class checks if the command to construct a histogram for an image has required number
 * of arguments and subsequently passes control to model.
 */

public class Histogram implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    handler.histogram(args[1], args[2]);
  }
}

