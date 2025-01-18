package src.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles image loading and storage operations. The class provides methods to load images in
 * various formats like jpg, png, ppm, etc. stored images are managed in a map, and we can retrieve
 * image data.
 */
public class SimpleImageHandler implements ImageHandler {

  protected final Map<String, Image> imageMap;

  /**
   * Initializes a new SimpleImageHandler instance. An empty map is created to store images.
   */
  public SimpleImageHandler() {
    imageMap = new HashMap<>();
  }

  /**
   * Gets the map of stored images.
   *
   * @return map where the keys are image names and the values are Image objects.
   */
  public Map<String, Image> getMap() {
    return imageMap;
  }

  /**
   * Retrieves an image by its name from the stored images.
   *
   * @param imageName name of the image to retrieve.
   * @return Image object associated with the given name, or null if not found.
   */
  public Image getImage(String imageName) {
    return imageMap.get(imageName);
  }


  @Override
  public void loadImagePixels(BufferedImage image, String imageName) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] pixels = new SimplePixel[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Color color = new Color(image.getRGB(i, j));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        pixels[i][j] = new SimplePixel(red, green, blue);
      }
    }
    Image img = new SimpleImage(pixels);
    imageMap.put(imageName, img);
  }


  @Override
  public void loadImagePixelsFromPPM(BufferedReader reader, String imageName) throws IOException {
    String magicNumber = reader.readLine().trim();
    validateMagicNumber(magicNumber);

    String line = skipComments(reader);

    int[] dimensions = parseDimensions(line);
    int width = dimensions[0];
    int height = dimensions[1];

    int maxColorValue = parseMaxColorValue(reader);

    Pixel[][] pixels = readAndConstructPixels(reader, width, height, maxColorValue);
    Image img = new SimpleImage(pixels);
    imageMap.put(imageName, img);
  }

  private void validateMagicNumber(String magicNumber) {
    if (!magicNumber.equals("P3")) {
      throw new IllegalArgumentException(
          "Invalid PPM file: expected 'P3', found '" + magicNumber + "'.");
    }
  }

  private String skipComments(BufferedReader reader) throws IOException {
    String line;
    while ((line = reader.readLine().trim()).startsWith("#")) {
      System.out.println("Skipping comment: " + line);
    }
    return line;
  }

  private int[] parseDimensions(String line) {
    String[] dimensions = line.split("\\s+");
    return new int[]{Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1])};
  }

  private int parseMaxColorValue(BufferedReader reader) throws IOException {
    int maxColorValue = Integer.parseInt(reader.readLine().trim());
    if (maxColorValue != 255) {
      throw new IllegalArgumentException("Invalid PPM file: max color value must be 255.");
    }
    return maxColorValue;
  }

  private Pixel[][] readAndConstructPixels(BufferedReader reader, int width, int height,
      int maxColorValue) throws IOException {
    StringBuilder pixelData = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      pixelData.append(line.trim()).append(" ");
    }

    String[] pixelValues = pixelData.toString().trim().split("\\s+");
    validatePixelValues(pixelValues, width, height);

    return constructPixels(pixelValues, width, height, maxColorValue);
  }

  private void validatePixelValues(String[] pixelValues, int width, int height) {
    if (pixelValues.length != width * height * 3) {
      throw new IllegalArgumentException(
          "Invalid PPM file: expected " + (width * height * 3) + " pixel values but found "
              + pixelValues.length + ".");
    }
  }

  private Pixel[][] constructPixels(String[] pixelValues, int width, int height,
      int maxColorValue) {
    Pixel[][] pixels = new SimplePixel[width][height];
    int index = 0;
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int red = Integer.parseInt(pixelValues[index++]);
        int green = Integer.parseInt(pixelValues[index++]);
        int blue = Integer.parseInt(pixelValues[index++]);

        red = adjustColorValue(red, maxColorValue);
        green = adjustColorValue(green, maxColorValue);
        blue = adjustColorValue(blue, maxColorValue);

        pixels[i][j] = new SimplePixel(red, green, blue);
      }
    }
    return pixels;
  }

  private int adjustColorValue(int color, int maxColorValue) {
    return (maxColorValue != 255) ? (color * 255) / maxColorValue : color;
  }


  /**
   * Saves the specified image to a file.
   *
   * @param imageName name of the image to save.
   * @throws IllegalArgumentException if the image is not found or if there is an error during
   *                                  saving.
   */

  @Override
  public OutputStream save(String imageName) throws IOException {
    Image image = this.getImage(imageName);
    StringBuilder sb = new StringBuilder();
    int width = image.getWidth();
    int height = image.getHeight();
    sb.append(width).append(" ").append(height).append(System.lineSeparator());
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        sb.append(image.getPixel(i, j).getR()).append(System.lineSeparator());
        sb.append(image.getPixel(i, j).getG()).append(System.lineSeparator());
        sb.append(image.getPixel(i, j).getB()).append(System.lineSeparator());
      }
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    outputStream.write(sb.toString().getBytes());
    return outputStream;
  }

  /**
   * Creates a new image consisting only of the blue component of the image specified.
   *
   * @param imageName         name of the original image to process.
   * @param modifiedImageName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */

  @Override
  public void blueComponent(String imageName, String modifiedImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] modifiedPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);
        int blueComponent = currentPixel.getB();
        Pixel newPixel = new SimplePixel(blueComponent, blueComponent, blueComponent);
        modifiedPixels[x][y] = newPixel;
      }
    }
    Image resultImage = new SimpleImage(modifiedPixels);
    imageMap.put(modifiedImageName, resultImage);
  }

  /**
   * Creates a new image consisting only of the green component of the image specified.
   *
   * @param imageName         name of the original image to process.
   * @param modifiedImageName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */

  @Override
  public void greenComponent(String imageName, String modifiedImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] greenPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);
        int greenComponent = currentPixel.getG();
        Pixel newPixel = new SimplePixel(greenComponent, greenComponent, greenComponent);
        greenPixels[x][y] = newPixel;
      }
    }
    Image resultImage = new SimpleImage(greenPixels);
    imageMap.put(modifiedImageName, resultImage);
  }


  /**
   * Creates a new image consisting only of the red component of the image specified.
   *
   * @param imageName         name of the original image to process.
   * @param modifiedImageName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */
  @Override
  public void redComponent(String imageName, String modifiedImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] redPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);
        int redComponent = currentPixel.getR();
        Pixel newPixel = new SimplePixel(redComponent, redComponent, redComponent);
        redPixels[x][y] = newPixel;
      }
    }
    Image resultImage = new SimpleImage(redPixels);
    imageMap.put(modifiedImageName, resultImage);
  }


  /**
   * Creates a new image representing the value component of the image specified.
   *
   * @param imageName  name of the original image to process.
   * @param resultName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */
  @Override
  public void valueComponent(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }
    Pixel[][] valuePixels = new SimplePixel[image.getWidth()][image.getHeight()];
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);
        int red = currentPixel.getR();
        int green = currentPixel.getG();
        int blue = currentPixel.getB();
        int value = Math.max(red, Math.max(green, blue));
        Pixel newPixel = new SimplePixel(value, value, value);
        valuePixels[x][y] = newPixel;
      }
    }
    Image resultImage = new SimpleImage(valuePixels);
    imageMap.put(resultName, resultImage);
  }


  /**
   * Creates a new image representing the intensity component of the image specified.
   *
   * @param imageName  name of the original image to process.
   * @param resultName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */
  @Override
  public void intensityComponent(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] intensityPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);
        int intensity = (currentPixel.getR() + currentPixel.getG() + currentPixel.getB()) / 3;
        Pixel newPixel = new SimplePixel(intensity, intensity, intensity);
        intensityPixels[x][y] = newPixel;
      }
    }
    Image resultImage = new SimpleImage(intensityPixels);
    imageMap.put(resultName, resultImage);
  }


  private Pixel applyTransformation(Pixel pixel, double[][] matrix) {
    int red = (int) Math.min(255,
        matrix[0][0] * pixel.getR() + matrix[0][1] * pixel.getG() + matrix[0][2] * pixel.getB());
    int green = (int) Math.min(255,
        matrix[1][0] * pixel.getR() + matrix[1][1] * pixel.getG() + matrix[1][2] * pixel.getB());
    int blue = (int) Math.min(255,
        matrix[2][0] * pixel.getR() + matrix[2][1] * pixel.getG() + matrix[2][2] * pixel.getB());
    return new SimplePixel(red, green, blue);
  }


  /**
   * Creates a new image representing the luma component of the image specified.
   *
   * @param imageName  name of the original image to process.
   * @param resultName name to assign to the modified image.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */

  @Override
  public void lumaComponent(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    double[][] lumaMatrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };

    Pixel[][] lumaPixels = new Pixel[image.getWidth()][image.getHeight()];
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        lumaPixels[x][y] = applyTransformation(image.getPixel(x, y), lumaMatrix);
      }
    }
    Image lumaImage = new SimpleImage(lumaPixels);
    imageMap.put(resultName, lumaImage);
  }


  /**
   * Creates a new image by vertically flipping the specified image.
   *
   * @param imageName  name of the original image to be flipped.
   * @param resultName name to assign to the new image created from the vertical flip.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */

  @Override
  public void verticalFlip(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] flippedPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        flippedPixels[x][image.getHeight() - 1 - y] = image.getPixel(x, y);
      }
    }
    Image resultImage = new SimpleImage(flippedPixels);
    imageMap.put(resultName, resultImage);
  }

  /**
   * Creates a new image by horizontally flipping the specified image.
   *
   * @param imageName  name of the original image to be flipped.
   * @param resultName name to assign to the new image created from the horizontal flip.
   * @throws IllegalArgumentException if no image is found with the specified name.
   */

  @Override
  public void horizontalFlip(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] flippedPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        flippedPixels[image.getWidth() - 1 - x][y] = image.getPixel(x, y);
      }
    }

    Image resultImage = new SimpleImage(flippedPixels);
    imageMap.put(resultName, resultImage);
  }

  /**
   * Applies a sepia effect to the specified image and creates a new Image object for it.
   *
   * @param imageName  name of the source image.
   * @param resultName name of the new image object with the sepia effect applied.
   */

  @Override
  public void sepia(String imageName, String resultName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    double[][] sepiaMatrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    Pixel[][] sepiaPixels = new Pixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        sepiaPixels[x][y] = applyTransformation(image.getPixel(x, y), sepiaMatrix);
      }
    }

    Image resultImage = new SimpleImage(sepiaPixels);
    imageMap.put(resultName, resultImage);
  }

  /**
   * Brightens the specified image by a given value and creates a new Image object for it.
   *
   * @param imageName  name of the source image.
   * @param resultName name of the new image object after brightening.
   * @param value      value by which to brighten the image.
   */

  @Override
  public void brighten(String imageName, String resultName, int value) {
    if (value < -255 || value > 255) {
      throw new IllegalArgumentException("Brightness value must be between -255 and 255.");
    }

    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] brightenedPixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel pixel = image.getPixel(x, y);
        int red = Math.min(255, Math.max(0, pixel.getR() + value));
        int green = Math.min(255, Math.max(0, pixel.getG() + value));
        int blue = Math.min(255, Math.max(0, pixel.getB() + value));

        brightenedPixels[x][y] = new SimplePixel(red, green, blue);
      }
    }

    Image resultImage = new SimpleImage(brightenedPixels);
    imageMap.put(resultName, resultImage);
  }


  /**
   * Applies a blur effect to the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after applying the blur effect.
   */

  @Override
  public void blur(String imageName, String modifiedImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    int width = image.getWidth();
    int height = image.getHeight();

    Pixel[][] modifiedPixels = new SimplePixel[width][height];

    double[][] blurKernel = {
        {1 / 9.0, 1 / 9.0, 1 / 9.0},
        {1 / 9.0, 1 / 9.0, 1 / 9.0},
        {1 / 9.0, 1 / 9.0, 1 / 9.0}
    };

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int neighborX = Math.min(Math.max(x + i, 0), width - 1);
            int neighborY = Math.min(Math.max(y + j, 0), height - 1);
            Pixel neighborPixel = image.getPixel(neighborX, neighborY);

            redSum += blurKernel[i + 1][j + 1] * neighborPixel.getR();
            greenSum += blurKernel[i + 1][j + 1] * neighborPixel.getG();
            blueSum += blurKernel[i + 1][j + 1] * neighborPixel.getB();
          }
        }

        int red = (int) Math.min(Math.max(redSum, 0), 255);
        int green = (int) Math.min(Math.max(greenSum, 0), 255);
        int blue = (int) Math.min(Math.max(blueSum, 0), 255);

        modifiedPixels[x][y] = new SimplePixel(red, green, blue);
      }
    }
    Image blurredImage = new SimpleImage(modifiedPixels);
    imageMap.put(modifiedImageName, blurredImage);
  }


  /**
   * Applies a sharpening effect to the specified image and creates a new Image object for it.
   *
   * @param imageName         name of the source image.
   * @param modifiedImageName name of the new image object after applying the sharpening effect.
   */

  @Override
  public void sharpen(String imageName, String modifiedImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    int width = image.getWidth();
    int height = image.getHeight();

    Pixel[][] modifiedPixels = new SimplePixel[width][height];

    int[][] sharpenKernel = {
        {0, -1, 0},
        {-1, 5, -1},
        {0, -1, 0}
    };
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int kernelValue = sharpenKernel[i + 1][j + 1];

            int neighborX = Math.min(Math.max(x + i, 0), width - 1);
            int neighborY = Math.min(Math.max(y + j, 0), height - 1);
            Pixel neighborPixel = image.getPixel(neighborX, neighborY);

            redSum += kernelValue * neighborPixel.getR();
            greenSum += kernelValue * neighborPixel.getG();
            blueSum += kernelValue * neighborPixel.getB();
          }
        }

        int red = Math.min(Math.max(redSum, 0), 255);
        int green = Math.min(Math.max(greenSum, 0), 255);
        int blue = Math.min(Math.max(blueSum, 0), 255);

        modifiedPixels[x][y] = new SimplePixel(red, green, blue);
      }
    }

    Image sharpenedImage = new SimpleImage(modifiedPixels);
    imageMap.put(modifiedImageName, sharpenedImage);
  }


  /**
   * Splits the specified image into its red, green, and blue components and saves each component as
   * a new image object.
   *
   * @param imageName      name of the source image.
   * @param redImageName   name of the new image object containing the red component.
   * @param greenImageName name of the new image object containing the green component.
   * @param blueImageName  name of the new image object containing the blue component.
   */
  @Override
  public void rgbSplit(String imageName, String redImageName, String greenImageName,
      String blueImageName) {
    Image image = imageMap.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    Pixel[][] redPixels = new SimplePixel[image.getWidth()][image.getHeight()];
    Pixel[][] greenPixels = new SimplePixel[image.getWidth()][image.getHeight()];
    Pixel[][] bluePixels = new SimplePixel[image.getWidth()][image.getHeight()];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel currentPixel = image.getPixel(x, y);

        int redComponent = currentPixel.getR();
        int greenComponent = currentPixel.getG();
        int blueComponent = currentPixel.getB();

        redPixels[x][y] = new SimplePixel(redComponent, redComponent, redComponent);

        greenPixels[x][y] = new SimplePixel(greenComponent, greenComponent, greenComponent);

        bluePixels[x][y] = new SimplePixel(blueComponent, blueComponent, blueComponent);
      }
    }

    Image redImage = new SimpleImage(redPixels);
    Image greenImage = new SimpleImage(greenPixels);
    Image blueImage = new SimpleImage(bluePixels);

    imageMap.put(redImageName, redImage);
    imageMap.put(greenImageName, greenImage);
    imageMap.put(blueImageName, blueImage);
  }


  /**
   * Combines the specified red, green, and blue image components into a single image and saves the
   * result as a new image object.
   *
   * @param modifiedImageName name of the new combined image.
   * @param redImageName      name of the red component image object.
   * @param greenImageName    name of the green component image object.
   * @param blueImageName     name of the blue component image object.
   */

  @Override
  public void rgbCombine(String modifiedImageName, String redImageName, String greenImageName,
      String blueImageName) {
    Image redImage = imageMap.get(redImageName);
    Image greenImage = imageMap.get(greenImageName);
    Image blueImage = imageMap.get(blueImageName);

    if (redImage == null || greenImage == null || blueImage == null) {
      throw new IllegalArgumentException("One or more images are missing for RGB combine");
    }

    if (redImage.getWidth() != greenImage.getWidth() || redImage.getWidth() != blueImage.getWidth()
        ||
        redImage.getHeight() != greenImage.getHeight()
        || redImage.getHeight() != blueImage.getHeight()) {
      throw new IllegalArgumentException(
          "All images must have the same dimensions for RGB combine");
    }

    int width = redImage.getWidth();
    int height = redImage.getHeight();

    Pixel[][] combinedPixels = new SimplePixel[width][height];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = redImage.getPixel(x, y).getR();
        int green = greenImage.getPixel(x, y).getG();
        int blue = blueImage.getPixel(x, y).getB();

        Pixel combinedPixel = new SimplePixel(red, green, blue);
        combinedPixels[x][y] = combinedPixel;
      }
    }

    Image combinedImage = new SimpleImage(combinedPixels);

    imageMap.put(modifiedImageName, combinedImage);
  }


}
