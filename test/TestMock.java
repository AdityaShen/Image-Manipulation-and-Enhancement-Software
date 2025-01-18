package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import src.controller.ScriptController;
import src.controller.SimpleScriptController;
import src.model.ExtendedImageHandlerAdapter;
import src.model.ExtendedMock;
import src.view.SimpleView;
import src.view.View;

/**
 * Test class for verifying the functionality of the Controller with respect to the
 * MockImageHandlerAdapter and the View components. This class contains setup and test methods for
 * simulating user inputs and processing them through the controller.
 */

public class TestMock {

  private ExtendedImageHandlerAdapter mockHandler;
  ScriptController controller;
  private StringBuilder log;
  private View view;

  @Before
  public void setUp() {
    log = new StringBuilder();
    view = new SimpleView();
    mockHandler = new ExtendedMock(log);
  }

  @Test
  public void testGreenComponent() {
    String input = "green-component photo green-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo green-photo\n", log.toString());
  }

  @Test
  public void testColorCorrect() {
    String input = "color-correct photo corrected-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo corrected-photo\n", log.toString());
  }

  @Test
  public void testCompress() {
    String input = "compress photo compressed-photo 0.75";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo compressed-photo 0.75\n", log.toString());
  }

  @Test
  public void testHistogram() {
    String input = "histogram photo histogram-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo histogram-photo\n", log.toString());
  }

  @Test
  public void testLevelsAdjust() {
    String input = "levels-adjust 50 100 200 photo adjusted-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: 50 100 200 photo adjusted-photo\n", log.toString());
  }

  @Test
  public void testLoadImagePixels() {
    String input = "load test-images/Photohi.jpg photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo\n", log.toString());
  }

  @Test
  public void testSave() {
    String input = "save photo saved-photo-path";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo saved-photo-path\n", log.toString());
  }

  @Test
  public void testRedComponent() {
    String input = "red-component photo red-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo red-photo\n", log.toString());
  }

  @Test
  public void testValueComponent() {
    String input = "value-component photo value-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo value-photo\n", log.toString());
  }

  @Test
  public void testIntensityComponent() {
    String input = "intensity-component photo intensity-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo intensity-photo\n", log.toString());
  }

  @Test
  public void testLumaComponent() {
    String input = "luma-component photo luma-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo luma-photo\n", log.toString());
  }

  @Test
  public void testVerticalFlip() {
    String input = "vertical-flip photo flipped-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo flipped-photo\n", log.toString());
  }

  @Test
  public void testHorizontalFlip() {
    String input = "horizontal-flip photo flipped-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo flipped-photo\n", log.toString());
  }

  @Test
  public void testSepia() {
    String input = "sepia photo sepia-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo sepia-photo\n", log.toString());
  }

  @Test
  public void testBrighten() {
    String input = "brighten 30 photo brightened-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo brightened-photo\n", log.toString());
  }

  @Test
  public void testDarken() {
    String input = "brighten -30 photo brightened-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo brightened-photo\n", log.toString());
  }

  @Test
  public void testBlur() {
    String input = "blur photo blurred-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo blurred-photo\n", log.toString());
  }

  @Test
  public void testSharpen() {
    String input = "sharpen photo sharpened-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo sharpened-photo\n", log.toString());
  }

  @Test
  public void testRgbSplit() {
    String input = "rgb-split photo red-photo green-photo blue-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo red-photo green-photo blue-photo\n", log.toString());
  }

  @Test
  public void testRgbCombine() {
    String input = "rgb-combine combined-photo red-photo green-photo blue-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: combined-photo red-photo green-photo blue-photo\n", log.toString());
  }

  @Test
  public void testDownscale() {
    String input = "downscale photo downscaled-photo 300 300";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo downscaled-photo 300 300\n", log.toString());
  }

  @Test
  public void testMask() {
    String input = "sepia photo photo-masked photo-sepia-masked";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
    assertEquals("Input: photo photo-masked photo-sepia-masked\n", log.toString());
  }

  @Test(expected = Exception.class)
  public void testInvalidCommand() {
    String input = "invalid combined-photo red-photo green-photo blue-photo";
    controller = SimpleScriptController.createKeyboard(input, mockHandler, view);
    controller.processInput();
  }


}
