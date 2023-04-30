package commands;

import model.ImageModel;

/**
 * This class allows the user to apply a greyScaleGreen effect to the image in the controller.
 */
public class GreyScaleGreen implements CommandImage {

  /**
   * This is the constructor for greyScaleGreen.
   */
  public GreyScaleGreen() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.greyScaleGreen();
  }
}

