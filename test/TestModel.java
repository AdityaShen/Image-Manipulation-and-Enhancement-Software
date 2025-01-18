package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;
import src.model.ExtendedImageHandlerAdapter;
import src.model.Image;
import src.model.ImageHandler;
import src.model.ImageHandlerAdapter;
import src.model.Pixel;
import src.model.SimpleExtendedImageHandlerAdapter;
import src.model.SimpleImage;
import src.model.SimpleImageHandler;
import src.model.SimpleImageHandlerAdapter;
import src.model.SimplePixel;

/**
 * Test class to validate the functionality of the model components. Ensures that images are
 * correctly loaded, manipulated, and transformed. Tests the implementation of various methods like
 * blur, sharpen, sepia,etc.
 */
public class TestModel {

  @Test
  public void testLoad() throws IOException {
    ImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);

    Image loadedImage = ((SimpleImageHandler) handler).getMap().get(imgName);

    assertNotNull("Image should be loaded into the map.", loadedImage);

    Pixel[][] pixels = loadedImage.getImage();

    assertNotNull("Pixel array should not be null.", pixels);

    assertTrue("Image width should be greater than 0.", pixels.length > 0);
    assertTrue("Image height should be greater than 0.", pixels[0].length > 0);
  }

  @Test(expected = Exception.class)
  public void testLoadInvalidImage() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "invalid-image-path.jpg";
    String imgName = "invalid-image";
    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);
  }

  @Test
  public void testBlueComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "blue-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);

    handler.blueComponent(imgName, newImgName);

    Image blueImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull("Transformed image should be present in the map.", blueImage);

    Pixel[][] pixels = blueImage.getImage();
    assertNotNull("Pixel array should not be null.", pixels);

    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        assertEquals("Red component should be 0.", 0, pixel.getR());
        assertEquals("Green component should be 0.", 0, pixel.getG());
      }
    }
  }

  @Test
  public void testSaveAsJpgAfterOperation() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "blue-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.blueComponent(imgName, newImgName);

    Image blueImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull("Transformed image should be present in the map.", blueImage);

    handler.save(newImgName);

    File savedFile = new File("out/" + newImgName + ".jpg");
    assertTrue("Saved file should not be empty.", savedFile.length() > 0);
  }

  @Test
  public void testGreenComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String newImgName = "green-photo";
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);
    handler.greenComponent(imgName, newImgName);
    Image greenImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull("Transformed image should be present in the map.", greenImage);
    Pixel[][] pixels = greenImage.getImage();
    assertNotNull("Pixel array should not be null.", pixels);

    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        assertEquals("Red component should be 0.", 0, pixel.getR());
        assertEquals("Blue component should be 0.", 0, pixel.getB());
      }
    }
  }

  @Test
  public void testRedComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "red-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.redComponent(imgName, newImgName);

    Image redImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull("Transformed image should be present in the map.", redImage);

    Pixel[][] pixels = redImage.getImage();
    assertNotNull("Pixel array should not be null.", pixels);

    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        assertEquals("Green component should be 0.", 0, pixel.getG());
        assertEquals("Blue component should be 0.", 0, pixel.getB());
      }
    }
  }


  @Test
  public void testValueComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "value-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);

    handler.valueComponent(imgName, newImgName);

    Image valueImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull("Transformed image should be present in the map.", valueImage);

    Pixel[][] pixels = valueImage.getImage();
    assertNotNull("Pixel array should not be null.", pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);
    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel transformedPixel = pixels[x][y];

        int expectedValue = Math.max(
            originalPixel.getR(),
            Math.max(originalPixel.getG(), originalPixel.getB())
        );

        assertEquals("Red component should match expected value.", expectedValue,
            transformedPixel.getR());
        assertEquals("Green component should match expected value.", expectedValue,
            transformedPixel.getG());
        assertEquals("Blue component should match expected value.", expectedValue,
            transformedPixel.getB());
      }
    }
  }

  @Test
  public void testIntensityComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "intensity-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.intensityComponent(imgName, newImgName);

    Image intensityImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(intensityImage);

    Pixel[][] pixels = intensityImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel transformedPixel = pixels[x][y];

        int expectedIntensity =
            (originalPixel.getR() + originalPixel.getG() + originalPixel.getB()) / 3;

        assertEquals(expectedIntensity, transformedPixel.getR());
        assertEquals(expectedIntensity, transformedPixel.getG());
        assertEquals(expectedIntensity, transformedPixel.getB());
      }
    }
  }


  @Test
  public void testLumaComponent() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "luma-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.lumaComponent(imgName, newImgName);

    Image lumaImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(lumaImage);

    Pixel[][] pixels = lumaImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel transformedPixel = pixels[x][y];

        int luma = (int) (0.299 * originalPixel.getR() + 0.587 * originalPixel.getG()
            + 0.114 * originalPixel.getB());

        assertEquals(luma, transformedPixel.getR());
        assertEquals(luma, transformedPixel.getG());
        assertEquals(luma, transformedPixel.getB());
      }
    }
  }

  @Test
  public void testVerticalFlip() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "vertical-flip-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.verticalFlip(imgName, newImgName);

    Image flippedImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(flippedImage);

    Pixel[][] pixels = flippedImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel flippedPixel = pixels[x][originalImage.getHeight() - 1 - y];

        assertEquals(originalPixel.getR(), flippedPixel.getR());
        assertEquals(originalPixel.getG(), flippedPixel.getG());
        assertEquals(originalPixel.getB(), flippedPixel.getB());
      }
    }
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "horizontal-flip-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.horizontalFlip(imgName, newImgName);

    Image flippedImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(flippedImage);

    Pixel[][] pixels = flippedImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel flippedPixel = pixels[originalImage.getWidth() - 1 - x][y];

        assertEquals(originalPixel.getR(), flippedPixel.getR());
        assertEquals(originalPixel.getG(), flippedPixel.getG());
        assertEquals(originalPixel.getB(), flippedPixel.getB());
      }
    }
  }


  @Test
  public void testSepia() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "sepia-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.sepia(imgName, newImgName);

    Image sepiaImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(sepiaImage);

    Pixel[][] pixels = sepiaImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        int r = (int) (originalPixel.getR() * 0.393 + originalPixel.getG() * 0.769
            + originalPixel.getB() * 0.189);
        int g = (int) (originalPixel.getR() * 0.349 + originalPixel.getG() * 0.686
            + originalPixel.getB() * 0.168);
        int b = (int) (originalPixel.getR() * 0.272 + originalPixel.getG() * 0.534
            + originalPixel.getB() * 0.131);

        assertEquals(Math.min(r, 255), pixels[x][y].getR());
        assertEquals(Math.min(g, 255), pixels[x][y].getG());
        assertEquals(Math.min(b, 255), pixels[x][y].getB());
      }
    }
  }

  @Test
  public void testBrighten() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "brightened-photo";
    int brightenValue = 50;

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.brighten(imgName, newImgName, brightenValue);

    Image brightenedImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(brightenedImage);

    Pixel[][] pixels = brightenedImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        int r = Math.min(originalPixel.getR() + brightenValue, 255);
        int g = Math.min(originalPixel.getG() + brightenValue, 255);
        int b = Math.min(originalPixel.getB() + brightenValue, 255);

        assertEquals(r, pixels[x][y].getR());
        assertEquals(g, pixels[x][y].getG());
        assertEquals(b, pixels[x][y].getB());
      }
    }
  }

  @Test
  public void testBlur() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "blurred-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.blur(imgName, newImgName);

    Image blurredImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(blurredImage);

    Pixel[][] blurredPixels = blurredImage.getImage();
    assertNotNull(blurredPixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int neighborX = Math.min(Math.max(x + i, 0), originalImage.getWidth() - 1);
            int neighborY = Math.min(Math.max(y + j, 0), originalImage.getHeight() - 1);
            Pixel neighborPixel = originalImage.getPixel(neighborX, neighborY);

            redSum += (1.0 / 9.0) * neighborPixel.getR();
            greenSum += (1.0 / 9.0) * neighborPixel.getG();
            blueSum += (1.0 / 9.0) * neighborPixel.getB();
          }
        }

        int expectedRed = (int) Math.min(Math.max(redSum, 0), 255);
        int expectedGreen = (int) Math.min(Math.max(greenSum, 0), 255);
        int expectedBlue = (int) Math.min(Math.max(blueSum, 0), 255);

        assertEquals(expectedRed, blurredPixels[x][y].getR());
        assertEquals(expectedGreen, blurredPixels[x][y].getG());
        assertEquals(expectedBlue, blurredPixels[x][y].getB());
      }
    }
  }

  @Test
  public void testSharpen() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "sharpened-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.sharpen(imgName, newImgName);

    Image sharpenedImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(sharpenedImage);

    Pixel[][] sharpenedPixels = sharpenedImage.getImage();
    assertNotNull(sharpenedPixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        int[][] sharpenKernel = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
        };

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int kernelValue = sharpenKernel[i + 1][j + 1];

            int neighborX = Math.min(Math.max(x + i, 0), originalImage.getWidth() - 1);
            int neighborY = Math.min(Math.max(y + j, 0), originalImage.getHeight() - 1);
            Pixel neighborPixel = originalImage.getPixel(neighborX, neighborY);

            redSum += kernelValue * neighborPixel.getR();
            greenSum += kernelValue * neighborPixel.getG();
            blueSum += kernelValue * neighborPixel.getB();
          }
        }

        int expectedRed = Math.min(Math.max(redSum, 0), 255);
        int expectedGreen = Math.min(Math.max(greenSum, 0), 255);
        int expectedBlue = Math.min(Math.max(blueSum, 0), 255);

        assertEquals(expectedRed, sharpenedPixels[x][y].getR());
        assertEquals(expectedGreen, sharpenedPixels[x][y].getG());
        assertEquals(expectedBlue, sharpenedPixels[x][y].getB());
      }
    }
  }

  @Test
  public void testRgbSplit() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String redResult = "red-photo";
    String greenResult = "green-photo";
    String blueResult = "blue-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);
    handler.rgbSplit(imgName, redResult, greenResult, blueResult);

    Image redImage = ((SimpleImageHandler) handler).getImage(redResult);
    Image greenImage = ((SimpleImageHandler) handler).getImage(greenResult);
    Image blueImage = ((SimpleImageHandler) handler).getImage(blueResult);

    assertNotNull(redImage);
    assertNotNull(greenImage);
    assertNotNull(blueImage);

    Pixel[][] redPixels = redImage.getImage();
    Pixel[][] greenPixels = greenImage.getImage();
    Pixel[][] bluePixels = blueImage.getImage();

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < redImage.getWidth(); x++) {
      for (int y = 0; y < redImage.getHeight(); y++) {
        Pixel originalPixel = redImage.getPixel(x, y);

        assertEquals(originalPixel.getR(), redPixels[x][y].getR());
        assertEquals(0, redPixels[x][y].getG());
        assertEquals(0, redPixels[x][y].getB());
      }
    }

    for (int x = 0; x < greenImage.getWidth(); x++) {
      for (int y = 0; y < greenImage.getHeight(); y++) {
        Pixel originalPixel = greenImage.getPixel(x, y);

        assertEquals(0, greenPixels[x][y].getR());
        assertEquals(originalPixel.getG(), greenPixels[x][y].getG());
        assertEquals(0, greenPixels[x][y].getB());
      }
    }

    for (int x = 0; x < blueImage.getWidth(); x++) {
      for (int y = 0; y < blueImage.getHeight(); y++) {
        Pixel originalPixel = blueImage.getPixel(x, y);

        assertEquals(0, bluePixels[x][y].getR());
        assertEquals(0, bluePixels[x][y].getG());
        assertEquals(originalPixel.getB(), bluePixels[x][y].getB());
      }
    }
  }

  @Test
  public void testRgbCombine() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String redImageName = "red-photo";
    String greenImageName = "green-photo";
    String blueImageName = "blue-photo";
    String combinedImageName = "combined-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);

    handler.rgbSplit(imgName, redImageName, greenImageName, blueImageName);
    handler.rgbCombine(combinedImageName, redImageName, greenImageName, blueImageName);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);
    Image combinedImage = ((SimpleImageHandler) handler).getImage(combinedImageName);

    assertNotNull(originalImage);
    assertNotNull(combinedImage);

    assertEquals(originalImage.getWidth(), combinedImage.getWidth());
    assertEquals(originalImage.getHeight(), combinedImage.getHeight());

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        Pixel combinedPixel = combinedImage.getPixel(x, y);

        assertEquals(originalPixel.getR(), combinedPixel.getR());
        assertEquals(originalPixel.getG(), combinedPixel.getG());
        assertEquals(originalPixel.getB(), combinedPixel.getB());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenInvalidNegativeValue() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    BufferedImage image = ImageIO.read(new File(imgPath));

    handler.loadImagePixels(image, imgName);

    handler.brighten(imgName, "bright-photo", -256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenInvalidPositiveValue() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);

    handler.brighten(imgName, "bright-photo", 256);
  }

  @Test
  public void testRedThenGreenComponent() throws IOException {
    String imgPath = "test-images/Photohi.jpg";
    ImageHandler handler = new SimpleImageHandler();
    String imgName = "photo";
    String redImgName = "red-photo";
    String greenRedImgName = "green-on-red-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);
    handler.redComponent(imgName, redImgName);
    Image redImage = ((SimpleImageHandler) handler).getImage(redImgName);
    assertNotNull(redImage);

    for (Pixel[] row : redImage.getImage()) {
      for (Pixel pixel : row) {
        assertEquals(0, pixel.getG());
        assertEquals(0, pixel.getB());
      }
    }

    handler.greenComponent(redImgName, greenRedImgName);
    Image greenRedImage = ((SimpleImageHandler) handler).getImage(greenRedImgName);
    assertNotNull(greenRedImage);

    for (Pixel[] row : greenRedImage.getImage()) {
      for (Pixel pixel : row) {
        assertEquals(0, pixel.getR());
        assertEquals(0, pixel.getB());
      }
    }
  }


  @Test
  public void testApplyGreenComponentThenBrighten() throws IOException {
    String imgPath = "test-images/Photohi.jpg";
    ImageHandler handler = new SimpleImageHandler();
    String imgName = "photo";
    String greenImgName = "green-photo";
    String greenBrightenedImgName = "green-brightened-photo";
    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);
    handler.greenComponent(imgName, greenImgName);
    handler.brighten(greenImgName, greenBrightenedImgName, 50);

    Image greenBrightenedImage = ((SimpleImageHandler) handler).getImage(greenBrightenedImgName);
    assertNotNull(greenBrightenedImage);

    Image greenImage = ((SimpleImageHandler) handler).getImage(greenImgName);
    int brightenValue = 50;
    Pixel[][] pixels = greenBrightenedImage.getImage();
    assertNotNull(pixels);

    for (int x = 0; x < greenImage.getWidth(); x++) {
      for (int y = 0; y < greenImage.getHeight(); y++) {
        Pixel originalPixel = greenImage.getPixel(x, y);
        int r = Math.min(originalPixel.getR() + brightenValue, 255);
        int g = Math.min(originalPixel.getG() + brightenValue, 255);
        int b = Math.min(originalPixel.getB() + brightenValue, 255);

        assertEquals(r, pixels[x][y].getR());
        assertEquals(g, pixels[x][y].getG());
        assertEquals(b, pixels[x][y].getB());
      }
    }

  }

  @Test
  public void testDarken() throws IOException {
    ImageHandler handler = new SimpleImageHandler();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String newImgName = "darkened-photo";
    int brightenValue = -50;

    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);
    handler.brighten(imgName, newImgName, brightenValue);

    Image darkenedImage = ((SimpleImageHandler) handler).getImage(newImgName);
    assertNotNull(darkenedImage);

    Pixel[][] pixels = darkenedImage.getImage();
    assertNotNull(pixels);

    Image originalImage = ((SimpleImageHandler) handler).getImage(imgName);

    for (int x = 0; x < originalImage.getWidth(); x++) {
      for (int y = 0; y < originalImage.getHeight(); y++) {
        Pixel originalPixel = originalImage.getPixel(x, y);
        int r = Math.max(originalPixel.getR() + brightenValue, 0);
        int g = Math.max(originalPixel.getG() + brightenValue, 0);
        int b = Math.max(originalPixel.getB() + brightenValue, 0);

        assertEquals(r, pixels[x][y].getR());
        assertEquals(g, pixels[x][y].getG());
        assertEquals(b, pixels[x][y].getB());
      }
    }
  }

  @Test
  public void testHistogramCorrectness() {
    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String imageName = "testImage";
    String histogramName = "histogramImage";

    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new SimplePixel(255, 0, 0);
    pixels[0][1] = new SimplePixel(0, 255, 0);
    pixels[1][0] = new SimplePixel(0, 0, 255);
    pixels[1][1] = new SimplePixel(255, 255, 0);
    Image testImage = new SimpleImage(pixels);
    handler.getMap().put(imageName, testImage);

    handler.histogram(imageName, histogramName);
    Image histogramImage = handler.getMap().get(histogramName);

    assertNotNull("Histogram image should not be null", histogramImage);

    int[][] colorLevels = SimpleImageHandlerAdapter.analyzeColorLevels(testImage);

    assertEquals(2, colorLevels[0][255]);
    assertEquals(2, colorLevels[1][255]);
    assertEquals(1, colorLevels[2][255]);

  }


  @Test
  public void testInvalidHistogram() {
    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String invalidImageName = "nonExistentImage";
    String histogramName = "invalidHistogramImage";
    assertThrows(IllegalArgumentException.class,
        () -> handler.histogram(invalidImageName, histogramName));

    String validImageName = "validImage";
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new SimplePixel(100, 150, 200);
    pixels[0][1] = new SimplePixel(50, 100, 150);
    pixels[1][0] = new SimplePixel(25, 75, 125);
    pixels[1][1] = new SimplePixel(200, 50, 100);
    Image testImage = new SimpleImage(pixels);
    handler.getMap().put(validImageName, testImage);

    handler.histogram(validImageName, histogramName);
    Image generatedHistogram = handler.getMap().get(histogramName);
    assertNotNull("Generated histogram image should not be null", generatedHistogram);

    int[][] expectedColorLevels = SimpleImageHandlerAdapter.analyzeColorLevels(testImage);

    expectedColorLevels[0][100] += 1;
    expectedColorLevels[1][150] -= 1;
    expectedColorLevels[2][200] += 2;

    int[][] actualColorLevels = SimpleImageHandlerAdapter.analyzeColorLevels(testImage);
    assertNotEquals("Expected altered red level should not match actual level",
        expectedColorLevels[0][100], actualColorLevels[0][100]);
    assertNotEquals("Expected altered green level should not match actual level",
        expectedColorLevels[1][150], actualColorLevels[1][150]);
    assertNotEquals("Expected altered blue level should not match actual level",
        expectedColorLevels[2][200], actualColorLevels[2][200]);
  }


  @Test
  public void testColorCorrectCorrectValid() {
    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String imageName = "testImage";
    String colorCorrectName = "colorCorrectImage";

    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new SimplePixel(255, 0, 255);
    pixels[0][1] = new SimplePixel(0, 255, 0);
    pixels[1][0] = new SimplePixel(0, 0, 255);
    pixels[1][1] = new SimplePixel(255, 255, 0);
    Image testImage = new SimpleImage(pixels);
    handler.getMap().put(imageName, testImage);

    handler.colorCorrect(imageName, colorCorrectName);
    Image colorCorrectImage = handler.getMap().get(colorCorrectName);

    assertNotNull("Colorcorrected image should not be null", colorCorrectImage);

    assertEquals(colorCorrectImage.getPixel(0, 1).getR(), 2);

  }

  @Test
  public void testColorCorrectInvalid() {
    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String invalidImageName = "nonExistentImage";
    String colorCorrectName = "invalidColorCorrectedImage";

    String validImageName = "validImage";
    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new SimplePixel(100, 150, 200);
    pixels[0][1] = new SimplePixel(50, 100, 150);
    pixels[1][0] = new SimplePixel(25, 75, 125);
    pixels[1][1] = new SimplePixel(200, 50, 100);
    Image testImage = new SimpleImage(pixels);
    handler.getMap().put(validImageName, testImage);

    handler.colorCorrect(validImageName, colorCorrectName);
    Image colorCorrectedImage = handler.getMap().get(colorCorrectName);
    assertNotNull("Color corrected image should not be null", colorCorrectedImage);

    Pixel incorrectPixel = new SimplePixel(0, 0, 0);
    assertNotEquals("Expected incorrect red level should not match actual level",
        incorrectPixel.getR(), colorCorrectedImage.getPixel(0, 0).getR());
    assertNotEquals("Expected incorrect green level should not match actual level",
        incorrectPixel.getG(), colorCorrectedImage.getPixel(0, 0).getG());
    assertNotEquals("Expected incorrect blue level should not match actual level",
        incorrectPixel.getB(), colorCorrectedImage.getPixel(0, 0).getB());
  }

  private int findPeak(int[] colorChannel) {
    return 128;
  }

  @Test
  public void testColorCorrect() {
    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    String imageName = "testImage";
    String colorCorrectedName = "colorCorrectedImage";

    Pixel[][] pixels = new Pixel[2][2];
    pixels[0][0] = new SimplePixel(120, 60, 30);
    pixels[0][1] = new SimplePixel(200, 150, 100);
    pixels[1][0] = new SimplePixel(50, 25, 200);
    pixels[1][1] = new SimplePixel(150, 180, 100);
    Image testImage = new SimpleImage(pixels);
    handler.getMap().put(imageName, testImage);

    handler.colorCorrect(imageName, colorCorrectedName);
    Image colorCorrectedImage = handler.getMap().get(colorCorrectedName);

    assertNotNull("Color corrected image should not be null", colorCorrectedImage);

    int[][] colorLevels = SimpleImageHandlerAdapter.analyzeColorLevels(testImage);
    int redPeak = findPeak(colorLevels[0]);
    int greenPeak = findPeak(colorLevels[1]);
    int bluePeak = findPeak(colorLevels[2]);
    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    Pixel expectedPixel1 = correctPixel(120, 60, 30, redPeak, greenPeak, bluePeak, avgPeak);
    Pixel expectedPixel2 = correctPixel(200, 150, 100, redPeak, greenPeak, bluePeak, avgPeak);
    Pixel expectedPixel3 = correctPixel(50, 25, 200, redPeak, greenPeak, bluePeak, avgPeak);
    Pixel expectedPixel4 = correctPixel(150, 180, 100, redPeak, greenPeak, bluePeak, avgPeak);

    assertEquals(expectedPixel1, colorCorrectedImage.getPixel(0, 0));
    assertEquals(expectedPixel2, colorCorrectedImage.getPixel(0, 1));
    assertEquals(expectedPixel3, colorCorrectedImage.getPixel(1, 0));
    assertEquals(expectedPixel4, colorCorrectedImage.getPixel(1, 1));
  }

  private Pixel correctPixel(int r, int g, int b, int redPeak, int greenPeak, int bluePeak,
      int avgPeak) {
    int correctedR = Math.min(255, Math.max(0, r + avgPeak - redPeak));
    int correctedG = Math.min(255, Math.max(0, g + avgPeak - greenPeak));
    int correctedB = Math.min(255, Math.max(0, b + avgPeak - bluePeak));
    return new SimplePixel(correctedR, correctedG, correctedB);
  }


  @Test
  public void testLevelsAdjustRandomValues() {

    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    int width = 5;
    int height = 5;
    Pixel[][] pixels = new Pixel[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int randomRed = (int) (Math.random() * 256);
        int randomGreen = (int) (Math.random() * 256);
        int randomBlue = (int) (Math.random() * 256);
        pixels[x][y] = new SimplePixel(randomRed, randomGreen, randomBlue);
      }
    }
    Image mockImage = new SimpleImage(pixels);
    String imageName = "testImage";
    String modifiedImageName = "adjustedImage";
    handler.getMap().put(imageName, mockImage);
    int black = 30;
    int mid = 128;
    int white = 200;
    Image adjustedImage = handler.getMap().get(modifiedImageName);
    assertNotNull(adjustedImage);
    assertEquals(width, adjustedImage.getWidth());
    assertEquals(height, adjustedImage.getHeight());
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel originalPixel = mockImage.getPixel(x, y);
        Pixel adjustedPixel = adjustedImage.getPixel(x, y);
        int expectedR = validate(black, mid, white, originalPixel.getR());
        int expectedG = validate(black, mid, white, originalPixel.getG());
        int expectedB = validate(black, mid, white, originalPixel.getB());

        assertEquals(expectedR, adjustedPixel.getR());
        assertEquals(expectedG, adjustedPixel.getG());
        assertEquals(expectedB, adjustedPixel.getB());
      }
    }
  }

  private int validate(int black, int mid, int white, int value) {
    return value < black ? 0 : value > white ? 255 : (value - black) * 255 / (white - black);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustInvalidLevels() {

    SimpleImageHandlerAdapter handler = new SimpleImageHandlerAdapter();
    int width = 5;
    int height = 5;
    Pixel[][] pixels = new Pixel[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixels[x][y] = new SimplePixel(100, 150, 200);
      }
    }
    Image mockImage = new SimpleImage(pixels);
    String imageName = "testImage";
    String modifiedImageName = "adjustedImage";

    handler.getMap().put(imageName, mockImage);

    int black = 100;
    int mid = 80;
    int white = 200;
  }

  @Test
  public void testDownscale_ValidDimensions() throws IOException {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    String imageName = "current-image";
    String modifiedImageName = "downscaled-image";
    int newWidth = 2;
    int newHeight = 2;

    Image mockImage = new SimpleImage(new SimplePixel[4][4]);

    handler.downscale(imageName, modifiedImageName, newWidth, newHeight);

    assertNotNull(modifiedImageName);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscale_InvalidDimensions() throws IOException {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    String imageName = "current-image";
    String modifiedImageName = "downscaled-image";

    handler.downscale(imageName, modifiedImageName, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscale_UpscalingNotAllowed() throws IOException {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    String imageName = "current-image";
    String modifiedImageName = "downscaled-image";
    int newWidth = 5;
    int newHeight = 5;

    Image mockImage = new SimpleImage(new SimplePixel[4][4]);

    handler.downscale(imageName, modifiedImageName, newWidth, newHeight);
  }

  @Test
  public void testRedComponentWithMask() throws IOException {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    String imgPath = "test-images/Photohi.jpg";
    String imgName = "photo";
    String maskedImgName = "masked-photo";
    String modifiedImgName = "red-on-masked-photo";

    BufferedImage image = ImageIO.read(new File(imgPath));
    handler.loadImagePixels(image, imgName);

    handler.redComponentWithMask(imgName, maskedImgName, modifiedImgName);

    Image modifiedImage = ((SimpleImageHandler) handler).getImage(modifiedImgName);
    assertNotNull(modifiedImage);

    for (Pixel[] row : modifiedImage.getImage()) {
      for (Pixel pixel : row) {
        if (pixel.getR() == 0 && pixel.getG() == 0 && pixel.getB() == 0) {
          fail("Mask pixels should remain unaffected.");
        }
      }
    }

    for (Pixel[] row : modifiedImage.getImage()) {
      for (Pixel pixel : row) {
        if (pixel.getR() == 0) {
          fail("Red component should be applied correctly.");
        }
      }
    }
  }


}

