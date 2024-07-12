package cosc202.andie;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p>
 * The ImagePanel also implements {@link MouseMotionListener} to allow for the display of a cat image that follows the mouse.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class ImagePanel extends JPanel implements MouseMotionListener {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;


    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    private Image sleep1Image;
    private Image awakeImage;
    private Image rightImage;
    private Image leftImage;
    private Image upImage;
    private Image downImage;
    private Image upLeftImage;
    private Image upRightImage;
    private Image downLeftImage;
    private Image downRightImage;
    private int imgState = 1;
    private int mouseX, mouseY;
    private int targetX, targetY;
    private final int SPEED = 4; // Speed of movement in pixels per frame
    private boolean sleeping;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() throws IOException{
        image = new EditableImage();
        scale = 1.0;
        sleep1Image = ImageIO.read(getClass().getClassLoader().getResource("sleep1.png"));
        awakeImage = ImageIO.read(getClass().getClassLoader().getResource("awake.png"));
        rightImage = ImageIO.read(getClass().getClassLoader().getResource("right1.png"));
        leftImage = ImageIO.read(getClass().getClassLoader().getResource("left1.png"));
        upImage = ImageIO.read(getClass().getClassLoader().getResource("up1.png"));
        downImage = ImageIO.read(getClass().getClassLoader().getResource("down1.png"));
        upLeftImage = ImageIO.read(getClass().getClassLoader().getResource("upleft1.png"));
        upRightImage = ImageIO.read(getClass().getClassLoader().getResource("upright1.png"));
        downLeftImage = ImageIO.read(getClass().getClassLoader().getResource("downleft1.png")); 
        downRightImage = ImageIO.read(getClass().getClassLoader().getResource("downright1.png"));
        addMouseMotionListener(this);

        // Initialize target position to current mouse position
        mouseX = 0;
        mouseY = 0;
        targetX = mouseX;
        targetY = mouseY;

        // Start timer to update Neko position
        Timer timer = new Timer(16, new ActionListener() { // 16ms for ~60 fps
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNekoPosition();
            }
        });
        timer.start();

        Timer imgtimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(imgState == 1) {
                    try {
                        sleep1Image = ImageIO.read(getClass().getClassLoader().getResource("sleep2.png"));
                        rightImage = ImageIO.read(getClass().getClassLoader().getResource("right2.png"));
                        leftImage = ImageIO.read(getClass().getClassLoader().getResource("left2.png"));
                        upImage = ImageIO.read(getClass().getClassLoader().getResource("up2.png"));
                        downImage = ImageIO.read(getClass().getClassLoader().getResource("down2.png"));
                        upLeftImage = ImageIO.read(getClass().getClassLoader().getResource("upleft2.png"));
                        upRightImage = ImageIO.read(getClass().getClassLoader().getResource("upright2.png"));
                        downLeftImage = ImageIO.read(getClass().getClassLoader().getResource("downleft2.png"));
                        downRightImage = ImageIO.read(getClass().getClassLoader().getResource("downright2.png"));
                    } catch (IOException e1) {}
                    imgState = 2;
                }else {
                    try {
                        sleep1Image = ImageIO.read(getClass().getClassLoader().getResource("sleep1.png"));
                        rightImage = ImageIO.read(getClass().getClassLoader().getResource("right1.png"));
                        leftImage = ImageIO.read(getClass().getClassLoader().getResource("left1.png"));
                        upImage = ImageIO.read(getClass().getClassLoader().getResource("up1.png"));
                        downImage = ImageIO.read(getClass().getClassLoader().getResource("down1.png"));
                        upLeftImage = ImageIO.read(getClass().getClassLoader().getResource("upleft1.png"));
                        upRightImage = ImageIO.read(getClass().getClassLoader().getResource("upright1.png"));
                        downLeftImage = ImageIO.read(getClass().getClassLoader().getResource("downleft1.png"));
                        downRightImage = ImageIO.read(getClass().getClassLoader().getResource("downright1.png"));
                    } catch (IOException e1) {}
                    imgState = 1;
                }
            }
        });
        imgtimer.start();
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Check if there is an image in the target.
     * </p>
     * 
     * @return True if there is an image in the target, false otherwise.
     */
    public boolean hasImage(){
        if(image.hasImage()){
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }


    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int) Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            return new Dimension(1280, 720);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    try{

        Image backImage = ImageIO.read(getClass().getClassLoader().getResource("ntb.png"));
        g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);

    }catch(IOException e){
        System.out.println("No image");
    }
    
    // Draw the image
    if (image.hasImage()) {
        int imageWidth = (int) Math.round(image.getCurrentImage().getWidth() * scale);
        int imageHeight = (int) Math.round(image.getCurrentImage().getHeight() * scale);

        int x = (getWidth() - imageWidth)/2;
        int y = (getHeight() - imageHeight)/2;

        Graphics2D g2  = (Graphics2D) g.create();
        g2.scale(scale, scale);
        g2.drawImage(image.getCurrentImage(), null, x, y);
        g2.dispose();
    }

    // Draw the appropriate cat image based on its movement
    if (mouseX == targetX && mouseY == targetY) {
        // Cat is not moving
        g.drawImage(sleep1Image, mouseX, mouseY, this);
    } else {
        // Cat is moving
        int dx = mouseX - targetX;
        int dy = mouseY - targetY;

        if(dx > 2*dy && dx > -2*dy) {
            g.drawImage(leftImage, mouseX, mouseY, this);
        }else if(dx < 2*dy && dx < -2*dy) {
            g.drawImage(rightImage, mouseX, mouseY, this);
        }else if(dy > 2*dx && dy > -2*dx) {
            g.drawImage(upImage, mouseX, mouseY, this);
        }else if(dy < 2*dx && dy < -2*dx) {
            g.drawImage(downImage, mouseX, mouseY, this);
        }else if (dx > 0 && dy > 0) {
            g.drawImage(upLeftImage, mouseX, mouseY, this);
        } else if (dx > 0 && dy < 0) {
            g.drawImage(downLeftImage, mouseX, mouseY, this);
        } else if (dx < 0 && dy > 0) {
            g.drawImage(upRightImage, mouseX, mouseY, this);
        } else if (dx < 0 && dy < 0) {
            g.drawImage(downRightImage, mouseX, mouseY, this);
        }
    }
}
    /**
     * <p>
     * Mouse event handler for when the mouse is moved.
     * </p>
     * @param e The MouseEvent that triggered the event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        targetX = e.getX() - awakeImage.getWidth(this) / 2;
        targetY = e.getY() - awakeImage.getHeight(this) / 2;
    }

    /**
     * <p>
     * Mouse event handler for when the mouse is dragged.
     * </p>
     * @param e The MouseEvent that triggered the event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * <p>
     * Update the position of the cat image.
     * </p>
     * 
     */
    private void updateNekoPosition() {
        int dx = targetX - mouseX;
        int dy = targetY - mouseY;
    
        // Calculate the distance between current position and target position
        double distance = Math.sqrt(dx*dx + dy*dy);
    
        // Calculate the angle between current position and target position
        double angle = Math.atan2(dy, dx);
    
        // Calculate the movement along x and y axes using trigonometry
        int moveX = (int) (Math.cos(angle) * Math.min(SPEED, distance));
        int moveY = (int) (Math.sin(angle) * Math.min(SPEED, distance));
    
        if(!sleeping) {
            mouseX += moveX;
            mouseY += moveY;
        }

        repaint();
    }
    
    
}
