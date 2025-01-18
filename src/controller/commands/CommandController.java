package src.controller.commands;

import src.model.ExtendedImageHandlerAdapter;

/**
 * Checks if the given operation requires the number of arguments passed from the script. It further
 * passes control to the model object.
 */
public interface CommandController {

  void execute(String[] args, ExtendedImageHandlerAdapter handler);
}
