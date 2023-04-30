package controller;

import java.io.IOException;

/**
 * This interface is representing an ImageController object which is the controller allowing the
 * user to input commands needed to change or adjust the image.
 */
public interface ImageController {

  /**
   * This method will run the ImageController and take inputs and output a view for the user to see.
   *
   * @throws IOException when the inputs do not work with the controller.
   */
  void playMethod();


}
