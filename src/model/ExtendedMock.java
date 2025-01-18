package src.model;

/**
 * This class adds to the previous functionalities in the MockImageHandlerAdapter and is used
 * primarily for testing purpose.
 */
public class ExtendedMock extends MockImageHandlerAdapter implements ExtendedImageHandlerAdapter {

  private StringBuilder log;

  /**
   * Constructs the mock model.
   *
   * @param log log to store inputs sent from controller
   */
  public ExtendedMock(StringBuilder log) {
    super(log);

    this.log = log;
  }

  @Override
  public void downscale(String imageName, String modifiedImageName, int newWidth, int newHeight) {
    log.append(
        "Input: " + imageName + " " + modifiedImageName + " " + newWidth + " " + newHeight + "\n");
  }

  @Override
  public void blurWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void sharpenWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void redComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void greenComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void blueComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void sepiaWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void lumaWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void intensityWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }

  @Override
  public void valueWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    log.append("Input: " + imageName + " " + maskedImageName + " " + modifiedImageName + "\n");
  }
}
