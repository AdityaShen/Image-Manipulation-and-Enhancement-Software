package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * VisualiseValue class checks if the command to visualise value of an image has required number of
 * arguments and subsequently passes control to model.
 */
public class VisualiseValue implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length < 3 || args.length > 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    } else if (args.length == 3) {
      handler.valueComponent(args[1], args[2]);
    } else if (args.length == 4) {
      handler.valueWithMask(args[1], args[2], args[3]);
    } else {
      if (args[3].equals("split")) {
        handler.valueWithSplit(args[1], args[2], Integer.parseInt(args[4]));
      } else {
        throw new IllegalArgumentException("Invalid command");
      }
    }
  }
}
