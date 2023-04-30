package commands;

import model.ImageModel;

/**
 * This class implements CommandImage and allows a user to flip the image through the controller.
 */
public class FlipImageHorizontally implements CommandImage {
  /**
   * This is the constructor for flipImageHorizontally.
   */
  public FlipImageHorizontally() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.flipImageHorizontally();
  }
}
