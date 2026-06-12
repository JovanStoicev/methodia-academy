package filters;

import java.awt.image.BufferedImage;

import static constants.ImageConstants.*;

public class BrightnessBlurFilter {

    public static BufferedImage apply(BufferedImage src, int radius) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be at least 1");
        }

        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        long[][] integral = buildIntegralBrightness(src, width, height);

        for (int y = 0; y < height; y++) {
            int y1 = Math.max(0, y - radius);
            int y2 = Math.min(height - 1, y + radius);

            for (int x = 0; x < width; x++) {
                int x1 = Math.max(0, x - radius);
                int x2 = Math.min(width - 1, x + radius);

                long sum = getAreaSum(integral, x1, y1, x2, y2);
                int count = (x2 - x1 + 1) * (y2 - y1 + 1);

                int brightness = (int) Math.round((double) sum / count);
                int rgb = (brightness << RED_SHIFT) | (brightness << GREEN_SHIFT) | brightness;

                out.setRGB(x, y, rgb);
            }
        }

        return out;
    }

    private static long[][] buildIntegralBrightness(BufferedImage src, int width, int height) {
        long[][] integral = new long[height + 1][width + 1];

        for (int y = 1; y <= height; y++) {
            long rowSum = 0;

            for (int x = 1; x <= width; x++) {
                int rgb = src.getRGB(x - 1, y - 1);

                int r = (rgb >> RED_SHIFT) & COLOR_MASK;
                int g = (rgb >> GREEN_SHIFT) & COLOR_MASK;
                int b = rgb & COLOR_MASK;

                int brightness = (int) Math.round((r+g+b)/3);

                rowSum += brightness;
                integral[y][x] = integral[y - 1][x] + rowSum;
            }
        }

        return integral;
    }

    private static long getAreaSum(long[][] integral, int x1, int y1, int x2, int y2) {
        return integral[y2 + 1][x2 + 1]
                - integral[y1][x2 + 1]
                - integral[y2 + 1][x1]
                + integral[y1][x1];
    }
}