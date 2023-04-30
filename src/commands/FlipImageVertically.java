package commands;

import model.ImageModel;

/**
 * This class allows a user to flip the image vertically through the controller.
 */
public class FlipImageVertically implements CommandImage {

  /**
   * This is the constructor for flipImageVeritcally.
   */
  public FlipImageVertically() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.flipImageVertically();
  }
}
