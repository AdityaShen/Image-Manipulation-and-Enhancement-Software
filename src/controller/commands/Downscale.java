package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * Downscale class checks if the command to downscale an image has the required number of arguments
 * and subsequently passes control to the model.
 */
public class Downscale implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 5) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    try {
      String imageName = args[1];
      String modifiedImageName = args[2];
      int newWidth = Integer.parseInt(args[3]);
      int newHeight = Integer.parseInt(args[4]);

      if (newWidth <= 0 || newHeight <= 0) {
        throw new IllegalArgumentException("Width and height must be positive integers");
      }

      handler.downscale(imageName, modifiedImageName, newWidth, newHeight);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Width and height must be valid integers");
    }
  }
}
