package design05;
public class ProcessingBoard {

   Processor myProcessor;
   Peripheral[] myPeripherals;
   String lastStateSet;
   
   public ProcessingBoard() {
      myProcessor = new Triple4Processor();
      myPeripherals = new Peripheral[2];
      myPeripherals[0] = new LCDScreen();
      myPeripherals[1] = new Bell();
      lastStateSet = null;
   }
   
   public int getProcessorBitSize () {
      return myProcessor.getArchitectureSize();
   }

   public String getLCDOutput() {
      return myPeripherals[1].getState();
   }

   public void resetStateToLastUserInput() {
      setState(lastStateSet);
   }

   public void processOneStep() {
      if (!myProcessor.isHalted()) {
         myProcessor.doNextStep();
         myPeripherals[0].doAction(myProcessor.getState().getCurrentOutput(0));
         myPeripherals[1].doAction(myProcessor.getState().getCurrentOutput(1));
      }
   }
   
   public void processTillHalt(int delayBetweenSteps) { //delay in ms
      int i = 0;
      while (!myProcessor.isHalted() && i < 100) {
         processOneStep();
         /*try {
            Thread.sleep(delayBetweenSteps);
         } catch (InterruptedException a) {
         
         }*/
      } 
           
   }

   public String getProcessorName() {
      return myProcessor.getName();
   }

   public void processTillHalt() { 
      processTillHalt(0);
   }
    
    // Sets the state of a processor. 
   public void setState(String strMemory) {
      lastStateSet = strMemory;
      ProcessorState myState = new Triple4ProcessorState(strMemory);
      myProcessor.setState(myState);   
      myPeripherals[0] = new Bell();
      myPeripherals[1] = new LCDScreen();

   }
    
   public int[] getMemory() {
      uint_4[] myMemory = myProcessor.getState().getAllMemory();
      int[] myIntMemory = new int[myMemory.length];
      for (int i = 0; i < myMemory.length; i++) {
         myIntMemory[i] = myMemory[i].toInteger();
      }
      return myIntMemory;
   }

   public int[] getRegisters() {
      int[] myRegisters = new int[myProcessor.getState().getNoRegisters() + 1]; //+1 IP
      for (int i = 1; i < myProcessor.getState().getNoRegisters(); i++) { 
         myRegisters[i] = myProcessor.getState().getRegister(i).toInteger();
      }
      myRegisters[0] = myProcessor.getState().getIP().toInteger();
      return myRegisters;
   }

   public String[] getRegisterNames() {
      String[] myNames = new String[myProcessor.getState().getNoRegisters() + 1];
      myNames[0] = "IP";
      for (int i = 1; i < myProcessor.getState().getNoRegisters() + 1; i++) {
         myNames[i] = "R";
         myNames[i] += i-1;         
      }
      return myNames;
   }
   
   public boolean isHaltedProcessor() {
      return myProcessor.isHalted();
   }
}
