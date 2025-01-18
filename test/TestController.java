package test;

import static org.junit.Assert.fail;

import org.junit.Test;
import src.controller.commands.BlurImage;
import src.controller.commands.BrightenImage;
import src.controller.commands.CommandController;
import src.controller.commands.ConvertToSepia;
import src.controller.commands.FlipHorizontal;
import src.controller.commands.FlipVertical;
import src.controller.commands.ImageLoader;
import src.controller.commands.RGBCombine;
import src.controller.commands.RGBSplit;
import src.controller.commands.SaveImage;
import src.controller.ScriptController;
import src.controller.commands.SharpenImage;
import src.controller.SimpleScriptController;
import src.controller.commands.VisualiseBlue;
import src.controller.commands.VisualiseGreen;
import src.controller.commands.VisualiseIntensity;
import src.controller.commands.VisualiseLuma;
import src.controller.commands.VisualiseRed;
import src.controller.commands.VisualiseValue;
import src.model.ExtendedImageHandlerAdapter;
import src.model.SimpleExtendedImageHandlerAdapter;
import src.view.SimpleView;
import src.view.View;

/**
 * A test class to validate the functionality of the controller components. It ensures that the
 * script is parsed correctly and checks for errors when an invalid number of arguments is passed.
 * Tests whether it parses the script correctly and throws errors if invalid number of arguments are
 * passed.
 */

public class TestController {

