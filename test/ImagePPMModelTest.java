import org.junit.Test;

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
 * Tests for the ImagePPMModel class.
 */
public class ImagePPMModelTest {

  /**
   * Method that initializes a new PPM file called "output.ppm" made up of a few color pixels
   * for testing purposes.
   *
   * @return a new ImagePPMModel initialized with the ppm image output.ppm
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
   * Tests the constructor for when the user inputs a file name that is not a .ppm file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    ImagePPMModel example = new ImagePPMModel("doesn'texist");
  }

  /**
   * Tests the constructor for when a user inputs a .ppm file that is actually not a ppm image
   * file, as it is missing the P3 in the first line of the file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNotaProperPPM() {
    try {
      File output = new File("output.ppm");
      FileWriter outt = new FileWriter(output);
      outt.write("# feep.ppm\n" +
              "4 4\n" +
              "15\n" +
              " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
              " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
              " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
              "15  0 15    0  0  0    0  0  0    0  0  0");
      outt.close();
      ImagePPMModel example = new ImagePPMModel("output.ppm");
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the save image method by saving output.ppm to a new file called outputs.ppm and then
   * reading the file contents of outputs.ppm.
   */
  @Test
  public void testSaveImagetoADifferentFile() {
    try {
      ImagePPMModel example = this.init();
      example.saveImage("outputs.ppm");
    } catch (IOException e) {
      return;
    }
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("outputs.ppm")); // if fails, it just returns
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
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0\n", builder.toString());
  }

  /**
   * Tests the save image method by saving output.ppm to the same file.
   */
  @Test
  public void testSaveImagetoSameFile() {
    try {
      ImagePPMModel example = this.init();
      example.saveImage("output.ppm");
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
            "15\n" +
            " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
            " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
            " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
            "15  0 15    0  0  0    0  0  0    0  0  0\n", builder.toString());
  }

  /**
   * Tests the same image function for png.
   */
  @Test
  public void testSaveImagetoADifferentFileTypePng() {
    try {
      ImagePPMModel example = this.init();
      example.saveImage("outputs.png");
    } catch (IOException e) {
      return;
    }
    ImageIOModel convert = new ImageIOModel("outputs.png");
    ImagePPMView exampleview = new ImagePPMView(convert);
    assertEquals(" 0 0 0 0 0 0 0 0 0 15 0 15\n" +
            " 0 0 0 0 15 7 0 0 0 0 0 0\n" +
            " 0 0 0 0 0 0 0 15 7 0 0 0\n" +
            " 15 0 15 0 0 0 0 0 0 0 0 0\n", exampleview.toString());
  }

  /**
   * Tests the method getFileContent.
   */
  @Test
  public void testgetFileContent() {
    try {
      ImagePPMModel example = this.init();
      String test = example.getFileContent();
      assertEquals("P3\n" +
              "4 4\n" +
              "15\n" +
              " 0  0  0    0  0  0    0  0  0   15  0 15\n" +
              " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
              " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
              "15  0 15    0  0  0    0  0  0    0  0  0\n", test);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the brightenImage function for when the user inputs a negative value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenImageNegative() {
    try {
      ImagePPMModel example = this.init();
      example.brightenImage(-19);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the darkenImage function for when the user inputs a negative value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenImageNegative() {
    try {
      ImagePPMModel example = this.init();
      example.darkenImage(-19);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Tests the brightenImage function by brightening output.ppm by 10.
   */
  @Test
  public void testBrightenImage() {
    try {
      ImagePPMModel example = this.init();
      example.brightenImage(10);
      example.saveImage("output.ppm");
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
            "15\n" +
            "10 10 10 10 10 10 10 10 10 15 10 15 \n" +
            "10 10 10 10 15 15 10 10 10 10 10 10 \n" +
            "10 10 10 10 10 10 10 15 15 10 10 10 \n" +
            "15 10 15 10 10 10 10 10 10 10 10 10 \n", builder.toString());
  }

  /**
   * Tests the brightenImage function by making sure that the values do not surpass the maxVal
   * of the image.
   */
  @Test
  public void testBrightenImageMaxVal() {
    try {
      ImagePPMModel example = this.init();
      example.brightenImage(1000);
      example.saveImage("output.ppm");
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
            "15\n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n", builder.toString());
  }

  /**
   * Tests the darkenImage method by darkening each pixel of output.ppm by 10.
   */
  @Test
  public void testDarkenImage() {
    try {
      ImagePPMModel example = this.init();
      example.darkenImage(10);
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 5 0 5 \n" +
            "0 0 0 0 5 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 5 0 0 0 0 \n" +
            "5 0 5 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the darkenImage function by ensuring that none of the values of the image fall below
   * zero.
   */
  @Test
  public void testDarkenImageZero() {
    try {
      ImagePPMModel example = this.init();
      example.darkenImage(1000);
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }


  /**
   * Tests the greyScaleRed method.
   */
  @Test
  public void testGreyScaleRed() {
    try {
      ImagePPMModel example = this.init();
      example.greyScaleRed();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 15 15 15 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "15 15 15 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScaleGreen method.
   */
  @Test
  public void testGreyScaleGreen() {
    try {
      ImagePPMModel example = this.init();
      example.greyScaleGreen();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 15 15 15 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 15 15 15 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScaleBlue method.
   */
  @Test
  public void testGreyScaleBlue() {
    Scanner sc;
    try {
      ImagePPMModel example = this.init();
      example.greyScaleBlue();
      example.saveImage("output.ppm");
    } catch (IOException e) {
      return;
    }
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 15 15 15 \n" +
            "0 0 0 7 7 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 7 7 7 0 0 0 \n" +
            "15 15 15 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScaleLuma method.
   */
  @Test
  public void testGreyScaleLuma() {
    try {
      ImagePPMModel example = this.init();
      example.greyScaleLuma();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 4 4 4 \n" +
            "0 0 0 11 11 11 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 11 11 11 0 0 0 \n" +
            "4 4 4 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScaleValue method.
   */
  @Test
  public void testGreyScaleValue() {
    try {
      ImagePPMModel example = this.init();
      example.greyScaleValue();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n" +
            "15 15 15 15 15 15 15 15 15 15 15 15 \n", builder.toString());
  }

  /**
   * Tests the greyScaleIntensity method.
   */
  @Test
  public void testGreyScaleIntensity() {
    try {
      ImagePPMModel example = this.init();
      example.greyScaleIntensity();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 20 20 20 \n" +
            "0 0 0 17 17 17 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 17 17 17 0 0 0 \n" +
            "20 20 20 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the flipeImageHorizontally method.
   */
  @Test
  public void testHorizontalFlip() {
    try {
      ImagePPMModel example = this.init();
      example.flipImageHorizontally();
      example.saveImage("output.ppm");
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
            "15\n" +
            "15 0 15 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 15 0 15 \n", builder.toString());
  }

  /**
   * Tests the flipImageVertically method by creating a new output.ppm file (to differentiate
   * from the testHorizontalFlip) and flipping it, then reading the contents of the file in.
   */
  @Test
  public void testVerticalFlip() {
    try {
      File output = new File("output.ppm");
      FileWriter outt = new FileWriter(output);
      outt.write("P3\n" +
              "# feep.ppm\n" +
              "4 4\n" +
              "15\n" +
              " 0  0  0    0  3  0    0  5  0   15  0 15\n" +
              " 0  0  0    0 15  7    0  0  0    0  0  0\n" +
              " 0  0  0    0  0  0    0 15  7    0  0  0\n" +
              "15  0 15    0  1  0    0  0  6    0  0  0");
      outt.close();
    } catch (IOException e) {
      return;
    }

    ImagePPMModel example = new ImagePPMModel("output.ppm");

    example.flipImageVertically();
    try {
      example.saveImage("output.ppm");
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
            "15\n" +
            "15 0 15 0 1 0 0 0 6 0 0 0 \n" +
            "0 0 0 0 0 0 0 15 7 0 0 0 \n" +
            "0 0 0 0 15 7 0 0 0 0 0 0 \n" +
            "0 0 0 0 3 0 0 5 0 15 0 15 \n", builder.toString());
  }

  /**
   * Tests that multiple methods can be used on the same image.
   */
  @Test
  public void testStackMethods() {
    try {
      ImagePPMModel example = this.init();
      example.brightenImage(10);
      example.flipImageVertically();
      example.flipImageHorizontally();
      example.saveImage("outputs.ppm");
    } catch (IOException e) {
      return;
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("outputs.ppm")); // if fails, it just returns
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
            "15\n" +
            "10 10 10 10 10 10 10 10 10 15 10 15 \n" +
            "10 10 10 10 15 15 10 10 10 10 10 10 \n" +
            "10 10 10 10 10 10 10 15 15 10 10 10 \n" +
            "15 10 15 10 10 10 10 10 10 10 10 10 \n", builder.toString());
  }

  /**
   * Tests the method sepia.
   */
  @Test
  public void testSepia() {
    try {
      ImagePPMModel example = this.init();
      example.sepia();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 8 7 6 \n" +
            "0 0 0 12 11 8 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 12 11 8 0 0 0 \n" +
            "8 7 6 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the greyScale method.
   */
  @Test
  public void testGreyScale() {
    try {
      ImagePPMModel example = this.init();
      example.greyScale();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 0 0 0 0 0 0 0 0 4 4 4 \n" +
            "0 0 0 11 11 11 0 0 0 0 0 0 \n" +
            "0 0 0 0 0 0 11 11 11 0 0 0 \n" +
            "4 4 4 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the sharpen method.
   */
  @Test
  public void testSharpen() {
    try {
      ImagePPMModel example = this.init();
      example.sharpen();
      example.saveImage("output.ppm");
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
            "15\n" +
            "0 18 4 3 2 5 3 6 5 0 9 0 \n" +
            "0 2 1 0 10 6 0 0 0 0 2 2 \n" +
            "1 5 3 3 0 5 3 6 6 0 10 3 \n" +
            "1 5 3 3 0 5 15 4 17 0 21 3 \n", builder.toString());
  }

  /**
   * Tests the blur method.
   */
  @Test
  public void testBlur() {
    try {
      ImagePPMModel example = this.init();
      example.blur();
      example.saveImage("output.ppm");
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
            "15\n" +
            "2 0 2 0 1 0 1 0 1 3 0 3 \n" +
            "0 1 0 0 3 1 0 1 0 0 0 0 \n" +
            "1 0 1 0 1 0 0 3 1 0 0 0 \n" +
            "3 0 3 0 0 0 0 0 0 0 0 0 \n", builder.toString());
  }

  /**
   * Tests the getFileName method.
   */
  @Test
  public void testgetFileName() {
    String name;
    try {
      ImagePPMModel example = this.init();
      name = example.getFileName();
    } catch (IOException e) {
      return;
    }

    assertEquals("output.ppm", name);
  }

}