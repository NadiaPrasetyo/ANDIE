package cosc202.andie;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Manages language-related actions and menu creation.
 * </p>
 * 
 * <p>
 * There are 5 implemented languages: English, Maori, Spanish, French, and Japanese.
 * </p>
 * 
 * @author Ninja-turtles
 */ 
public class LanguageActions {

    ResourceBundle bundle;

    protected ArrayList<Action> actions;
    protected JFrame frame;
    protected JMenuBar menus;
    protected ColourActions colourActions;
    protected EditActions editActions;
    protected FileActions fileActions;
    protected FilterActions filterActions;
    protected ToolActions toolActions;
    protected ImagePanel imagePanel;
    protected JToolBar jToolBar;
    protected MacroActions macroActions;

    /**
     * <p>
     * Constructs a LanguageActions object.
     * Initialises default locale to English (New Zealand).
     * </p>
     * 
     * <p>
     * Keyboard shortcuts for language selection:
     * English: T
     * Maori: 7
     * Spanish: 8
     * French: 9
     * Japanese: 0
     * </p>
     * @param frame The main JFrame of andie
     * @param menus The JMenuBar containing menus
     */
    public LanguageActions(JFrame frame, JMenuBar menus, ColourActions colourActions, EditActions editActions, FileActions fileActions, FilterActions filterActions, ToolActions toolActions, ImagePanel imagePanel, JToolBar jToolBar, MacroActions macroActions) {
        Locale.setDefault(new Locale.Builder().setLanguage("en").setRegion("NZ").build());
        this.frame = frame;
        this.menus = menus;
        this.colourActions = colourActions;
        this.editActions = editActions;
        this.fileActions = fileActions;
        this.filterActions = filterActions;
        this.toolActions = toolActions;
        this.imagePanel = imagePanel;
        this.jToolBar = jToolBar;
        this.macroActions = macroActions;

        actions = new ArrayList<Action>();
        actions.add(new LanguageAction("English", null, "Change the language to English", KeyEvent.VK_E, KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK, new Locale.Builder().setLanguage("en").setRegion("NZ").build()));
        actions.add(new LanguageAction("Maori", null, "Hurihia te reo ki te reo Maori", KeyEvent.VK_M, KeyEvent.VK_7, InputEvent.CTRL_DOWN_MASK, new Locale.Builder().setLanguage("mi").setRegion("NZ").build()));
        actions.add(new LanguageAction("Spanish", null, "Cambia el idioma a español", KeyEvent.VK_S, KeyEvent.VK_8, InputEvent.CTRL_DOWN_MASK, new Locale.Builder().setLanguage("sp").setRegion("NZ").build()));
        actions.add(new LanguageAction("French", null, "Changez la langue en français", KeyEvent.VK_F, KeyEvent.VK_9, InputEvent.CTRL_DOWN_MASK, new Locale.Builder().setLanguage("fr").setRegion("NZ").build()));
        actions.add(new LanguageAction("Japanese", null, "言語を日本語に変更します", KeyEvent.VK_J, KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK, new Locale.Builder().setLanguage("jp").setRegion("NZ").build()));
    }

    private static String currentLanguage = "0"; // Default language

    // Method to set the currently selected language
    public static void setCurrentLanguage(String language) {
        currentLanguage = language;
    }

    // Method to get the currently selected language
    public static String getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * <p>
     * Creates and returns a JMenu for language selection.
     * </p>
     * @return The JMenu for language selection
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu("Language");
        languageMenu.setName("language");

        for(Action action: actions) {
            languageMenu.add(new JMenuItem(action));
        }
        
