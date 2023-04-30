import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JFrame;


import model.ImageModel;

/**
 * Class to draw the histogram graph.
 */
public class DrawGraph extends JPanel {
  private static final Stroke GRAPH_STROKE = new BasicStroke(3f);

  private BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
  private int[][] graphData;

  private JFrame frame = new JFrame("DrawGraph");

  /**
   * Constructor.
   *
   * @param model given ImageModel.
   */
  public DrawGraph(ImageModel model) {

    graphData = model.histogramMaker();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(this);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  /**
   * Paints the histogram graphs.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    double xScale = ((double) getWidth() - 2 * 20) / (graphData.length - 1);
    double yScale = ((double) getHeight() - 2 * 20) / (1000);

    List<Point> graphPointsRed = new ArrayList<Point>();
    for (int i = 0; i < graphData.length; i++) {
      int x1 = (int) (i * xScale + 20);
      int y1 = (int) ((1000 - graphData[i][0]) * yScale + 20);
      graphPointsRed.add(new Point(x1, y1));
    }

    List<Point> graphPointsGreen = new ArrayList<Point>();
    for (int i = 0; i < graphData.length; i++) {
      int x1 = (int) (i * xScale + 20);
      int y1 = (int) ((1000 - graphData[i][1]) * yScale + 20);
      graphPointsGreen.add(new Point(x1, y1));
    }

    List<Point> graphPointsBlue = new ArrayList<Point>();
    for (int i = 0; i < graphData.length; i++) {
      int x1 = (int) (i * xScale + 20);
      int y1 = (int) ((1000 - graphData[i][2]) * yScale + 20);
      graphPointsBlue.add(new Point(x1, y1));
    }

    List<Point> graphPointsIntensity = new ArrayList<Point>();
    for (int i = 0; i < graphData.length; i++) {
      int x1 = (int) (i * xScale + 20);
      int y1 = (int) ((1000 - graphData[i][3]) * yScale + 20);
      graphPointsIntensity.add(new Point(x1, y1));
    }

    // create x and y axes
    g2.drawLine(20, getHeight() - 20, 20, 20);
    g2.drawLine(20, getHeight() - 20, getWidth() - 20, getHeight() - 20);

    g2.setColor(Color.red);
    g2.setStroke(GRAPH_STROKE);
    for (int i = 0; i < 256; i++) {
      int x1 = graphPointsRed.get(i).x;
      int y1 = graphPointsRed.get(i).y;
      if (i != 255) {
        int x2 = graphPointsRed.get(i + 1).x;
        int y2 = graphPointsRed.get(i + 1).y;
        g2.drawLine(x1, y1, x2, y2);
      }
    }

    g2.setColor(Color.green);
    for (int a = 0; a < 256; a++) {
      int x3 = graphPointsGreen.get(a).x;
      int y3 = graphPointsGreen.get(a).y;
      if (a != 255) {
        int x4 = graphPointsGreen.get(a + 1).x;
        int y4 = graphPointsGreen.get(a + 1).y;
        g2.drawLine(x3, y3, x4, y4);
      }
    }

    g2.setColor(Color.blue);
    for (int a = 0; a < 256; a++) {
      int x3 = graphPointsBlue.get(a).x;
      int y3 = graphPointsBlue.get(a).y;
      if (a != 255) {
        int x4 = graphPointsBlue.get(a + 1).x;
        int y4 = graphPointsBlue.get(a + 1).y;
        g2.drawLine(x3, y3, x4, y4);
      }
    }

    g2.setColor(Color.black);
    for (int i = 0; i < 256; i++) {
      int x1 = graphPointsIntensity.get(i).x;
      int y1 = graphPointsIntensity.get(i).y;
      if (i != 255) {
        int x2 = graphPointsIntensity.get(i + 1).x;
        int y2 = graphPointsIntensity.get(i + 1).y;
        g2.drawLine(x1, y1, x2, y2);
      }
    }


  }

  /**
   * Gets the size of the graph window.
   *
   * @return
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

  /**
   * Closes the current graph window.
   */
  public void close() {
    frame.setVisible(false); //you can't see me!
    frame.dispose(); //Destroy the JFrame object
  }
}