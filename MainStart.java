/******************** Supposed to be Start Panel in Main Frame *****************************/
import javax.swing.*;

import View.FramesToBePanelsLater.StartFrame;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainStart {

    public static void main(String[] args) {    	
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartFrame frame = new StartFrame();   //NOUR
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }
}