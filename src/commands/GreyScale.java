package commands;

import model.ImageModel;

/**
 * This class allows users to apply a greyScale effect to the ImageModel through the controller.
 */
public class GreyScale implements CommandImage {

  /**
   * This is the constructor for greyScale.
   */

  public GreyScale() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.greyScale();
  }
}

