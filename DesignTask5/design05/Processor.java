package design05;

public interface Processor {
    // steps through program loaded in. 
    // * Does nothing after reaching halt
    // * Does nothing if not given a program
    public void doNextStep();
    
    // Sets the state of a processor. 
    public void setState(ProcessorState state);
    
    //returns the current state of a processor
    public ProcessorState getState();
    
    public boolean isHalted();
    
    public String getName();
    
    //returns 4 for a 4 bit processor, 8 for 8 bit etc
    public int getArchitectureSize();
    
}
