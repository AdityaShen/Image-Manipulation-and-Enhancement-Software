package src.controller.commands;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.model.ExtendedImageHandlerAdapter;

/**
 * ImageLoader class checks if the command to load an image has required number of arguments and
 * subsequently passes control to model.
 */
public class ImageLoader implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    String imagePath = args[1];
    try {
      if (imagePath.endsWith(".ppm")) {
        BufferedReader reader = new BufferedReader(new FileReader(imagePath));
        handler.loadImagePixelsFromPPM(reader, args[2]);
      } else {
        BufferedImage image = ImageIO.read(new File(imagePath));
        handler.loadImagePixels(image, args[2]);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
