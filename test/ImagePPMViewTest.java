import org.junit.Test;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;


import model.ImagePPMModel;
import view.ImagePPMView;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the ImagePPMView class.
 */
public class ImagePPMViewTest {

  /**
   * Initializes a ppm image for testing purposes called output.ppm.
   *
   * @return an ImagePPMModel with the output.ppm.
   * @throws IOException if the inputs are not valid.
   */
  private ImagePPMModel init() throws IOException {
    File output = new File("output.ppm");
    FileWriter outt = new FileWriter(output);
    outt.write("P3\n" +
            "# feep.ppm\n" +
            "4 4\n" +
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0");
    outt.close();


    ImagePPMModel example = new ImagePPMModel("output.ppm");
    return example;
  }

  /**
   * Tests the first constructor when passed a null model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testViewConstructor() {
    ImagePPMView exampleView = new ImagePPMView(null);
  }

  /**
   * Tests the second constructor when passed a null Readable in.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSecondViewConstructor() {
    try {
      ImagePPMModel example = this.init();
      ImagePPMView exampleView = new ImagePPMView(example, null);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the toString method.
   */
  @Test
  public void testtoString() {
    try {
      ImagePPMModel example = this.init();
      ImagePPMView exampleView = new ImagePPMView(example);
      assertEquals("P3\n" +
              "4 4\n" +
              "15\n" +
              " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
              " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
              " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
              "15  0 15    0  0  0    0  0  0    0  0  0\n", exampleView.toString());
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the toString method after a method is done on the model.
   */
  @Test
  public void testBrightenImageView() {
    try {
      ImagePPMModel example = this.init();
      example.brightenImage(10);
      ImagePPMView exampleView = new ImagePPMView(example);
      assertEquals("P3\n" +
              "4 4\n" +
              "15\n" +
              "10 10 10 10 10 10 10 10 10 15 10 15 \n" +
              "10 10 10 10 15 15 10 10 10 10 10 10 \n" +
              "10 10 10 10 10 10 10 15 15 10 10 10 \n" +
              "15 10 15 10 10 10 10 10 10 10 10 10 \n", exampleView.toString());
    } catch (IOException e) {
      return;
    }
  }


  /**
   * Tests the toString method after a method is done on the model.
   */
  @Test
  public void testDarkenImageView() {
    try {
      ImagePPMModel example = this.init();
      example.darkenImage(10);
      ImagePPMView exampleView = new ImagePPMView(example);
      assertEquals("P3\n" +
              "4 4\n" +
              "15\n" +
              "0 0 0 0 0 0 0 0 0 5 0 5 \n" +
              "0 0 0 0 5 0 0 0 0 0 0 0 \n" +
              "0 0 0 0 0 0 0 5 0 0 0 0 \n" +
              "5 0 5 0 0 0 0 0 0 0 0 0 \n", exampleView.toString());
    } catch (IOException e) {
      return;
    }
  }

}