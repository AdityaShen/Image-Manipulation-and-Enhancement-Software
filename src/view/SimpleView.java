package src.view;

/**
 * A simple view class for displaying status messages related to image processing operations. It
 * returns messages indicating the success or failure of various actions, as well as error
 * notifications. This class implements the View interface and offers methods to output messages for
 * operations such as loading, saving, and manipulating images.
 */

public class SimpleView implements View {


  @Override
  public void viewLoad(String[] args) {
    System.out.println("Image loaded successfully: " + args[1]);
  }

  @Override
  public void viewSave(String[] args) {
    System.out.println("Image saved successfully: " + args[2]);
  }


  @Override
  public void viewBlueComponent(String[] args) {
    System.out.println("Blue component created successfully");
  }


  @Override
  public void viewGreenComponent(String[] args) {
    System.out.println("Green component created successfully");
  }


  @Override
  public void viewRedComponent(String[] args) {
    System.out.println("Red component created successfully");
  }


  @Override
  public void viewValueComponent(String[] args) {
    System.out.println("Value component created successfully");
  }


  @Override
  public void viewIntensityComponent(String[] args) {
    System.out.println("Intensity component created successfully");
  }


  @Override
  public void viewLumaComponent(String[] args) {
    System.out.println("Luma component created successfully");
  }


  @Override
  public void viewVerticalFlip(String[] args) {
    System.out.println("Vertical flip completed successfully");
  }


  @Override
  public void viewHorizontalFlip(String[] args) {
    System.out.println("Horizontal flip completed successfully");
  }


  @Override
  public void viewSepia(String[] args) {
    System.out.println("Sepia filter applied successfully");
  }


  @Override
  public void viewBrighten(String[] args) {
    System.out.println("Image brightened by " + args[3]);
  }


  @Override
  public void viewBlur(String[] args) {
    System.out.println("Blur applied successfully");
  }


  @Override
  public void viewSharpen(String[] args) {
    System.out.println("Sharpen applied successfully");
  }


  @Override
  public void viewRgbSplit(String[] args) {
    System.out.println("RGB split completed successfully");
  }


  @Override
  public void viewRgbCombine(String[] args) {
    System.out.println("RGB combine completed successfully");
  }

  @Override
  public void viewHistogram(String[] args) {
    System.out.println("Histogram applied successfully");
  }

  @Override
  public void viewColorCorrect(String[] args) {
    System.out.println("Color correct applied successfully");
  }

  @Override
  public void viewCompress(String[] args) {
    System.out.println("Compress applied successfully");
  }

  @Override
  public void viewLevelsAdjust(String[] args) {
    System.out.println("Levels adjust applied successfully");
  }


  @Override
  public void viewError(String[] command) {
    System.err.print("Unknown command: ");
    System.err.println(String.join(" ", command));
  }

  @Override
  public void printScriptProcessed() {
    System.out.println("Script processed successfully");
  }


  @Override
  public void viewDownscale(String[] args) {
    System.out.println("Downscale operation completed successfully");
  }

}
