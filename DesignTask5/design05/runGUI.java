package design05;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import design05.*;

public class runGUI {
/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        Triple4Simulator me = new Triple4Simulator();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
       try {
            // Set System L&F
          UIManager.setLookAndFeel(
              UIManager.getSystemLookAndFeelClassName());
       } 
       catch (UnsupportedLookAndFeelException e) {
       // handle exception
       }
       catch (ClassNotFoundException e) {
       // handle exception
       }
       catch (InstantiationException e) {
       // handle exception
       }
       catch (IllegalAccessException e) {
       // handle exception
       }        
       javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
             createAndShowGUI();
         }
       });
    }
    
}
