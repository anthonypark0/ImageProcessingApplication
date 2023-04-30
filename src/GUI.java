import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import commands.Blur;
import commands.BrightenImage;
import commands.CommandImage;
import commands.DarkenImage;
import commands.FlipImageHorizontally;
import commands.FlipImageVertically;
import commands.GreyScale;
import commands.GreyScaleBlue;
import commands.GreyScaleGreen;
import commands.GreyScaleIntensity;
import commands.GreyScaleRed;
import commands.GreyScaleValue;
import commands.SaveInHashMap;
import commands.Sepia;
import commands.Sharpen;
import model.ImageIOModel;
import model.ImageModel;
import model.ImagePPMModel;

/**
 * This is the GUI class which is the Graphical User Interface and it creates a graphical version
 * of our editing photo program.
 */
public class GUI extends JFrame implements ActionListener {

  private JLabel fileImage;
  private String photoSavedFileName;

  private String filename;

  private String referralName = "referral";
  private String editedName = "edited";

  private ImageModel model = null;
  private ImageModel editingModel = null;
  private SaveInHashMap storage = new SaveInHashMap();

  private DrawGraph graph;

  /**
   * This is the constructor for the GUI.
   */
  public GUI() {

    super();
    JScrollPane mainScrollPane;
    JPanel mainPanel;
    String photoFileName;
    JPanel imagePanel;
    JScrollPane imageScrolled;

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    setSize(500, 500);
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing An Image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);
    photoFileName = "";
    fileImage = new JLabel();

    imageScrolled = new JScrollPane(fileImage);

