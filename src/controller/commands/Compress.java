package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * Compress class checks if the command to compress an image has required number of arguments and
 * subsequently passes control to model.
 */
public class Compress implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 4) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    try {
      double percentage = Double.parseDouble(args[3]);
      String imageName = args[1];
      String modifiedImageName = args[2];
      handler.compress(imageName, modifiedImageName, percentage);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid compression percentage");
    }
  }
}
