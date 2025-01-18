package src.model;

/**
 * This interface extends the ImageHandlerAdapter interface and provides additional image processing
 * methods.
 */
public interface ExtendedImageHandlerAdapter extends ImageHandlerAdapter {

  /**
   * Scales down the image to the specified width and height.
   *
   * @param imageName         The name of the image to be downscaled.
   * @param modifiedImageName The name of the resulting downscaled image.
   * @param newWidth          The new width of the downscaled image.
   * @param newHeight         The new height of the downscaled image.
   */
  void downscale(String imageName, String modifiedImageName, int newWidth, int newHeight);

  /**
   * Applies a blur effect to the image using a mask.
   *
   * @param imageName         The name of the image to be blurred.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting blurred image.
   */
  void blurWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Applies a sharpen effect to the image using a mask.
   *
   * @param imageName         The name of the image to be sharpened.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting sharpened image.
   */
  void sharpenWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to the red component using a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting red component image.
   */
  void redComponentWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to the green component using a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting green component image.
   */
  void greenComponentWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to the blue component using a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting blue component image.
   */
  void blueComponentWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Applies a sepia effect to the image using a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting sepia image.
   */
  void sepiaWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to greyscale using the luma component with a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting luma greyscale image.
   */
  void lumaWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to greyscale using the intensity component with a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting intensity greyscale image.
   */
  void intensityWithMask(String imageName, String maskedImageName, String modifiedImageName);

  /**
   * Converts the image to greyscale using the value component with a mask.
   *
   * @param imageName         The name of the image to be processed.
   * @param maskedImageName   The name of the mask image to be applied.
   * @param modifiedImageName The name of the resulting value greyscale image.
   */
  void valueWithMask(String imageName, String maskedImageName, String modifiedImageName);
}

