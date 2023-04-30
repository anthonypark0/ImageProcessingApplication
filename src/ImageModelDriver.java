import java.io.InputStreamReader;

import controller.ImagePPMController;

/**
 * Class to represent an entry point for the program to play a MarbleSolitaire game.
 */
public final class ImageModelDriver {

  /**
   * This runs the method ImageController.
   *
   * @param args the inputs.
   */
  public static void main(String[] args) {
    ImagePPMController controller = new ImagePPMController(new InputStreamReader(System.in));
    controller.playMethod();
  }

}