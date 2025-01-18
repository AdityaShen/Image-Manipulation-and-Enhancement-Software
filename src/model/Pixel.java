package src.model;

/**
 * Represents a pixel in an image, encapsulating its RGB color values. Provides methods to retrieve
 * the individual color components of the pixel.
 */
public interface Pixel {

  /**
   * Retrieves red component value of the pixel.
   *
   * @return the red component value.
   */
  int getR();

  /**
   * Retrieves green component value of the pixel.
   *
   * @return the green component value.
   */
  int getG();

  /**
   * Retrieves blue component value of the pixel.
   *
   * @return the blue component value.
   */
  int getB();


}
