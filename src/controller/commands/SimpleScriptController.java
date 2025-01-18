package src.controller.commands;

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
   * Factory method to create Simplescriptcontroller instance.
   *
   * @param input   Input String array containing CLI or FILE at start
   * @param handler Model object
   * @param view    View object
   * @return SimpleScriptController instance
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
   * Factory method to create Simplescriptcontroller instance when input is from CLI.
   *
   * @param handler Model object
   * @param view    View object
   * @return SimpleScriptController instance
   */
  public static SimpleScriptController createCLI(ExtendedImageHandlerAdapter handler, View view) {
    return new SimpleScriptController(new InputStreamReader(System.in), handler, view);
  }

  /**
   * Factory method to create Simplescriptcontroller instance when input is from keyboard.
   *
   * @param input   String array of input
   * @param handler Model object
   * @param view    View object
   * @return SimpleScriptController instance
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

    commandToController.put("downscale", new Pair<>(new Downscale(), args -> {
      view.viewDownscale(args);
      return null;
    }));
  }


  @Override
  public void processInput() {
    CommandController commandController;
    initializeMap();
    Scanner scanner = new Scanner(input);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty() || line.startsWith("#")) {
        continue;
      }
      String[] tokens = line.split("\\s+");
      commandController = commandToController.get(tokens[0]).getFirst();

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
