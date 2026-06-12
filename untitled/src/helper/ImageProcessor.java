package helper;

import filters.BrightnessBlurFilter;
import filters.GaussianBlur;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    private static final String OUTPUT_DIR = "./BluredImage";

    public static void process(String imageName, int filterChoice) throws IOException {
        String extension = imageName.substring(imageName.lastIndexOf('.') + 1).toLowerCase();
        BufferedImage src = ImageIO.read(new File(imageName));
        if (src == null) throw new IOException("Could not read image: " + imageName);

        BufferedImage out;
        String prefix;

        switch (filterChoice) {
            case 1 -> { out = GaussianBlur.blur(src, 10.0); prefix = "blured_"; }
            case 2 -> { out = BrightnessBlurFilter.apply(src, 10); prefix = "brightness_blur_";
            }
            default -> throw new IllegalArgumentException("Unknown filter: " + filterChoice);
        }

        save(stripAlphaIfNeeded(out, extension), extension, prefix + imageName);
    }

    private static void save(BufferedImage img, String extension, String fileName) throws IOException {
        new File(OUTPUT_DIR).mkdirs();
        File outFile = new File(OUTPUT_DIR, fileName);
        if (!ImageIO.write(img, extension, outFile)) {
            throw new IOException("No writer found for format: " + extension);
        }
    }

    private static BufferedImage stripAlphaIfNeeded(BufferedImage img, String extension) {
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("bmp")) return img;
        BufferedImage rgb = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgb.createGraphics();
        g.drawImage(img, 0, 0, Color.WHITE, null);
        g.dispose();
        return rgb;
    }
}
