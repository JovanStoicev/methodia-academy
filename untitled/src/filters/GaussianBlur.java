package filters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GaussianBlur {

    public static BufferedImage blur(BufferedImage src, double sigma) {
        int radius = Math.max(1, (int) Math.ceil(sigma * 3));
        float[] kernel = buildKernel(sigma, radius);

        int width = src.getWidth(), height = src.getHeight();BufferedImage img = toIntARGB(src);
        int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        int[] tmp = new int[width * height];

        convolve1D(pixels, tmp, width, height, kernel, radius, true);
        convolve1D(tmp, pixels, width, height, kernel, radius, false);

        if (src.getTransparency() == BufferedImage.OPAQUE) {
            BufferedImage rgb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g = rgb.createGraphics();
            g.drawImage(img, 0, 0, java.awt.Color.WHITE, null);
            g.dispose();
            return rgb;
        }
        return img;

    }

    private static float[] buildKernel(double sigma, int radius) {
        float[] k = new float[radius * 2 + 1];
        double s2 = 2 * sigma * sigma, sum = 0;
        for (int i = -radius; i <= radius; i++) {
            double v = Math.exp(-(i * i) / s2);
            k[i + radius] = (float) v;
            sum += v;
        }
        for (int i = 0; i < k.length; i++) k[i] /= sum;
        return k;
    }

    private static void convolve1D(int[] in, int[] out, int width, int height,
                                   float[] kernel, int radius, boolean horizontal) {
        int len = horizontal ? width : height;
        int outer = horizontal ? height : width;
        int stride = horizontal ? 1 : width;

        for (int o = 0; o < outer; o++) {
            int base = horizontal ? o * width : o;
            for (int i = 0; i < len; i++) {
                float a = 0, r = 0, g = 0, b = 0;
                for (int k = -radius; k <= radius; k++) {
                    int idx = Math.min(len - 1, Math.max(0, i + k));
                    int px = in[base + idx * stride];
                    float blurWeight = kernel[k + radius];
                    a += ((px >>> 24) & 0xFF) * blurWeight;
                    r += ((px >>> 16) & 0xFF) * blurWeight;
                    g += ((px >>> 8)  & 0xFF) * blurWeight;
                    b += ( px         & 0xFF) * blurWeight;
                }
                out[base + i * stride] =
                        ((int) a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
            }
        }
    }

    private static BufferedImage toIntARGB(BufferedImage src) {
        if (src.getType() == BufferedImage.TYPE_INT_ARGB) {
            BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            copy.getGraphics().drawImage(src, 0, 0, null);
            return copy;
        }
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        out.getGraphics().drawImage(src, 0, 0, null);
        return out;
    }
}
