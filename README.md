# Image-Manipulation-and-Enhancement-Software

## Authors
- **Aditya Shenoy** - 002054764  
- **Sacheth Nagamangala Srikanth** - 002357518  

---

## Overview
The **Image Manipulation and Enhancement (IME) Tool** is a versatile software application for loading, modifying, and saving images in various formats such as JPEG, JPG, PNG, and PPM. The tool provides advanced image manipulation features, including:

- Brightness adjustment
- Image flipping (horizontal and vertical)
- Visualization of individual RGB channels
- Combination of individual channels into one image
- Filters (e.g., blur, sharpen, sepia)
- Advanced features like color correction, histogram adjustments, downscaling, and masking

The project follows the **Model-View-Controller (MVC)** architecture for modularity, scalability, and ease of maintenance.

---

## Features
1. **Image Operations:**
   - Brighten/Darken
   - Horizontal/Vertical Flip
   - Blur/Sharpen
   - Sepia Tone
   - RGB Channel Splitting/Combining
   - Value, Intensity, and Luma Visualization
   - Advanced Features: Color Correction, Histogram Adjustments, Downscale, Masking

2. **File Handling:**
   - Supports image formats: **JPEG, JPG, PNG, PPM**

3. **Multiple Interfaces:**
   - **CLI-based Interaction**
   - **Script File Execution**
   - **Graphical User Interface (GUI)**

4. **Testing and Error Handling:**
   - Comprehensive tests for controllers, models, and GUI using mocks.
   - Graceful handling of invalid input and unknown commands.

---

## Project Structure

### Model Layer
- **Image.java**: Interface defining image properties like width, height, pixel data, and maximum RGB value.
- **SimpleImage.java**: Implementation of `Image.java`, representing an image as a 2D array of pixels with validation for dimensions and pixel values.
- **Pixel.java**: Interface defining methods to get and set RGB values for a pixel.
- **SimplePixel.java**: Implementation of `Pixel.java`, representing a pixel with RGB values and validation for valid ranges (0-255).
- **ImageHandler.java**: Interface defining basic image manipulation operations like loading, saving, and transformations (blur, sharpen, etc.).
- **SimpleImageHandler.java**: Implements `ImageHandler.java` for handling images and basic operations.
- **ExtendedImageHandlerAdapter.java**: Extends image manipulation operations to include advanced features like downscaling and masking.
- **SimpleExtendedImageHandlerAdapter.java**: Implements the `ExtendedImageHandlerAdapter.java` interface.

### Controller Layer
- **CommandController.java**: Interface for defining image manipulation commands.
- **ScriptController.java**: Interface for reading and executing script files.
- **SimpleScriptController.java**: Reads script files and delegates commands to their respective implementations.
- **GUIController.java**: Handles interactions between the GUI and model, processes user inputs, and delegates commands.

### View Layer
- **View.java**: Interface for displaying success/error messages to the console.
- **SimpleView.java**: Implementation of `View.java` for CLI-based outputs.
- **GUIInterface.java**: Interface for updating the GUI and displaying error messages.
- **SimpleGUI.java**: User-friendly GUI for performing image manipulations with buttons, sliders, and dialogs.

---

## How to Run the Application

### CLI Interaction
1. Compile all files in the project.
2. Run the following command to launch the interactive CLI:
   ```bash
   java Main -text

### Script file Execution
1. Prepare a script file (e.g., script.txt) with the commands for loading, manipulating, and saving images.
2. Run the following command:
   ```bash
   java Main -file <scriptFileName>.txt
3. The program will process each command in the script file and save the results to the specified output directory.

### Launching the GUI
1. Double click / run the .jar executable from the _src_ folder
2. Alternatively, run the following command:
   ```bash
      java Main
3. The graphical user interface will open, allowing you to perform image manipulations using buttons, sliders, and dialogs.


## Output Images

Output images of various operations can be found in the _out_ folder <br/>

Below are sample outputs for various image manipulation operations on the GUI:

###Brighten:
![image](https://github.com/user-attachments/assets/f175dd9e-089e-454c-86c3-d1e953eabf70)

###Sepia:

![image](https://github.com/user-attachments/assets/12a53c19-a564-411e-aa74-424b7b84536c)

###Horizontal Flip:

![image](https://github.com/user-attachments/assets/f750005c-9de7-4933-9a4d-f0d4a6be0086)

###Vertical Flip:

![image](https://github.com/user-attachments/assets/a8feac4f-4e46-45d7-8b6d-7e6b8203c4b3)

###Histogram at the bottom right of the screen, continuously updated in real time with every transformation:

![image](https://github.com/user-attachments/assets/24b87bc6-cb7f-4dc5-adf3-4e84db346598)




## Image citation

Bird Image: [Blue Bird Vector](https://pngtree.com/freepng/blue-bird-vector-or-color-illustration_5266726.html) <br/>
Lake Image: Personal camera

## Author's note:

This project is a collaborative effort for academic purposes. Contributions and feedback are welcome for enhancing the tool further!



