package cosc202.andie;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * <p>
 * This class is used to draw shapes on the canvas
 * </p>
 * 
 * @author Ninja-turtles
 */
public class drawShapeOperation implements ImageOperation, java.io.Serializable{
    private char shape;
    private int x1, y1, width, length;
    private int thickness;
    private Color color;
    private boolean fill;
    private ArrayList<Point> points;
    //Selected state
    private boolean wasSelected;

    /**
     * <p>
     * Accessor method for the selected state
     * </p>
     * @return the selected state
     */
    @Override
    public boolean getSelected() {
        return wasSelected;
    }

    /**
     * Constructor for the drawShapeOperation class
     * @param shape the shape to be drawn
     * @param x1 the x-coordinate of the shape
     * @param y1 the y-coordinate of the shape
     * @param width the width of the shape
     * @param length the length of the shape
     * @param thickness the thickness of the shape
     * @param color the color of the shape
     *  @param fill the fill of the shape
     */
    drawShapeOperation(char shape, int x1, int y1, int width, int length, int thickness, Color color, boolean fill) {
        this.shape = shape;
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.length = length;
        this.thickness = thickness;
        this.color = color;
        this.fill = fill;
    }

    /**
     * <p>
     * Constructor for the drawShapeOperation class specifically for freehand drawing
     * </p>
     * @param points the points to be drawn
     * @param thickness the thickness of the shape
     * @param color the color of the shape
     * @param fill the fill of the shape
     * 
     */
    drawShapeOperation(ArrayList<Point> points, int thickness, Color color, boolean fill) {
        this.shape = 'f';
        this.thickness = thickness;
        this.points = points;
        this.color = color;
        this.fill = fill;
    }

    /**
     * <p>
     * Applies the operation to the image
     * </p>
     * @param input the image to be modified
     * @param selected the selected state of the image
     * @return the modified image
     * 
     */
    @Override
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        //Clones the input image
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics2D g = output.createGraphics();
        g.drawImage(input, 0, 0, null);

        //Draws the shape
        switch (shape) {
            case 'l':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));
                g.drawLine(x1, y1, width, length);
                break;
            case 'r':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));
                if (fill) {
                    g.fillRect(x1, y1, width, length);
                } else {
                    g.drawRect(x1, y1, width, length);
                }
                break;
            case 's':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));

                if (width > length) {
                    width = length;
                } else if (length > width){
                    length = width;
                }

                if (fill) {
                    g.fillRect(x1, y1, width, length);
                } else {
                    g.drawRect(x1, y1, width, length);
                }
                break;
            case 'o':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));
                if (fill) {
                    g.fillOval(x1, y1, width, length);
                } else {
                    g.drawOval(x1, y1, width, length);
                }
                break;
            case 'c':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));

                if (width > length) {
                    width = length;
                } else if (length > width){
                    length = width;
                }

                if(fill){
                    g.fillOval(x1, y1,width, length);
                }
                else{
                    g.drawOval(x1, y1, width, length);
                }
                break;
            case 'f':
                g.setColor(color);
                g.setStroke(new java.awt.BasicStroke(thickness));
                for (int i = 0; i < points.size() - 1; i++) {
                    g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
                }
                break;
        }
        return output;
    }


    

}
