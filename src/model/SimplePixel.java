package src.model;

/**
 * This class implements the Pixel interface and provides methods to access the RGB color components
 * of a pixel. It represents a pixel with RGB color values.
 */
public class SimplePixel implements Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a SimplePixel with the specified RGB values.
   *
   * @param red   red component of the pixel.
   * @param green green component of the pixel.
   * @param blue  blue component of the pixel.
   */
  public SimplePixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Retrieves red component of the pixel.
   *
   * @return red component value of this pixel.
   */
  @Override
  public int getR() {
    return this.red;
  }

  /**
   * Retrieves green component of the pixel.
   *
   * @return green component value of this pixel.
   */
  @Override
  public int getG() {
    return this.green;
  }

  /**
   * Retrieves blue component of the pixel.
   *
   * @return blue component value of this pixel.
   */
  @Override
  public int getB() {
    return this.blue;
  }
}
