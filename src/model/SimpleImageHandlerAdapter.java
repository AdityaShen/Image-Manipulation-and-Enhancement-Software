package src.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * Class that extends SimpleImageHandler and implements the ImageHandlerAdapter interface ,
 * providing additional image manipulation features such as levels adjust, compress, histogram and
 * color correction.
 */
public class SimpleImageHandlerAdapter extends SimpleImageHandler implements ImageHandlerAdapter {

  /**
   * Constructor that calls the superclass constructor.
   */
  public SimpleImageHandlerAdapter() {
    super();
  }

  /**
   * adjusts level of the specified image using black, mid, and white threshold values.
   *
   * @param black             black level threshold (0-255)
   * @param mid               mid level threshold (must be greater than black and lessr than white)
   * @param white             white level threshold (0-255)
   * @param imageName         name of the original image in the map
   * @param modifiedImageName name to store the modified image
   * @throws IllegalArgumentException if levels are invalid or imageName is not found
   */
  @Override
  public void levelsAdjust(int black, int mid, int white, String imageName,
      String modifiedImageName) {
    if (black < 0 || mid <= black || white <= mid || white > 255) {
      throw new IllegalArgumentException("Invalid levels: ensure 0 <= black < mid < white <= 255");
    }

    Image image = super.getMap().get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    double[] coefficients = computeQuadraticCoefficients(black, mid, white);
    Image adjustedImage = applyLevelsAdjustment(image, coefficients);
    super.getMap().put(modifiedImageName, adjustedImage);
  }

