import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends GaussianBlur {
    public static void main(String[] args) throws IOException {
        BufferedImage src = ImageIO.read(new File("image.jpg"));
        BufferedImage blurred = blur(src, 10.0);
        ImageIO.write(blurred, "png", new File("bluredImage.png"));
    }
}