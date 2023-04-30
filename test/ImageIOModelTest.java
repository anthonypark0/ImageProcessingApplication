import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.ImageIOModel;
import model.ImagePPMModel;
import view.ImagePPMView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ImageIOModel class.
 */
public class ImageIOModelTest {

  /**
   * Initializes a small example .png file for testing purposes to properly observe each method by
   * initializing a ppm file and the converting to a .png using a previously tested method from
   * the ImagePPM class.
   * @return an ImageIOModel containing output.ppm.png.
   * @throws IOException if communication fails.
   */
  private ImageIOModel init() throws IOException {
    File output = new File("output.ppm");
    FileWriter outt = new FileWriter(output);
    outt.write("P3\n" +
            "# feep.ppm\n" +
            "4 4\n" +
            "255\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0");
    outt.close();


    ImagePPMModel example = new ImagePPMModel("output.ppm");
    example.pixelate();
    ImageIOModel pngExample = new ImageIOModel("output.ppm.png");
    return pngExample;
  }

  /**
   * Tests the constructor for the class.
   * @throws IOException if communication fails.
   */
  @Test
  public void testConstructor() throws IOException {
    ImageIOModel example = this.init();
    assertEquals(4, example.getImage().getWidth());
    assertEquals(4, example.getImage().getHeight());
    assertEquals("output.ppm.png", example.getFileName());
    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", example.getFileContent());
  }

