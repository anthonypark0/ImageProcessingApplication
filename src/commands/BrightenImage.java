package commands;

import model.ImageModel;

/**
 * This is the class that allows a user to brighten an image through the controller.
 */
public class BrightenImage implements CommandImage {
  private final int bright;

  /**
   * This is the constructor for brightenImage.
   *
   * @param bright is how much the user wants to brighten the image.
   */
  public BrightenImage(int bright) {
    this.bright = bright;
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    m.brightenImage(bright);
  }
}