  @Test
  public void testLoadImage() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] args = new String[]{"load", "test-images/Photohi.jpg", "photo"};
    try {
      load.execute(args, handler);
    } catch (Exception e) {
      fail("Got unexpected exception" + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testLoadInvalidImage() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] args = new String[]{"load", "test-images/invalidimage.jpg", "invalidPhoto"};
    load.execute(args, handler);
  }

  @Test
  public void testSaveImage() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = new String[]{"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController save = new SaveImage();
    String[] argsSave = new String[]{"save", "photo", "savedphoto.jpg"};
    try {
      save.execute(argsSave, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testSaveWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController save = new SaveImage();
    String[] argsSave = new String[]{"save", "photo"};
    save.execute(argsSave, handler);
  }

  @Test(expected = Exception.class)
  public void testSaveWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController save = new SaveImage();
    String[] argsSave = new String[]{"save", "photo", "photo.jpg", "savephoto.jpg"};
    save.execute(argsSave, handler);
  }

  @Test
  public void testBlurValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] args = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(args, handler);

    CommandController blurCommand = new BlurImage();

    String[] argsBlur = {"blur", "photo", "photo-blurred"};
    try {
      blurCommand.execute(argsBlur, handler);
    } catch (Exception e) {
      fail("Got unexpected exception" + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testBlurWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController blurCommand = new BlurImage();
    String[] argsBlur = {"blur", "photo"};

    blurCommand.execute(argsBlur, handler);
  }

  @Test(expected = Exception.class)
  public void testBlurWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController blurCommand = new BlurImage();
    String[] argsBlur = {"blur", "photo", "photo-blurred", "photo-blurred.jpg"};

    blurCommand.execute(argsBlur, handler);
  }

  @Test
  public void testBrightenValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController brightenCommand = new BrightenImage();
    String[] argsBrighten = {"brighten", "30", "photo", "brightened-photo"};
    try {
      brightenCommand.execute(argsBrighten, handler);
    } catch (Exception e) {
      fail("Got unexpected exception" + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testBrightenWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController brightenCommand = new BrightenImage();
    String[] argsBrighten = {"brighten", "photo", "brightened-photo"};
    brightenCommand.execute(argsBrighten, handler);
  }

  @Test(expected = Exception.class)
  public void testBrightenWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController brightenCommand = new BrightenImage();
    String[] argsBrighten = {"brighten", "photo", "brightened-photo", "50", "photo-blurred.jpg"};
    brightenCommand.execute(argsBrighten, handler);
  }

  @Test
  public void testConvertToSepiaValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sepiaCommand = new ConvertToSepia();
    String[] argsSepia = {"sepia", "photo", "photo-sepia"};

    try {
      sepiaCommand.execute(argsSepia, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testConvertToSepiaWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sepiaCommand = new ConvertToSepia();
    String[] argsSepia = {"sepia", "photo"};

    sepiaCommand.execute(argsSepia, handler);
  }

  @Test(expected = Exception.class)
  public void testConvertToSepiaWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sepiaCommand = new ConvertToSepia();
    String[] argsSepia = {"sepia", "photo", "photo-sepia", "photo-sepia.jpg"};

    sepiaCommand.execute(argsSepia, handler);
  }

  @Test
  public void testFlipHorizontalValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipHorizontal();
    String[] argsFlip = {"flip", "photo", "photo-flipped"};

    try {
      flipCommand.execute(argsFlip, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testFlipHorizontalWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipHorizontal();
    String[] argsFlip = {"flip", "photo"};

    flipCommand.execute(argsFlip, handler);
  }

  @Test(expected = Exception.class)
  public void testFlipHorizontalWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipHorizontal();
    String[] argsFlip = {"flip", "photo", "photo-flipped", "photo-flipped.jpg"};

    flipCommand.execute(argsFlip, handler);
  }

  @Test
  public void testFlipVerticalValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipVertical();
    String[] argsFlip = {"flip", "photo", "photo-flipped-vertical"};

    try {
      flipCommand.execute(argsFlip, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testFlipVerticalWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipVertical();
    String[] argsFlip = {"flip", "photo"};

    flipCommand.execute(argsFlip, handler);
  }

  @Test(expected = Exception.class)
  public void testFlipVerticalWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();

    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController flipCommand = new FlipVertical();
    String[] argsFlip = {"flip", "photo", "photo-flipped-vertical", "extra-arg"};

    flipCommand.execute(argsFlip, handler);
  }

  @Test
  public void testRGBSplitValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit = {"rgbSplit", "photo", "red-output", "green-output", "blue-output"};

    try {
      rgbSplitCommand.execute(argsRGBSplit, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testRGBSplitWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit = {"rgbSplit", "photo", "red-output"};

    rgbSplitCommand.execute(argsRGBSplit, handler);
  }

  @Test(expected = Exception.class)
  public void testRGBSplitWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit =
        {"rgbSplit", "photo", "red-output", "green-output", "blue-output", "yellow-output"};

    rgbSplitCommand.execute(argsRGBSplit, handler);
  }

  @Test
  public void testSharpenValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sharpenCommand = new SharpenImage();
    String[] argsSharpen = {"sharpen", "photo", "sharpened-photo"};

    try {
      sharpenCommand.execute(argsSharpen, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testSharpenWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sharpenCommand = new SharpenImage();
    String[] argsSharpen = {"sharpen", "photo"};

    sharpenCommand.execute(argsSharpen, handler);
  }

  @Test(expected = Exception.class)
  public void testSharpenWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController sharpenCommand = new SharpenImage();
    String[] argsSharpen = {"sharpen", "photo", "sharpened-photo", "sharpened-photo.jpg"};

    sharpenCommand.execute(argsSharpen, handler);
  }

  @Test
  public void testVisualiseBlueValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseBlueCommand = new VisualiseBlue();
    String[] argsVisualiseBlue = {"visualiseBlue", "photo", "blue-photo"};

    try {
      visualiseBlueCommand.execute(argsVisualiseBlue, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseBlueWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseBlueCommand = new VisualiseBlue();
    String[] argsVisualiseBlue = {"visualiseBlue", "photo"};

    visualiseBlueCommand.execute(argsVisualiseBlue, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseBlueWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseBlueCommand = new VisualiseBlue();
    String[] argsVisualiseBlue = {"visualiseBlue", "photo", "blue-photo", "blue-photo.jpg"};

    visualiseBlueCommand.execute(argsVisualiseBlue, handler);
  }

  @Test
  public void testVisualiseRedValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseRedCommand = new VisualiseRed();
    String[] argsVisualiseRed = {"visualiseRed", "photo", "red-photo"};

    try {
      visualiseRedCommand.execute(argsVisualiseRed, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseRedWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseRedCommand = new VisualiseRed();
    String[] argsVisualiseRed = {"visualiseRed", "photo"};

    visualiseRedCommand.execute(argsVisualiseRed, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseRedWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseRedCommand = new VisualiseRed();
    String[] argsVisualiseRed = {"visualiseRed", "photo", "red-photo", "red-photo.jpg"};

    visualiseRedCommand.execute(argsVisualiseRed, handler);
  }

  @Test
  public void testVisualiseGreenValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseGreenCommand = new VisualiseGreen();
    String[] argsVisualiseGreen = {"visualiseGreen", "photo", "green-photo"};

    try {
      visualiseGreenCommand.execute(argsVisualiseGreen, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseGreenWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseGreenCommand = new VisualiseGreen();
    String[] argsVisualiseGreen = {"visualiseGreen", "photo"};

    visualiseGreenCommand.execute(argsVisualiseGreen, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseGreenWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseGreenCommand = new VisualiseGreen();
    String[] argsVisualiseGreen = {"visualiseGreen", "photo", "green-photo", "green-photo.jpg"};

    visualiseGreenCommand.execute(argsVisualiseGreen, handler);
  }

  @Test
  public void testVisualiseIntensityValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseIntensityCommand = new VisualiseIntensity();
    String[] argsVisualiseIntensity = {"visualiseIntensity", "photo", "intensity-photo"};

    try {
      visualiseIntensityCommand.execute(argsVisualiseIntensity, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseIntensityWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseIntensityCommand = new VisualiseIntensity();
    String[] argsVisualiseIntensity = {"visualiseIntensity", "photo"};

    visualiseIntensityCommand.execute(argsVisualiseIntensity, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseIntensityWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseIntensityCommand = new VisualiseIntensity();
    String[] argsVisualiseIntensity =
        {"visualiseIntensity", "photo", "intensity-photo", "intensity-photo.jpg"};

    visualiseIntensityCommand.execute(argsVisualiseIntensity, handler);
  }

  @Test
  public void testVisualiseLumaValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseLumaCommand = new VisualiseLuma();
    String[] argsVisualiseLuma = {"visualiseLuma", "photo", "luma-photo"};

    try {
      visualiseLumaCommand.execute(argsVisualiseLuma, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseLumaWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseLumaCommand = new VisualiseLuma();
    String[] argsVisualiseLuma = {"visualiseLuma", "photo"};

    visualiseLumaCommand.execute(argsVisualiseLuma, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseLumaWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseLumaCommand = new VisualiseLuma();
    String[] argsVisualiseLuma = {"visualiseLuma", "photo", "luma-photo", "luma-photo.jpg"};

    visualiseLumaCommand.execute(argsVisualiseLuma, handler);
  }

  @Test
  public void testVisualiseValueValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseValueCommand = new VisualiseValue();
    String[] argsVisualiseValue = {"visualiseValue", "photo", "value-photo"};

    try {
      visualiseValueCommand.execute(argsVisualiseValue, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testVisualiseValueWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseValueCommand = new VisualiseValue();
    String[] argsVisualiseValue = {"visualiseValue", "photo"};

    visualiseValueCommand.execute(argsVisualiseValue, handler);
  }

  @Test(expected = Exception.class)
  public void testVisualiseValueWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController visualiseValueCommand = new VisualiseValue();
    String[] argsVisualiseValue = {"visualiseValue", "photo", "value-photo", "value-photo.jpg"};

    visualiseValueCommand.execute(argsVisualiseValue, handler);
  }

  @Test
  public void testRGBCombineValid() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit =
        {"rgbSplit", "photo", "red-component", "green-component", "blue-component"};
    rgbSplitCommand.execute(argsRGBSplit, handler);

    CommandController rgbCombineCommand = new RGBCombine();
    String[] argsRGBCombine =
        {"rgbCombine", "combined-image", "red-component", "green-component", "blue-component"};

    try {
      rgbCombineCommand.execute(argsRGBCombine, handler);
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testRGBCombineWithLessArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit =
        {"rgbSplit", "photo", "red-component", "green-component", "blue-component"};
    rgbSplitCommand.execute(argsRGBSplit, handler);

    CommandController rgbCombineCommand = new RGBCombine();
    String[] argsRGBCombine = {"rgbCombine", "red-component", "green-component", "blue-component"};

    rgbCombineCommand.execute(argsRGBCombine, handler);
  }

  @Test(expected = Exception.class)
  public void testRGBCombineWithMoreArguments() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    CommandController load = new ImageLoader();
    String[] argsLoad = {"load", "test-images/Photohi.jpg", "photo"};
    load.execute(argsLoad, handler);

    CommandController rgbSplitCommand = new RGBSplit();
    String[] argsRGBSplit =
        {"rgbSplit", "photo", "red-component", "green-component", "blue-component"};
    rgbSplitCommand.execute(argsRGBSplit, handler);

    CommandController rgbCombineCommand = new RGBCombine();
    String[] argsRGBCombine =
        {"rgbCombine", "red-component",
            "green-component",
            "blue-component",
            "combined-image",
            "combined-image.jpg"};

    rgbCombineCommand.execute(argsRGBCombine, handler);
  }

  @Test
  public void testProcessScriptValidFile() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    View view = new SimpleView();
    ScriptController scriptController = SimpleScriptController.createFile(
        new String[]{"FILE", "script.txt"}, handler, view);
    try {
      scriptController.processInput();
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = Exception.class)
  public void testProcessScriptInvalidFile() {
    ExtendedImageHandlerAdapter handler = new SimpleExtendedImageHandlerAdapter();
    View view = new SimpleView();
    ScriptController scriptController = SimpleScriptController.createFile(
        new String[]{"FILE", "invalid-script.txt"}, handler, view);
    scriptController.processInput();
  }

}