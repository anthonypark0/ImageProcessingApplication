package commands;

import model.ImageModel;

/**
 * This is the class that represents the greyScaleValue function that a user can use to edit an
 * image.
 */
public class GreyScaleValue implements CommandImage {

  /**
   * This is the contructor for the greyScaleValue class.
   */
  public GreyScaleValue() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.greyScaleValue();
  }
}
