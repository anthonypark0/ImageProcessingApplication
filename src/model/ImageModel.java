package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This is an interface used to represent an Image and has the methods needed to adjust an image
 * however it needs to be done.
 */
public interface ImageModel {

  /**
   * This function blurs the current ImageIOModel image.
   */
  void blur();

  /**
   * This image applies the sharpen filter to the current ImageIOModel image.
   */
  void sharpen();

  /**
   * This function applies the greyScale filter to the current ImageIOModel image.
   */
  void greyScale();

  /**
   * This function applies a sepia filter to the image.
   */
  void sepia();

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the value.
   */
  void greyScaleValue();

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the intensity.
   */
  void greyScaleIntensity();

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the luma.
   */
  void greyScaleLuma();

  /**
   * This function is used to save the current file and all the contents within it to a PPM file.
   *
   * @throws IOException -  if the file exists but is a directory rather than a regular file,
   *                     does not exist but cannot be created, or cannot be opened for any other
   *                     reason
   */
  void saveImage(String filename) throws IOException;

  /**
   * This method creates a greyscale image with the red-component of the image.
   */
  void greyScaleRed();

  /**
   * This method creates a greyscale image with the blue-component of the image.
   */
  void greyScaleBlue();

  /**
   * This method creates a greyscale image with the green-component of the image.
   */
  void greyScaleGreen();

  /**
   * This method is a void method that will flip this image horizontally.
   */
  void flipImageHorizontally();

  /**
   * This method will flip the image current PPM Image vertically.
   */
  void flipImageVertically();

  /**
   * This method will brighten the image by however much the user wants to brighten the image.
   *
   * @param bright is the level of brightness a user wants to use to brighten an image.
   */
  void brightenImage(int bright);

  /**
   * This method will darken the image by however much the user wants to darken the image.
   *
   * @param dark is the level of darkness a user wants to use to darken an image.
   */
  void darkenImage(int dark);

  /**
   * This allows a user to recieve the filecontent of PPM. This is used so the user can
   * view the image before and after changes.
   *
   * @return the fileContent.
   */
  String getFileContent();

  /**
   * This method will return the fileName String.
   *
   * @return this.fileName, which is the name of the ImageModel.
   */
  String getFileName();


  BufferedImage getImage();

  int[][] histogramMaker();
}
