package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * ConvertToSepia class checks if the command to convert an image to sepia has required number of
 * arguments and subsequently passes control to model.
 */
public class ConvertToSepia implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length < 3 || args.length > 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    } else if (args.length == 3) {
      handler.sepia(args[1], args[2]);
    } else if (args.length == 4) {
      handler.sepiaWithMask(args[1], args[2], args[3]);
    } else if (args.length == 5) {
      if (args[3].equals("split")) {
        handler.sepiaWithSplit(args[1], args[2], Integer.parseInt(args[4]));
      } else {
        throw new IllegalArgumentException("Invalid command");
      }
    }
  }
}
