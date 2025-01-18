package src.model;

/**
 * Represents a simple image composed of a 2D array of pixels. Provides methods to access the
 * dimensions of the image, retrieve specific pixels, and get all the image data.
 */
public class SimpleImage implements Image {

  private final Pixel[][] image;

  /**
   * Constructs a SimpleImage with the given pixel data.
   *
   * @param image 2D array of Pixel objects representing the image.
   * @throws IllegalArgumentException if the image array is of invalid dimensions or empty.
   */
  public SimpleImage(Pixel[][] image) {
    if (image.length == 0 || image[0].length == 0) {
      throw new IllegalArgumentException("Invalid image size");
    }
    this.image = image;
  }


  /**
   * Gets width of the image.
   *
   * @return width of the image as an integer.
   */
  @Override
  public int getWidth() {
    return this.image.length;
  }

  /**
   * Gets height of the image.
   *
   * @return height of the image as an integer.
   */
  @Override
  public int getHeight() {
    return this.image[0].length;
  }

  /**
   * Gets pixel at the specified coordinates.
   *
   * @param x the x-coord of the pixel to retrieve.
   * @param y y-coord of the pixel to retrieve.
   * @return Pixel object at the specified coordinates.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  @Override
  public Pixel getPixel(int x, int y) {
    if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight()) {
      throw new IllegalArgumentException("Invalid pixel coordinates");
    }
    return image[x][y];
  }

  /**
   * Gets the entire image data as a 2D array of pixels.
   *
   * @return a 2D array of Pixel objects representing the image.
   */
  @Override
  public Pixel[][] getImage() {
    return this.image;
  }
}
