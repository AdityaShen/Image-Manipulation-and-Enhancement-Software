package src.model;

/**
 * Represents an image with properties like width, height, etc. Defines methods to retrieve image
 * dimensions, max pixel value, and access pixel data.
 */
public interface Image {

  /**
   * Gets width of the image.
   *
   * @return width of the image in pixels.
   */
  int getWidth();

  /**
   * Gets height of the image.
   *
   * @return height of image in pixels.
   */
  int getHeight();


  /**
   * Gets the pixel data for the image.
   *
   * @return 2D array of pixels representing  image.
   */
  Pixel getPixel(int x, int y);

  /**
   * Gets entire image data as a 2D array of pixels.
   *
   * @return 2D array of pixels representing the entire image.
   */
  Pixel[][] getImage();

}
