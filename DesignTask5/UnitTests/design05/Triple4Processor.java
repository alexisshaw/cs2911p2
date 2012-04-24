package design05;

public class Triple4Processor implements Processor {
   
   //Processor state holders 
    ProcessorState myState;
    boolean isInitialised;
    private boolean isHalted;

  //CPU Commands - 4 bit
    private final uint_4 halt          = new uint_4(0x0);    
    private final uint_4 addR0R1       = new uint_4(0x1);
    private final uint_4 subR0R1       = new uint_4(0x2);
    private final uint_4 incR0         = new uint_4(0x3);
    private final uint_4 incR1         = new uint_4(0x4);
    private final uint_4 decR0         = new uint_4(0x5);
    private final uint_4 decR1         = new uint_4(0x6);
    private final uint_4 swap          = new uint_4(0x7);
  //CPU Commands - 8 bit
    private final uint_4 xoriR0data    = new uint_4(0x8); //has side effect of outputing data to output stream
    private final uint_4 lpmR0data     = new uint_4(0x9);
    private final uint_4 lpmR1data     = new uint_4(0xA);
    private final uint_4 stsdataR0     = new uint_4(0xB);
    private final uint_4 stsdataR1     = new uint_4(0xC);
    private final uint_4 jmpdata       = new uint_4(0xD); 
    private final uint_4 cpiR0bredata  = new uint_4(0xE); 
    private final uint_4 cpiR0brnedata = new uint_4(0xF);

   //Contructor                
    public Triple4Processor() {
       super();
       myState = new Triple4ProcessorState();
       isInitialised = false;
       myState.instructionPointer = new uint_4(0);
       myState.currentOutput = null;
       isHalted = false;
    }  
    
    @Override
    public boolean isHalted() {
       return isHalted;
    }  
 
   //Process Runner  
    @Override
    public void doNextStep() {
       if ((isInitialised) && (!isHalted)) {
          uint_4 currentMemory[] = myState.getAllMemory();
          uint_4 currentAction = currentMemory[myState.getIP().toInteger()];
          if (currentAction.toInteger() < 8) {
             doAction(currentAction);
             myState.getIP().add(1);
          } else if (currentAction.toInteger() < 16) {
             doAction(currentAction, currentMemory[myState.getIP().toInteger()]);
             myState.getIP().add(2);
          }
       } 
    }    
   //4 bit CPU calculations 
    private void doAction(uint_4 action) {
       if (action.toInteger() >= 8) { //pass onto 8 bit action modifier
          doAction(action, myState.getMemory(myState.getIP().toInteger()+1));
          myState.getIP().add(1);
       } else {
          if (action.equals(addR0R1)) {  //add R0, R1
             myState.setRegister(0, myState.getRegister(0).add(myState.getRegister(1)));
          } else if (action.equals(subR0R1)) { //sub R0, R1
             myState.setRegister(0, myState.getRegister(0).sub(myState.getRegister(1)));          
          } else if (action.equals(incR0)) { //inc R0
             myState.setRegister(0, myState.getRegister(0).add(1));   
          } else if (action.equals(incR1)) { //inc R1
             myState.setRegister(1, myState.getRegister(1).add(1));             
          } else if (action.equals(decR0)) { //subi R0, 1 
             myState.setRegister(0, myState.getRegister(0).sub(1));             
          } else if (action.equals(decR1)) { //subi R1, 1
             myState.setRegister(1, myState.getRegister(1).sub(1));                       
          } else if (action.equals(swap)) { //swap registers? 
             uint_4 temp = myState.getRegister(1);
             myState.setRegister(1, myState.getRegister(0));
             myState.setRegister(1, temp);
             if (myState.currentOutput == null) 
                myState.currentOutput[1] = "0";
             else 
                myState.currentOutput[1] += "0";
          } else if (action.equals(halt)) { //halt
             isHalted = true;
             if (myState.currentOutput == null) 
                myState.currentOutput[1] = "0";
             else 
                myState.currentOutput[1] += "0";
          }      
       }    
    }

   //8 bit CPU calculations (requiring a 4 bit action command and a 4 bit piece of data
    private void doAction(uint_4 action, uint_4 data) {
       if ((action.toInteger() < 0) || (action.toInteger() > 16)) { //be confused 
       } else if (action.toInteger() <= 8) { //pass onto 4 bit action modifier
          doAction(action);
          myState.getIP().sub(1);               
       } else {
          assert(myState.getMemorySize() > data.toInteger() && data.toInteger() >= 0);
          if (action.equals(xoriR0data)) {  //xori R0, data + effect: data sent along output line
             myState.setRegister(0, myState.getRegister(0).XOR(data));
             if (myState.currentOutput == null) 
                myState.currentOutput[0] = data.toString();
             else 
                myState.currentOutput[0] += data.toString();
          } else if (action.equals(lpmR0data)) { //lpm R0, [data]
             myState.setRegister(0, data);
          } else if (action.equals(lpmR1data)) { //lpm R1, [data]
             myState.setRegister(1, data);
          } else if (action.equals(stsdataR0)) { //sts data, R0
             uint_4 memory[] = myState.getAllMemory();
             memory[data.toInteger()] = myState.getRegister(0);
             myState.setMemory(memory);
          } else if (action.equals(stsdataR1)) { //sts data, R1
             uint_4 memory[] = myState.getAllMemory();
             memory[data.toInteger()] = myState.getRegister(1);
             myState.setMemory(memory);
          } else if (action.equals(jmpdata)) { //jmp data (using -2 as ip is incremented twice after this fnction call)
             myState.setIP(data.sub(2));
          } else if (action.equals(cpiR0bredata)) { //cpi R0, 0, bre data 
             if (myState.getRegister(0).toInteger() == 0) 
                myState.setIP(data.sub(2));
          } else if (action.equals(cpiR0brnedata)) { //cpi R0, 0, brne data 
             if (myState.getRegister(0).toInteger() != 0) 
                myState.setIP(data.sub(2));
          }      
       }    
    }

   //Initialise Processor with intructions
    @Override
    public void setState(ProcessorState state) {
       isHalted = false;
       isInitialised = true;
       myState = state;
    }
   //return a state for storing
    @Override
    public ProcessorState getState() {
        if (!isInitialised) {
           return null;
        } else {
           return myState;
        }
    }
}
