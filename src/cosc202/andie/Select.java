package cosc202.andie;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


/**
 * <p>
 * This class is used to select a rectangle area of an image
 * </p>
 * <p>
 * The selected area is applied to the image as a transparent rectangle
 * The unselected area is greyed out
 * </p>
 * @author Ninja-turtles
 */
public class Select extends ImagePanel implements MouseListener {

    //datafields for selection coordinates
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    //datafield for selected area
    private Rectangle selectedArea;
    private ImagePanel target;

    //Image of the original before any operations is added in the selected area
    private BufferedImage beforeSelected;

    /**
     * <p>
     * Accessor for the beforeSelected image
     * </p>
     * @return BufferedImage representing the image before any operations are applied
     */
    public BufferedImage getBeforeSelected() {
        return beforeSelected;
    }

    /**
     * <p>
     * Accessor for the selected area
     * </p>
     * @return Rectangle representing the selected area
     */
    public Rectangle getSelectedArea() {
        return selectedArea;
    }

    /**
     * <p>
     * Constructor for the Select class
     * </p>
     * @param target
     * @throws Exception
     */
    public Select(ImagePanel target) throws Exception{
        this.target = target;
        beforeSelected = target.getImage().getCurrentImage();
        target.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }


    /**
     * <p>
     * Event that mouse is pressed after select is selected
     * Creates a starting point on Rectangle selection
     * </p>
     * @param e MouseEvent representing the mouse event
     */
    public void mousePressed(MouseEvent e) {
        this.startX = e.getX();
        this.startY = e.getY();
        
    }

    /**
     * <p>
     * Event that mouse is released to select for rectangle selection
     * Creates endpoint for rectangle selection
     * </p>
     * 
     * @param e MouseEvent representing the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.endX = e.getX();
        this.endY = e.getY();
        createSelection();
        target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        target.removeMouseListener(this);
    }

    /**empty method for mouse entering field */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**empty method for mouse exiting field */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /** 
     * unused
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    /**
     * Creates a maximum boundary for the selection to be within the image
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        selectedArea.setBounds((int) Math.min(startX, e.getX()) - 50,
        (int) Math.min(startY, e.getY()) - 25,
        (int) Math.abs((e.getX()) - (startX)),
        (int) Math.abs((e.getY()) - (startY)));
        repaint();
    }

    /**
     * <p>
     * Mutator for the selected area
     * </p>
     * @param selectedArea Rectangle representing the selected area
     */
    public void setSelectedArea(Rectangle selectedArea) {
        this.selectedArea = selectedArea;
    }

    /**
     * <p>
     * Creates and applies the selected rectangle
     * </p>
     */
    public void createSelection(){
        //checks that both press and release mouse events has been triggered
        //checks that the release point is positive (rectangle selection can be made)
        if (endX> startX && endY> startY){
            //System.out.println("Pressed: " +  "(" + startX + ", "+ startY +")" + "released: " +  "(" + endX + ", "+ endY +")");            
            selectedArea = new Rectangle(startX, startY, (endX-startX), (endY-startY));
            
            
            apply(beforeSelected);//applies the selected rectangle area
            
        }
    }

    /**
     * <p>  
     * Deselects the selected area
     * </p>
     */
    public void deSelect(){
        removeSelection(target.getImage().getCurrentImage(), false);
    }

