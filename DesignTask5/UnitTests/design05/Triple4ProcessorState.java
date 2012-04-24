package design05;

public class Triple4ProcessorState extends ProcessorState {
    private final int numRegisters = 2;
    //R0
    //R1
    private final int standardMemorySize = 16;
    uint_4[] registers;
    
    //format for string should be data (ints < 16) separated by whiteSpace
    public Triple4ProcessorState(String strMemory) {
        registers = new uint_4[numRegisters];
        for (int i = 0; i< numRegisters; i++) {
           registers[i] = new uint_4(0);
        }
        currentOutput = new String[2]; //bell and text output
        String[] temp = strMemory.split(" ", 20);
        int[] startMemory = new int[standardMemorySize];
        for (int i = 0; i < standardMemorySize; i++) {
           if (i > temp.length) 
              startMemory[i] = 0;
           else 
              startMemory[i] = Integer.parseInt(temp[i]);
        }
        
        setMemory(startMemory);
        
    }


    public Triple4ProcessorState(uint_4 [] startMemory) {
        registers = new uint_4[numRegisters];
        myMemory = startMemory;
    }
    
    
    public Triple4ProcessorState() {
        registers = new uint_4[numRegisters];
        myMemory = new uint_4[16];
    }

    @Override
    public void setRegister(int registerNo, uint_4 value) {
        if ((registerNo > getNoRegisters()) || (registerNo < 0)) {
           //do nothing
        }
        registers[registerNo] = value;
    }

    @Override
    public uint_4 getRegister(int registerNo) {
        if ((registerNo > getNoRegisters()) || (registerNo < 0)) {
           return null;
        }
        return registers[registerNo];
    }

    @Override
    public int getNoRegisters() {
        return numRegisters;
    }

    @Override
    public int getMemorySize() {
        return myMemory.length;
    }
}
