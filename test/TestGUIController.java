package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import src.controller.GUIController;
import src.controller.Operations;
import src.model.ExtendedImageHandlerAdapter;
import src.model.ExtendedMock;
import src.view.GUIInterface;
import src.view.MockGUI;

/**
 * Test class for the GUIController clsubass. It simulates user interactions with the GUI and
 * verifies that the controller correctly handles operations.
 */
public class TestGUIController {

  Operations controller;
  private StringBuilder log;

  @Before
  public void setUp() {
    log = new StringBuilder();
    GUIInterface gui = new MockGUI(log);
    ExtendedImageHandlerAdapter mockHandler = new ExtendedMock(log);
    controller = new GUIController(mockHandler, gui);
  }

  @Test
  public void testGreenComponent() throws IOException {
    controller.greenComponent();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testBlueComponent() throws IOException {
    controller.blueComponent();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testRedComponent() throws IOException {
    controller.redComponent();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testLumaComponent() throws IOException {
    controller.luma();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testIntensityComponent() throws IOException {
    controller.intensity();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testValueComponent() throws IOException {
    controller.value();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testBlur() throws IOException {
    controller.blur();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    controller.sharpen();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testSepia() throws IOException {
    controller.sepia();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testRGBCombine() throws IOException {
    controller.rgbCombine("redPath", "greenPath", "bluePath");
    assertEquals("Input: current-image red-current-image green-current-image "
        + "blue-current-image\n", log.toString());
  }

  @Test
  public void testSplit() throws IOException {
    controller.split("blur", new String[]{"split", "50"});
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testLoad() throws IOException {
    controller.load("test-filepath");
    assertEquals("Input: test-filepath\n", log.toString());
  }

  @Test
  public void testCompress() throws IOException {
    controller.compress(50);
    assertEquals("Input: current-image current-image 50\n", log.toString());
  }

  @Test
  public void testDownscale() throws IOException {
    controller.downscale(100, 100);
    assertEquals("Input: current-image current-image 100 100\n", log.toString());
  }

  @Test
  public void testLevelsAdjust() throws IOException {
    controller.levelAdjust(0, 128, 255);
    assertEquals("Input: 0 128 255 current-image current-image\n", log.toString());
  }

  @Test
  public void testColorCorrect() throws IOException {
    controller.colorCorrect();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testSave() throws IOException {
    controller.save("test-filepath");
    assertEquals("Input: test-filepath\n", log.toString());
  }

  @Test
  public void testBrighten() throws IOException {
    controller.brighten(50);
    assertEquals("Input: 50 current-image current-image\n", log.toString());
  }

  @Test
  public void testVerticalFlip() throws IOException {
    controller.verticalFlip();
    assertEquals("Input: current-image current-image\n", log.toString());
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    controller.horizontalFlip();
    assertEquals("Input: current-image current-image\n", log.toString());
  }


  @Test
  public void testRgbSplit() throws IOException {
    controller.rgbSplit("red-path", "green-path", "blue-path");
    assertEquals("Input: current-image current-image-red current-image-green "
        + "current-image-blue\n", log.toString());
  }

  @Test
  public void testReloadImage() throws IOException {
    controller.reloadImage();
    assertEquals("Input: current-image histogram\n", log.toString());
  }

}
