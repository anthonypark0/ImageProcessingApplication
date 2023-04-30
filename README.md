This is an Image processing and enhancement coding file. Within this project, we take are
changing an PPM, PNG, JPG, or BMP and are able to interact with the image file that is being used. 
The user is able to change the file, save the file, and edit it, directly with the controller. The
user has the options to brighten the photo, darken the photo, sharpen the photo, 
flip the photo horizontally, flip the photo vertically, as well as apply various filters to the 
image, such as blur, greyscale blue, greyscale green, greyscale, greyscale intensity, 
greyscale luma, greyscale red, greyscale value, and serpia. We also have a save function that allows
a user to save the image directly to the file on their computer. Lastly, we contain a SaveInHashMap
class that is not a subclass of the CommandImage class, but more so used in the controller, so that
a user can save the image and create a reference name to it locally, in case the user chooses to
reference that photo later on. 

In order to write this program, we used the MVC design. We have an interface representing each
part of the program. We have a class representing the Model, View, and the Controller.

Within the model, we have an ImageModel interface. Other classes are able to extend this model. Each
Extensions of this model will pretty much be a representation of another type of image. We have
an ImagePPMModel, which extends the ImageModel interface, which is specifically designed just for
a PPM file. We also have the ImageIOModel, which does all that the ImagePPMModel does, but instead
supports the compatibility for PNG, JPG, and BMP models. This is where we contain all of our 
methods for users to edit the photo, as well as our method that allows a user to change a file from
a ImageIOModel to a ImagePPMModel and vice versa. This was done, so that way we didn't have to 
rewrite all of our functions that we wrote for ImageIOModel, in the ImagePPMModel. 

We also have a view class, which actually includes an abstract class. This abstract class is for
renderImage and renderMessage which is a class that is referenced within the ImagePPMController.
This abstract class contains methods that are specifically used in the Controller. This was made
abstract because it is a not very complicated and every subclass that needs to display an output to
the controller can easily reference the methods in the AbstractImageView class without making a new
method. The view class prints out each image in the style of a PPM and is compatible for 
ImagePPMModel and the ImageIOModel. 

Lastly, we have the Controller. There is interface ImageController, which contains one main method
being playMethod. The ImagePPMController is a controller that works with both the ImageIOModel
and the ImagePPMModel. The controller interacts with the user and the controller works with the
model to make a smooth running program.

With our program, a user is able to change the file, save the file, and edit it, directly with 
the controller. The user has the options to brighten the photo, darken the photo, sharpen the photo,
flip the photo horizontally, flip the photo vertically, as well as apply various filters to the
image, such as blur, greyscale blue, greyscale green, greyscale, greyscale intensity,
greyscale luma, greyscale red, greyscale value, and serpia. They can save an edited image and also 
create a new image with a new reference name locally. 

Since Assignment 4, we made some changes to our code. In order to add these new features and 
support compatibility for these new features, we added a helper function in ImageIOModel and 
ImagePPMModel, both made for converting images. This helper function was written to eliminate the
amount of code rewrite for some functions. Additionally, we made a SaveInHashMap class as we 
realized that having our hashmap stored in our controller was not the best design choice. We
adjusted the controller, simply to fit the new commands, but did not fix any big design in the 
controller. We did however, add a command design pattern overall, which we thought could be 
efficient in deciding when we should play a method in the controller. 

The two example images used as in our file were provided by Northeastern University's CS3500 class 
on the Assignment 5 homepage. 

Assignment 6: We made multiple changes to adjust to the new assignment's requirements. Instead of 
directly referencing our controller and view in the GUI, we only used the model to built our 
GUI as we found that getting output/inputting input into our controller would be an inefficient
and difficult process to build our GUI. Because our view only displayed a text of version of our
image, we therefore found it best to not use the view at all. However, despite the fact that our 
design remains the same, because we thought it would be the best choice to incorporate into our 
code, we also added classes to the ImagePPMModel and ImageIOModel, so we could make the histogram
that is required in Assignment 6. 