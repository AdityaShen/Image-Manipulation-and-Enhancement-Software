package src.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This interface handles image manipulation and processing operations. it defines methods for
 * loading, applying image transformations or visualization and saving images.
 */
public interface ImageHandler {

  void loadImagePixels(BufferedImage image, String imageName) throws IOException;

  void loadImagePixelsFromPPM(BufferedReader reader, String imageName) throws IOException;

  /**
   * Saves processed image.
   *
   * @param imageName name of the image to be saved.
   */
  OutputStream save(String imageName) throws IOException;

  /**
   * Extracts the blue component from specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the blue component.
   */
  void blueComponent(String imageName, String modifiedImageName);

  /**
   * Extracts the green component from specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the green component.
   */
  void greenComponent(String imageName, String modifiedImageName);

  /**
   * Extracts the red component from specified image and creates a new Image object for it. as a new
   * image.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the red component.
   */
  void redComponent(String imageName, String modifiedImageName);

  /**
   * Extracts the value component from the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the value component.
   */
  void valueComponent(String imageName, String modifiedImageName);

  /**
   * Extracts the intensity component from the specified image and creates a new Image object for
   * it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the intensity component.
   */
  void intensityComponent(String imageName, String modifiedImageName);

  /**
   * Extracts the luma component from the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object containing the luma component.
   */
  void lumaComponent(String imageName, String modifiedImageName);

  /**
   * Flips the specified image vertically and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after vertical flipping.
   */
  void verticalFlip(String imageName, String modifiedImageName);

  /**
   * Flips the specified image horizontally and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after horizontal flipping.
   */
  void horizontalFlip(String imageName, String modifiedImageName);

  /**
   * Applies a sepia effect to the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object with the sepia effect applied.
   */
  void sepia(String imageName, String modifiedImageName);

  /**
   * Brightens the specified image by a given value and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after brightening.
   * @param value             value by which to brighten the image.
   */
  void brighten(String imageName, String modifiedImageName, int value);

  /**
   * Applies a blur effect to the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after applying the blur effect.
   */
  void blur(String imageName, String modifiedImageName);

  /**
   * Applies a sharpening effect to the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after applying the sharpening effect.
   */
  void sharpen(String imageName, String modifiedImageName);

  /**
   * Splits the specified image into its red, green, and blue components and saves each component as
   * a new image object.
   *
   * @param imageName   name of the source image.
   * @param redResult   name of the new image object containing the red component.
   * @param greenResult name of the new image object containing the green component.
   * @param blueResult  name of the new image object containing the blue component.
   */
  void rgbSplit(String imageName, String redResult, String greenResult, String blueResult);

  /**
   * Combines the specified red, green, and blue image components into a single image and saves the
   * result as a new image object.
   *
   * @param modifiedImageName name of the new combined image.
   * @param redImageName      name of the red component image object.
   * @param greenImageName    name of the green component image object.
   * @param blueImageName     name of the blue component image object.
   */
  void rgbCombine(String modifiedImageName, String redImageName, String greenImageName,
      String blueImageName);
}
