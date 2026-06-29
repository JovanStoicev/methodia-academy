package filters.registry;

import filters.CropFilter;

import java.awt.image.BufferedImage;

public class CropFilterImpl implements Filter {

    @Override
    public String name() {
        return "crop";
    }

    @Override
    public int argumentCount() {
        return 4;
    }

    @Override
    public BufferedImage apply(BufferedImage src, String[] args, int index) {
        int x = Integer.parseInt(args[index]);
        int y = Integer.parseInt(args[index + 1]);
        int width = Integer.parseInt(args[index + 2]);
        int height = Integer.parseInt(args[index + 3]);

        return CropFilter.apply(src, x, y, width, height);
    }
}
