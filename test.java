
//simport java.awt.BorderLayout;
import java.io.File;

public class test {
    public static void main(String[] args) {
        // Create the frame with title
        TFrame see = new TFrame(" ~UNO~ ");

        // Create panel
        TPanel tee = new TPanel();

        // Try to set background image
        String imagePath = "C:\\Users\\Administrateur\\Documents\\ma personalzation\\Saved Pictures\\template3.png";

        // Verify file existence before attempting to load
        File imageFile = new File(imagePath);
        System.out.println("Image path: " + imagePath);
        System.out.println("File exists: " + imageFile.exists());
        if (imageFile.exists()) {
            System.out.println("File is readable: " + imageFile.canRead());
            System.out.println("File size: " + imageFile.length() + " bytes");
        }

        // Try to load the image
        boolean success = tee.setBackgroundImage(imagePath);

        if (!success) {
            System.out.println("Failed to load image. Trying with alternative approaches...");

            // Try alternative 1: Check if it's a path issue with backslashes
            String altPath1 = imagePath.replace("\\", "/");
            System.out.println("Trying alternative path 1: " + altPath1);
            success = tee.setBackgroundImage(altPath1);

            // Try alternative 2: Check if the file exists in a different location
            if (!success) {
                String altPath2 = "template3.png"; // Try relative path
                System.out.println("Trying alternative path 2: " + altPath2);
                success = tee.setBackgroundImage(altPath2);
            }

            // If still not successful, set a background color instead
            if (!success) {
                System.out.println("All image loading attempts failed. Setting background color instead.");
                tee.backgroundColor("black");
            }
        }

        // Add content panel to frame and make visible
        see.setContentPanel(tee);
        see.setVisible(true);

        // Debug panel visibility after it's displayed
        System.out.println("\nPanel debug information:");
        tee.debugVisibility();
    }
}