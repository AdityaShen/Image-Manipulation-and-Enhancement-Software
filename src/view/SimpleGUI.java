package src.view;


import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import src.controller.Operations;

/**
 * Graphical user interface for the IME application. It creates an interactive window where the user
 * can perform various operations on the loaded image and observe the output image.
 */
public class SimpleGUI extends JFrame implements GUIInterface {

  private final JPanel imagePanel;

  private final JLabel imageLabel;
  private final JLabel histogramLabel;

  private final JMenuItem openItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;

  private final JButton redComponentButton;
  private final JButton blueComponentButton;
  private final JButton greenComponentButton;
  private final JButton lumaButton;
  private final JButton intensityButton;
  private final JButton valueButton;
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton sepiaButton;
  private final JButton rgbSplitButton;
  private final JButton rgbCombineButton;
  private final JButton applyCompressButton;
  private final JButton colorCorrectButton;
  private final JButton levelAdjustButton;
  private final JButton downscaleButton;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JButton applyBrightnessButton;
  private final JButton applyOperationButton;
  private final JButton cancelOperationButton;

  private final JSlider compressSlider;
  private final JSlider brightnessSlider = new JSlider(
      JSlider.HORIZONTAL, -255, 255, 0);
  private final FileNameExtensionFilter filter;

  private final JTextField adjustBValue;
  private final JTextField adjustMValue;
  private final JTextField adjustWValue;
  private final JTextField setDownscaleWidth;
  private final JTextField setDownscaleHeight;
  private final JPanel mainPanel;
  private String selectedFilter;
  private String selectedGreyscale;
  private final JButton splitPreviewButton;
  private final JTextField splitPreviewPercentageValue;
  private final JPanel splitPreviewPanel;

  private String currentCommand;


  private Hashtable<Integer, JLabel> createSliderLabels(int currentValue) {
    Hashtable<Integer, JLabel> labels = new Hashtable<>();
    labels.put(currentValue, new JLabel(String.valueOf(currentValue)));
    return labels;
  }


