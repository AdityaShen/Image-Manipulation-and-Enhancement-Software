package src.model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface that extends the ImageHandler interface and provides additional image manipulation
 * operations. includes level adjust, color correction, compressing images, and generating
 * histograms.
 */

public interface ImageHandlerAdapter extends ImageHandler {

  /**
   * adjusts level of the specified image using black, mid, and white threshold values.
   *
   * @param black             black point adjustment.
   * @param mid               mid point adjustment.
   * @param white             white point adjustment.
   * @param imageName         name of the source image to be modified.
   * @param modifiedImageName name of the modified image to be saved.
   */

  void levelsAdjust(int black, int mid, int white, String imageName, String modifiedImageName);

  /**
   * Performs color correction on an image by  analyzing the frequency of color values and finding
   * the peaks of each color, then adjusting them based on their average peak value.
   *
   * @param imageName         name of the source image to apply color-correct.
   * @param modifiedImageName the name of the modified image to be saved.
   */

  void colorCorrect(String imageName, String modifiedImageName);


  /**
   * Compresses an image by applying lossy compression on its RGB channels.
   *
   * @param imageName         name of the image to be compressed.
   * @param modifiedImageName name of the compressed image to be saved.
   * @param percentage        percentage by which the image should be compressed (must be b/w 0 to
   *                          100).
   */

  void compress(String imageName, String modifiedImageName, double percentage);


  /**
   * Generates a histogram representation of an image by constructing a graphical histogram as
   * 255x255 pixel image.
   *
   * @param imageName         name of the source image for which to generate the histogram.
   * @param modifiedImageName name of the image to save the generated histogram.
   */

  void histogram(String imageName, String modifiedImageName);

  /**
   * Processes the image based on its name and returns a BufferedImage.
   *
   * @param imageName The name of the image to be processed.
   * @return The processed image as a BufferedImage.
   * @throws IOException If an I/O error occurs while processing the image.
   */
  BufferedImage processImage(String imageName) throws IOException;

  /**
   * Applies a blur effect to the image and creates a new modified image with the specified value.
   *
   * @param imageName         The name of the image to be blurred.
   * @param modifiedImageName The name of the resulting blurred image.
   * @param value             The intensity or strength of the blur effect.
   */
  void blurWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Applies a sharpen effect to the image and creates a new modified image with the specified
   * value.
   *
   * @param imageName         The name of the image to be sharpened.
   * @param modifiedImageName The name of the resulting sharpened image.
   * @param value             The intensity or strength of the sharpen effect.
   */
  void sharpenWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Applies a sepia tone effect to the image and creates a new modified image with the specified
   * value.
   *
   * @param imageName         The name of the image to apply the sepia effect.
   * @param modifiedImageName The name of the resulting sepia image.
   * @param value             The intensity or strength of the sepia effect.
   */
  void sepiaWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Converts the image to greyscale using the luma component and creates a new modified image with
   * the specified value.
   *
   * @param imageName         The name of the image to be converted to greyscale using the luma
   *                          component.
   * @param modifiedImageName The name of the resulting luma greyscale image.
   * @param value             The intensity or strength of the luma effect.
   */
  void lumaWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Converts the image to greyscale using the intensity component and creates a new modified image
   * with the specified value.
   *
   * @param imageName         The name of the image to be converted to greyscale using the intensity
   *                          component.
   * @param modifiedImageName The name of the resulting intensity greyscale image.
   * @param value             The intensity or strength of the intensity effect.
   */
  void intensityWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Converts the image to greyscale using the value component and creates a new modified image with
   * the specified value.
   *
   * @param imageName         The name of the image to be converted to greyscale using the value
   *                          component.
   * @param modifiedImageName The name of the resulting value greyscale image.
   * @param value             The intensity or strength of the value effect.
   */
  void valueWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Applies color correction to the image and creates a new modified image with the specified
   * value.
   *
   * @param imageName         The name of the image to apply color correction.
   * @param modifiedImageName The name of the resulting color-corrected image.
   * @param value             The intensity or strength of the color correction effect.
   */
  void colorCorrectWithSplit(String imageName, String modifiedImageName, int value);

  /**
   * Adjusts the levels (black, midtones, and white) of the image and creates a new modified image
   * with the specified value.
   *
   * @param black             The adjustment for the black point.
   * @param mid               The adjustment for the midtones.
   * @param white             The adjustment for the white point.
   * @param imageName         The name of the image to be level-adjusted.
   * @param modifiedImageName The name of the resulting level-adjusted image.
   * @param value             The intensity or strength of the level adjustment.
   */
  void levelAdjustWithSplit(int black, int mid, int white, String imageName,
      String modifiedImageName, int value);

  /**
   * Splits the image into filtered and current images, adjusting based on a width percentage.
   *
   * @param current         The current image to be split.
   * @param filtered        The filtered image to be split.
   * @param widthPercentage The percentage of the width to be applied during the split.
   * @return The resulting image after the split.
   */
  Image split(Image current, Image filtered, float widthPercentage);

}
