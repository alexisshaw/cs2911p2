/**
 * CelsiusConverter.java is a 1.4 application that 
 * demonstrates the use of JButton, JTextField and
 * JLabel.  It requires no other files.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Triple4Simulator implements ActionListener {
    private int programCounter;
    JFrame converterFrame;
    JPanel memoryPanel;
    JTextField[] memory;

    JPanel buttons;    
    JButton load;
    JButton step;
    JButton run;

    JPanel outputs;    
    JLabel[] memoryOutput;
    JLabel[] registerOutput;
    JLabel pc;


    public Triple4Simulator() {
        //Create and set up the window.
        converterFrame = new JFrame("Run the Processor?");
        converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        converterFrame.setSize(new Dimension(480, 320));

        //Create and set up the panel.
        memoryPanel = new JPanel(new GridLayout(4, 1));
        buttons = new JPanel(new GridLayout(4, 1));
        outputs = new JPanel(new GridLayout(8, 4));

        //Add the widgets.
        addWidgets();

        //Set the default button.
        converterFrame.getRootPane().setDefaultButton(load);

        //Add the panel to the window.
        converterFrame.getContentPane().add(memoryPanel, BorderLayout.CENTER);
        converterFrame.getContentPane().add(buttons, BorderLayout.SOUTH);
        converterFrame.getContentPane().add(outputs, BorderLayout.EAST);

        //Display the window.
        converterFrame.pack();
        converterFrame.setVisible(true);
    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
        //Create widgets.

        programCounter = 0;
        
        pc = new JLabel("PC: " + programCounter, SwingConstants.LEFT);
        memory = new JTextField[16];
        for (int i = 0; i < 16; i++) {
           memory[i] = new JTextField(2);
        }
        
        memoryOutput = new JLabel[4];
        for (int i = 0; i < 4; i++) { 
           memoryOutput[i] = new JLabel("   0 0 0 0    ", SwingConstants.LEFT);
        }
        
        registerOutput = new JLabel[2];        
        for (int i = 0; i < 2; i++) {
           registerOutput[i] = new JLabel("R" + i + ": ", SwingConstants.LEFT);
        }
        
        load = new JButton("Load into memory");
        step = new JButton("Step");
        run = new JButton("Run");

        //Listen to events from the Convert button.
        load.addActionListener(this);

        //Add the widgets to the container.
        //The memory section
        for (int i = 0; i < 16; i++) {
           memoryPanel.add(memory[i]);
        }
        //the processor state
        for (int i = 0; i < 4; i++) {
           outputs.add(memoryOutput[i]);
           memoryOutput[i].setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        }
        for (int i = 0; i < 2; i++) {
           outputs.add(registerOutput[i]);
           registerOutput[i].setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        }
        outputs.add(pc);

        buttons.add(load);
        buttons.add(step);
        buttons.add(run);
        
    }

    public void actionPerformed(ActionEvent event) {
        //Parse degrees Celsius as a double and convert to Fahrenheit.
        int tempFahr = (int)((Double.parseDouble(memory[0].getText()))
                             * 1.8 + 32);
        registerOutput[0].setText("R0: " + tempFahr);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        CelsiusConverter converter = new CelsiusConverter();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
