package src.controller.commands;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import src.model.ExtendedImageHandlerAdapter;

/**
 * SaveImage class checks if the command to save an image has required number of arguments and
 * subsequently passes control to model.
 */
public class SaveImage implements CommandController {

  @Override
  public void execute(String[] args, ExtendedImageHandlerAdapter handler) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Wrong number of arguments");
    }
    try {
      OutputStream outputStream = handler.save(args[1]);
      saveImage(outputStream, args[2]);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveImage(OutputStream outputStream, String filepath) throws IOException {
    String data = outputStream.toString();
    Scanner sc = new Scanner(data);

    int width = sc.nextInt();
    int height = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = image.getRaster();

    int[] pixel = new int[3];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixel[0] = sc.nextInt();
        pixel[1] = sc.nextInt();
        pixel[2] = sc.nextInt();
        raster.setPixel(x, y, pixel);
      }
    }

    File output;
    File filePathFile = new File(filepath);
    if (filePathFile.isAbsolute()) {
      output = filePathFile;
    } else {
      File outDir = new File("out");
      if (!outDir.exists() && !outDir.mkdir()) {
        throw new IOException("Failed to create the `out` directory.");
      }
      output = new File(outDir, filepath);
    }

    String[] parts = filepath.split("\\.");
    String fileType = parts[parts.length - 1];

    try {
      ImageIO.write(image, fileType, output);
    } catch (IOException e) {
      throw new IOException("Failed to save the image! Please check the file path and permissions.",
          e);
    }
  }

}
