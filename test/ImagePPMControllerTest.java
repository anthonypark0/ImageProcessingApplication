import org.junit.Test;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import controller.ImageController;
import controller.ImagePPMController;
import model.ImagePPMModel;
import view.ImagePPMView;

import static org.junit.Assert.assertEquals;

/**
 * This is a class that tests the ImagePPM Controller.
 */
public class ImagePPMControllerTest {

  /**
   * Method that initializes a new PPM file called "output.ppm" made up of a few color pixels
   * for testing purposes.
   *
   * @return a new ImagePPMModel initialized with the ppm image output.ppm
   * @throws IOException if transmission fails
   */
  private void init() throws IOException {
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
  }

  /**
   * This test will test NumParameters in the ImageController.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNumParamsView() throws IOException {
    ImageController c = new ImagePPMController(null);
    c.playMethod();
  }

  /**
   * This test will test to make sure an IllegalArgumentException is thrown when it Fails to read
   * the next line.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailstoReadNextLine() throws IOException {
    Readable r = new StringReader("\n \n");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * This test will test to make sure an IllegalArgumentException is thrown when it Fails to read
   * a file, because the file does not exist.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNonexistentFile() throws IOException {
    Readable r = new StringReader("save pee poo");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * This test will test to make sure an IllegalArgumentException is thrown when it Fails to read
   * a file, because the file does not exist.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRefferalName() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output save output.ppm what quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests loading an image.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testLoadImage() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("output.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0\n", out.toString());
  }

  /**
   * Tests if user tries to save to an invalid referral name.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageInvalidReferralName() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output nice save bruh what q");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests an invalid command line that is too short.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommandLine() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests an invalid command line for brighten.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBrightenCommand() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output brighten output.ppm output");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests an invalid command line for darken.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDarkenCommand() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output darken output.ppm output");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests if the loaded ppm file doesn't actually exist.
   *
   * @throws IOException if transmission fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPPMNoExist() throws IOException {
    this.init();
    Readable r = new StringReader("load dontexist.ppm dontexist");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
  }

  /**
   * Tests the saveImage method.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testSaveImage() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0\n", out.toString());
  }

  /**
   * Tests saving two different images.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testTwoSaveImage() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "brighten 10 output output-brighter" +
            " save outputs.ppm output-brighter " +
            "save output.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "10 10 10 10 10 10 10 10 10 15 10 15 \n" +
            "10 10 10 10 15 15 10 10 10 10 10 10 \n" +
            "10 10 10 10 10 10 10 15 15 10 10 10 \n" +
            "15 10 15 10 10 10 10 10 10 10 10 10 \n", out.toString());

    ImagePPMModel exampleModel2 = new ImagePPMModel("output.ppm");
    StringBuilder out2 = new StringBuilder();
    ImagePPMView example2 = new ImagePPMView(exampleModel2, out);
    example2.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0\n", exampleModel2.getFileContent());
  }


  /**
   * This test will test to make sure an image will flip normally in the horizontal flip method.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testHorizontalFlip() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "horizontal-flip output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "15 0 15 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 15 0 15 \n", out.toString());
  }

  /**
   * This test will test to make sure an image will flip normally in the vertical flip method.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testVerticalFlip() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "vertical-flip output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "15 0 15 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 15 0 15 \n", out.toString());
  }

  /**
   * This test will test to make sure an image will brighten to whatever number the
   * user chooses to input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testbrightenImage() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "brighten 10 output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "10 10 10 10 10 10 10 10 10 15 10 15 \n" +
            "10 10 10 10 15 15 10 10 10 10 10 10 \n" +
            "10 10 10 10 10 10 10 15 15 10 10 10 \n" +
            "15 10 15 10 10 10 10 10 10 10 10 10 \n", out.toString());
  }

  /**
   * This test will test to make sure an image will darken to whatever number the
   * user chooses to input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testdarkenImage() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "darken 10 output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 5 0 5 \n" +
            "0 0 0 0 5 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 5 0 0 0 0 \n" +
            "5 0 5 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This test tests that the image will be greyscaled for red based on input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testgreyScaleRed() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "red-component output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 15 15 15 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "15 15 15 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This test tests that the image will be greyscaled for blue based on input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testgreyScaleBlue() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "blue-component output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 15 15 15 \n" +
            "0 0 0 7 7 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 7 7 7 0 0 0 \n" +
            "15 15 15 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This test tests that the image will be greyscaled for green based on input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testgreyScaleGreen() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "green-component output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 15 15 15 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 15 15 15 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This test tests that the image will be greyscaled for luma based on input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testgreyScaleLuma() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "luma-component output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 4 4 4 \n" +
            "0 0 0 11 11 11 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 11 11 11 0 0 0 \n" +
            "4 4 4 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This test tests that the image will be greyscaled for intensity based on input.
   *
   * @throws IOException if transmission fails.
   */
  @Test
  public void testgreyScaleIntensity() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "intensity-component output output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 20 20 20 \n" +
            "0 0 0 17 17 17 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 17 17 17 0 0 0 \n" +
            "20 20 20 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }


  /**
   * This test tests that the image will be greyscaled for intensity based on input.
   */
  @Test
  public void testTextFile() throws IOException {
    this.init();
    Readable r = new StringReader("asdsa sad Assignment4Text.txt asdas");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "0 0 0 0 0 0 0 0 0 20 20 20 \n" +
            "0 0 0 17 17 17 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 17 17 17 0 0 0 \n" +
            "20 20 20 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This tests the Blur Method to make sure that the methods are accepted.
   */
  @Test
  public void testBlurMethod() throws IOException {
    this.init();
    Readable r = new StringReader("load output.ppm output " +
            "blur output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This tests the JPG image is able to go in as an input for the Controller.
   */
  @Test
  public void testJPG() throws IOException {
    this.init();
    Readable r = new StringReader("load tester.jpg output " +
            "blur output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", out.toString());

  }

  /**
   * This tests the PNG image is able to go in as an input for the Controller.
   */
  @Test
  public void testPNG() throws IOException {
    this.init();
    Readable r = new StringReader("load tester.PNG output " +
            "blur output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This tests the BMP image is able to go in as an input for the Controller.
   */
  @Test
  public void testBMP() throws IOException {
    this.init();
    Readable r = new StringReader("load tester.bmp output " +
            "blur output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }

  /**
   * This tests the PPM image is able to go in as an input for the Controller.
   */
  @Test
  public void testPPM() throws IOException {
    this.init();
    Readable r = new StringReader("load tester.PPM output " +
            "blur output output save outputs.ppm output quit");
    ImageController c = new ImagePPMController(r);
    c.playMethod();
    ImagePPMModel exampleModel = new ImagePPMModel("outputs.ppm");
    StringBuilder out = new StringBuilder();
    ImagePPMView example = new ImagePPMView(exampleModel, out);
    example.renderImage();
    assertEquals("P3\n" +
            "4 4\n" +
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", out.toString());
  }
}
