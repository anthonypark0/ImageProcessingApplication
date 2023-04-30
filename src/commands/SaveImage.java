package commands;

import java.io.IOException;

import model.ImageModel;

/**
 * This class represents the command for a user to save an Image. The user can do this through
 * the controller.
 */
public class SaveImage implements CommandImage {
  private final String filename;

  /**
   * This is the constructor for saveImage.
   *
   * @param filename is the name of the file that the user wants to save.
   */
  public SaveImage(String filename) {
    this.filename = filename;
  }

  /**
   * This runs the command that the user chooses to input.
   *
   * @param m is the ImageModel that the user is choosing to change.
   */
  @Override
  public void play(ImageModel m) {
    try {
      m.saveImage(this.filename);
    } catch (IOException e) {
      return;
    }
  }
}
