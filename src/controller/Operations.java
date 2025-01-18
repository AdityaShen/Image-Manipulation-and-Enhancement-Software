package src.controller;

import java.io.IOException;

/**
 *  Contains operations that can be performed on an image.
 */
public interface Operations {

  /**
   * Loads an image from the given file path.
   *
   * @param filepath The location of the image file to load.
   * @throws IOException If an error occurs while loading the image.
   */
  void load(String filepath) throws IOException;

  /**
   * Saves the current image to the given file path.
   *
   * @param filepath The location where the image will be saved.
   * @throws IOException If an error occurs while saving the image.
   */
  void save(String filepath) throws IOException;

  /**
   * Converts the image to the red color component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void redComponent() throws IOException;

  /**
   * Converts the image to the green color component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void greenComponent() throws IOException;

  /**
   * Converts the image to the blue color component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void blueComponent() throws IOException;

  /**
   * Converts the image to greyscale using the luma component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void luma() throws IOException;

  /**
   * Converts the image to greyscale using the value component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void value() throws IOException;

  /**
   * Converts the image to greyscale using the intensity component.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void intensity() throws IOException;

  /**
   * Applies a blur effect to the image.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void blur() throws IOException;

  /**
   * Applies a sharpen effect to the image.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void sharpen() throws IOException;

  /**
   * Applies a sepia tone effect to the image.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void sepia() throws IOException;

  /**
   * Adjusts the brightness of the image by the specified amount.
   *
   * @param scale The amount to adjust the brightness by.
   * @throws IOException If an error occurs during the image processing.
   */
  void brighten(int scale) throws IOException;

  /**
   * Flips the image vertically.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void verticalFlip() throws IOException;

  /**
   * Flips the image horizontally.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void horizontalFlip() throws IOException;

  /**
   * Corrects the colors of the image.
   *
   * @throws IOException If an error occurs during the image processing.
   */
  void colorCorrect() throws IOException;

  /**
   * Adjusts the brightness, mid, and white balance of the image.
   *
   * @param b The brightness level.
   * @param m The mid level.
   * @param w The white balance level.
   * @throws IOException If an error occurs during the image processing.
   */
  void levelAdjust(int b, int m, int w) throws IOException;

  /**
   * Compresses the image by the given percentage.
   *
   * @param percentage The percentage to compress the image by.
   * @throws IOException If an error occurs during the image processing.
   */
  void compress(double percentage) throws IOException;

  /**
   * Scales down the image to the specified width and height.
   *
   * @param width  The new width of the image.
   * @param height The new height of the image.
   * @throws IOException If an error occurs during the image processing.
   */
  void downscale(int width, int height) throws IOException;

  /**
   * Splits the image into its RGB channels and saves them as separate images.
   *
   * @param redFilePath   The file path for the red channel image.
   * @param greenFilePath The file path for the green channel image.
   * @param blueFilePath  The file path for the blue channel image.
   * @throws IOException If an error occurs during the image processing.
   */
  void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath) throws IOException;

  /**
   * Combines separate RGB channel images into a full-color image.
   *
   * @param redImageFile   The file path for the red channel image.
   * @param greenImageFile The file path for the green channel image.
   * @param blueImageFile  The file path for the blue channel image.
   * @throws IOException If an error occurs during the image processing.
   */
  void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
      throws IOException;

  /**
   * Performs a specific image operation based on the given command and arguments.
   *
   * @param command   The command for the operation to be performed.
   * @param splitArgs The arguments for the operation.
   * @throws IOException If an error occurs during the image processing.
   */
  void split(String command, String[] splitArgs) throws IOException;

  /**
   * Reloads the original image, discarding any changes made.
   *
   * @throws IOException If an error occurs during the image reloading process.
   */
  void reloadImage() throws IOException;

  /**
   * Exits the program.
   */
  void quit();

}
