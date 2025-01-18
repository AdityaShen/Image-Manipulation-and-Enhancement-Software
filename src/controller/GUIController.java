package src.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.InputMismatchException;

import java.util.stream.Stream;
import src.controller.commands.BlurImage;
import src.controller.commands.BrightenImage;
import src.controller.commands.ColorCorrect;
import src.controller.commands.CommandController;
import src.controller.commands.Compress;
import src.controller.commands.ConvertToSepia;
import src.controller.commands.Downscale;
import src.controller.commands.FlipHorizontal;
import src.controller.commands.FlipVertical;
import src.controller.commands.Histogram;
import src.controller.commands.ImageLoader;
import src.controller.commands.LevelsAdjust;
import src.controller.commands.RGBCombine;
import src.controller.commands.RGBSplit;
import src.controller.commands.SaveImage;
import src.controller.commands.SharpenImage;
import src.controller.commands.VisualiseBlue;
import src.controller.commands.VisualiseGreen;
import src.controller.commands.VisualiseIntensity;
import src.controller.commands.VisualiseLuma;
import src.controller.commands.VisualiseRed;
import src.controller.commands.VisualiseValue;
import src.model.ExtendedImageHandlerAdapter;
import src.view.GUIInterface;


/**
 * The GUIController class acts as the controller in the MVC pattern, managing user interactions
 * with the view. It delegates commands to the CommandController, which in turn invokes the
 * appropriate functions in the model. The GUIController updates the view based on the changes made
 * in the model, ensuring proper flow between the user interface and business logic.
 */
public class GUIController implements Operations {

  private final ExtendedImageHandlerAdapter handler;
  private final String currentImageName;
  private final String splitImageName;
  private final String histogram;
  private final GUIInterface view;


  /**
   * Constructs a new GUIController with the specified image handler and view.
   *
   * @param handler The model responsible for handling image operations.
   * @param view    The view representing the user interface that interacts with the controller.
   */
  public GUIController(ExtendedImageHandlerAdapter handler, GUIInterface view) {
    this.handler = handler;
    this.view = view;
    view.addOperations(this);
    this.currentImageName = "current-image";
    this.splitImageName = "split-image";
    this.histogram = "histogram";
  }

  /**
   * Refreshes the view by updating the current image and histogram display.
   *
   * @throws IOException If an I/O error occurs during the view refresh.
   */
  private void refreshView() throws IOException {
    BufferedImage image = handler.processImage(currentImageName);
    BufferedImage histogramImage = handler.processImage(histogram);
    view.refreshGUI(currentImageName, histogram, image, histogramImage);
  }

  private void executeCommand(String[] args, CommandController command) throws IOException {
    try {
      command.execute(args, handler);
      String[] argsHistogram = {"histogram", currentImageName, histogram};
      CommandController histogramCmd = new Histogram();
      histogramCmd.execute(argsHistogram, handler);
      refreshView();
    } catch (IllegalArgumentException | InputMismatchException e) {
      view.displayError(e.getMessage());
    }
  }

  private void load(String filepath, String imageName) throws IOException {
    String[] args = new String[]{"load", filepath, imageName};
    executeCommand(args, new ImageLoader());
  }

  @Override
  public void load(String filepath) throws IOException {
    load(filepath, currentImageName);
  }

  private void save(String filepath, String imageName) throws IOException {
    String[] args = new String[]{"save", imageName, filepath};
    executeCommand(args, new SaveImage());
  }

  @Override
  public void save(String filepath) throws IOException {
    this.save(filepath, currentImageName);
  }

  @Override
  public void brighten(int scale) throws IOException {
    String[] args = new String[]{"brighten", String.valueOf(scale), currentImageName,
        currentImageName};
    executeCommand(args, new BrightenImage());
  }

  @Override
  public void verticalFlip() throws IOException {
    String[] args = new String[]{"vertical-flip", currentImageName, currentImageName};
    executeCommand(args, new FlipVertical());
  }

  @Override
  public void horizontalFlip() throws IOException {
    String[] args = new String[]{"horizontal-flip", currentImageName, currentImageName};
    executeCommand(args, new FlipHorizontal());
  }

  @Override
  public void colorCorrect() throws IOException {
    String[] args = {"color-correct", currentImageName, currentImageName};
    executeCommand(args, new ColorCorrect());
  }

