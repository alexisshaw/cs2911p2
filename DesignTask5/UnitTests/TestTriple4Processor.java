
package UnitTests;
import design05.*;

public class TestTriple4Processor implements Test {

   public String toString () {
      return "testing 4442 Processor is functional...";
   }

   public void run () {
      {

         Triple4Processor myProcessor = new Triple4Processor();
         Triple4ProcessorState myState = new Triple4ProcessorState("0 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0");
         myProcessor.setState(myState);

         System.out.println("   Testing halting occurs after it runs a halt..");         
         assert(!myProcessor.isHalted());
         myProcessor.doNextStep();
         assert(myProcessor.isHalted());

         System.out.println("   Testing addition, and incrementing works");         
         myState = new Triple4ProcessorState("1 3 4 1 5 6 7 7 3 9 10 11 12 14 15 0");
         myProcessor.setState(myState);
         myProcessor.doNextStep();
         ProcessorState myTestState = myProcessor.getState();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(0).toInteger() == 0);
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(myTestState.getRegister(0).toInteger() == 1);
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(myTestState.getRegister(1).toInteger() == 1);
         assert(!myProcessor.isHalted());
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(0).toInteger() == 2);
         System.out.println("   Testing decrementing and subtraction work...");
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(0).toInteger() == 1);
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(1).toInteger() == 0);

         System.out.println("   Testing register swapping occurs..");
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(0).toInteger() == 0);
         assert(myTestState.getRegister(1).toInteger() == 1);
         
         myProcessor.doNextStep();
         assert(myTestState != null);
         assert(!myProcessor.isHalted());
         assert(myTestState.getRegister(1).toInteger() == 0);
         assert(myTestState.getRegister(0).toInteger() == 1);
         
      }
   }
   
}
