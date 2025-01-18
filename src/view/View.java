package src.view;

/**
 * An interface representing the view for image processing operations. Provides methods for
 * displaying messages related to various image manipulation actions like loading, saving, and
 * applying filters/transformations. Defines how these messages are presented to the user.
 */

public interface View {

  /**
   * Displays a message indicating that an image has been successfully loaded.
   *
   * @param args array of string arguments, where args[1] is the name of the loaded image
   */
  void viewLoad(String[] args);

  /**
   * Displays a message indicating that an image has been successfully saved.
   *
   * @param args array of string arguments, where args[2] is the name of the saved image
   */
  void viewSave(String[] args);

  /**
   * Displays a message indicating that the blue component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the blue component creation
   */
  void viewBlueComponent(String[] args);

  /**
   * Displays a message indicating that the green component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the green component creation
   */
  void viewGreenComponent(String[] args);

  /**
   * Displays a message indicating that the red component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the red component creation
   */
  void viewRedComponent(String[] args);

  /**
   * Displays a message indicating that the value component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the value component creation
   */
  void viewValueComponent(String[] args);

  /**
   * Displays a message indicating that the intensity component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the intensity component creation
   */
  void viewIntensityComponent(String[] args);

  /**
   * Displays a message indicating that the luma component of an image has been created
   * successfully.
   *
   * @param args array of string arguments relevant to the luma component creation
   */
  void viewLumaComponent(String[] args);

  /**
   * Displays a message indicating that a vertical flip operation on an image has been completed
   * successfully.
   *
   * @param args array of string arguments relevant to the vertical flip operation
   */
  void viewVerticalFlip(String[] args);

  /**
   * Displays a message indicating that a horizontal flip operation on an image has been completed
   * successfully.
   *
   * @param args array of string arguments relevant to the horizontal flip operation
   */
  void viewHorizontalFlip(String[] args);

  /**
   * Displays a message indicating that a sepia filter has been applied to an image successfully.
   *
   * @param args array of string arguments relevant to the sepia filter application
   */
  void viewSepia(String[] args);

  /**
   * Displays a message indicating that an image has been brightened successfully.
   *
   * @param args array of string arguments, where args[3] indicates the brightness adjustment value
   */
  void viewBrighten(String[] args);

  /**
   * Displays a message indicating that a blur effect has been applied to an image successfully.
   *
   * @param args array of string arguments relevant to the blur operation
   */
  void viewBlur(String[] args);

  /**
   * Displays a message indicating that a sharpen effect has been applied to an image successfully.
   *
   * @param args array of string arguments relevant to the sharpen operation
   */
  void viewSharpen(String[] args);

  /**
   * Displays a message indicating that an RGB split operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB split operation
   */
  void viewRgbSplit(String[] args);

  /**
   * Displays a message indicating that an RGB combine operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB combine operation
   */
  void viewRgbCombine(String[] args);

  /**
   * Displays a message indicating that an RGB combine operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB combine operation
   */
  void viewHistogram(String[] args);

  /**
   * Displays a message indicating that an RGB combine operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB combine operation
   */
  void viewColorCorrect(String[] args);

  /**
   * Displays a message indicating that an RGB combine operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB combine operation
   */
  void viewCompress(String[] args);

  /**
   * Displays a message indicating that an RGB combine operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the RGB combine operation
   */
  void viewLevelsAdjust(String[] args);

  /**
   * Displays an error message for an unknown command.
   *
   * @param command array of string arguments representing the unknown command
   */
  void viewError(String[] command);

  /**
   * Displays a message indicating that a script has been processed successfully.
   */
  void printScriptProcessed();

  /**
   * Displays a message indicating that a downscale operation has been completed successfully.
   *
   * @param args array of string arguments relevant to the downscale operation.
   */
  void viewDownscale(String[] args);
}

