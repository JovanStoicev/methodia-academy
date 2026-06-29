import helper.ImageProcessor;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                printUsage();
                return;
            }

            String imagePath = args[0];

            ImageProcessor.processPipeline(imagePath, args);

            System.out.println("Done.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("""
                Example:
                  image.jpg averagebrightnessblur 3 colorfilter red crop 10 15 100 110

                Filters:
                  gaussianblur <sigma>
                  averagebrightnessblur <radius>
                  grayscale
                  colorfilter <red|green|blue>
                  crop <x> <y> <width> <height>
                """);
    }
}