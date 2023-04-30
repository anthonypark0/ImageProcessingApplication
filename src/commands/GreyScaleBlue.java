package commands;

import model.ImageModel;

/**
 * This class allows the user to apply a greyScaleBlue effect to the image in the controller.
 */
public class GreyScaleBlue implements CommandImage {

  /**
   * This is the constructor for greyScaleBlue.
   */
  public GreyScaleBlue() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.greyScaleBlue();
  }
}
