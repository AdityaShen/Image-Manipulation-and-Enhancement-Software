package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;


/**
 * LevelsAdjust class checks if the command to Levels adjust an image has required number of
 * arguments and subsequently passes control to model.
 */

public class LevelsAdjust implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 6 && args.length != 8) {
      throw new IllegalArgumentException("Wrong number of arguments for levels-adjust command");
    } else if (args.length == 6) {
      try {
        int black = Integer.parseInt(args[1]);
        int mid = Integer.parseInt(args[2]);
        int white = Integer.parseInt(args[3]);
        String imageName = args[4];
        String destImageName = args[5];
        handler.levelsAdjust(black, mid, white, imageName, destImageName);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Invalid level values: black, mid, and white must be integers");
      }
    } else if (args.length == 8) {
      if (args[6].equals("split")) {
        try {
          int black = Integer.parseInt(args[1]);
          int mid = Integer.parseInt(args[2]);
          int white = Integer.parseInt(args[3]);
          String imageName = args[4];
          String destImageName = args[5];
          handler.levelAdjustWithSplit(black, mid, white, imageName, destImageName,
              Integer.parseInt(args[7]));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "Invalid level values: black, mid, and white must be integers");
        }
      } else {
        throw new IllegalArgumentException("Wrong number of arguments for levels-adjust command");
      }
    }
  }
}
