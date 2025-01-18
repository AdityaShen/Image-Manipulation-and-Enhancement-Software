package src.model;

/**
 * The SimpleExtendedImageHandlerAdapter class extends SimpleImageHandlerAdapter and implements
 * ExtendedImageHandlerAdapter, providing additional image processing methods with various
 * operations such as blur, sharpen, sepia, and component-based transformations with or without
 * masks and downscale.
 */
public class SimpleExtendedImageHandlerAdapter extends SimpleImageHandlerAdapter implements
    ExtendedImageHandlerAdapter {

  /**
   * Constructor that calls the superclass constructor.
   */
  public SimpleExtendedImageHandlerAdapter() {
    super();
  }

  @Override
  public void downscale(String imageName, String modifiedImageName, int newWidth, int newHeight) {

    Image originalImage = super.getMap().get(imageName);
    if (originalImage == null) {
      throw new IllegalArgumentException("No image found with name: " + imageName);
    }

    int originalWidth = originalImage.getWidth();
    int originalHeight = originalImage.getHeight();
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("New dimensions must be positive.");
    }
    if (newWidth > originalWidth || newHeight > originalHeight) {
      throw new IllegalArgumentException(
          "Upscaling is not allowed. Target dimensions must be smaller than the original.");
    }

    Pixel[][] downscaledPixels = new SimplePixel[newWidth][newHeight];

    for (int x = 0; x < newWidth; x++) {
      for (int y = 0; y < newHeight; y++) {
        downscaledPixels[x][y] = getInterpolatedPixel(x, y, newWidth, newHeight, originalImage,
            originalWidth, originalHeight);
      }
    }

    Image downscaledImage = new SimpleImage(downscaledPixels);
    super.getMap().put(modifiedImageName, downscaledImage);
  }


  private Pixel getInterpolatedPixel(int x, int y, int newWidth, int newHeight,
      Image originalImage, int originalWidth, int originalHeight) {
    double sourceX = ((double) x / newWidth) * originalWidth;
    double sourceY = ((double) y / newHeight) * originalHeight;

    int floorX = (int) Math.floor(sourceX);
    int floorY = (int) Math.floor(sourceY);
    int ceilX = Math.min((int) Math.ceil(sourceX), originalWidth - 1);
    int ceilY = Math.min((int) Math.ceil(sourceY), originalHeight - 1);

    Pixel a = originalImage.getPixel(floorX, floorY);
    Pixel b = originalImage.getPixel(ceilX, floorY);
    Pixel c = originalImage.getPixel(floorX, ceilY);
    Pixel d = originalImage.getPixel(ceilX, ceilY);

    double weightX = sourceX - floorX;
    double weightY = sourceY - floorY;

    int interpolatedRed = bilinearInterpolate(weightX, weightY, a.getR(), b.getR(), c.getR(),
        d.getR());
    int interpolatedGreen = bilinearInterpolate(weightX, weightY, a.getG(), b.getG(), c.getG(),
        d.getG());
    int interpolatedBlue = bilinearInterpolate(weightX, weightY, a.getB(), b.getB(), c.getB(),
        d.getB());

    return new SimplePixel(interpolatedRed, interpolatedGreen, interpolatedBlue);
  }


  private int bilinearInterpolate(double weightX, double weightY, int ca, int cb, int cc, int cd) {
    double m = cb * weightX + ca * (1 - weightX);
    double n = cd * weightX + cc * (1 - weightX);

    return (int) Math.round(n * weightY + m * (1 - weightY));
  }

  @Override
  public void blurWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String blurredImageName = "blurredImage";
    blur(imageName, blurredImageName);
    Image operatedImage = getImage(blurredImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void sharpenWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String sharpenedImageName = "sharpenedImage";
    sharpen(imageName, sharpenedImageName);
    Image operatedImage = getImage(sharpenedImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void redComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String redImageName = "redImage";
    redComponent(imageName, redImageName);
    Image operatedImage = getImage(redImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void greenComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String greenImageName = "greenImage";
    greenComponent(imageName, greenImageName);
    Image operatedImage = getImage(greenImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void blueComponentWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String blueImageName = "blueImage";
    blueComponent(imageName, blueImageName);
    Image operatedImage = getImage(blueImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void sepiaWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String sepiaImageName = "sepiaImage";
    sepia(imageName, sepiaImageName);
    Image operatedImage = getImage(sepiaImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void lumaWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String lumaImageName = "lumaImage";
    lumaComponent(imageName, lumaImageName);
    Image operatedImage = getImage(lumaImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void intensityWithMask(String imageName, String maskedImageName,
      String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String intensityImageName = "intensityImage";
    intensityComponent(imageName, intensityImageName);
    Image operatedImage = getImage(intensityImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  @Override
  public void valueWithMask(String imageName, String maskedImageName, String modifiedImageName) {
    Image originalImage = getImage(imageName);
    Image maskedImage = createMaskedImage(imageName);
    String valueImageName = "valueImage";
    valueComponent(imageName, valueImageName);
    Image operatedImage = getImage(valueImageName);
    storeModifiedImage(originalImage, maskedImage, operatedImage, modifiedImageName);
  }

  private Image createMaskedImage(String imageName) {
    if (getMap().containsKey("maskedImage")) {
      return getMap().get("maskedImage");
    }
    Image originalImage = getImage(imageName);
    int imageWidth = originalImage.getWidth();
    int imageHeight = originalImage.getHeight();
    Pixel[][] maskedImagePixels = new SimplePixel[originalImage.getWidth()]
        [originalImage.getHeight()];
    for (int x = 0; x < imageWidth; x++) {
      for (int y = 0; y < imageHeight; y++) {
        int red = originalImage.getPixel(x, y).getR();
        int green = originalImage.getPixel(x, y).getG();
        int blue = originalImage.getPixel(x, y).getB();
        int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
        if (gray > 128) {
          maskedImagePixels[x][y] = new SimplePixel(255, 255, 255);
        } else {
          maskedImagePixels[x][y] = new SimplePixel(0, 0, 0);
        }
      }
    }
    Image maskedImage = new SimpleImage(maskedImagePixels);
    return maskedImage;
  }

  private void storeModifiedImage(Image originalImage, Image maskedImage, Image operatedImage,
      String modifiedImageName) {
    Pixel[][] modifiedImagePixels = new Pixel[originalImage.getWidth()][originalImage.getHeight()];
    for (int i = 0; i < originalImage.getWidth(); i++) {
      for (int j = 0; j < originalImage.getHeight(); j++) {
        Pixel originalPixel = originalImage.getPixel(i, j);
        Pixel maskedPixel = maskedImage.getPixel(i, j);
        Pixel operatedPixel = operatedImage.getPixel(i, j);
        if (maskedPixel.getR() == 255) {
          modifiedImagePixels[i][j] = originalPixel;
        } else {
          modifiedImagePixels[i][j] = operatedPixel;
        }
      }
    }
    Image modifiedImage = new SimpleImage(modifiedImagePixels);
    getMap().put(modifiedImageName, modifiedImage);
  }
}
