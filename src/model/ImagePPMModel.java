package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This is a public class that implements ImageModel, and it represents a type of PPM image. This
 * class contains functions that allow a user to adjust how the Image looks. A user is able to do
 * this through the ImageController.
 */

public class ImagePPMModel implements ImageModel {

  private final String filename;
  private ArrayList<ArrayList<ArrayList<Integer>>> color;
  private final int height;
  private final int width;
  private final int maxVal;
  private String fileContent;


  /**
   * This is a constructor that is used to build the ImagePPMModel object.
   *
   * @param filename is the name of the file of the PPM image.
   */
  public ImagePPMModel(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename)); // if fails, it just returns
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("This is not a valid PPM file.");
    }
    this.filename = filename; // this sets the file name
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();

      if (s.charAt(0) != '#') {
        builder.append(s + "\n");
      }
    }
    this.color = new ArrayList<>();
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3") && !token.equals("P6")) {
      throw new IllegalArgumentException("Not a PPM image");
    }
    this.width = sc.nextInt();
    this.height = sc.nextInt();
    this.maxVal = sc.nextInt();

    for (int i = 0; i < height; i++) {
      color.add(new ArrayList<ArrayList<Integer>>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        color.get(i).add(new ArrayList<>());
        color.get(i).get(j).add(r);
        color.get(i).get(j).add(g);
        color.get(i).get(j).add(b);
      }
    }
    this.fileContent = builder.toString();
  }

  /**
   * This runs and saves an image.
   *
   * @param args the arguments.
   * @throws IOException when the method can't fully run.
   */
  public static void main(String[] args) throws IOException {
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

    ImagePPMModel example = new ImagePPMModel("Koala.ppm");
    example.greyScaleValue();
    example.saveImage("blurred.png");
  }

  /**
   * This is a helper function used to reinitialize and rewrite the content that lies within
   * PPM file.
   */
  private void reinitializeContentString() {
    StringBuilder builder = new StringBuilder();
    builder.append("P3"
            + "\n" + this.width + " " + height + "\n" + this.maxVal + "\n");
    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        for (int s = 0; s < 3; s++) {
          builder.append(color.get(i).get(j).get(s) + " ");
        }
      }
      builder.append("\n");
    }

    this.fileContent = builder.toString();
  }

  /**
   * This method creates a greyscale image with the red-component of the image.
   */
  @Override
  public void greyScaleRed() {
    int red;

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        red = color.get(i).get(j).get(0);

        this.color.get(i).get(j).set(0, red);
        this.color.get(i).get(j).set(1, red);
        this.color.get(i).get(j).set(2, red);

      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method creates a greyscale image with the blue-component of the image.
   */
  @Override
  public void greyScaleBlue() {
    int blue;

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        blue = color.get(i).get(j).get(2);

        this.color.get(i).get(j).set(0, blue);
        this.color.get(i).get(j).set(1, blue);
        this.color.get(i).get(j).set(2, blue);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method creates a greyscale image with the green-component of the image.
   */
  @Override
  public void greyScaleGreen() {
    int green;

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        green = color.get(i).get(j).get(1);

        this.color.get(i).get(j).set(0, green);
        this.color.get(i).get(j).set(1, green);
        this.color.get(i).get(j).set(2, green);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This function blurs the current ImageIOModel image.
   */
  @Override
  public void blur() {

    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};


    for (int y = 0; y < color.size(); y++) {
      for (int x = 0; x < color.get(y).size(); x++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
            int imageX = (x - 3 / 2 + i + width) % width;
            int imageY = (y - 3 / 2 + j + height) % height;
            int r = color.get(imageY).get(imageX).get(0);
            int g = color.get(imageY).get(imageX).get(1);
            int b = color.get(imageY).get(imageX).get(2);
            red += (int) (r * kernel[i][j]);
            green += (int) (g * kernel[i][j]);
            blue += (int) (b * kernel[i][j]);
          }
        }
        this.color.get(y).get(x).set(0, red);
        this.color.get(y).get(x).set(1, green);
        this.color.get(y).get(x).set(2, blue);

      }


      this.reinitializeContentString();
    }
  }

  /**
   * This image applies the sharpen filter to the current ImageIOModel image.
   */
  @Override
  public void sharpen() {
    double[][] kernel = {{-0.125, -0.125, -0.125, -0.125, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, 0.25, 1, 0.25, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, -0.125, -0.125, -0.125, -0.125}};

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        for (int i = 0; i < 5; i++) {
          for (int j = 0; j < 5; j++) {
            int imageX = (x - 3 / 2 + i + width) % width;
            int imageY = (y - 3 / 2 + j + height) % height;
            int r = color.get(imageY).get(imageX).get(0);
            int g = color.get(imageY).get(imageX).get(1);
            int b = color.get(imageY).get(imageX).get(2);
            red += (int) (r * kernel[i][j]);
            green += (int) (g * kernel[i][j]);
            blue += (int) (b * kernel[i][j]);
          }
        }

        if (red > 255) {
          red = 255;
        }
        if (green > 255) {
          green = 255;
        }
        if (blue > 255) {
          blue = 255;
        }

        if (red < 0) {
          red = 0;
        }
        if (green < 0) {
          green = 0;
        }
        if (blue < 0) {
          blue = 0;
        }
        this.color.get(y).get(x).set(0, red);
        this.color.get(y).get(x).set(1, green);
        this.color.get(y).get(x).set(2, blue);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This function applies the greyScale filter to the current ImageIOModel image.
   */
  @Override
  public void greyScale() {
    double[][] kernel = {{0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}};

    for (int row = 0; row < color.size(); row++) {
      for (int col = 0; col < color.get(row).size(); col++) {
        int red = 0;
        int green = 0;
        int blue = 0;

        int r = this.color.get(row).get(col).get(0);
        int g = this.color.get(row).get(col).get(1);
        int b = this.color.get(row).get(col).get(2);

        red = (int) ((r * kernel[0][0]) + (g * kernel[0][1]) + (b * kernel[0][2]));
        green = (int) ((r * kernel[1][0]) + (g * kernel[1][1]) + (b * kernel[1][2]));
        blue = (int) ((r * kernel[2][0]) + (g * kernel[2][1]) + (b * kernel[2][2]));

        if (red > 255) {
          red = 255;
        }
        if (green > 255) {
          green = 255;
        }
        if (blue > 255) {
          blue = 255;
        }

        if (red < 0) {
          red = 0;
        }
        if (green < 0) {
          green = 0;
        }
        if (blue < 0) {
          blue = 0;
        }

        this.color.get(row).get(col).set(0, red);
        this.color.get(row).get(col).set(1, green);
        this.color.get(row).get(col).set(2, blue);


      }
    }

    this.reinitializeContentString();
  }

  /**
   * This function applies a sepia filter to the image.
   */
  @Override
  public void sepia() {
    double[][] kernel = {{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168},
      {0.272, 0.534, 0.131}};
    for (int row = 0; row < color.size(); row++) {
      for (int col = 0; col < color.get(row).size(); col++) {

        int r = this.color.get(row).get(col).get(0);
        int g = this.color.get(row).get(col).get(1);
        int b = this.color.get(row).get(col).get(2);

        int red = (int) ((r * kernel[0][0]) + (g * kernel[0][1]) + (b * kernel[0][2]));
        int green = (int) ((r * kernel[1][0]) + (g * kernel[1][1]) + (b * kernel[1][2]));
        int blue = (int) ((r * kernel[2][0]) + (g * kernel[2][1]) + (b * kernel[2][2]));

        if (red > 255) {
          red = 255;
        }
        if (green > 255) {
          green = 255;
        }
        if (blue > 255) {
          blue = 255;
        }

        if (red < 0) {
          red = 0;
        }
        if (green < 0) {
          green = 0;
        }
        if (blue < 0) {
          blue = 0;
        }

        this.color.get(row).get(col).set(0, red);
        this.color.get(row).get(col).set(1, green);
        this.color.get(row).get(col).set(2, blue);
      }
    }

    this.reinitializeContentString();
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the value.
   */
  @Override
  public void greyScaleValue() {
    int value = 0;
    int red;
    int green;
    int blue;

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        red = color.get(i).get(j).get(0);
        green = color.get(i).get(j).get(1);
        blue = color.get(i).get(j).get(2);

        if (red > green && red > blue) {
          value = red;
        }
        if (green > red && green > blue) {
          value = green;
        }
        if (blue > red && blue > green) {
          value = blue;
        }

        this.color.get(i).get(j).set(0, value);
        this.color.get(i).get(j).set(1, value);
        this.color.get(i).get(j).set(2, value);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the intensity.
   */
  @Override
  public void greyScaleIntensity() {
    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {

        int r = color.get(i).get(j).get(0);
        int g = color.get(i).get(j).get(1);
        int b = color.get(i).get(j).get(2);


        int intensity = r + g + b / 3;

        this.color.get(i).get(j).set(0, intensity);
        this.color.get(i).get(j).set(1, intensity);
        this.color.get(i).get(j).set(2, intensity);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the luma.
   */
  @Override
  public void greyScaleLuma() {
    int r;
    int g;
    int b;

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {

        r = color.get(i).get(j).get(0);
        g = color.get(i).get(j).get(1);
        b = color.get(i).get(j).get(2);

        double luma = 0.2126 * r + 0.7152 * g + 0.0722 * b;

        this.color.get(i).get(j).set(0, (int) luma);
        this.color.get(i).get(j).set(1, (int) luma);
        this.color.get(i).get(j).set(2, (int) luma);
      }
    }
    this.reinitializeContentString();
  }


  /**
   * This function is used to save the current file and all the contents within it to a PPM file.
   *
   * @throws IOException -  if the file exists but is a directory rather than a regular file,
   *                     does not exist but cannot be created, or cannot be opened for any other
   *                     reason
   */
  @Override
  public void saveImage(String filename) throws IOException {
    int lastIndexofPeriod = filename.lastIndexOf('.');
    int filenamelength = filename.length();
    String formatname = filename.substring(lastIndexofPeriod + 1, filenamelength);

    if (!(formatname.equals("ppm"))) {
      this.pixelate();
      ImageIOModel convert = new ImageIOModel(this.filename + "." + formatname);
      convert.saveImage(filename);
    } else {

      File save = new File(filename);
      FileWriter output = new FileWriter(save, false);
      output.write(this.fileContent);
      output.close();
    }
  }

  /**
   * This method is a void method that will flip this image horizontally.
   */
  @Override
  public void flipImageHorizontally() {
    int s1;
    int s2;
    int s3;
    int e1;
    int e2;
    int e3;
    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size() / 2; j++) {
        s1 = color.get(i).get(j).get(0);
        s2 = color.get(i).get(j).get(1);
        s3 = color.get(i).get(j).get(2);

        e1 = color.get(i).get(color.get(i).size() - 1 - j).get(0);
        e2 = color.get(i).get(color.get(i).size() - 1 - j).get(1);
        e3 = color.get(i).get(color.get(i).size() - 1 - j).get(2);

        this.color.get(i).get(color.get(i).size() - 1 - j).set(0, s1);
        this.color.get(i).get(color.get(i).size() - 1 - j).set(1, s2);
        this.color.get(i).get(color.get(i).size() - 1 - j).set(2, s3);

        this.color.get(i).get(j).set(0, e1);
        this.color.get(i).get(j).set(1, e2);
        this.color.get(i).get(j).set(2, e3);
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method will flip the image current PPM Image vertically.
   */
  @Override
  public void flipImageVertically() {
    ArrayList<ArrayList<Integer>> start;
    ArrayList<ArrayList<Integer>> end;
    for (int i = 0; i < this.color.size() / 2; i++) {
      start = this.color.get(i);
      end = this.color.get(this.color.size() - i - 1);
      this.color.set(this.color.size() - i - 1, start);
      this.color.set(i, end);
    }
    this.reinitializeContentString();
  }

  /**
   * This method will brighten the image by however much the user wants to brighten the image.
   *
   * @param bright is the level of brightness a user wants to use to brighten an image.
   */
  @Override
  public void brightenImage(int bright) {
    if (bright < 0) {
      throw new IllegalArgumentException("no negatives");
    }
    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        for (int x = 0; x < 3; x++) {
          if (color.get(i).get(j).get(x) + bright >= this.maxVal) {
            color.get(i).get(j).set(x, this.maxVal);
          } else {
            color.get(i).get(j).set(x, color.get(i).get(j).get(x) + bright);
          }
        }
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This method will darken the image by however much the user wants to darken the image.
   *
   * @param dark is the level of darkness a user wants to use to darken an image.
   */
  @Override
  public void darkenImage(int dark) {
    if (dark < 0) {
      throw new IllegalArgumentException("no negatives");
    }
    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        for (int x = 0; x < 3; x++) {
          if (color.get(i).get(j).get(x) - dark <= 0) {
            color.get(i).get(j).set(x, 0);
          } else {
            color.get(i).get(j).set(x, color.get(i).get(j).get(x) - dark);
          }
        }
      }
    }
    this.reinitializeContentString();
  }

  /**
   * This allows a user to recieve the filecontent of PPM. This is used so the user can
   * view the image before and after changes.
   *
   * @return the fileContent.
   */
  @Override
  public String getFileContent() {
    final String unMutablecopy = this.fileContent;
    return unMutablecopy;
  }

  /**
   * This method will return the fileName String.
   *
   * @return this.fileName, which is the name of the ImageModel.
   */
  @Override
  public String getFileName() {
    return this.filename;
  }

  /**
   * This creates a new non PPM image/file and basically translates the current PPM file to
   * a PNG file.
   */

  public void pixelate() {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        int r = this.color.get(y).get(x).get(0); //red
        int g = this.color.get(y).get(x).get(1); //green
        int b = this.color.get(y).get(x).get(2); //blue
        img.setRGB(x, y, new Color(r, g, b).getRGB());
      }
    }
    try {
      File f = new File(filename + ".png");
      ImageIO.write(img, "png", f);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Override
  public int[][] histogramMaker() {
    int[][] histogramData;
    histogramData = new int[256][4];
    for (int a = 0; a < histogramData.length; a++) {
      for (int b = 0; b < histogramData[a].length; b++) {
        histogramData[a][b] = 0;
      }
    }

    for (int i = 0; i < color.size(); i++) {
      for (int j = 0; j < color.get(i).size(); j++) {
        int r = this.color.get(i).get(j).get(0);
        int g = this.color.get(i).get(j).get(1);
        int b = this.color.get(i).get(j).get(2);
        int avg = r + g + b / 3;

        for (int c = 0; c < histogramData.length; c++) {

          if (r == c) {
            histogramData[c][0]++;
          }
          if (g == c) {
            histogramData[c][1]++;
          }
          if (b == c) {
            histogramData[c][2]++;
          }
          if (avg == c) {
            histogramData[c][3]++;
          }
        }

      }
    }


    return histogramData;
  }

  /**
   * This class gives back the arrayList that represent the colors in the PPM file.
   *
   * @return an ArrayList of an ArrayList of Integers that represent the Color.
   */
  private ArrayList<ArrayList<ArrayList<Integer>>> getColor() {
    return this.color;
  }

  /**
   * This gets the current Bufferedimage that is stored.
   *
   * @return the image.
   */
  @Override
  public BufferedImage getImage() {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        int r = this.color.get(y).get(x).get(0); //red
        int g = this.color.get(y).get(x).get(1); //green
        int b = this.color.get(y).get(x).get(2); //blue
        img.setRGB(x, y, new Color(r, g, b).getRGB());
      }
    }
    return img;
  }
}