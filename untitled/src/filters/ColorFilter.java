package filters;

import java.awt.image.BufferedImage;

public class ColorFilter {

    public static BufferedImage apply(BufferedImage src, String color) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = src.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int newRgb;

                switch (color) {
                    case "red" -> newRgb = (r << 16);
                    case "green" -> newRgb = (g << 8);
                    case "blue" -> newRgb = b;
                    default -> throw new IllegalArgumentException("Unknown color: " + color);
                }

                out.setRGB(x, y, newRgb);
            }
        }

        return out;
    }
}