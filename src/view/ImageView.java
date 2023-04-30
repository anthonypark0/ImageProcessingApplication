package view;

import java.io.IOException;

/**
 * This interface represents the ImageView object which is used to create a view for an ASCII PPM
 * file.
 */
public interface ImageView {

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderImage() throws IOException;

}
