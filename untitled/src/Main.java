import filters.GaussianBlur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter image full name (ex. image.jpg): ");
        String imageName = scanner.nextLine();
        String extension = imageName.substring(imageName.lastIndexOf('.') + 1);
        if (extension.equalsIgnoreCase("jpg")) {
            extension = "jpeg";
        }
        scanner.close();

        File outDir = new File("./BluredImage");
        outDir.mkdirs();

        try{
            BufferedImage src = ImageIO.read(new File(imageName));
            BufferedImage blurred = GaussianBlur.blur(src, 10.0);

            boolean ok = ImageIO.write(blurred, extension, new File(outDir, "blured_" + imageName));
            if (!ok) throw new IOException("No writer for format: " + extension);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}