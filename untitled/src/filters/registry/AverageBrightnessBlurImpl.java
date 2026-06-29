package filters.registry;

import filters.BrightnessBlurFilter;

import java.awt.image.BufferedImage;

public class AverageBrightnessBlurImpl implements Filter {

    @Override
    public String name() {
        return "averagebrightnessblur";
    }

    @Override
    public int argumentCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage src, String[] args, int index) {
        int radius = Integer.parseInt(args[index]);
        return BrightnessBlurFilter.apply(src, radius);
    }
}