  /**
   * Constructs SimpleGUI class with the specified caption.
   *
   * @param caption The title of the GUI window.
   */
  public SimpleGUI(String caption) {
    super(caption);

    filter = new FileNameExtensionFilter("JPG, PNG, & PPM Images",
        "jpg", "png", "ppm");

    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    openItem = new JMenuItem("Open...");
    saveItem = new JMenuItem("Save As");
    exitItem = new JMenuItem("Exit");

    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(exitItem);

    menuBar.add(fileMenu);

    setJMenuBar(menuBar);

    mainPanel = new JPanel(new GridBagLayout());

    JPanel mainScreen = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new java.awt.Insets(5, 2, 5, 2);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 0.95;
    mainPanel.add(mainScreen, gbc);

    JPanel rtScreen = new JPanel(new GridBagLayout());
    JPanel ltScreen = new JPanel(new GridBagLayout());

    GridBagConstraints rtConstraints = new GridBagConstraints();
    rtConstraints.gridx = 1;
    rtConstraints.gridy = 0;
    rtConstraints.weightx = 0.2;
    rtConstraints.weighty = 1;
    rtConstraints.fill = GridBagConstraints.BOTH;

    rtScreen.setLayout(new BoxLayout(rtScreen, BoxLayout.Y_AXIS));

    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    JPanel componentPanel = new JPanel(new GridBagLayout());
    componentPanel.setBorder(BorderFactory.createTitledBorder("Component"));
    GridBagConstraints componentPanelConstraints = new GridBagConstraints();
    componentPanelConstraints.anchor = GridBagConstraints.CENTER;
    componentPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    componentPanelConstraints.weightx = 1;
    componentPanelConstraints.weighty = 0;
    componentPanelConstraints.insets = new Insets(2, 3, 2, 3);

    redComponentButton = new JButton("Red Component");
    redComponentButton.setToolTipText("Apply red component");
    componentPanelConstraints.gridx = 0;
    componentPanelConstraints.gridy = 0;
    componentPanel.add(redComponentButton, componentPanelConstraints);

    blueComponentButton = new JButton("Blue Component");
    blueComponentButton.setToolTipText("Apply blue component");
    componentPanelConstraints.gridx = 1;
    componentPanelConstraints.gridy = 0;
    componentPanel.add(blueComponentButton, componentPanelConstraints);

    greenComponentButton = new JButton("Green Component");
    greenComponentButton.setToolTipText("Apply green component");
    componentPanelConstraints.gridx = 2;
    componentPanelConstraints.gridy = 0;
    componentPanel.add(greenComponentButton, componentPanelConstraints);
    operationPanel.add(componentPanel, BorderLayout.CENTER);

    JPanel greyscalePanel = new JPanel(new GridBagLayout());
    greyscalePanel.setBorder(BorderFactory.createTitledBorder("Greyscale"));
    GridBagConstraints greyscalePanelConstraints = new GridBagConstraints();
    greyscalePanelConstraints.anchor = GridBagConstraints.CENTER;
    greyscalePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    greyscalePanelConstraints.weightx = 1;
    greyscalePanelConstraints.weighty = 0;
    greyscalePanelConstraints.insets = new Insets(0, 3, 0, 3);

    lumaButton = new JButton("Luma Component");
    lumaButton.setToolTipText("Apply luma component");
    greyscalePanelConstraints.gridx = 0;
    greyscalePanelConstraints.gridy = 0;
    greyscalePanel.add(lumaButton, greyscalePanelConstraints);

    intensityButton = new JButton("Intensity Component");
    intensityButton.setToolTipText("Apply intensity component");
    greyscalePanelConstraints.gridx = 1;
    greyscalePanelConstraints.gridy = 0;
    greyscalePanel.add(intensityButton, greyscalePanelConstraints);

    valueButton = new JButton("Value Component");
    valueButton.setToolTipText("Apply value component");
    greyscalePanelConstraints.gridx = 2;
    greyscalePanelConstraints.gridy = 0;
    greyscalePanel.add(valueButton, greyscalePanelConstraints);
    operationPanel.add(greyscalePanel, BorderLayout.CENTER);

    JPanel flipPanel = new JPanel(new GridBagLayout());
    flipPanel.setBorder(BorderFactory.createTitledBorder("Flip Image"));
    GridBagConstraints flipPanelConstraints = new GridBagConstraints();
    flipPanelConstraints.anchor = GridBagConstraints.CENTER;
    flipPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    flipPanelConstraints.weightx = 0.5;
    flipPanelConstraints.weighty = 0;
    flipPanelConstraints.insets = new Insets(2, 3, 2, 3);

    horizontalFlipButton = new JButton("Horizontal Flip");
    horizontalFlipButton.setToolTipText("Apply horizontal flip");
    flipPanelConstraints.gridx = 0;
    flipPanelConstraints.gridy = 0;
    flipPanel.add(horizontalFlipButton, flipPanelConstraints);

    verticalFlipButton = new JButton("Vertical Flip");
    verticalFlipButton.setToolTipText("Apply vertical flip");
    flipPanelConstraints.gridx = 1;
    flipPanel.add(verticalFlipButton, flipPanelConstraints);

    operationPanel.add(flipPanel, BorderLayout.CENTER); // Adjust the position as needed

    // RGB Operations Panel
    JPanel rgbPanel = new JPanel(new GridBagLayout());
    rgbPanel.setBorder(BorderFactory.createTitledBorder("RGB Operations"));
    GridBagConstraints rgbPanelConstraints = new GridBagConstraints();
    rgbPanelConstraints.anchor = GridBagConstraints.CENTER;
    rgbPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    rgbPanelConstraints.weightx = 0.5; // Each button occupies half the space
    rgbPanelConstraints.weighty = 0;
    rgbPanelConstraints.insets = new Insets(2, 3, 2, 3);

    rgbSplitButton = new JButton("RGB Split");
    rgbSplitButton.setToolTipText("Split the RGB Channels");
    rgbPanelConstraints.gridx = 0;
    rgbPanelConstraints.gridy = 0;
    rgbPanel.add(rgbSplitButton, rgbPanelConstraints);

    rgbCombineButton = new JButton("RGB Combine");
    rgbCombineButton.setToolTipText("Combine the RGB Channels");
    rgbPanelConstraints.gridx = 1;
    rgbPanel.add(rgbCombineButton, rgbPanelConstraints);

    operationPanel.add(rgbPanel, BorderLayout.NORTH);

    JPanel blurSharpenPanel = new JPanel(new GridBagLayout());
    blurSharpenPanel.setBorder(BorderFactory.createTitledBorder("Filter Image"));
    GridBagConstraints blurSharpenConstraints = new GridBagConstraints();
    blurSharpenConstraints.anchor = GridBagConstraints.CENTER;
    blurSharpenConstraints.fill = GridBagConstraints.HORIZONTAL;
    blurSharpenConstraints.weightx = 0.5;
    blurSharpenConstraints.weighty = 0;
    blurSharpenConstraints.insets = new Insets(2, 3, 2, 3);

    blurButton = new JButton("Blur");
    blurButton.setToolTipText("Apply Blur Filter");
    blurSharpenConstraints.gridx = 0;
    blurSharpenConstraints.gridy = 0;
    blurSharpenPanel.add(blurButton, blurSharpenConstraints);

    sharpenButton = new JButton("Sharpen");
    sharpenButton.setToolTipText("Apply Sharpen Filter");
    blurSharpenConstraints.gridx = 1;
    blurSharpenPanel.add(sharpenButton, blurSharpenConstraints);

    operationPanel.add(blurSharpenPanel, BorderLayout.CENTER);

    JPanel colorOperationsPanel = new JPanel(new GridBagLayout());
    colorOperationsPanel.setBorder(BorderFactory.createTitledBorder("Color Operations"));
    GridBagConstraints colorOperationsConstraints = new GridBagConstraints();
    colorOperationsConstraints.anchor = GridBagConstraints.CENTER;
    colorOperationsConstraints.fill = GridBagConstraints.HORIZONTAL;
    colorOperationsConstraints.weightx = 0.5;
    colorOperationsConstraints.weighty = 0;
    colorOperationsConstraints.insets = new Insets(2, 3, 2, 3);

    sepiaButton = new JButton("Sepia");
    sepiaButton.setToolTipText("Apply Sepia Filter");
    colorOperationsConstraints.gridx = 0;
    colorOperationsConstraints.gridy = 0;
    colorOperationsPanel.add(sepiaButton, colorOperationsConstraints);

    colorCorrectButton = new JButton("Color Correct");
    colorCorrectButton.setToolTipText("Apply color correction");
    colorOperationsConstraints.gridx = 1;
    colorOperationsPanel.add(colorCorrectButton, colorOperationsConstraints);

    operationPanel.add(colorOperationsPanel, BorderLayout.CENTER);

    splitPreviewPanel = new JPanel(new GridBagLayout());
    splitPreviewPanel.setBorder(BorderFactory.createTitledBorder("Split preview"));
    GridBagConstraints splitPreviewPanelConstraints = new GridBagConstraints();
    splitPreviewPanelConstraints.anchor = GridBagConstraints.CENTER;
    splitPreviewPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    splitPreviewPanelConstraints.weightx = 1;
    splitPreviewPanelConstraints.weighty = 1;
    splitPreviewPanelConstraints.insets = new Insets(3, 3, 3, 3);

    splitPreviewPercentageValue = new JTextField("0", 3);
    splitPreviewPanelConstraints.gridx = 0;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(splitPreviewPercentageValue, splitPreviewPanelConstraints);

    splitPreviewButton = new JButton("View");
    splitPreviewButton.setToolTipText("Apply split");
    splitPreviewPanelConstraints.gridx = 1;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(splitPreviewButton, splitPreviewPanelConstraints);

    applyOperationButton = new JButton("Apply");
    applyOperationButton.setToolTipText("Apply the current operation on entire image.");
    splitPreviewPanelConstraints.gridx = 2;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(applyOperationButton, splitPreviewPanelConstraints);

    cancelOperationButton = new JButton("Cancel");
    cancelOperationButton.setToolTipText("Cancel the current operation.");
    splitPreviewPanelConstraints.gridx = 3;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(cancelOperationButton, splitPreviewPanelConstraints);

    operationPanel.add(splitPreviewPanel);

    JPanel basicOperationsPanel = new JPanel(new GridBagLayout());
    basicOperationsPanel.setBorder(BorderFactory.createTitledBorder("Brighten"));
    GridBagConstraints basicOperationsPanelConstraints = new GridBagConstraints();
    basicOperationsPanelConstraints.anchor = GridBagConstraints.CENTER;
    basicOperationsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    basicOperationsPanelConstraints.weightx = 1;
    basicOperationsPanelConstraints.weighty = 1;
    basicOperationsPanelConstraints.insets = new Insets(3, 3, 3, 3);

    applyBrightnessButton = new JButton("Apply");

    brightnessSlider.setMajorTickSpacing(100);
    brightnessSlider.setMinorTickSpacing(25);
    brightnessSlider.setPaintTicks(true);
    brightnessSlider.setPaintLabels(true);
    brightnessSlider.setToolTipText("Select brightness level (-255 to 255)");

    brightnessSlider.addChangeListener(e -> {
      int value = brightnessSlider.getValue();
      brightnessSlider.setLabelTable(createSliderLabels(value));
    });

    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridwidth = 2;

    basicOperationsPanelConstraints.gridy = 1;
    basicOperationsPanel.add(brightnessSlider, basicOperationsPanelConstraints);

    basicOperationsPanelConstraints.gridy = 2;
    basicOperationsPanel.add(applyBrightnessButton, basicOperationsPanelConstraints);
    operationPanel.add(basicOperationsPanel);

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    buttonPanel.setBorder(BorderFactory.createTitledBorder("Level Adjust"));
    GridBagConstraints buttonConstraints = new GridBagConstraints();
    buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
    buttonConstraints.weightx = 1.0;
    buttonConstraints.insets = new Insets(3, 3, 3, 3);

    buttonConstraints.gridy = 3;

    adjustBValue = new JTextField("Enter Black", 8);
    adjustBValue.setForeground(Color.GRAY);
    adjustBValue.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (adjustBValue.getText().equals("Enter Black")) {
          adjustBValue.setText("");
          adjustBValue.setForeground(Color.BLACK);
        }
      }

