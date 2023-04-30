package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This ImageIOModel represents a png/jpg/bmp image as well as a bunch of functions that allows
 * one to edit the image.
 */
public class ImageIOModel implements ImageModel {

  private final int width;
  private final int height;

  private File f;

  private BufferedImage image;
  private final String filename;


  /**
   * This is the constructor for the ImageIOModel.
   *
   * @param filename is the name of the file.
   */
  public ImageIOModel(String filename) {

    int tempwidth = 963;
    int tempheight = 640;
    try {
      this.f = new File(filename);
      this.image = new BufferedImage(tempwidth, tempheight, BufferedImage.TYPE_INT_ARGB);
      this.image = ImageIO.read(f);
    } catch (IOException e) {
      System.out.println(e);
    }

    this.width = image.getWidth();
    this.height = image.getHeight();
    this.filename = filename;
  }

  /**
   * This function blurs the current ImageIOModel image.
   */
  @Override
  public void blur() {
    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
            int imageX = (x - 3 / 2 + i + width) % width;
            int imageY = (y - 3 / 2 + j + height) % height;
            int test = (x - 3 / 2 + i + width);
            int test2 = 3 % width;
            int p = image.getRGB(imageX, imageY);
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;
            red += (int) (r * kernel[i][j]);
            green += (int) (g * kernel[i][j]);
            blue += (int) (b * kernel[i][j]);
          }
        }
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
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
            int p = image.getRGB(imageX, imageY);
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;
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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This function applies the greyScale filter to the current ImageIOModel image.
   */
  @Override
  public void greyScale() {
    double[][] kernel = {{0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}};

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;

        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

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
        // Pixel is written to output image
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This function applies a sepia filter to the image.
   */
  @Override
  public void sepia() {
    double[][] kernel = {{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168},
      {0.272, 0.534, 0.131}};
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;

        int p = image.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This image converts the current file to a PPM.
   *
   * @throws IOException when the file is unable to be converted.
   */
  public void convertToPPM() throws IOException {
    StringBuilder builder = new StringBuilder();

    builder.append("P3\n" + width + " " + height + "\n" + "255\n");

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        int p = image.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        builder.append(r + " " + g + " " + b + " ");
      }
      builder.append("\n");
    }

    File output = new File(filename + ".ppm");
    FileWriter outt = new FileWriter(output);

    outt.write(String.valueOf(builder));
    outt.close();
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the value.
   */
  @Override
  public void greyScaleValue() {
    int value = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;

        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        if (r > g && r > b) {
          value = r;
        }
        if (g > r && g > b) {
          value = g;
        }
        if (b > r && b > g) {
          value = b;
        }
        red = value;
        green = value;
        blue = value;

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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the intensity.
   */
  @Override
  public void greyScaleIntensity() {
    int value = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        value = r + g + b / 3;
        red = value;
        green = value;
        blue = value;
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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method visualizes the PPM file into a greyScale Image and changes the luma.
   */
  @Override
  public void greyScaleLuma() {
    int value = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        value = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        red = value;
        green = value;
        blue = value;
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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
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
    if (formatname.equals("ppm")) {
      this.convertToPPM();
      ImagePPMModel convert = new ImagePPMModel(this.filename + ".ppm");
      convert.saveImage(filename);
    }
    try {
      f = new File(filename);
      ImageIO.write(this.image, formatname, f);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * This method creates a greyscale image with the red-component of the image.
   */
  @Override
  public void greyScaleRed() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int p = image.getRGB(x, y);
        int red = (p >> 16) & 0xff;
        int green = (p >> 16) & 0xff;
        int blue = (p >> 16) & 0xff;
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method creates a greyscale image with the blue-component of the image.
   */
  @Override
  public void greyScaleBlue() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int p = image.getRGB(x, y);
        int g = (p >> 8) & 0xff;
        int red = g;
        int green = g;
        int blue = g;
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method creates a greyscale image with the green-component of the image.
   */
  @Override
  public void greyScaleGreen() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int p = image.getRGB(x, y);
        int b = p & 0xff;
        int red = b;
        int green = b;
        int blue = b;
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method is a void method that will flip this image horizontally.
   */
  @Override
  public void flipImageHorizontally() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width / 2; x++) {
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        int endp = image.getRGB(width - 1 - x, y);
        int endr = (endp >> 16) & 0xff;
        int endg = (endp >> 8) & 0xff;
        int endb = endp & 0xff;

        image.setRGB(x, y, new Color(endr, endg, endb).getRGB());
        image.setRGB(width - 1 - x, y, new Color(r, g, b).getRGB());
      }
    }
  }

  /**
   * This method will flip the image current PPM Image vertically.
   */
  @Override
  public void flipImageVertically() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height / 2; y++) {
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        int endp = image.getRGB(x, height - 1 - y);
        int endr = (endp >> 16) & 0xff;
        int endg = (endp >> 8) & 0xff;
        int endb = endp & 0xff;

        image.setRGB(x, y, new Color(endr, endg, endb).getRGB());
        image.setRGB(x, height - 1 - y, new Color(r, g, b).getRGB());
      }
    }
  }

  /**
   * This method will brighten the image by however much the user wants to brighten the image.
   *
   * @param bright is the level of brightness a user wants to use to brighten an image.
   */
  @Override
  public void brightenImage(int bright) {
    if (bright < 0) {
      throw new IllegalArgumentException();
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        int red = r + bright;
        int green = g + bright;
        int blue = b + bright;
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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This method will darken the image by however much the user wants to darken the image.
   *
   * @param dark is the level of darkness a user wants to use to darken an image.
   */
  @Override
  public void darkenImage(int dark) {
    if (dark < 0) {
      throw new IllegalArgumentException();
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int p = image.getRGB(x, y);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        red = r - dark;
        green = g - dark;
        blue = b - dark;
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
        image.setRGB(x, y, new Color(red, green, blue).getRGB());
      }
    }
  }

  /**
   * This allows a user to recieve the filecontent of PPM. This is used so the user can
   * view the image before and after changes.
   *
   * @return the fileContent.
   */
  @Override
  public String getFileContent() {
    StringBuilder output = new StringBuilder();
    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
        int p = image.getRGB(y, x);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        output.append(" " + r + " " + g + " " + b);
      }
      output.append("\n");
    }
    return String.valueOf(output);
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
   * This gets the current Bufferedimage that is stored.
   *
   * @return the image.
   */
  @Override
  public BufferedImage getImage() {
    return this.image;
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

    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
        int p = image.getRGB(y, x);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
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

}