    imageScrolled.setPreferredSize(new Dimension(600, 400));
    imagePanel.add(imageScrolled);

    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);

    // editing panel
    JPanel editingPanel = new JPanel();
    editingPanel.setBorder(BorderFactory.createTitledBorder("Edit"));
    editingPanel.setLayout(new BoxLayout(editingPanel, BoxLayout.LINE_AXIS));
    mainPanel.add(editingPanel);

    //Flip Horizontally Button
    JPanel flipHEditing = new JPanel();
    flipHEditing.setLayout(new FlowLayout());
    editingPanel.add(flipHEditing);
    JButton flipHEditingButton = new JButton("Flip Horizontally");
    flipHEditingButton.setActionCommand("Flip Horizontally");
    flipHEditingButton.addActionListener(this);
    editingPanel.add(flipHEditingButton);


    //Flip Vertically Button
    JPanel flipVEditing = new JPanel();
    flipVEditing.setLayout(new FlowLayout());
    editingPanel.add(flipVEditing);
    JButton flipVEditingButton = new JButton("Flip vertically");
    flipVEditingButton.setActionCommand("Flip Vertically");
    flipVEditingButton.addActionListener(this);
    editingPanel.add(flipVEditingButton);

    //greyscale Button
    JPanel greyscaleEditing = new JPanel();
    greyscaleEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleEditing);
    JButton greyscaleEditingButton = new JButton("Greyscale");
    greyscaleEditingButton.setActionCommand("GreyScale");
    greyscaleEditingButton.addActionListener(this);
    editingPanel.add(greyscaleEditingButton);

    //greyscale Blue Button
    JPanel greyscaleBEditing = new JPanel();
    greyscaleBEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleBEditing);
    JButton greyscaleBEditingButton = new JButton("Greyscale Blue");
    greyscaleBEditingButton.setActionCommand("Greyscale Blue");
    greyscaleBEditingButton.addActionListener(this);
    editingPanel.add(greyscaleBEditingButton);

    //greyscale Green Button
    JPanel greyscaleGEditing = new JPanel();
    greyscaleGEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleGEditing);
    JButton greyscaleGEditingButton = new JButton("Greyscale Green");
    greyscaleGEditingButton.setActionCommand("Greyscale Green");
    greyscaleGEditingButton.addActionListener(this);
    editingPanel.add(greyscaleGEditingButton);

    //greyscale Red Button
    JPanel greyscaleREditing = new JPanel();
    greyscaleREditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleREditing);
    JButton greyscaleREditingButton = new JButton("Greyscale Red");
    greyscaleREditingButton.setActionCommand("Greyscale Red");
    greyscaleREditingButton.addActionListener(this);
    editingPanel.add(greyscaleREditingButton);

    //greyscale Intensity Button
    JPanel greyscaleIEditing = new JPanel();
    greyscaleIEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleIEditing);
    JButton greyscaleIEditingButton = new JButton("Greyscale Intensity");
    greyscaleIEditingButton.setActionCommand("Greyscale Intensity");
    greyscaleIEditingButton.addActionListener(this);
    editingPanel.add(greyscaleIEditingButton);

    //greyscale Luma Button
    JPanel greyscaleLEditing = new JPanel();
    greyscaleLEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleLEditing);
    JButton greyscaleLEditingButton = new JButton("Greyscale Luma");
    greyscaleLEditingButton.setActionCommand("Greyscale Luma");
    greyscaleLEditingButton.addActionListener(this);
    editingPanel.add(greyscaleLEditingButton);

    //greyscale Value Button
    JPanel greyscaleVEditing = new JPanel();
    greyscaleVEditing.setLayout(new FlowLayout());
    editingPanel.add(greyscaleVEditing);
    JButton greyscaleVEditingButton = new JButton("Greyscale Value");
    greyscaleVEditingButton.setActionCommand("Greyscale Value");
    greyscaleVEditingButton.addActionListener(this);
    editingPanel.add(greyscaleVEditingButton);

    //greyscale Sepia Button
    JPanel sepiaEditing = new JPanel();
    sepiaEditing.setLayout(new FlowLayout());
    editingPanel.add(sepiaEditing);
    JButton sepiaEditingButton = new JButton("Sepia");
    sepiaEditingButton.setActionCommand("Sepia");
    sepiaEditingButton.addActionListener(this);
    editingPanel.add(sepiaEditingButton);

    //blur Button
    JPanel blurEditing = new JPanel();
    blurEditing.setLayout(new FlowLayout());
    editingPanel.add(blurEditing);
    JButton blurEditingButton = new JButton("Blur");
    blurEditingButton.setActionCommand("Blur");
    blurEditingButton.addActionListener(this);
    editingPanel.add(blurEditingButton);

    //sharpen Button
    JPanel sharpenEditing = new JPanel();
    sharpenEditing.setLayout(new FlowLayout());
    editingPanel.add(sharpenEditing);
    JButton sharpenEditingButton = new JButton("Sharpen");
    sharpenEditingButton.setActionCommand("Sharpen");
    sharpenEditingButton.addActionListener(this);
    editingPanel.add(sharpenEditingButton);


    //brighten Button
    JPanel brightenEditing = new JPanel();
    brightenEditing.setLayout(new FlowLayout());
    editingPanel.add(brightenEditing);
    JButton brightenEditingButton = new JButton("Brighten");
    brightenEditingButton.setActionCommand("Bright");
    brightenEditingButton.addActionListener(this);
    editingPanel.add(brightenEditingButton);


    //Darken Button
    JPanel darkenEditing = new JPanel();
    darkenEditing.setLayout(new FlowLayout());
    editingPanel.add(darkenEditing);
    JButton darkenEditingButton = new JButton("Darken");
    darkenEditingButton.setActionCommand("Dark");
    darkenEditingButton.addActionListener(this);
    editingPanel.add(darkenEditingButton);

    // image display
    try {
      BufferedImage myPicture = ImageIO.read(new File(photoFileName));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      add(picLabel);
    } catch (IOException e) {
      System.out.print(e);
    }
    if (this.editingModel != null) {
      mainPanel.add(this.graph);
      this.graph.setLayout(new FlowLayout());
    }
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  /**
   * This performs an action based on e, which is the ActionEvent.
   *
   * @param e the event to be processed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    FileNameExtensionFilter filter = new FileNameExtensionFilter(//filters type of images
            "PPM, JPG, BMP, and PNG", "jpg", "bmp", "ppm", "png");
    int dark = 0;
    CommandImage c = null;
    // TODO Auto-generated method stub
    switch (e.getActionCommand()) {
      case "Open file": {
        final JFileChooser fchooser = new JFileChooser();
        fchooser.setFileFilter(filter); // applies the filter when choosing type of image
        int retvalue = fchooser.showOpenDialog(null); // open image shows
        if (retvalue == JFileChooser.APPROVE_OPTION) { // if it is approved
          File f = fchooser.getSelectedFile(); // file gets selected
          String path = f.getAbsolutePath();
          filename = f.getAbsolutePath();
          fileImage.setIcon(new ImageIcon(path));
          try {
            model = new ImagePPMModel(filename);
            editingModel = new ImagePPMModel(filename);
            storage.addImage(referralName, model);
            storage.addImage(editedName, editingModel);

          } catch (Exception q) {
            model = new ImageIOModel(filename);
            editingModel = new ImageIOModel(filename);
            storage.addImage(referralName, model);
            storage.addImage(editedName, editingModel);

          }

        }
      }
      break;

      case "Save file": {
        if (editingModel == null) {
          break;
        }
        final JFileChooser fchooser = new JFileChooser(); // chooses the file
        int retvalue = fchooser.showSaveDialog(null); // save dialogue comes up
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          photoSavedFileName = f.getAbsolutePath();
          try {
            storage.getImageModel(editedName).saveImage(photoSavedFileName);
          } catch (Exception p) {
            System.out.println(p);
          }
        }
      }
      break;

      case "Flip Horizontally": {
        if (editingModel == null) {
          break;
        }
        c = new FlipImageHorizontally();
        c.play(editingModel);

      }
      break;

      case "Flip Vertically": {
        if (editingModel == null) {
          break;
        }
        c = new FlipImageVertically();
        c.play(editingModel);

      }
      break;

      case "GreyScale": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScale();
        c.play(editingModel);

      }
      break;

      case "Greyscale Blue": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScaleBlue();
        c.play(editingModel);

      }
      break;

      case "Greyscale Green": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScaleGreen();
        c.play(editingModel);

      }
      break;

      case "Greyscale Red": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScaleRed();
        c.play(editingModel);

      }
      break;

      case "Greyscale Intensity": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScaleIntensity();
        c.play(editingModel);

      }
      break;

      case "Greyscale Value": {
        if (editingModel == null) {
          break;
        }
        c = new GreyScaleValue();
        c.play(editingModel);

      }
      break;

      case "Sepia": {
        if (editingModel == null) {
          break;
        }
        c = new Sepia();
        c.play(editingModel);
      }
      break;

      case "Blur": {
        if (editingModel == null) {
          break;
        }
        c = new Blur();
        c.play(editingModel);
      }
      break;
      case "Sharpen": {
        if (editingModel == null) {
          break;
        }
        c = new Sharpen();
        c.play(editingModel);

      }
      break;
      case "Bright": {
        if (editingModel == null) {
          break;
        }
        String inputValue = JOptionPane.showInputDialog("How much would you like to darken "
                + "the image? Please enter a non-negative integer.");
        try {
          dark = Integer.parseInt(inputValue);
        } catch (NumberFormatException q) {
          JOptionPane.showMessageDialog(GUI.this, "Your original input " +
                  "was not a non-negative integer. Try again.");
        }
        c = new BrightenImage(dark);
        c.play(editingModel);
      }
      break;
      case "Dark": {
        if (editingModel == null) {
          break;
        }
        String inputValue = JOptionPane.showInputDialog("How much would you like to darken "
                + "the image? Please enter a non-negative integer.");
        try {
          dark = Integer.parseInt(inputValue);
        } catch (NumberFormatException q) {
          JOptionPane.showMessageDialog(GUI.this, "Your original input " +
                  "was not a non-negative integer. Try again.");
        }
        c = new DarkenImage(dark);
        c.play(editingModel);
      }
      break;
      default: {
        // no action intended in default case
      }
      break;
    }
    try {
      storage.addImage(editedName, editingModel);
    } catch (Exception q) {
      System.out.print(q);
    }
    if (graph != null) {
      graph.close();
    }
    if (editingModel != null) {
      this.graph = new DrawGraph(editingModel);
    }
    fileImage.setIcon(new ImageIcon(editingModel.getImage()));
  }

}