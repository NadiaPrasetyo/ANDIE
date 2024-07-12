package cosc202.andie;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.*;
import javax.imageio.*;


/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class Andie {
    
    //datafield for the main frame
    public static JFrame frame;

    //datafield to keep track of the changes
    public static boolean hasChanged;

    /*
     * Sets the datafield hasChanged to true indicating that changes
     * have been made to the image currently displayed.
     */
    public static void hasChanged(){
        hasChanged = true;
    }

    /*
     * Determines if changes have been made to the currently displayed image or not.
     * @return true if changes have been made, false otherwise.
     */
    public static boolean isChanged(){
        return hasChanged;
    }

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see ToolActions
     * 
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {


        // Set up the main GUI frame
        frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("ninjaturtles.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        Image menubarImage = ImageIO.read(Andie.class.getClassLoader().getResource("ntbackground.png"));

        // Add in menus for various types of action the user may perform.
        MenuBarBackground menuBar = new MenuBarBackground(menubarImage);
        menuBar.getPreferredSize();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        JMenu fa = fileActions.createMenu();
        menuBar.add(fa);


        //Record 
        MacroActions macroActions = new MacroActions();
        menuBar.add(macroActions.createMenu());

        // Likewise Edit menus are verya common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        JMenu ea = editActions.createMenu();
        menuBar.add(ea);

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        JMenu va = viewActions.createMenu();
        menuBar.add(va);

        // View actions control how the image is displayed, but do not alter its actual content
        ToolActions toolActions = new ToolActions();
        JMenu ta = toolActions.createMenu();
        menuBar.add(ta);

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        JMenu fa2 = filterActions.createMenu();
        menuBar.add(fa2);

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        JMenu ca = colourActions.createMenu();
        menuBar.add(ca);

        ToolBar newToolBar = new ToolBar(frame, editActions, viewActions);
        //create the new toolbar on the screen 
        JToolBar jToolBar = newToolBar.createToolBar();



        // A menu to change the Language of Andie
        LanguageActions languageActions = new LanguageActions(frame, menuBar, colourActions, editActions, fileActions, filterActions, toolActions, imagePanel, jToolBar, macroActions);
        menuBar.getSize();
        JMenu la = languageActions.createMenu();
        menuBar.add(la);

        //
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);

        JMenu menu = menuBar.getMenu(7);

        // Determine the AppData directory
        String appDataDir = System.getenv("APPDATA");
        String appDir = appDataDir + "\\AndieNinjaTurtles";
        String settingsFilePath = appDir + "\\settings.txt";

        try {
            // Open the settings file
            File file = new File(settingsFilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Read settings
            String line = reader.readLine();
            JMenuItem jMenuItem = menu.getItem(Integer.parseInt(line));
            jMenuItem.doClick();

            // Close the reader
            reader.close();
            
            System.out.println("Settings loaded successfully.");

        } catch (Exception e) {
            System.err.println("Failed to load settings.");
        }

    }

    private static void saveSettings() {
        String appDataDir = System.getenv("APPDATA");
        String appDir = appDataDir + "\\AndieNinjaTurtles";

        // Create directory if it doesn't exist
        File directory = new File(appDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Path to settings file
        String settingsFilePath = appDir + "\\settings.txt";

        // save settings
        String settings = LanguageActions.getCurrentLanguage();;

        // Save settings to file
        try {
            FileWriter writer = new FileWriter(settingsFilePath);
            writer.write(settings);
            writer.close();
            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save settings.");
        }
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        Locale.setDefault(new Locale.Builder().setLanguage("en").setRegion("NZ").build());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });

        // Add shutdown hook to save settings
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    saveSettings();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
