package commands;

import model.ImageModel;

/**
 * This represents a greyScaleLuma effect that can be made on an image that the user chooses to
 * edit.
 */
public class GreyscaleLuma implements CommandImage {

  /**
   * This is the constructor for greyscaleLuma.
   */
  public GreyscaleLuma() {
    // This simply does not need to take in any values.
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.greyScaleLuma();
  }
}
