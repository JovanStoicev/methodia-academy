package filters.registry;

import java.awt.image.BufferedImage;

public interface Filter {
    String name();
    int argumentCount();
    BufferedImage apply(BufferedImage src, String[] args, int index);
}
