package filters.registry;

import filters.GaussianBlur;

import java.awt.image.BufferedImage;

public class GaussianBlurImpl implements Filter {

    @Override
    public String name() {
        return "gaussianblur";
    }

    @Override
    public int argumentCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage src, String[] args, int index) {
        double sigma = Double.parseDouble(args[index]);
        return GaussianBlur.blur(src, sigma);
    }
}
