package design05;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math.*;

import design05.*;

public class Triple4Simulator implements ActionListener {
    
    public static final String instruction = 
    ( " To use become familiar with the layout. To the left\n" +
      "   is a memory loading section. From here load your \n" + 
      "   instructions into the processor. Use the buttons \n" +
      "   to control the processor (step, run)\n" +
      " Use the text areas where you load your code in to comment.\n" +
      "   Comments may follow an integer instruction and a space\n" + 
      
      " The Instruction Set Follows:\n" +
    
      "  0 = Halt\n" + 
      "  1 = Add (R0 = R0 + R1)\n"+
      "  2 = Subtract (R0 = R0 - R1)\n"+
      "  3 = Increment R0 (R0 = R0 + 1) \n"+
      "  4 = Increment R1 (R1 = R1 + 1) \n"+
      "  5 = Decrement R0 (R0 = R0 - 1) \n"+
      "  6 = Decrement R1 (R1 = R1 - 1) \n"+
      "  7 = Swap Registers (R0 <=> R1) and ring bell!\n"+ 
      "  8 = Print <data> (The numerical value of <data> is printed)\n"+
      "  9 = Load value at address <data> into R0\n"+
      "  10 = Load value at address <data> into R1\n"+
      "  11 = Store R0 into address <data>\n"+
      "  12 = Store R1 into address <data>\n"+
      "  13 = Jump to address <data>\n"+
      "  14 = Jump to address <data> if R0 == 0\n"+
      "  15 = Jump to address <data> if R0 != 0\n");
    
    ProcessingBoard myBoard;
    private int programCounter;
    
    JFrame processorFrame;
    JFrame instructionFrame;
    
    JTextArea instructions;
    
    JPanel memoryPanel;
    JTextField[] memory;

    JPanel buttons;    
    JButton load;
    JButton step;
    JButton run;  
    JButton clear;
    JButton showInstructions;

    JPanel outputs;    
    JLabel[] memoryOutput;
    JLabel[] registerOutput;
    JLabel LCDScreen;
    
    
    JLabel memoryTitle;
    JLabel processorTitle;


