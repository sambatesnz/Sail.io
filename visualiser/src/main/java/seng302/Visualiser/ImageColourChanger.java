package seng302.Visualiser;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Changes white of an image into a different colour
 */
public class ImageColourChanger {
    private BufferedImage image;

    public ImageColourChanger(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage colouredImage(int[] newColour) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                if (pixels[0] == 255 && pixels[1] == 255 && pixels[2] == 255) {
                    raster.setPixel(xx, yy, newColour);
                } else if (pixels[3] != 0){
                    raster.setPixel(xx, yy, new int[] {0, 0, 0, 255});
                }
            }
        }
        return image;
    }
}
