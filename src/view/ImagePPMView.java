package view;

import model.ImageModel;

/**
 * This is an ImagePPMView class which extends the AbstractImageView. This class will display a
 * view that represents an ImagePPM.
 */
public class ImagePPMView extends AbstractImageView {

  /**
   * Constructor for ImagePPMView class.
   *
   * @param type is the type of ImageModel going through.
   */
  public ImagePPMView(ImageModel type) {
    super(type);
  }

  /**
   * Constructor for ImagePPMView class.
   *
   * @param type is the type of ImageModel we are using to display the image
   * @param out  is the text the user gets to see after their inputs.
   */
  public ImagePPMView(ImageModel type, Appendable out) {
    super(type, out);
  }


  /**
   * This creates a string for the ImageModel.
   *
   * @return the fileContent in a representable view for the user to use.
   */
  @Override
  public String toString() {
    return this.type.getFileContent();
  }

}
