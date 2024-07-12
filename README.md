**ANDIE - Non destructive image editor**
COSC202 - Software Engineer project at the University of Otago

## Contributions list
1. Nadia:
    - Gaussian Blur
    - Sharpen Filter
    - Exception Handling
    - Unit testing
    - Continuous integration (pipeline)
    - Region selection (2nd part)
    - Crop to selection (2nd part)
    - Region to selection filter extension (Extra feature)
    - Drawing functions (2nd Part) 
    

2. Jari:
    - LanguageActions (multilingual support)
    - Colour Channel Cycle - fixed
    - Image Invert
    - Block Averaging (2nd part)
    - Brigtness (2nd part)
    - Contrast (2nd part)
    - Random Scattering (2nd part)
    - Neko mouse pet (Extra feature)

3. Lachlan:
    - New Tools Menu drop down
    - Toolbar actions menu (2nd part)
    - Resize Image(Smaller/Larger)
    - Rotate Image(Left/Right)
    - Flip image(Horizontal/Vertical)
    - Keyboard shortcuts (2nd part)
    - Macros (2nd part)

4. Judith:
    - Median Filter
    - Image Export
    - Exception Handling in fileActions
    - Extended filters (2nd part)
    - Edge Handling (2nd part)
    - Emboss filter (2nd part)
    - Sobel Filter (2nd part)
    - The Andie Look(Ninja Turtle Theme - Extra Feature)


## Testing
We mostly did testings based on running ANDIE and clicking buttons to see if the features work well.
We have been cross reviewing each other's code to make sure our features work as they are meant to

We've also added unit testing that contains tests for:
- Gaussian blur
- Sharpen filter
- Median Filter
- Region Selection
- Crop to selection
- Drawing operation
- Macros Testing
- Sobel Filter
- Emboss Filter

This is included in the gradle build. A new folder containing the tests have been added as well.


## Known Issues/Bugs
1. More of a hack than issue - When a selection is created an 'empty' operation is added that doesn't  change the image but creates the selection area and relays it to the EditableImage to be kept in the form of an image operation. This means that when a selection is made, 1 undo and redo will seemingly do nothing to the image


## User Guide
File Actions contain open, save, save as, export and exit. There are checks in place for file types to be opened, save as-ed, and exported to make sure image files are used. There is a check for save and exit to confirm unsaved changes with the user.
A file must be opened for any other buttons to work.

- Open will open only image files, if non-image files are opened, an error popup appears
- Save will save the edited image file, along with it's operations file
- Save As and Export creates a file chooser with file type options png, jpeg, and gif (image files)
- Exit will close ANDIE without saving the image

Edit Actions contain undo, redo, select and draw. Select creates a rectangular selection region that allows the user to crop/ apply filters only in the selected region. Selection does NOT work with resize, rotate, and flip functions. Crop to selection option pops up after a selection area has been made. Draw option allows user to create shapes and lines (including freehand) on the whole image and within a region of selection that can be selected using Select button. Draw provides user with options to change thickness and colour of the lines.

Tool Actions contains resize, rotate and flip image functions. clicking on the tools menu will allow user to see options and the keyboard shortcuts for each button. There is a toolbar for toolactions functions in icons and images just below the menu.

Filter Actions contains sharpen, blur and other filters. The blur submenu contains soft blur and gaussian blur, Median filter, and Mean filter. The other filters submenu contains Emboss filter, Sobel filter, Random scattering, and block averaging.

other information of the filters:
- Sharpen and Soft blur are buttons with a specific value of radius (not determined by the user)
- Median filter and Mean filter creates a popup spinner with a dialog that allows user to choose the radius of the filters to be applied
- Gaussian blur, Random scattering, and Block averaging creates a popup slider that allows user to determine the radius of the filters within the range of the slider
- Emboss filter and Sobel filter creates a popup menu to allow user to choose the direction of the filter

Colour Actions
The colour actions menu contains features that alters the colour of the image.
This menu includes Grey scale, Colour channelling cycle, Invert image, Brightness, and Contrast.
 
 - Grey scale and Invert image are buttons with a fixed function that is accessed by a single click of the button
 - Colour channel cycle has 3 channels (R, G, and B) that the user can individually cycle through - 6 combinations
 - Brightness and Contrast creates a popup slider that allows user to determine the amount of percentage of colour change within the range of the slider

Language Actions
The language menu contains 5 options: English, Maori, French, Spanish, and Japanese. The user can choose between for the ANDIE application. All of the menus will be changed according to the chosen language. The default language is English. Language change will change all menus, error messages, and options (but not description of buttons).


## Keyboard shortcuts
Ctrl +
A - SaveAs
B - Soft Blur
C - Start Macro
D - Draw
E - Exit
F - Zoom Full
G - Gaussian Blur
H - Flip horizontal
I - Zoom in
J - Median filter
K - Brightness
L - Random Scattering
M - Mean Filter
N - Contrast
O - Open
P - Zoom out
Q - Export
R - Redo
S - Save
T - Language English
U - Stop Macro
V - Flip vertical
W - GreyScale
X - Sharpen
Y - Select
Z - Undo
1 - Image Invert
2 - Emboss Filter
3 - Sobel Filter
4 - Colour channel cycle Red and Green
5 - Colour channel cycle Red and Blue
6 - Colour channel cycle Green and Blue
7 - Language Maori
8 - Language French
9 - Language Spanish
0 - Language Japanese
Down - Resize smaller
Up - Resize larger
Right - Rotate right
Left - Rotate left
ScrollLock - Block average
Slash - Import Macro

## Additional Build instructions
There are image files that contain the neko animation frames (in src folder).
Gradle testing has been added in the build.gradle and a test folder has been created to contain the unit tests.

