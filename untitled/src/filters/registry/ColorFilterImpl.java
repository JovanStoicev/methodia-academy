package filters.registry;

import filters.ColorFilter;

import java.awt.image.BufferedImage;

public class ColorFilterImpl implements Filter {

    @Override
    public String name() {
        return "colorfilter";
    }

    @Override
    public int argumentCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage src, String[] args, int index) {
        String color = args[index].toLowerCase();
        return ColorFilter.apply(src, color);
    }
}
