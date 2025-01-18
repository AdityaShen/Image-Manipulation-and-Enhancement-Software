package src.view;

import java.awt.image.BufferedImage;
import src.controller.Operations;


/**
 * mock GUI to test controller.
 */
public class MockGUI implements GUIInterface {

  private final StringBuilder log;

  /**
   * Constructs the mock GUI.
   *
   * @param log log to store inputs sent from the controller
   */
  public MockGUI(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void refreshGUI(String imageName, String histogram, BufferedImage img,
      BufferedImage histogramImage) {
    log.append("Input: " + imageName + " " + histogram + "\n");
  }

  @Override
  public void splitView(BufferedImage image, String splitImageName) {
    log.append("Input: " + splitImageName + "\n");
  }

  @Override
  public void addOperations(Operations operations) {
    log.append("Input: Operations added\n");
  }

  @Override
  public void displayError(String error) {
    log.append("Error: " + error + "\n");
  }
}
