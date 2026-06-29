package filters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CropFilter {

    public static BufferedImage apply(BufferedImage src, int x, int y, int width, int height) {
        if (src == null) {
            throw new IllegalArgumentException("Source image cannot be null");
        }

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Crop x and y must be >= 0");
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Crop width and height must be > 0");
        }

        if (x >= src.getWidth() || y >= src.getHeight()) {
            throw new IllegalArgumentException("Crop start position is outside image bounds");
        }

        if (x + width > src.getWidth()) {
            width = src.getWidth() - x;
        }

        if (y + height > src.getHeight()) {
            height = src.getHeight() - y;
        }

        int type = src.getType();
        if (type == BufferedImage.TYPE_CUSTOM) {
            type = BufferedImage.TYPE_INT_ARGB;
        }

        BufferedImage cropped = new BufferedImage(width, height, type);

        Graphics2D g = cropped.createGraphics();
        g.drawImage(
                src,
                0, 0, width, height,
                x, y, x + width, y + height,
                null
        );
        g.dispose();

        return cropped;
    }
}