    /**
     * <p>
     * Deletes selection from the image
     * Updates the image with the selection removed
     * </p>
     * @param input
     * @return BufferedImage representing the image without the selection
     * 
     */
    public BufferedImage removeSelection(BufferedImage input, Boolean cropping){
        
        //creates a copy of the image (with selection)
        BufferedImage clone = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        Graphics2D g = clone.createGraphics();

        Rectangle selectionToBeRemoved = target.getImage().getSelectedArea();

        // draws the unselected area of the image back in
        //top block
        g.drawImage(beforeSelected, 0, 0, input.getWidth(), selectionToBeRemoved.y, 0, 0, input.getWidth(), selectionToBeRemoved.y, null);
        //left block
        g.drawImage(beforeSelected, 0, selectionToBeRemoved.y, selectionToBeRemoved.x, selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), 0, selectionToBeRemoved.y, selectionToBeRemoved.x, selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), null);
        //right block
        g.drawImage(beforeSelected, selectionToBeRemoved.x + (int)selectionToBeRemoved.getWidth(), selectionToBeRemoved.y, input.getWidth(), selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), selectionToBeRemoved.x + (int)selectionToBeRemoved.getWidth(), selectionToBeRemoved.y, input.getWidth(), selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), null);
        //bottom block
        g.drawImage(beforeSelected, 0, selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), input.getWidth(), input.getHeight(), 0, selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), input.getWidth(), input.getHeight(), null);
        //middle block
        g.drawImage(input, selectionToBeRemoved.x, selectionToBeRemoved.y, selectionToBeRemoved.x + (int)selectionToBeRemoved.getWidth(), selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), selectionToBeRemoved.x, selectionToBeRemoved.y, selectionToBeRemoved.x + (int)selectionToBeRemoved.getWidth(), selectionToBeRemoved.y + (int)selectionToBeRemoved.getHeight(), null);
        g.dispose();

        if(cropping){
            target.getImage().updateSelection(false, true, clone);//updates the selection state

        }else{
            target.getImage().updateSelection(false, false, clone);//updates the selection state
        }
        //removes grey out of background *essential for PNGs
        target.getImage().undo();
        target.getImage().redo();
        selectionToBeRemoved = null;
        selectedArea = null;
        return clone;

    }


    /**
     * <p>
     * Applies the selected rectangle area to the image as a transparent rectangle
     * The unselected area is greyed out
     * Updates the image with the selected area
     * </p>
     * @param input
     * @return BufferedImage representing the image with the selected area
     * 
     * 
     */
    public BufferedImage apply(BufferedImage input){
        //creates a copy of the image
        BufferedImage clone = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
            Graphics2D g = clone.createGraphics();
            
            g.drawImage(input, 0, 0, null);//draws the image onto the clone
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, input.getWidth(), input.getHeight());// greys out image
            
            //check that selection has been made
            if (selectedArea != null) {
                g.setColor(Color.BLACK);
                g.draw(selectedArea);// draws outline of selected area
                //draws the image within the selected area
                g.drawImage(input, selectedArea.x, selectedArea.y, selectedArea.x + selectedArea.width, selectedArea.y + selectedArea.height, selectedArea.x, selectedArea.y, selectedArea.x + selectedArea.width, selectedArea.y + selectedArea.height, null);
                g.dispose();
                target.getImage().updateSelection(true, selectedArea,clone);//updates the selection state


                // checks that the selected area is not null
            } else if (target.getImage().getSelectedArea() != null) {
                g.setColor(Color.BLACK);
                g.draw(target.getImage().getSelectedArea());// draws outline of selected area
                //draws the image within the selected area
                g.drawImage(input, target.getImage().getSelectedArea().x, target.getImage().getSelectedArea().y, target.getImage().getSelectedArea().x + target.getImage().getSelectedArea().width, target.getImage().getSelectedArea().y + target.getImage().getSelectedArea().height, target.getImage().getSelectedArea().x, target.getImage().getSelectedArea().y, target.getImage().getSelectedArea().x + target.getImage().getSelectedArea().width, target.getImage().getSelectedArea().y + target.getImage().getSelectedArea().height, null);
                g.dispose();
                target.getImage().setSelected(true);//updates the selection state
            }
            

        return clone;
    }

    /**
     * <p>
     * This method greys out the unselected area of the image
     * </p>
     * @param input
     * @param selectedArea
     * @return BufferedImage representing the image with the selected area
     */
    public static BufferedImage reSelect(BufferedImage input, Rectangle selectedArea){
        //creates a copy of the image
        BufferedImage clone = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        Graphics2D g = clone.createGraphics();
            
        g.drawImage(input, 0, 0, null);//draws the image onto the clone
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, input.getWidth(), input.getHeight());// greys out image
            
        g.setColor(Color.BLACK);
        g.draw(selectedArea);// draws outline of selected area
        //draws the image within the selected area
        g.drawImage(input, selectedArea.x, selectedArea.y, selectedArea.x + selectedArea.width, selectedArea.y + selectedArea.height, selectedArea.x, selectedArea.y, selectedArea.x + selectedArea.width, selectedArea.y + selectedArea.height, null);
        g.dispose();
                
        return clone;
    }

    /**
     * <p>
     * Applies the selected rectangle area to the image as a transparent rectangle
     * The unselected area is greyed out
     * </p>
     * @param input
     * @param op
     * @param x
     * @param y
     * @param width
     * @param height
     * @return BufferedImage representing the image with the selected area
     * 
     */
    public static BufferedImage selectApply(BufferedImage input, ImageOperation op, int x, int y, int width, int height){
        //creates a copy of the image
        BufferedImage clone = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        BufferedImage filterApplied = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        filterApplied = op.apply(filterApplied, true);//applies the selected rectangle area with the specified filter
            Graphics2D g = clone.createGraphics();
            
                //draws the filtered image within the selected area
                g.drawImage(filterApplied, x, y, x + width, y + height, x, y, x + width, y + height, null);
                g.dispose();
                
        return clone;
    }


}