        for (int j = 0; j < languageMenu.getItemCount(); j++) {
            JMenuItem menuItem = languageMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(200, 162, 200));
            }
        }

        return languageMenu;
    }

    /**
     * <p>
    * Inner class for language change action.
    * </p>
    */
    public class LanguageAction extends AbstractAction {
        int acceleratorKey;
        int acceleratorModifier;
        private Locale locale;
        private ResourceBundle bundle;
        
        /**
         * <p>
         * Constructs a LanguageAction object.
         * </p>
         * @param name The name of the language
         * @param icon The icon for the language
         * @param desc The description of the language action
         * @param mnemonic The mnemonic for the language action
         * @param locale The locale representing the language
         */
        LanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier, Locale locale) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
            this.locale = locale;
            this.bundle = ResourceBundle.getBundle("MessageBundle", locale);
        }

        /**
         * <p>
         * Do the language change action.
         * </p>
         * @param e The ActionEvent for the action
         */
        public void actionPerformed(ActionEvent e) {

            System.out.println(locale.toString());

            if(locale.toString().equals("en_NZ")) {
                setCurrentLanguage("0");
            }
            if(locale.toString().equals("mi_NZ")) {
                setCurrentLanguage("1");
            }
            if(locale.toString().equals("sp_NZ")) {
                setCurrentLanguage("2");
            }
            if(locale.toString().equals("fr_NZ")) {
                setCurrentLanguage("3");
            }
            if(locale.toString().equals("jp_NZ")) {
                setCurrentLanguage("4");
            }

            if (Locale.getDefault().equals(locale)) {
                System.out.println("The selected language is already active.");
                return;
            }
        
            Locale.setDefault(locale);

            for(int i = 0; i < jToolBar.getComponentCount(); i++) {

                JButton button = (JButton) jToolBar.getComponentAtIndex(i);
                String key = button.getName();
                button.setText(bundle.getString(key));
                key = "tooltip" + key;
                try {
                    button.setToolTipText(bundle.getString(key));
                } catch (Exception exc) {
                    continue;
                }

            }

            // This works. i dont feel like making it better.

            colourActions.error = bundle.getString("error");
            colourActions.loadimagefirst = bundle.getString("loadimagefirst");
            colourActions.apply = bundle.getString("apply");

            editActions.error = bundle.getString("error");
            editActions.errorundo = bundle.getString("errorundo");
            editActions.errorredo = bundle.getString("errorredo");
            editActions.selectoptions = bundle.getString("selectoptions");
            editActions.selectbutton = bundle.getString("selectbutton");
            editActions.confirmbutton = bundle.getString("confirmbutton");
            editActions.cropbutton = bundle.getString("cropbutton");
            editActions.cancelbutton = bundle.getString("cancelbutton");
            editActions.errorselect = bundle.getString("errorselect");
            editActions.selectconfirmerror = bundle.getString("selectconfirmerror");
            editActions.areaselecterror = bundle.getString("areaselecterror");     
            editActions.drawerror = bundle.getString("drawerror");
            editActions.drawoptions = bundle.getString("drawoptions");
            editActions.drawmenu = bundle.getString("drawmenu");
            editActions.rectangle = bundle.getString("rectangle");
            editActions.square = bundle.getString("square");
            editActions.oval = bundle.getString("oval");
            editActions.circle = bundle.getString("circle");
            editActions.freehand = bundle.getString("freehand");
            editActions.line = bundle.getString("line");
            editActions.drawimage = bundle.getString("drawimage");
            editActions.fillshape = bundle.getString("fillshape");
            editActions.setsettings = bundle.getString("setsettings");
            editActions.setcolor = bundle.getString("setcolor");
            editActions.setthickness = bundle.getString("setthickness");
            editActions.fillmenu = bundle.getString("fillmenu");
            editActions.applydraw = bundle.getString("applydraw");
            editActions.cleardraw = bundle.getString("cleardraw");
            editActions.undoclear = bundle.getString("undoclear");
            editActions.errorclear = bundle.getString("errorclear");

            fileActions.error = bundle.getString("error");
            fileActions.nosave = bundle.getString("nosave");
            fileActions.errorsave = bundle.getString("errorsave");
            fileActions.loadimagefirst = bundle.getString("loadimagefirst");
            fileActions.filetype = bundle.getString("filetype");
            fileActions.errorexport = bundle.getString("errorexport");
            fileActions.unsavedchanges = bundle.getString("unsavedchanges");
            fileActions.unsavedchanges2 = bundle.getString("unsavedchanges2");
            
            filterActions.error = bundle.getString("error");
            filterActions.loadimagefirst = bundle.getString("loadimagefirst");
            filterActions.apply = bundle.getString("apply");
            filterActions.direction = bundle.getString("direction");
            filterActions.radiusstring = bundle.getString("radiusstring");
            filterActions.blockstring = bundle.getString("blockstring");
            filterActions.scatterstring = bundle.getString("scatterstring");
            filterActions.directionnorthwest = bundle.getString("directionnorthwest");
            filterActions.directionnorth = bundle.getString("directionnorth");
            filterActions.directionnortheast = bundle.getString("directionnortheast");
            filterActions.directionsouthwest = bundle.getString("directionsouthwest");
            filterActions.directionsouth = bundle.getString("directionsouth");
            filterActions.directionsoutheast = bundle.getString("directionsoutheast");
            filterActions.directionwest = bundle.getString("directionwest");
            filterActions.directioneast = bundle.getString("directioneast");
            filterActions.directionhorizontal = bundle.getString("directionhorizontal");
            filterActions.directionvertical = bundle.getString("directionvertical");
            
            toolActions.error = bundle.getString("error");
            toolActions.loadimagefirst = bundle.getString("loadimagefirst");
            toolActions.errorresize = bundle.getString("errorresize");
            toolActions.errorrotate = bundle.getString("errorrotate");
            toolActions.errorflip = bundle.getString("errorflip");
            toolActions.errorSelect2 = bundle.getString("errorSelect2");

            macroActions.error = bundle.getString("error");
            macroActions.emptymacro = bundle.getString("emptymacro");
            macroActions.savemacro = bundle.getString("savemacro");
            macroActions.saveimagemacro = bundle.getString("saveimagemacro");
            macroActions.repaintmacro = bundle.getString("repaintmacro");

            imagePanel.getImage().error = bundle.getString("error");
            imagePanel.getImage().openingfile = bundle.getString("openingfile");
            imagePanel.getImage().notcorrecttype = bundle.getString("notcorrecttype");
            imagePanel.getImage().deserializingopsfile = bundle.getString("deserializingopsfile");
            imagePanel.getImage().errorsave = bundle.getString("errorsave");
            imagePanel.getImage().errorexport = bundle.getString("errorexport");
            imagePanel.getImage().selectconfirmerror = bundle.getString("selectconfirmerror");

            for (int i = 0; i < menus.getMenuCount(); i++) {
                JMenu menu = menus.getMenu(i);
                updateMenuText(menu);
                updateMenuAndSubMenuText(menu); // Update menu and submenu items
            }
            refreshUI();
            System.out.println("The language has been changed to " + bundle.getString("currentLang") + "!");
        }

        /**
         * <p>
         * Recursively update the text of menu items within a menu.
         * </p>
         * @param menu The menu to update
         */
        private void updateMenuAndSubMenuText(JMenu menu) {
            for (int j = 0; j < menu.getItemCount(); j++) {
                JMenuItem menuItem = menu.getItem(j);
                if (menuItem != null) {
                    if (menuItem instanceof JMenu) {
                        updateMenuAndSubMenuText((JMenu) menuItem); // Recursively update submenu items
                    }
                    updateMenuText(menuItem);
                }
            }
        }

        /**
         * <p>
         * Updates the text of each item based on the current locale.
         * </p>
         * @param menuItem The JMenuItem to update
         */
        private void updateMenuText(JMenuItem menuItem) {

            String key = menuItem.getName();
            if (key != null && !key.isEmpty()) {
                menuItem.setText(bundle.getString(key));
                key = "tooltip" + key;
                try {
                    menuItem.setToolTipText(bundle.getString(key));
                } catch (Exception e) {
                    //skip
                }
            }

        }

    }

    /**
     * <p>
     * Refreshes the UI to upadte the language change.
     * </p>
     */
    private void refreshUI() {
        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JComponent) {
                updateComponent((JComponent) component);
            }
        }
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    /**
     * <p>
     * Updates the text of the item based on the current locale.
     * </p>
     * @param component The JComponent to update
     */
    private void updateComponent(JComponent component) {
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setText(button.getText());
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            label.setText(label.getText());
        }
    }

}