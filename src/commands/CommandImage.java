package commands;

import model.ImageModel;

/**
 * This is the interface that represents the commands. The subclasses of this interface represent
 * each command that a user can make through the controller.
 */
public interface CommandImage {

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  void play(ImageModel m);
}
