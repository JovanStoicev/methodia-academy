import helper.ImageProcessor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter image full name (ex. image.jpg): ");
        String imageName = scanner.nextLine();

        System.out.println("Choose filter: 1 = Gaussian Blur, 2 = Brightness Blur");
        int choice = Integer.parseInt(scanner.nextLine().trim());

        try {
            ImageProcessor.process(imageName, choice);
            System.out.println("Done.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