  /**
   * Tests the sepia method.
   */
  @Test
  public void testSepia() {
    try {
      ImageIOModel example = this.init();
      example.sepia();
      example.convertToPPM();
      ImagePPMModel result = new ImagePPMModel("output.ppm.png.ppm");
      result.saveImage("output.ppm");
    } catch (IOException e) {
      return;
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("output.ppm")); // if fails, it just returns
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("This is not a valid PPM file.");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + "\n");
      }
    }

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 0 0 0 0 0 0 8 7 6 \n" +
            "0 0 0 12 11 8 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 12 11 8 0 0 0 \n" +
            "8 7 6 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the flipImageVertically method.
   */
  @Test
  public void testVerticalFlip() throws IOException {
    try {
      File output = new File("output.ppm");
      FileWriter outt = new FileWriter(output);
      outt.write("P3\n" +
              "# feep.ppm\n" +
              "4 4\n" +
              "255\n" +
              " 0  0  0    0  3  0    0  5  0   15  0 15\n" +
              " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
              " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
              "15  0 15    0  1  0    0  0  6    0  0  0");
      outt.close();
    } catch (IOException e) {
      return;
    }

    ImagePPMModel example = new ImagePPMModel("output.ppm");
    example.pixelate();
    ImageIOModel pngExample = new ImageIOModel("output.ppm.png");

    pngExample.flipImageVertically();
    pngExample.convertToPPM();
    ImagePPMModel output = new ImagePPMModel("output.ppm.png.ppm");
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("output.ppm.png.ppm")); // if fails, it just returns
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("This is not a valid PPM file.");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + "\n");
      }
    }

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "15 0 15 0 1 0 0 0 6 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 3 0 0 5 0 15 0 15 \n", builder.toString());
  }

  /**
   * Tests the getFileContent method.
   */
  @Test
  public void testgetFileContent() {
    String filecontent;
    try {
      ImageIOModel example = this.init();
      filecontent = example.getFileContent();
    } catch (IOException e) {
      return;
    }

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", filecontent);
  }

  /**
   * Tests the saveImage method.
   */
  @Test
  public void testsaveImage() {
    try {
      ImageIOModel example = this.init();
      example.saveImage("output.ppm.png");
    } catch (IOException e) {
      return;
    }

    ImageIOModel test = new ImageIOModel("output.ppm.png");

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", test.getFileContent());
  }

  /**
   * Tests the saveImage method's capability to save to a different file.
   */
  @Test
  public void testsaveImagetoDiff() {
    try {
      ImageIOModel example = this.init();
      example.saveImage("output2.ppm.png");
    } catch (IOException e) {
      return;
    }

    ImageIOModel test = new ImageIOModel("output2.ppm.png");
    ImageIOModel testOriginal = new ImageIOModel("output.ppm.png");

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", testOriginal.getFileContent());

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", test.getFileContent());
  }


  /**
   * Tests the saveImage method's capability to save a .png to a .ppm file.
   */
  @Test
  public void testsaveImagetoPPM() {
    try {
      ImageIOModel example = this.init();
      example.saveImage("outputtype.ppm");
    } catch (IOException e) {
      return;
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("outputtype.ppm")); // if fails, it just returns
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("This is not a valid PPM file.");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + "\n");
      }
    }

    assertEquals("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 0 0 0 0 0 0 15 0 15 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "15 0 15 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScale method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScale() throws IOException {

    ImageIOModel example = this.init();
    example.greyScale();
    example.saveImage("grey.png");
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 4 4 4\n" +
            " 0 0 0 11 11 11 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 11 11 11 0 0 0\n" +
            " 4 4 4 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the blur method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testBlur() throws IOException {

    ImageIOModel example = this.init();
    example.blur();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 2 0 2 0 1 0 1 0 1 3 0 3\n" +
            " 0 1 0 0 3 1 0 1 0 0 0 0\n" +
            " 1 0 1 0 1 0 0 3 1 0 0 0\n" +
            " 3 0 3 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the sharpen method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testSharpen() throws IOException {

    ImageIOModel example = this.init();
    example.sharpen();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 18 4 3 2 5 3 6 5 0 9 0\n" +
            " 0 2 1 0 10 6 0 0 0 0 2 2\n" +
            " 1 5 3 3 0 5 3 6 6 0 10 3\n" +
            " 1 5 3 3 0 5 15 4 17 0 21 3\n", view.toString());
  }

  /**
   * Tests the flipImageHorizontally method.
   */
  @Test
  public void testFlipHorizontally() throws IOException {

    ImageIOModel example = this.init();
    example.flipImageHorizontally();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 15 0 15 0 0 0 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 0 0 15 0 15\n", view.toString());
  }

  /**
   * Tests the greyScaleValue method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleValue() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleValue();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n", view.toString());
  }

  /**
   * Tests greyScaleIntensity methods.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleIntensity() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleValue();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n" +
            " 0 0 0 15 15 15 15 15 15 15 15 15\n", view.toString());
  }

  /**
   * Tests the greyScaleLuma method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleLuma() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleLuma();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 4 4 4\n" +
            " 0 0 0 11 11 11 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 11 11 11 0 0 0\n" +
            " 4 4 4 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the greyScaleRed method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleRed() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleRed();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 15 15\n" +
            " 0 0 0 0 0 0 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 0 0 0 0 0\n" +
            " 15 15 15 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the greyScaleGreen method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleGreen() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleGreen();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 15 15 15\n" +
            " 0 0 0 7 7 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 7 7 7 0 0 0\n" +
            " 15 15 15 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the greyScaleBlue method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testGreyScaleBlue() throws IOException {

    ImageIOModel example = this.init();
    example.greyScaleBlue();
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 0 0 0\n" +
            " 0 0 0 15 15 15 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 15 15 15 0 0 0\n" +
            " 0 0 0 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the brighten method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testBrighten() throws IOException {

    ImageIOModel example = this.init();
    example.brightenImage(10);
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 10 10 10 10 10 10 10 10 10 25 10 25\n" +
            " 10 10 10 10 25 17 10 10 10 10 10 10\n" +
            " 10 10 10 10 10 10 10 25 17 10 10 10\n" +
            " 25 10 25 10 10 10 10 10 10 10 10 10\n", view.toString());
  }

  /**
   * Tests if brightened by a value greater than 255, automatically sets each value to
   * 255.
   * @throws IOException if communication fails.
   */
  @Test
  public void testBrightenPastMax() throws IOException {

    ImageIOModel example = this.init();
    example.brightenImage(10000);
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 255 255 255 255 255 255 255 255 255 255 255 255\n" +
            " 255 255 255 255 255 255 255 255 255 255 255 255\n" +
            " 255 255 255 255 255 255 255 255 255 255 255 255\n" +
            " 255 255 255 255 255 255 255 255 255 255 255 255\n", view.toString());
  }

  /**
   * Tests the darkenImage function for when the user inputs a negative value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenImageNegative() {
    try {
      ImageIOModel example = this.init();
      example.brightenImage(-19);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the darken method.
   * @throws IOException if communication fails.
   */
  @Test
  public void testDarken() throws IOException {

    ImageIOModel example = this.init();
    example.darkenImage(10);
    ImagePPMView view = new ImagePPMView(example);

    assertEquals(" 0 0 0 0 0 0 0 0 0 5 0 5\n" +
            " 0 0 0 0 5 0 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 5 0 0 0 0\n" +
            " 5 0 5 0 0 0 0 0 0 0 0 0\n", view.toString());
  }

  /**
   * Tests the darkenImage function for when the user inputs a negative value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenImageNegative() {
    try {
      ImageIOModel example = this.init();
      example.darkenImage(-19);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the getFileName method.
   */
  @Test
  public void testgetFileName() {
    String name;
    try {
      ImageIOModel example = this.init();
      name = example.getFileName();
    } catch (IOException e) {
      return;
    }

    assertEquals("output.ppm.png", name);
  }

  /**
   * Tests the getImage method.
   */
  @Test
  public void testgetImage() throws IOException {
    BufferedImage imagex;
    ImageIOModel example = this.init();
    imagex = example.getImage();

    for (int x = 0; x < imagex.getWidth(); x++) {
      for (int y = 0; y < imagex.getHeight(); y++) {
        assertEquals(example.getImage().getRGB(x, y), imagex.getRGB(x, y));
      }
    }
  }
}