    public Triple4Simulator() {
        //Create the Processing board etc
        myBoard = new ProcessingBoard();
        myBoard.setState("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");

        //Create and set up the window.
        processorFrame = new JFrame(myBoard.getProcessorName() + " Simulator");
        processorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        processorFrame.setSize(new Dimension(960, 640));

        instructionFrame = new JFrame("Instructions");
        instructionFrame.setSize(new Dimension(960, 640));

        //Create and set up the panel.
        memoryPanel = new JPanel(new GridLayout(myBoard.getProcessorBitSize () + 1, 1));
        buttons = new JPanel(new FlowLayout());
        outputs = new JPanel(new GridLayout(myBoard.getProcessorBitSize () + myBoard.getRegisters().length + 3, 4));

        //Add the widgets.
        addWidgets();

        //Add the panel to the window.
        processorFrame.getContentPane().add(memoryPanel, BorderLayout.CENTER);
        processorFrame.getContentPane().add(buttons, BorderLayout.SOUTH);
        processorFrame.getContentPane().add(outputs, BorderLayout.EAST);
        instructions = new JTextArea(instruction);
        instructionFrame.getContentPane().add(instructions, BorderLayout.CENTER);

        //Display the window.
        processorFrame.pack();
        processorFrame.setVisible(true);

    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
        //Create widgets.
        memoryTitle = new JLabel("Memory Input", SwingConstants.CENTER);
        processorTitle = new JLabel("Inside Your Processor", SwingConstants.CENTER);
        
        for (int i = 0; i < myBoard.getProcessorBitSize(); i++) {
           if (i == myBoard.getProcessorBitSize()/2) {
              memoryPanel.add(memoryTitle);
           } else {
              memoryPanel.add(new JLabel("", SwingConstants.LEFT));
           }
        }     
        LCDScreen = new JLabel("empty", SwingConstants.CENTER);
   
  
        outputs.add(processorTitle);

        memory = new JTextField[myBoard.getProcessorBitSize ()*myBoard.getProcessorBitSize ()];
        for (int i = 0; i < myBoard.getProcessorBitSize ()*myBoard.getProcessorBitSize (); i++) {
           memory[i] = new JTextField(2);
        }
        
        memoryOutput = new JLabel[myBoard.getProcessorBitSize ()];
        for (int i = 0; i < myBoard.getProcessorBitSize (); i++) { 
           memoryOutput[i] = new JLabel("   ", SwingConstants.CENTER);
           for (int j = 0; j < myBoard.getProcessorBitSize(); j++) {
              memoryOutput[i].setText(memoryOutput[i].getText() + "0 ");
           }
           memoryOutput[i].setText(memoryOutput[i].getText() + "   ");
        }
        
        registerOutput = new JLabel[myBoard.getRegisters().length];        
        for (int i = 0; i < myBoard.getRegisters().length; i++) {
           registerOutput[i] = new JLabel(myBoard.getRegisterNames()[i] + ": ", SwingConstants.LEFT);
        }
        
        load = new JButton("Load into processor");
        clear = new JButton("Clear to last loaded state");        
        step = new JButton("Step");
        run = new JButton("Run");
        showInstructions = new JButton("Show Instructions");

        //Listen to events from the Convert button.
        load.addActionListener(this);
        step.addActionListener(this);
        run.addActionListener(this);
        clear.addActionListener(this);
        showInstructions.addActionListener(this);
        
        //Add the widgets to the container.
        //The memory section
        for (int i = 0; i <myBoard.getProcessorBitSize()*myBoard.getProcessorBitSize (); i++) {
           memoryPanel.add(memory[i]);
        }
        //the processor state
        for (int i = 0; i < myBoard.getProcessorBitSize (); i++) {
           outputs.add(memoryOutput[i]);
           memoryOutput[i].setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        }
        for (int i = 0; i < myBoard.getRegisters().length; i++) {
           outputs.add(registerOutput[i]);
           registerOutput[i].setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        }
        
        outputs.add(new JLabel("LCD SCREEN", SwingConstants.CENTER));
        outputs.add(LCDScreen);

        buttons.add(load);
        buttons.add(clear);
        buttons.add(step);
        buttons.add(run);
        buttons.add(showInstructions);
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == step) {
           myBoard.processOneStep();
           updateText();

        } else if (event.getSource() == load) {
           loadMemory(); 
           updateText();

        } else if (event.getSource() == run) {
           myBoard.processTillHalt();
           updateText();
           
        } else if (event.getSource() == clear) {
           myBoard.resetStateToLastUserInput();
           updateText();
        } else if (event.getSource() == showInstructions) {
           instructionFrame.pack();
           instructionFrame.setVisible(true);          
        } else {
           updateText();
           registerOutput[0].setText("Error: Somebody else is listening...");
           registerOutput[1].setText("Error: Somebody else is listening...");

        }
    }
    
    private void updateText() {
       int[] currentMemory = myBoard.getMemory(); 
       int k = 0;
       for (int i = 0; i < memoryOutput.length; i++) {
          memoryOutput[i].setText("<html>   ");
          for (int j = 0; j < Math.sqrt(memory.length); j++) {
             if (k == myBoard.getRegisters()[0]) {
                memoryOutput[i].setText(memoryOutput[i].getText() + "<FONT COLOR=\"FF0000\">");
             }
             memoryOutput[i].setText(memoryOutput[i].getText() + currentMemory[k]);
             if (k == myBoard.getRegisters()[0]) {
                memoryOutput[i].setText(memoryOutput[i].getText() + "</FONT>");                                 
             }
             
             if (currentMemory[k] < 10) {
                memoryOutput[i].setText(memoryOutput[i].getText() + " ");                  
             }
             memoryOutput[i].setText(memoryOutput[i].getText() + " ");    
             k++;              
          }
          memoryOutput[i].setText(memoryOutput[i].getText() + "   </html>");    
       }
       
       for (int i = 0; i < registerOutput.length; i++) {
          registerOutput[i].setText(myBoard.getRegisterNames()[i] + ": " + myBoard.getRegisters()[i]);
       }  
       if ((myBoard.getLCDOutput() == null) || (myBoard.getLCDOutput() == "")) {
          LCDScreen.setText("empty");
       } else {
          LCDScreen.setText(myBoard.getLCDOutput());
       }
    }
    
    private void loadMemory() {
       String memoryString = "";
       String temp;
       for (int i = 0; i < memory.length; i++) {
          try {
             temp = memory[i].getText();
             temp = temp.split(" ", 20)[0];
             memoryString += (int)((Double.parseDouble(temp)));
          } catch (java.lang.NumberFormatException a) {
             memoryString += 0;
          }
          memoryString += " ";
       }
       myBoard.setState(memoryString);

    }
    
}
