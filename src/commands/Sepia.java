package commands;

import model.ImageModel;

/**
 * This class allows the user to add the serpia effect on an image within the controller.
 */
public class Sepia implements CommandImage {

  /**
   * This is the constructor for Serpia.
   */
  public Sepia() {
  // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.sepia();
  }
}
