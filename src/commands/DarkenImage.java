package commands;

import model.ImageModel;

/**
 * This class represents a command for the user to be able to darken the image.
 */
public class DarkenImage implements CommandImage {
  private final int dark;

  /**
   * This is the constructor for darken class.
   *
   * @param dark is however much the user wants to darken the image.
   */
  public DarkenImage(int dark) {
    this.dark = dark;
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.darkenImage(dark);
  }
}
