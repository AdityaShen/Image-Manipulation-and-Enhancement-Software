package src.controller;


/**
 * This interface represents the different script commands to perform operations on images and
 * passes control to a command controller object based on the command in the script. It parses the
 * entire script and invokes the command controller for each command.
 */
public interface ScriptController {

  /**
   * Parse the script line by line and processes it.
   */
  void processInput();
}
