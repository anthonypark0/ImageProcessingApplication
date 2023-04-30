package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import commands.Blur;
import commands.CommandImage;
import commands.Sepia;
import commands.Sharpen;
import commands.BrightenImage;
import commands.DarkenImage;
import commands.FlipImageHorizontally;
import commands.FlipImageVertically;
import commands.GreyScale;
import commands.GreyScaleBlue;
import commands.GreyScaleGreen;
import commands.GreyScaleIntensity;
import commands.GreyScaleRed;
import commands.GreyScaleValue;
import commands.GreyscaleLuma;
import commands.SaveInHashMap;
import model.ImageIOModel;
import model.ImageModel;
import model.ImagePPMModel;
import view.ImagePPMView;

/**
 * This is a class that allows a user to change and interact with a PPM file and make any changes
 * to a PPM file.
 */
public class ImagePPMController implements ImageController {
  private final Readable in;
  private ImagePPMView modelView;


  /**
   * This is the constructor for ImagePPM.
   *
   * @param in is what the user inputs into the program.
   */
  public ImagePPMController(Readable in) {
    if (in == null) {
      throw new IllegalArgumentException("No Null Values");
    }
    this.in = in;
  }

  /**
   * This runs the entire program and works with the user to make any changes to the PPM file if
   * needed.
   *
   * @throws IllegalArgumentException if the program is unable to do whatever the user gives/asks
   *                                  for.
   */
  @Override
  public void playMethod() throws IllegalArgumentException {
    boolean loadedImage = false;
    SaveInHashMap s = new SaveInHashMap();
    CommandImage o;
    ImageModel model;
    ImageModel newModel;
    for (int x = 0; x < 1; x--) {
      if (loadedImage) {
        this.renderImageHelper();
      }
      Scanner scan = new Scanner(this.in);
      String[] spacedOutList;
      String input;
      try {
        input = scan.nextLine();
      } catch (NoSuchElementException e) {
        throw new IllegalArgumentException("Failed input");
      }

      spacedOutList = input.split("\\s+");

      for (int u = 0; u < spacedOutList.length; u++) {

        if (input.contains(".txt")) {
          String nameOfTxt = "";
          for (String r : spacedOutList) {
            if (r.contains(".txt")) {
              input = "";
              nameOfTxt = r;
              break;
            }
          }
          try {
            File myObj = new File(nameOfTxt);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              input += myReader.nextLine() + "\n";
            }
            input += " quit";
            myReader.close();
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file is unable to be found");
          }
          spacedOutList = input.split("\\s+");
        }

        if (spacedOutList[0].equals("quit")) {
          return;
        }
        if (((spacedOutList[u].equals("brighten") || spacedOutList[u].equals("darken"))
                && spacedOutList.length < 4) || (spacedOutList.length < 3)) {
          throw new IllegalArgumentException("invalid command line");
        }
        if (spacedOutList[u].equals("load")) {
          String filename = spacedOutList[u + 1];
          try {
            model = new ImagePPMModel(filename);
          } catch (Exception e) {
            model = new ImageIOModel(filename);
          }
          modelView = new ImagePPMView(model);
          String referralName = spacedOutList[u + 2];
          s.addImage(referralName, model);
          loadedImage = true;

        } else if (spacedOutList[u].equals("save")) {
          String filename = spacedOutList[u + 1];
          String referralname = spacedOutList[u + 2];
          try {
            s.getImageModel(referralname).saveImage(filename);
          } catch (Exception e) {
            throw new IllegalArgumentException();
          }

        } else if (spacedOutList[u].equals("quit")) {
          return;
        } else if (spacedOutList[u].equals("vertical-flip")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new FlipImageVertically();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("horizontal-flip")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new FlipImageHorizontally();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("brighten")) {
          model = s.getImageModel(spacedOutList[u + 2]);
          newModel = createImage(model.getFileName());
          o = new BrightenImage(Integer.parseInt(spacedOutList[u + 1]));
          o.play(newModel);
          s.addImage(spacedOutList[u + 3], newModel);
        } else if (spacedOutList[u].equals("darken")) {
          model = s.getImageModel(spacedOutList[u + 2]);
          newModel = createImage(model.getFileName());
          o = new DarkenImage(Integer.parseInt(spacedOutList[u + 1]));
          o.play(newModel);
          s.addImage(spacedOutList[u + 3], newModel);
        } else if (spacedOutList[u].equals("value-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScaleValue();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("luma-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyscaleLuma();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("intensity-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScaleIntensity();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("red-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScaleRed();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("blue-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScaleBlue();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("green-component")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScaleGreen();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("blur")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new Blur();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("greyscale")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new GreyScale();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("sepia")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new Sepia();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        } else if (spacedOutList[u].equals("sharpen")) {
          model = s.getImageModel(spacedOutList[u + 1]);
          newModel = createImage(model.getFileName());
          o = new Sharpen();
          o.play(newModel);
          s.addImage(spacedOutList[u + 2], newModel);
        }

      }

    }
  }

  /**
   * This is a helper function that lets the view output the image.
   */
  private void renderImageHelper() {
    try {
      modelView.renderImage();
    } catch (IOException e) {
      throw new IllegalStateException("Failed transmission");
    }
  }

  /**
   * This is a helper function that helps the ImageModel create a new Image/save a new image.
   *
   * @param s is the name of the file.
   * @return the Image but in a ImageModel object.
   */
  private ImageModel createImage(String s) {
    ImageModel m;
    try {
      m = new ImagePPMModel(s);
    } catch (Exception e) {
      m = new ImageIOModel(s);
    }
    return m;
  }
}