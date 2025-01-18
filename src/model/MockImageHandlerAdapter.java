package src.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Mock model to test the controller.
 */
public class MockImageHandlerAdapter implements ImageHandlerAdapter {

  private StringBuilder log;

  /**
   * Constructs the mock model.
   *
   * @param log log to store inputs sent from controller
   */
  public MockImageHandlerAdapter(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void levelsAdjust(int black, int mid, int white, String imageName,
      String modifiedImageName) {
    log.append("Input: " + black + " " + mid + " " + white + " " + imageName + " " +
        modifiedImageName + "\n");
  }

  @Override
  public void colorCorrect(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }


  @Override
  public void compress(String imageName, String modifiedImageName, double percentage) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + percentage + "\n");
  }

  @Override
  public void histogram(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public BufferedImage processImage(String imageName) throws IOException {
    log.append("Input: " + imageName + "\n");
    return null;
  }

  @Override
  public void blurWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void sharpenWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void sepiaWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void lumaWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void intensityWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void valueWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void colorCorrectWithSplit(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + " " + value + "\n");
  }

  @Override
  public void levelAdjustWithSplit(int black, int mid, int white, String imageName,
      String modifiedImageName, int value) {
    log.append("Input: " + black + " " + mid + " " + white + " " + imageName + " " +
        modifiedImageName + " " + value + "\n");
  }

  @Override
  public Image split(Image current, Image filtered, float widthPercentage) {
    return null;
  }

  @Override
  public void loadImagePixels(BufferedImage image, String imageName) throws IOException {
    log.append("Input: " + imageName + "\n");
  }

  @Override
  public void loadImagePixelsFromPPM(BufferedReader reader, String imageName) throws IOException {
    log.append("Input: " + imageName + "\n");
  }

  @Override
  public OutputStream save(String imageName) {
    log.append("Input: " + imageName + "\n");
    return null;
  }

  @Override
  public void blueComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void greenComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void redComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void valueComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void intensityComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void lumaComponent(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void verticalFlip(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void horizontalFlip(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void sepia(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void brighten(String imageName, String modifiedImageName, int value) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void blur(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void sharpen(String imageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void rgbSplit(String imageName, String redResult, String greenResult, String blueResult) {
    log.append(
        "Input: " + imageName + " " + redResult + " " + greenResult + " " + blueResult + "\n");
  }

  @Override
  public void rgbCombine(String modifiedImageName, String redImageName, String greenImageName,
      String blueImageName) {
    log.append("Input: " + modifiedImageName + " " + redImageName + " " + greenImageName + " " +
        blueImageName + "\n");
  }
}
