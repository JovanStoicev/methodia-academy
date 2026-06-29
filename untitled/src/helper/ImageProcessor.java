package helper;

import filters.registry.Filter;
import filters.registry.FilterRegistry;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class ImageProcessor {

    private static final String OUTPUT_DIR = "./ProcessedImages";
    private static final String OUTPUT_FILE_PREFIX = "processed_image.";

    public static void processPipeline(String imagePath, String[] args) throws Exception {
        String extension = getExtension(imagePath);

        BufferedImage image = readImage(imagePath);
        if (image == null) {
            throw new IllegalArgumentException("Could not read image: " + imagePath);
        }

        int index = 1;

        while (index < args.length) {
            String filterName = args[index].toLowerCase();

            Filter filter = FilterRegistry.get(filterName);

            if (filter == null) {
                throw new IllegalArgumentException("Unknown filter: " + filterName);
            }

            int paramsStart = index + 1;
            int paramsEnd = paramsStart + filter.argumentCount();

            if (paramsEnd > args.length) {
                throw new IllegalArgumentException(
                        filterName + " requires " + filter.argumentCount() + " argument(s)"
                );
            }

            image = filter.apply(image, args, paramsStart);

            index = paramsEnd;
        }

        save(stripAlphaIfNeeded(image, extension), extension, OUTPUT_FILE_PREFIX + extension);
    }

    private static BufferedImage readImage(String imagePath) throws Exception {
        if (isHttpUrl(imagePath)) {
            return ImageIO.read(URI.create(imagePath).toURL());
        }

        return ImageIO.read(new File(imagePath));
    }

    private static void save(BufferedImage img, String extension, String fileName) throws Exception {
        new File(OUTPUT_DIR).mkdirs();

        File outFile = new File(OUTPUT_DIR, fileName);

        if (!ImageIO.write(img, extension, outFile)) {
            throw new IllegalArgumentException("No writer found for format: " + extension);
        }
    }

    private static String getExtension(String imagePath) {
        String pathWithoutQuery = imagePath.split("[?#]", 2)[0];
        int dot = pathWithoutQuery.lastIndexOf(".");
        if (dot == -1) {
            throw new IllegalArgumentException("Image must have extension");
        }

        return pathWithoutQuery.substring(dot + 1).toLowerCase();
    }

    private static boolean isHttpUrl(String imagePath) {
        String normalized = imagePath.toLowerCase();
        return normalized.startsWith("http://") || normalized.startsWith("https://");
    }

    private static BufferedImage stripAlphaIfNeeded(BufferedImage img, String extension) {
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("bmp")) {
            return img;
        }

        BufferedImage rgb = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = rgb.createGraphics();
        g.drawImage(img, 0, 0, Color.WHITE, null);
        g.dispose();

        return rgb;
    }
}
