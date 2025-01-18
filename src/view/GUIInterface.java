package src.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import src.controller.Operations;

/**
 * This interface represents the view of the IME application. It defines methods
 * for refreshing the view, adding operations, and displaying error messages.
 */
public interface GUIInterface {

  /**
   * Refreshes the view with updated image and histogram data.
   *
   * @param imageName The name of the image to display.
   * @param histogram The name of the histogram image to display.
   * @param img The image to be displayed.
   * @param histogramImage The histogram image to be displayed.
   * @throws IOException If an error occurs while refreshing the view.
   */
  void refreshGUI(String imageName, String histogram, BufferedImage img,
      BufferedImage histogramImage) throws IOException;

  /**
   * Splits the current image view.
   *
   * @param image The image to be split.
   * @param splitImageName The name of the split image.
   * @throws IOException If an error occurs during the split process.
   */
  void splitView(BufferedImage image, String splitImageName) throws IOException;

  /**
   * Adds operations to the view.
   *
   * @param operations The features to be added.
   */
  void addOperations(Operations operations);

  /**
   * Displays an error message to the user.
   *
   * @param error The error message.
   */
  void displayError(String error);
}


