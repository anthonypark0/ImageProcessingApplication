package view;

import java.io.IOException;

import model.ImageModel;

/**
 * This is an abstract class used to describe ImageViews. This abstract class helps other ImageView
 * classes to work.
 */
public abstract class AbstractImageView implements ImageView {
  ImageModel type;
  Appendable out;

  /**
   * Constructor for AbstractImageView class.
   *
   * @param type is the type of ImageModel going through.
   */
  public AbstractImageView(ImageModel type) {
    if (type == null) {
      throw new IllegalArgumentException("No Null Types");
    }
    this.type = type;
    this.out = System.out;
  }

  /**
   * Constructor for AbstractImageView class.
   *
   * @param type is the type of ImagePPMModel we are using to display the image.
   * @param out  is the text the user gets to see after their inputs.
   */
  public AbstractImageView(ImageModel type, Appendable out) {
    if (type == null || out == null) {
      throw new IllegalArgumentException("No Null Types");
    }
    this.type = type;
    this.out = out;
  }

  /**
   * Render the image to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderImage() throws IOException {
    this.out.append(this.toString());
  }


}
