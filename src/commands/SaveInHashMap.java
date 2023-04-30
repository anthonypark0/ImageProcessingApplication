package commands;

import java.util.HashMap;
import java.util.Map;

import model.ImageModel;

/**
 * This class stores a map for ImageModels that the user chooses to store. This class allows a
 * user to add ImadeModel(s) and retrieve them throught the controller.
 */
public class SaveInHashMap {
  private final Map<String, ImageModel> savedInputs;

  /**
   * This is a constructor for the saveInHashMap.
   */
  public SaveInHashMap() {
    this.savedInputs = new HashMap<String, ImageModel>();
  }

  /**
   * This function allows the user to add an image into the hashmap so they can retrieve or edit
   * it later if they choose to.
   *
   * @param s is the name of the ImageModel.
   * @param i is the ImageModel that the user wants to add.
   */
  public void addImage(String s, ImageModel i) {
    savedInputs.put(s, i);
  }

  /**
   * This retrieves an ImageModel the user wants to gain access to.
   *
   * @param s is the name of the model, also the key String value used to retrieve the ImageModel.
   * @return the ImageModel.
   */
  public ImageModel getImageModel(String s) {
    return savedInputs.get(s);
  }
}
