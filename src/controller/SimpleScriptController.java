package src.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import src.controller.commands.ColorCorrect;
import src.controller.commands.CommandController;
import src.controller.commands.Compress;
import src.controller.commands.Downscale;
import src.controller.commands.Histogram;
import src.controller.commands.ImageLoader;
import src.controller.commands.LevelsAdjust;
import src.controller.commands.SaveImage;
import src.controller.commands.BlurImage;
import src.controller.commands.BrightenImage;
import src.controller.commands.SharpenImage;
import src.controller.commands.VisualiseIntensity;
import src.controller.commands.VisualiseGreen;
import src.controller.commands.VisualiseLuma;
import src.controller.commands.VisualiseRed;
import src.controller.commands.VisualiseBlue;
import src.controller.commands.FlipHorizontal;
import src.controller.commands.FlipVertical;
import src.controller.commands.ConvertToSepia;
import src.controller.commands.RGBCombine;
import src.controller.commands.RGBSplit;
import src.controller.commands.VisualiseValue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import src.model.ExtendedImageHandlerAdapter;
import src.view.View;
import src.controller.helper.Pair;


/**
 * SimpleScriptController class contains the link from controller to model and view respectively. It
 * processes the script line by line and ignores all comments. Further, it passes control to the
 * command controller object for the given functionality.
 */
public class SimpleScriptController implements ScriptController {

  private Readable input;
  private final ExtendedImageHandlerAdapter imageHandler;
  private final View view;
  Map<String, Pair<CommandController, Function<String[], Void>>> commandToController;

  private SimpleScriptController(Readable input, ExtendedImageHandlerAdapter handler, View view) {
    this.input = input;
    this.imageHandler = handler;
    this.view = view;
  }


  /**
   * Factory method to create a SimpleScriptController based on the provided input. It determines
   * whether to create the controller for CLI, file, or keyboard input.
   *
   * @param input   array of strings representing the input type and additional arguments
   * @param handler image handler to process image manipulation commands
   * @param view    view for displaying results
   * @return new instance of SimpleScriptController, or null if the input type is unsupported
   */

  public static SimpleScriptController create(String[] input, ExtendedImageHandlerAdapter handler,
      View view) {
    if (input[0].equalsIgnoreCase("-text")) {
      return createCLI(handler, view);
    } else if (input[0].equalsIgnoreCase("-file")) {
      return createFile(input, handler, view);
    }
    return null;
  }

  /**
   * Creates a SimpleScriptController instance that reads input from the command line interface.
   *
   * @param handler image handler to process image manipulation commands
   * @param view    view for displaying results
   * @return a new SimpleScriptController that processes input from the CLI
   */

  public static SimpleScriptController createCLI(ExtendedImageHandlerAdapter handler, View view) {
    return new SimpleScriptController(new InputStreamReader(System.in), handler, view);
  }


  /**
   * Creates a SimpleScriptController instance that reads input from a specified file.
   *
   * @param input   array where the second element is the filename to read input from
   * @param handler image handler to process image manipulation commands
   * @param view    view for displaying results
   * @return new SimpleScriptController that processes input from the specified file
   */

  public static SimpleScriptController createFile(String[] input,
      ExtendedImageHandlerAdapter handler,
      View view) {
    String filename = input[1];
    try {
      FileReader fileReader = new FileReader(filename);
      return new SimpleScriptController(fileReader, handler, view);
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
    }
    return null;
  }


  /**
   * Creates a SimpleScriptController instance that reads input from a string.
   *
   * @param input   input string containing commands to be processed
   * @param handler image handler to process image manipulation commands
   * @param view    view for displaying results
   * @return new SimpleScriptController that processes the provided input string
   */

  public static SimpleScriptController createKeyboard(String input,
      ExtendedImageHandlerAdapter handler,
      View view) {
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    return new SimpleScriptController(new InputStreamReader(inputStream), handler, view);
  }

  private void initializeMap() {
    commandToController = new HashMap<>();

    commandToController.put("load", new Pair<>(new ImageLoader(), args -> {
      view.viewLoad(args);
      return null;
    }));

    commandToController.put("save", new Pair<>(new SaveImage(), args -> {
      view.viewSave(args);
      return null;
    }));

    commandToController.put("blur", new Pair<>(new BlurImage(), args -> {
      view.viewBlur(args);
      return null;
    }));

    commandToController.put("brighten", new Pair<>(new BrightenImage(), args -> {
      view.viewBrighten(args);
      return null;
    }));

    commandToController.put("sepia", new Pair<>(new ConvertToSepia(), args -> {
      view.viewSepia(args);
      return null;
    }));

    commandToController.put("horizontal-flip", new Pair<>(new FlipHorizontal(), args -> {
      view.viewHorizontalFlip(args);
      return null;
    }));

    commandToController.put("vertical-flip", new Pair<>(new FlipVertical(), args -> {
      view.viewVerticalFlip(args);
      return null;
    }));

    commandToController.put("rgb-combine", new Pair<>(new RGBCombine(), args -> {
      view.viewRgbCombine(args);
      return null;
    }));

    commandToController.put("rgb-split", new Pair<>(new RGBSplit(), args -> {
      view.viewRgbSplit(args);
      return null;
    }));

    commandToController.put("sharpen", new Pair<>(new SharpenImage(), args -> {
      view.viewSharpen(args);
      return null;
    }));

    commandToController.put("blue-component", new Pair<>(new VisualiseBlue(), args -> {
      view.viewBlueComponent(args);
      return null;
    }));

    commandToController.put("red-component", new Pair<>(new VisualiseRed(), args -> {
      view.viewRedComponent(args);
      return null;
    }));

    commandToController.put("green-component", new Pair<>(new VisualiseGreen(), args -> {
      view.viewGreenComponent(args);
      return null;
    }));

    commandToController.put("intensity-component", new Pair<>(new VisualiseIntensity(), args -> {
      view.viewIntensityComponent(args);
      return null;
    }));

    commandToController.put("value-component", new Pair<>(new VisualiseValue(), args -> {
      view.viewValueComponent(args);
      return null;
    }));

    commandToController.put("luma-component", new Pair<>(new VisualiseLuma(), args -> {
      view.viewLumaComponent(args);
      return null;
    }));

    commandToController.put("histogram", new Pair<>(new Histogram(), args -> {
      view.viewHistogram(args);
      return null;
    }));

    commandToController.put("color-correct", new Pair<>(new ColorCorrect(), args -> {
      view.viewColorCorrect(args);
      return null;
    }));

    commandToController.put("compress", new Pair<>(new Compress(), args -> {
      view.viewCompress(args);
      return null;
    }));

    commandToController.put("levels-adjust", new Pair<>(new LevelsAdjust(), args -> {
      view.viewLevelsAdjust(args);
      return null;
    }));

    commandToController.put("downscale", new Pair<>(new Downscale(), args -> {
      view.viewDownscale(args);
      return null;
    }));

  }

  @Override
  public void processInput() {
    initializeMap();
    Scanner scanner = new Scanner(input);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty() || line.startsWith("#")) {
        continue;
      }
      String[] tokens = line.split("\\s+");
      CommandController commandController = commandToController.get(tokens[0]).getFirst();

      if (commandController == null) {
        view.viewError(tokens);
        return;
      }
      commandController.execute(tokens, imageHandler);
      commandToController.get(tokens[0]).getSecond().apply(tokens);
    }
    view.printScriptProcessed();
  }
}
