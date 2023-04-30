package commands;

import model.ImageModel;

/**
 * This class allows a user to sharpen an ImageModel, which they can edit through the controller.
 */
public class Sharpen implements CommandImage {

  /**
   * This is a constructor for sharpen.
   */
  public Sharpen() {
  // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.sharpen();
  }
}
