package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;


/**
 * ColorCorrect class checks if the command to Color correct an image has required number of
 * arguments and subsequently passes control to model.
 */

public class ColorCorrect implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    } else {
      if (args.length == 3) {
        handler.colorCorrect(args[1], args[2]);
      } else if (args.length == 5) {
        if (args[3].equals("split")) {
          handler.colorCorrectWithSplit(args[1], args[2], Integer.parseInt(args[4]));
        } else {
          throw new IllegalArgumentException("Invalid command");
        }
      }
    }
  }
}