  private double[] computeQuadraticCoefficients(int black, int mid, int white) {
    double y = Math.pow(black, 2) * (mid - white)
        - black * (Math.pow(mid, 2) - Math.pow(white, 2))
        - mid * Math.pow(white, 2) + white * Math.pow(mid, 2);

    double aA = -black * (128 - 255) + 128 * white - 255 * mid;
    double bA =
        Math.pow(black, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2);
    double cA = Math.pow(black, 2) * (255 * mid - 128 * white)
        - black * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));

    double a = aA / y;
    double b = bA / y;
    double c = cA / y;

    return new double[]{a, b, c};
  }

  private Image applyLevelsAdjustment(Image image, double[] coefficients) {
    double a = coefficients[0];
    double b = coefficients[1];
    double c = coefficients[2];

    Pixel[][] adjustedPixels = new SimplePixel[image.getWidth()][image.getHeight()];
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel pixel = image.getPixel(x, y);
        int adjustedR = applyQuadratic(a, b, c, pixel.getR());
        int adjustedG = applyQuadratic(a, b, c, pixel.getG());
        int adjustedB = applyQuadratic(a, b, c, pixel.getB());

        adjustedPixels[x][y] = new SimplePixel(adjustedR, adjustedG, adjustedB);
      }
    }
    return new SimpleImage(adjustedPixels);
  }

  private int applyQuadratic(double a, double b, double c, int value) {
    double adjusted = a * Math.pow(value, 2) + b * value + c;
    return (int) Math.max(0, Math.min(255, Math.round(adjusted)));
  }

  private double[][][] extractChannels(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();

    double[][] reds = new double[width][height];
    double[][] greens = new double[width][height];
    double[][] blues = new double[width][height];

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Pixel pixel = image.getPixel(i, j);
        reds[i][j] = pixel.getR();
        greens[i][j] = pixel.getG();
        blues[i][j] = pixel.getB();
      }
    }

    return new double[][][]{reds, greens, blues};
  }


  private Pixel[][] recombineChannels(double[][] reds, double[][] greens, double[][] blues) {
    int width = reds.length;
    int height = reds[0].length;

    Pixel[][] pixels = new SimplePixel[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int red = (int) Math.min(255, Math.max(0, reds[i][j]));
        int green = (int) Math.min(255, Math.max(0, greens[i][j]));
        int blue = (int) Math.min(255, Math.max(0, blues[i][j]));
        pixels[i][j] = new SimplePixel(red, green, blue);
      }
    }
    return pixels;
  }


  /**
   * Compresses an image by applying lossy compression on its RGB channels.
   *
   * @param imageName         name of the original image in the map
   * @param modifiedImageName name to store the compressed image
   * @param percentage        compression percentage (must be b/w 1 to 100)
   * @throws IllegalArgumentException if compression percentage is invalid or imageName is not
   *                                  found
   */
  @Override
  public void compress(String imageName, String modifiedImageName, double percentage) {
    if (percentage <= 0 || percentage > 100) {
      throw new IllegalArgumentException("Compression percentage must be between 0 and 100.");
    }
    Image original = super.getMap().get(imageName);
    if (original == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }
    double[][][] channels = extractChannels(original);
    double[][] reds = channels[0];
    double[][] greens = channels[1];
    double[][] blues = channels[2];
    double[][][] compressedChannels = compressRGBChannels(original, reds, greens, blues,
        percentage);
    double[][] processedReds = compressedChannels[0];
    double[][] processedGreens = compressedChannels[1];
    double[][] processedBlues = compressedChannels[2];
    Pixel[][] compressedPixels = recombineChannels(processedReds, processedGreens, processedBlues);

    Image resultImage = new SimpleImage(compressedPixels);
    super.getMap().put(modifiedImageName, resultImage);
  }


  private double[][][] compressRGBChannels(Image original, double[][] reds, double[][] greens,
      double[][] blues, double percentage) {

    int width = original.getWidth();
    int height = original.getHeight();

    int paddedSize = padSize(Math.max(width, height));

    double[][] paddedReds = padChannel(reds, paddedSize);
    double[][] paddedGreens = padChannel(greens, paddedSize);
    double[][] paddedBlues = padChannel(blues, paddedSize);

    double compressionPercentage = percentage / 100;

    double[][] compressedReds = compressChannel(paddedReds, compressionPercentage);
    double[][] compressedGreens = compressChannel(paddedGreens, compressionPercentage);
    double[][] compressedBlues = compressChannel(paddedBlues, compressionPercentage);

    double[][] unpaddedReds = unpadChannel(compressedReds, width, height);
    double[][] unpaddedGreens = unpadChannel(compressedGreens, width, height);
    double[][] unpaddedBlues = unpadChannel(compressedBlues, width, height);

    return new double[][][]{unpaddedReds, unpaddedGreens, unpaddedBlues};
  }

  private int padSize(int size) {
    int paddedSize = 1;
    while (paddedSize < size) {
      paddedSize *= 2;
    }
    return paddedSize;
  }

  private double[][] padChannel(double[][] channel, int paddedSize) {
    double[][] paddedChannel = new double[paddedSize][paddedSize];
    for (int i = 0; i < channel.length; i++) {
      System.arraycopy(channel[i], 0, paddedChannel[i], 0, channel[i].length);
    }
    return paddedChannel;
  }

  private double[][] unpadChannel(double[][] paddedImage, int originalWidth, int originalHeight) {
    double[][] originalImage = new double[originalWidth][originalHeight];

    for (int i = 0; i < originalWidth; i++) {
      System.arraycopy(paddedImage[i], 0, originalImage[i], 0, originalHeight);
    }

    return originalImage;
  }


  private double[][] compressChannel(double[][] channel, double percentage) {
    int height = channel.length;
    int width = channel[0].length;

    double[][] transformedImage = haarTransform2D(channel, width);

    double[] uniqueValues = getUniqueValues(transformedImage);
    double threshold = findThreshold(uniqueValues, percentage);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (Math.abs(transformedImage[i][j]) < threshold) {
          transformedImage[i][j] = 0.0;
        }
      }
    }

    transformedImage = inverseHaarTransform2D(transformedImage, width);

    return transformedImage;
  }

  private double[][] haarTransform2D(double[][] x, int s) {
    int c = s;

    while (c > 1) {
      for (int i = 0; i < s; i++) {
        double[] row = new double[c];
        System.arraycopy(x[i], 0, row, 0, c);
        row = transformSequence1D(row);
        System.arraycopy(row, 0, x[i], 0, c);
      }

      for (int j = 0; j < s; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        column = transformSequence1D(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = column[i];
        }
      }

      c = c / 2;
    }

    return x;
  }

  private double[] transformSequence1D(double[] s) {
    List<Double> avg = new ArrayList<>();
    List<Double> diff = new ArrayList<>();

    for (int i = 0; i < s.length; i += 2) {
      double a = s[i];
      double b = s[i + 1];
      double av = (a + b) / Math.sqrt(2);
      double di = (a - b) / Math.sqrt(2);
      avg.add(av);
      diff.add(di);
    }

    List<Double> result = new ArrayList<>(avg);
    result.addAll(diff);

    return result.stream().mapToDouble(Double::doubleValue).toArray();
  }


  private double findThreshold(double[] values, double percentage) {
    int numToReset = (int) (values.length * percentage);
    if (numToReset < 1) {
      return 0.0;
    }

    Arrays.sort(values);

    return values[numToReset - 1];
  }

  private double[] getUniqueValues(double[][] image) {
    Set<Double> uniqueValues = new HashSet<>();

    for (double[] row : image) {
      for (double value : row) {
        if (value != 0.0) {
          uniqueValues.add(Math.abs(value));
        }
      }
    }
    return uniqueValues.stream().mapToDouble(Double::doubleValue).toArray();
  }

  private double[][] inverseHaarTransform2D(double[][] x, int s) {
    int c = 2;

    while (c <= s) {
      for (int j = 0; j < s; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        column = inverseTransform1D(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = column[i];
        }
      }

      for (int i = 0; i < s; i++) {
        double[] row = new double[c];
        System.arraycopy(x[i], 0, row, 0, c);
        row = inverseTransform1D(row);
        System.arraycopy(row, 0, x[i], 0, c);
      }

      c = c * 2;
    }

    return x;
  }

  private double[] inverseTransform1D(double[] s) {
    double[] avg = Arrays.copyOfRange(s, 0, s.length / 2);
    double[] diff = Arrays.copyOfRange(s, s.length / 2, s.length);

    List<Double> result = new ArrayList<>();
    for (int i = 0, j = 0; i < avg.length; i++, j++) {
      double a = avg[i];
      double b = diff[j];
      double av = (a + b) / Math.sqrt(2);
      double di = (a - b) / Math.sqrt(2);
      result.add(av);
      result.add(di);
    }

    return result.stream().mapToDouble(Double::doubleValue).toArray();
  }

  /**
   * Performs color correction on an image by  analyzing the frequency of color values and finding
   * the peaks of each color, then adjusting them based on their average peak value.
   *
   * @param imageName         name of the image to be color-corrected.
   * @param modifiedImageName name for the modified, color-corrected image.
   * @throws IllegalArgumentException if image with the given name is not found in map.
   */

  @Override
  public void colorCorrect(String imageName, String modifiedImageName) {
    Image image = super.getMap().get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    int[][] frequencies = analyzeColorLevels(image);
    int[] reds = frequencies[0];
    int[] greens = frequencies[1];
    int[] blues = frequencies[2];

    int redPeak = findPeak(reds);
    int greenPeak = findPeak(greens);
    int bluePeak = findPeak(blues);

    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    Image correctedImage = populateColorCorrectedPixels(redPeak, greenPeak, bluePeak, avgPeak,
        image);
    super.getMap().put(modifiedImageName, correctedImage);
  }

  private Image populateColorCorrectedPixels(int redPeak, int greenPeak,
      int bluePeak, int avgPeak, Image image) {
    Pixel[][] correctedPixels = new SimplePixel[image.getWidth()][image.getHeight()];
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Pixel pixel = image.getPixel(x, y);
        int correctedR = Math.min(255, Math.max(0, pixel.getR() + avgPeak - redPeak));
        int correctedG = Math.min(255, Math.max(0, pixel.getG() + avgPeak - greenPeak));
        int correctedB = Math.min(255, Math.max(0, pixel.getB() + avgPeak - bluePeak));

        correctedPixels[x][y] = new SimplePixel(correctedR, correctedG, correctedB);
      }
    }
    return new SimpleImage(correctedPixels);
  }

  private int findPeak(int[] histogram) {
    int peakValue = 0;
    int peakPosition = 0;
    for (int i = 10; i <= 245; i++) {
      if (histogram[i] > peakValue) {
        peakValue = histogram[i];
        peakPosition = i;
      }
    }

    return peakPosition;
  }


  /**
   * Generates a histogram representation of an image by constructing a graphical histogram as
   * 255x255 pixel image.
   *
   * @param imageName         name of the image for which the histogram will be generated.
   * @param modifiedImageName name to assign to the generated histogram image.
   * @throws IllegalArgumentException if image with the given name is not found in map.
   */

  @Override
  public void histogram(String imageName, String modifiedImageName) {
    Image sourceImage = super.getMap().get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    BufferedImage histogramGraph = constructHistogram();
    BufferedImage histogram = buildHistogramImage(sourceImage, histogramGraph);

    Image histogramImage = convertBufferedImageToImage(histogram);
    super.getMap().put(modifiedImageName, histogramImage);
  }

  private BufferedImage constructHistogram() {
    BufferedImage histogramGraph;
    histogramGraph = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    Graphics2D canvas = histogramGraph.createGraphics();
    canvas.setColor(new Color(255, 255, 255));
    canvas.fillRect(0, 0, histogramGraph.getWidth(), histogramGraph.getHeight());

    canvas.setColor(new Color(200, 200, 200));
    for (int step = 0; step < histogramGraph.getWidth(); step += 10) {
      canvas.drawLine(step, 0, step, histogramGraph.getHeight());
      canvas.drawLine(0, step, histogramGraph.getWidth(), step);
    }
    canvas.dispose();
    return histogramGraph;
  }

  private static int computeMaxFrequency(int[]... colorChannels) {
    int maxFrequency = 0;
    for (int[] channel : colorChannels) {
      for (int intensity : channel) {
        if (intensity > maxFrequency) {
          maxFrequency = intensity;
        }
      }
    }
    return maxFrequency;
  }

  private BufferedImage buildHistogramImage(Image sourceImg, BufferedImage histogramGraph) {
    int[][] colorLevels = analyzeColorLevels(sourceImg);
    int[] reds = colorLevels[0];
    int[] greens = colorLevels[1];
    int[] blues = colorLevels[2];

    int highestFreq = computeMaxFrequency(reds, greens, blues);

    Graphics2D drawArea = histogramGraph.createGraphics();
    renderChannel(drawArea, reds, Color.RED, highestFreq, histogramGraph);
    renderChannel(drawArea, greens, Color.GREEN, highestFreq, histogramGraph);
    renderChannel(drawArea, blues, Color.BLUE, highestFreq, histogramGraph);
    drawArea.dispose();

    return histogramGraph;
  }

  private void renderChannel(Graphics2D graphics, int[] colorData, Color channelColor, int peakFreq,
      BufferedImage histogramGraph) {
    graphics.setColor(channelColor);
    int priorY = histogramGraph.getHeight();
    for (int x = 0; x < colorData.length; x++) {
      int yPoint =
          histogramGraph.getHeight() - (colorData[x] * histogramGraph.getHeight() / peakFreq);
      graphics.drawLine(priorY, x, yPoint, x);
      priorY = yPoint;
    }
  }


  /**
   * To store frequencies from 0 to 255.
   *
   * @param inputImage input image from user
   * @return array of frequencies
   */
  public static int[][] analyzeColorLevels(Image inputImage) {
    int[] redLevels = new int[256];
    int[] greenLevels = new int[256];
    int[] blueLevels = new int[256];

    for (int widthIdx = 0; widthIdx < inputImage.getWidth(); widthIdx++) {
      for (int heightIdx = 0; heightIdx < inputImage.getHeight(); heightIdx++) {
        Pixel pixel = inputImage.getPixel(widthIdx, heightIdx);
        redLevels[pixel.getR()]++;
        greenLevels[pixel.getG()]++;
        blueLevels[pixel.getB()]++;
      }
    }
    return new int[][]{redLevels, greenLevels, blueLevels};
  }

  private Image convertBufferedImageToImage(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Pixel[][] pixels = new SimplePixel[height][width];

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        int rgb = bufferedImage.getRGB(y, x);
        Color color = new Color(rgb);
        pixels[x][y] = new SimplePixel(color.getRed(), color.getGreen(), color.getBlue());
      }
    }

    return new SimpleImage(pixels);
  }


  /**
   * processes an image by reading its pixel data and dimesnions from a file and creating
   * BufferedImage representation of it.
   *
   * @param imageName The name of the image to be processed.
   * @return Image of desired pixels
   * @throws IOException if image formst is not recognized
   */
  public BufferedImage processImage(String imageName) throws IOException {
    OutputStream outputStream = save(imageName);

    String data = outputStream.toString();
    Scanner sc = new Scanner(data);

    int width = sc.nextInt();
    int height = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = image.getRaster();

    int[] pixel = new int[3]; // RGB components
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixel[0] = sc.nextInt();   // red component
        pixel[1] = sc.nextInt();   // green component
        pixel[2] = sc.nextInt();   // blue component
        raster.setPixel(x, y, pixel);
      }
    }

    return image;
  }

  @Override
  public void blurWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    blur(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void sharpenWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    sharpen(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void sepiaWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    sepia(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void lumaWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    lumaComponent(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void intensityWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    intensityComponent(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void valueWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    valueComponent(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void colorCorrectWithSplit(String imageName, String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    colorCorrect(imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }

  @Override
  public void levelAdjustWithSplit(int black, int mid, int white, String imageName,
      String modifiedImageName, int value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("Value must be between 0 and 100");
    }
    Image image = getImage(imageName);
    String intermediateImageName = "intermediateImageName";
    levelsAdjust(black, mid, white, imageName, intermediateImageName);
    Image intermediateImage = getImage(intermediateImageName);
    Image splitImage = split(image, intermediateImage, value);
    imageMap.put(modifiedImageName, splitImage);
    imageMap.remove(intermediateImageName);
  }


  /**
   * Generates a split view of image while trying operations. splits image vertically and shoes the
   * operated on the left and originial on right based on split percentage.
   *
   * @param current         The current image to be split.
   * @param filtered        The filtered image to be split.
   * @param widthPercentage The percentage of the width to be applied during the split.
   * @return Split image
   */
  public Image split(Image current, Image filtered, float widthPercentage) {
    int percentageWidth = (int) (current.getWidth() * (widthPercentage / 100));
    Pixel[][] splitImagePixels = new Pixel[current.getWidth()][current.getHeight()];
    for (int i = 0; i < current.getWidth(); i++) {
      for (int j = 0; j < current.getHeight(); j++) {
        if (i <= percentageWidth) {
          splitImagePixels[i][j] = new SimplePixel(filtered.getPixel(i, j).getR(),
              filtered.getPixel(i, j).getG(),
              filtered.getPixel(i, j).getB());
        } else {
          splitImagePixels[i][j] = new SimplePixel(current.getPixel(i, j).getR(),
              current.getPixel(i, j).getG(),
              current.getPixel(i, j).getB());
        }
      }
    }
    return new SimpleImage(splitImagePixels);
  }


}