      public void focusLost(FocusEvent e) {
        if (adjustBValue.getText().isEmpty()) {
          adjustBValue.setText(
              "Enter Black");
          adjustBValue.setForeground(Color.GRAY);
        }
      }
    });
    buttonConstraints.gridx = 0;
    buttonPanel.add(adjustBValue, buttonConstraints);

    adjustMValue = new JTextField("Enter Mid", 8);
    adjustMValue.setForeground(Color.GRAY);
    adjustMValue.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (adjustMValue.getText().equals("Enter Mid")) {
          adjustMValue.setText("");
          adjustMValue.setForeground(Color.BLACK);
        }
      }

      public void focusLost(FocusEvent e) {
        if (adjustMValue.getText().isEmpty()) {
          adjustMValue.setText("Enter Mid");
          adjustMValue.setForeground(Color.GRAY);
        }
      }
    });
    buttonConstraints.gridx = 1;
    buttonPanel.add(adjustMValue, buttonConstraints);

    adjustWValue = new JTextField("Enter White", 8);
    adjustWValue.setForeground(Color.GRAY);
    adjustWValue.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (adjustWValue.getText().equals("Enter White")) {
          adjustWValue.setText("");
          adjustWValue.setForeground(Color.BLACK);
        }
      }

      public void focusLost(FocusEvent e) {
        if (adjustWValue.getText().isEmpty()) {
          adjustWValue.setText(
              "Enter White");
          adjustWValue.setForeground(Color.GRAY);
        }
      }
    });
    buttonConstraints.gridx = 2;
    buttonPanel.add(adjustWValue, buttonConstraints);

    levelAdjustButton = new JButton("Level Adjust");
    levelAdjustButton.setToolTipText("Apply level adjust");
    buttonConstraints.gridx = 3;
    buttonPanel.add(levelAdjustButton, buttonConstraints);

    JPanel downscalePanel = new JPanel(new GridBagLayout());
    downscalePanel.setBorder(BorderFactory.createTitledBorder("Downscale"));
    GridBagConstraints downscaleConstraints = new GridBagConstraints();
    downscaleConstraints.fill = GridBagConstraints.HORIZONTAL;
    downscaleConstraints.weightx = 1.0;
    downscaleConstraints.insets = new Insets(3, 3, 3, 3);

    downscaleConstraints.gridy = 3;

    setDownscaleWidth = new JTextField("Set Width", 8);
    setDownscaleWidth.setForeground(Color.GRAY);
    setDownscaleWidth.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (setDownscaleWidth.getText().equals("Set Width")) {
          setDownscaleWidth.setText("");
          setDownscaleWidth.setForeground(Color.BLACK);
        }
      }

      public void focusLost(FocusEvent e) {
        if (setDownscaleWidth.getText().isEmpty()) {
          setDownscaleWidth.setText(
              "Set Width");
          setDownscaleWidth.setForeground(Color.GRAY);
        }
      }
    });
    downscaleConstraints.gridx = 0;
    downscalePanel.add(setDownscaleWidth, downscaleConstraints);

    setDownscaleHeight = new JTextField("Set Height", 8);
    setDownscaleHeight.setForeground(Color.GRAY);
    setDownscaleHeight.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        if (setDownscaleHeight.getText().equals("Set Height")) {
          setDownscaleHeight.setText("");
          setDownscaleHeight.setForeground(Color.BLACK);
        }
      }

      public void focusLost(FocusEvent e) {
        if (setDownscaleHeight.getText().isEmpty()) {
          setDownscaleHeight.setText(
              "Set Height");
          setDownscaleHeight.setForeground(Color.GRAY);
        }
      }
    });
    downscaleConstraints.gridx = 1;
    downscalePanel.add(setDownscaleHeight, downscaleConstraints);

    downscaleButton = new JButton("Downscale");
    downscaleButton.setToolTipText("Apply downscale");
    downscaleConstraints.gridx = 3;
    downscalePanel.add(downscaleButton, downscaleConstraints);

    JPanel compressPanel = new JPanel(new GridBagLayout());
    compressPanel.setBorder(BorderFactory.createTitledBorder("Compress"));
    GridBagConstraints compressPanelConstraints = new GridBagConstraints();
    compressPanelConstraints.anchor = GridBagConstraints.CENTER;
    compressPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    compressPanelConstraints.weightx = 1;
    compressPanelConstraints.weighty = 1;
    compressPanelConstraints.insets = new Insets(3, 3, 3, 3);

    compressSlider = new JSlider(0, 100, 50);
    compressSlider.setMajorTickSpacing(50);
    compressSlider.setMinorTickSpacing(10);
    compressSlider.setPaintTicks(true);
    compressSlider.setPaintLabels(true);
    compressSlider.setToolTipText("Select compression level (0 to 100)");

    compressSlider.addChangeListener(e -> {
      int value = compressSlider.getValue();
      compressSlider.setLabelTable(createSliderLabels(value));
    });

    compressPanelConstraints.gridx = 0;
    compressPanelConstraints.gridwidth = 2;

    compressPanelConstraints.gridy = 1;
    compressPanel.add(compressSlider, compressPanelConstraints);

    applyCompressButton = new JButton("Apply");
    compressPanelConstraints.gridy = 2;
    compressPanel.add(applyCompressButton, compressPanelConstraints);

    operationPanel.add(compressPanel, BorderLayout.AFTER_LAST_LINE);

    operationPanel.add(buttonPanel);

    operationPanel.add(downscalePanel);

    this.enableOperationButtons(false);
    this.enableSplitPreview(false);

    rtScreen.add(operationPanel);

    JScrollPane operationPanelScroller = new JScrollPane(operationPanel);
    operationPanelScroller
        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    rtScreen.add(operationPanelScroller, BorderLayout.CENTER);

    histogramLabel = new JLabel();

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setSize(400, 500);
    histogramPanel.add(histogramLabel);
    histogramPanel.setVisible(true);

    rtScreen.add(histogramPanel);

    GridBagConstraints ltConstraints = new GridBagConstraints();
    ltConstraints.gridx = 0;
    ltConstraints.gridy = 0;
    ltConstraints.weightx = 0.8;
    ltConstraints.weighty = 1;
    ltConstraints.fill = GridBagConstraints.BOTH;

    ltScreen.setLayout(new BorderLayout());

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    ltScreen.add(scroller, BorderLayout.CENTER);

    imageLabel = new JLabel("Please load an image from the menu.");
    imagePanel.add(imageLabel, BorderLayout.CENTER);

    mainScreen.add(ltScreen, ltConstraints);
    mainScreen.add(rtScreen, rtConstraints);

    add(mainPanel);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
    setVisible(true);
  }

  private void enableSplitPreview(boolean enable) {
    splitPreviewPanel.setVisible(enable);
    splitPreviewPercentageValue.setEnabled(enable);
    splitPreviewButton.setEnabled(enable);
    applyOperationButton.setEnabled(enable);
  }

  private void enableOperationButtons(boolean state) {
    redComponentButton.setEnabled(state);
    greenComponentButton.setEnabled(state);
    blueComponentButton.setEnabled(state);
    lumaButton.setEnabled(state);
    intensityButton.setEnabled(state);
    rgbCombineButton.setEnabled(state);
    valueButton.setEnabled(state);
    brightnessSlider.setEnabled(state);
    applyBrightnessButton.setEnabled(state);
    horizontalFlipButton.setEnabled(state);
    verticalFlipButton.setEnabled(state);
    blurButton.setEnabled(state);
    sharpenButton.setEnabled(state);
    sepiaButton.setEnabled(state);
    rgbSplitButton.setEnabled(state);
    rgbCombineButton.setEnabled(state);
    colorCorrectButton.setEnabled(state);
    levelAdjustButton.setEnabled(state);
    adjustBValue.setEnabled(state);
    adjustMValue.setEnabled(state);
    adjustWValue.setEnabled(state);
    compressSlider.setEnabled(state);
    applyCompressButton.setEnabled(state);
    saveItem.setEnabled(state);
    downscaleButton.setEnabled(state);
    setDownscaleWidth.setEnabled(state);
    setDownscaleHeight.setEnabled(state);
  }

  @Override
  public void refreshGUI(String imageName, String histogram, BufferedImage img,
      BufferedImage histogramImage) {
    refreshImage(img);
    refreshHistogram(histogramImage);
  }

  private void refreshImage(BufferedImage img) {
    imageLabel.setText("");
    imageLabel.setIcon(new ImageIcon(img));
  }

  private void refreshHistogram(BufferedImage histogramImage) {
    this.histogramLabel.setIcon(new ImageIcon(histogramImage));
  }

  @Override
  public void splitView(BufferedImage bufferedImage, String splitImageName) {
    try {
      imageLabel.setText("");
      imageLabel.setIcon(new ImageIcon(bufferedImage));

    } catch (InputMismatchException e) {
      displayError("Invalid input. Please provide a valid file.");
    }
  }


  private void filter(Operations operations) {
    try {
      switch (selectedFilter) {
        case "blur":
          operations.blur();
          break;
        case "sharpen":
          operations.sharpen();
          break;
        default:
          throw new InputMismatchException();
      }
    } catch (Exception e) {
      displayError("Invalid operation");
    }
  }

  private void loadImage(Operations operations) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();

      try {
        operations.load(f.getPath());
        imagePanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));

        this.enableOperationButtons(true);

        displaySuccessMessage(mainPanel, "Successfully loaded the image.");
      } catch (IOException | InputMismatchException e) {
        displayError("Invalid file. Please provide valid file in input.");
      }
    }
  }

  private String openFilePathSelector(String defaultFilename) {
    JFileChooser fileChooser = new JFileChooser();

    fileChooser.setFileFilter(filter);

    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  private void rgbCombine(Operations operations) {
    String message = "Please select the red, green and blue components you wish to combine.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Combine",
        JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = openFilePathSelector("red-split.png");
    if (!filepathRed.isEmpty()) {
      filepathGreen = openFilePathSelector("green-split.png");
      if (!filepathGreen.isEmpty()) {
        filepathBlue = openFilePathSelector("blue-split.png");
      }
    }

    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        operations.rgbCombine(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException | InputMismatchException e) {
        displayError(getInvalidFilePathErrorMessage());
      }
    } else {
      displayError(getInvalidFilePathErrorMessage());
    }
  }


  private String openFileSaver(String defaultFilename) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(filter);

    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  private void rgbSplit(Operations operations) {
    String message = "Please provide 3 destinations and image names to save RGB split images.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Split",
        JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = openFileSaver("red-split.png");
    if (!filepathRed.isEmpty()) {
      filepathGreen = openFileSaver("green-split.png");
      if (!filepathGreen.isEmpty()) {
        filepathBlue = openFileSaver("blue-split.png");
      }
    }

    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        operations.rgbSplit(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException e) {
        displayError(getInvalidFilePathErrorMessage());
      } catch (InputMismatchException e) {
        displayError(getPerformOperationErrorMessage());
      }
    } else {
      displayError(getInvalidFilePathErrorMessage());
    }
  }

  private void displaySuccessMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Success",
        JOptionPane.INFORMATION_MESSAGE);
  }


  private void saveImage(Operations operations) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      //Please validate the file format
      try {
        String path = f.getAbsolutePath();
        operations.save(path);
        displaySuccessMessage(mainPanel, "Successfully saved the image.");
      } catch (IOException | InputMismatchException e) {
        displayError("Unable to save the image. Please provide a valid path and try again");
      }
    }
  }

  private void sepia(Operations operations) {
    try {
      operations.sepia();
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }

  private void greyscale(Operations operations) {
    try {
      switch (selectedGreyscale) {
        case "value-component":
          operations.value();
          break;
        case "intensity-component":
          operations.intensity();
          break;
        case "luma-component":
          operations.luma();
          break;
        default:
          throw new InputMismatchException();
      }
    } catch (IOException | InputMismatchException | NullPointerException e) {
      displayError("Please select a valid Greyscale");
    }
  }


  private void horizontalFlip(Operations operations) {
    try {
      operations.horizontalFlip();
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }


  private void verticalFlip(Operations operations) {
    try {
      operations.verticalFlip();
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }


  private void brighten(Operations operations) {
    try {
      int brightnessValue = brightnessSlider.getValue();
      operations.brighten(brightnessValue);
      displaySuccessMessage(mainPanel, "Brightness adjusted by " + brightnessValue);
    } catch (IOException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }


  private void compress(Operations operations) {
    try {
      double compressValue = compressSlider.getValue();
      operations.compress(compressValue);
      displaySuccessMessage(mainPanel, "Image compressed by " + compressValue + "%");
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }


  private void colorCorrect(Operations operations) {
    try {
      operations.colorCorrect();
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }


  private void levelAdjust(Operations operations) {
    try {
      int black = Integer.parseInt(adjustBValue.getText());
      int mid = Integer.parseInt(adjustMValue.getText());
      int white = Integer.parseInt(adjustWValue.getText());
      operations.levelAdjust(black, mid, white);
      adjustBValue.setText("");
      adjustMValue.setText("");
      adjustWValue.setText("");
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
      adjustBValue.setText("");
      adjustMValue.setText("");
      adjustWValue.setText("");
    } catch (NumberFormatException e) {
      displayError("Invalid black, mid or white value. It should be a numeric value");
      adjustBValue.setText("");
      adjustMValue.setText("");
      adjustWValue.setText("");
    }
  }

  private void performDownscale(Operations operations) {
    try {
      int width = Integer.parseInt(setDownscaleWidth.getText());
      int height = Integer.parseInt(setDownscaleHeight.getText());
      operations.downscale(width, height);
      setDownscaleWidth.setText("");
      setDownscaleHeight.setText("");
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
      setDownscaleWidth.setText("");
      setDownscaleHeight.setText("");
    } catch (NumberFormatException e) {
      displayError("Invalid width or height value. It should be a numeric value");
      setDownscaleWidth.setText("");
      setDownscaleHeight.setText("");
    }
  }


  private void previewFilter(Operations operations) {
    try {
      switch (selectedFilter) {
        case "blur":
          this.previewSplit("blur", operations);
          break;
        case "sharpen":
          this.previewSplit("sharpen", operations);
          break;
        default:
          throw new InputMismatchException();
      }
    } catch (InputMismatchException | NullPointerException e) {
      displayError("Please select a valid operation.");
    }
  }


  private void previewSplit(String command, Operations operations) {
    this.enableOperationButtons(false);
    this.enableSplitPreview(true);

    int value = 0;
    try {
      value = Integer.parseInt(splitPreviewPercentageValue.getText());
    } catch (NumberFormatException e) {
      displayError("Invalid Percentage value. It should be a numeric value");
    }

    try {
      String[] args = new String[]{"split", String.valueOf(value)};
      this.currentCommand = command;
      switch (command) {
        case "blur":
          operations.split("blur", args);
          break;
        case "sharpen":
          operations.split("sharpen", args);
          break;
        case "sepia":
          operations.split("sepia", args);
          break;
        case "luma-component":
          operations.split("luma-component", args);
          break;
        case "intensity-component":
          operations.split("intensity-component", args);
          break;
        case "color-correct":
          operations.split("color-correct", args);
          break;
        case "value-component":
          operations.split("value-component", args);
          break;
        case "level-adjust":
          try {
            int black = Integer.parseInt(adjustBValue.getText());
            int mid = Integer.parseInt(adjustMValue.getText());
            int white = Integer.parseInt(adjustWValue.getText());
            String[] leveAdjustArgs = new String[]{String.valueOf(black), String.valueOf(mid),
                String.valueOf(white)};
            String[] finalArgs = Stream.of(leveAdjustArgs, args).flatMap(Stream::of)
                .toArray(String[]::new);
            operations.split("level-adjust", finalArgs);
          } catch (NumberFormatException e) {
            displayError("Invalid black, mid or white value. It should be a positive integer and "
                + "black < mid < white");
            adjustBValue.setText("");
            adjustMValue.setText("");
            adjustWValue.setText("");
            this.enableOperationButtons(true);
            this.enableSplitPreview(false);
          }
          break;
        default:
          throw new InputMismatchException("Invalid Split view operation");
      }
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);
    }
  }

  private void applyFilter(Operations operations) {
    try {
      switch (currentCommand) {
        case "blur":
        case "sharpen":
          this.filter(operations);
          break;
        case "sepia":
          this.sepia(operations);
          break;
        case "luma-component":
          this.selectedGreyscale = "luma-component";
          this.greyscale(operations);
          break;
        case "value-component":
          this.selectedGreyscale = "value-component";
          this.greyscale(operations);
          break;
        case "intensity-component":
          this.selectedGreyscale = "intensity-component";
          this.greyscale(operations);
          break;
        case "color-correct":
          this.colorCorrect(operations);
          break;
        case "downscale":
          this.performDownscale(operations);
          break;
        case "level-adjust":
          this.levelAdjust(operations);
          break;
        default:
          throw new InputMismatchException("Invalid Split view operation");
      }
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);
    } catch (InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    } catch (NumberFormatException e) {
      displayError("Invalid percentage value. It should be a non-negative integer");
    }
  }


  private void cancelFilter(Operations operations) {
    try {
      this.currentCommand = null;
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);

      operations.reloadImage();
    } catch (IOException | InputMismatchException e) {
      displayError(getPerformOperationErrorMessage());
    }
  }

  private void updateSplitPreview(Operations operations) {
    this.previewSplit(currentCommand, operations);
  }

  @Override
  public void addOperations(Operations operations) {
    rgbCombineButton.addActionListener(event -> rgbCombine(operations));

    lumaButton.addActionListener(event -> {
      previewSplit("luma-component", operations);
    });

    intensityButton.addActionListener(event -> {
      previewSplit("intensity-component", operations);
    });

    valueButton.addActionListener(event -> {
      previewSplit("value-component", operations);
    });

    blurButton.addActionListener(event -> {
      selectedFilter = "blur";
      previewSplit("blur", operations);
    });

    sharpenButton.addActionListener(event -> {
      selectedFilter = "sharpen";
      previewSplit("sharpen", operations);
    });

    redComponentButton.addActionListener(event -> {
      try {
        operations.redComponent();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    blueComponentButton.addActionListener(event -> {
      try {
        operations.blueComponent();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    greenComponentButton.addActionListener(event -> {
      try {
        operations.greenComponent();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    horizontalFlipButton.addActionListener(event -> horizontalFlip(operations));

    verticalFlipButton.addActionListener(event -> verticalFlip(operations));

    applyBrightnessButton.addActionListener(event -> brighten(operations));

    rgbSplitButton.addActionListener(event -> rgbSplit(operations));

    exitItem.addActionListener(event -> operations.quit());

    openItem.addActionListener(event -> loadImage(operations));

    saveItem.addActionListener(event -> saveImage(operations));

    sepiaButton.addActionListener(event -> previewSplit("sepia", operations));

    applyCompressButton.addActionListener(event -> compress(operations));

    levelAdjustButton.addActionListener(event -> previewSplit("level-adjust", operations));

    downscaleButton.addActionListener(event -> performDownscale(operations));

    colorCorrectButton.addActionListener(event -> previewSplit("color-correct", operations));

    splitPreviewButton.addActionListener(event -> updateSplitPreview(operations));

    applyOperationButton.addActionListener(event -> applyFilter(operations));

    cancelOperationButton.addActionListener(event -> cancelFilter(operations));
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(mainPanel, error, "An error occurred",
        JOptionPane.ERROR_MESSAGE);
  }

  private String getPerformOperationErrorMessage() {
    return "Failed to perform the operation. "
        + "Please try again with a valid image";
  }

  private String getInvalidFilePathErrorMessage() {
    return "Failed to perform the operation. Please provide a valid file path";
  }
}