  @Override
  public void levelAdjust(int black, int mid, int white) throws IOException {
    String[] args = {"levels-adjust", String.valueOf(black), String.valueOf(mid),
        String.valueOf(white), currentImageName, currentImageName};
    executeCommand(args, new LevelsAdjust());
  }

  @Override
  public void downscale(int width, int height) throws IOException {
    String[] args = {"downscale", currentImageName, currentImageName,
        String.valueOf(width), String.valueOf(height)};
    executeCommand(args, new Downscale());
  }

  @Override
  public void compress(double percentage) throws IOException {
    String[] args = new String[]{"compress", currentImageName, currentImageName,
        String.valueOf(percentage)};
    executeCommand(args, new Compress());
  }

  @Override
  public void redComponent() throws IOException {
    String[] args = {"red-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseRed());
  }

  @Override
  public void blueComponent() throws IOException {
    String[] args = {"blue-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseBlue());
  }

  @Override
  public void greenComponent() throws IOException {
    String[] args = {"green-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseGreen());
  }

  @Override
  public void luma() throws IOException {
    String[] args = {"luma-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseLuma());
  }

  @Override
  public void value() throws IOException {
    String[] args = {"value-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseValue());
  }

  @Override
  public void intensity() throws IOException {
    String[] args = {"intensity-component", currentImageName, currentImageName};
    executeCommand(args, new VisualiseIntensity());
  }

  @Override
  public void blur() throws IOException {
    String[] args = {"blur", currentImageName, currentImageName};
    executeCommand(args, new BlurImage());
  }

  @Override
  public void sharpen() throws IOException {
    String[] args = {"sharpen", currentImageName, currentImageName};
    executeCommand(args, new SharpenImage());
  }

  @Override
  public void sepia() throws IOException {
    String[] args = {"sepia", currentImageName, currentImageName};
    executeCommand(args, new ConvertToSepia());
  }

  @Override
  public void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
      throws IOException {
    String red = "red-" + currentImageName;
    String green = "green-" + currentImageName;
    String blue = "blue-" + currentImageName;

    load(redImageFile, red);
    load(greenImageFile, green);
    load(blueImageFile, blue);

    String[] args = {"rgb-combine", currentImageName, red, green, blue};
    executeCommand(args, new RGBCombine());
  }

  @Override
  public void split(String command, String[] splitArgs) throws IOException {
    String[] basicArgs = {currentImageName, splitImageName};
    String[] args;
    CommandController cmd;
    switch (command) {
      case "blur":
        args = Stream.of(new String[]{"blur"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new BlurImage();
        break;
      case "sharpen":
        args = Stream.of(new String[]{"sharpen"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new SharpenImage();
        break;
      case "sepia":
        args = Stream.of(new String[]{"sepia"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new ConvertToSepia();
        break;
      case "luma-component":
        args = Stream.of(new String[]{"luma-component"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new VisualiseLuma();
        break;
      case "intensity-component":
        args = Stream.of(new String[]{"intensity-component"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new VisualiseIntensity();
        break;
      case "color-correct":
        args = Stream.of(new String[]{"color-correct"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new ColorCorrect();
        break;
      case "value-component":
        args = Stream.of(new String[]{"value-component"}, basicArgs, splitArgs)
            .flatMap(Stream::of)
            .toArray(String[]::new);
        cmd = new VisualiseValue();
        break;
      case "level-adjust":
        args = new String[]{"level-adjust", splitArgs[0], splitArgs[1], splitArgs[2],
            currentImageName, splitImageName,
            splitArgs[3], splitArgs[4]};
        cmd = new LevelsAdjust();
        break;
      default:
        throw new IllegalArgumentException("Incorrect split operation: " + command);
    }
    cmd.execute(args, handler);
    BufferedImage image = handler.processImage(splitImageName);
    view.splitView(image, splitImageName);
  }


  @Override
  public void reloadImage() throws IOException {
    this.refreshView();
  }

  @Override
  public void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath)
      throws IOException {
    String red = currentImageName + "-red";
    String green = currentImageName + "-green";
    String blue = currentImageName + "-blue";

    String[] args = new String[]{"rgb-split", currentImageName, red, green, blue};
    CommandController rgbSplit = new RGBSplit();
    rgbSplit.execute(args, handler);

    save(redFilePath, red);
    save(greenFilePath, green);
    save(blueFilePath, blue);
  }

  @Override
  public void quit() {
    System.exit(0);
  }

}
