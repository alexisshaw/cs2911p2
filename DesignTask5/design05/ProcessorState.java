package design05;

public abstract class ProcessorState {
    protected uint_4[] myMemory;
    protected uint_4 instructionPointer;
    private int counter; 
    
    protected String[] currentOutput;
    
    public String getCurrentOutput(int peripheralNumber) {
       if (( currentOutput == null) || (peripheralNumber > currentOutput.length) || (peripheralNumber < 0)) {
          return null;       
       }
       String output = currentOutput[peripheralNumber];
       if (currentOutput[peripheralNumber] != null) {
          output = new String(currentOutput[peripheralNumber]);
       }
       currentOutput[peripheralNumber] = null;
       return output;
       
    }

    public abstract void setRegister(int registerNo, uint_4 value); //does nothing if invalid register
    public abstract uint_4 getRegister(int registerNo); //returns null if invalid register
    public abstract int getNoRegisters();
    public abstract int getMemorySize(); //returns memory size in nibbles. (eg 16 for a standard 4442 Processor)
    
    public void setMemory(int[] memory){
       instructionPointer = new uint_4(0);
       for (int i = 0; i < getNoRegisters(); i++) {
          setRegister(i, new uint_4(0));
       }
       
       myMemory = new uint_4[memory.length];
       for (int i = 0; i < memory.length; i++) {
          myMemory[i] = new uint_4(memory[i]);
       }        
    }
    
    public void setMemory(uint_4[] memory){
       instructionPointer = new uint_4(0);
       for (int i = 0; i < getNoRegisters(); i++) {
          setRegister(i, new uint_4(0));
       }
       myMemory = memory;
    }
    public void setMemory(int addr, uint_4 newValue) {
       instructionPointer = new uint_4(0);
       for (int i = 0; i < getNoRegisters(); i++) {
          setRegister(i, new uint_4(0));
       }
       if(addr>myMemory.length){
          return;
       }
       myMemory[addr] = newValue;
    }
    
    public uint_4[] getAllMemory(){
        return myMemory;  
    }

   public uint_4 getMemory(int addr){
        if(addr>myMemory.length){
           return null;
        }
        return myMemory[addr];  
    }

    public void setIP(int ip){
        instructionPointer = new uint_4(ip);
    }

    public void setIP(uint_4 ip){
        instructionPointer = ip;
    }
    
    public uint_4 getIP() {
       return this.instructionPointer;
    }

    public void incrementStateCounter() {
        counter++;
    }
    
    public int getNumStepsStateHasTaken() {
        return counter;
    }
}
