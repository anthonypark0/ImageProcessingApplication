import javax.swing.JFrame;

/**
 * Class to play the GUI for the program.
 */
public class PlayGUI {

  /**
   * Plays our program utilizing the GUI.
   * @param args arguments.
   */
  public static void main(String[] args) {
    GUI.setDefaultLookAndFeelDecorated(false);
    GUI frame = new GUI();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}