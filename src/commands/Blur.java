package commands;

import model.ImageModel;

/**
 * This class allows a user to blur an image throught the controller.
 */
public class Blur implements CommandImage {

  /**
   * This is the constructor for the blue class.
   */
  public Blur() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.blur();
  }
}
