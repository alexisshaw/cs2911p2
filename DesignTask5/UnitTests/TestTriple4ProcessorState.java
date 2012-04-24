
package UnitTests;
import design05.*;

public class TestTriple4ProcessorState implements Test {

   public String toString () {
      return "testing 4442 Processor can store its state correctly is functional...";
   }

   public void run () {
      {
         System.out.println("   Testing initialisation...");
         Triple4ProcessorState myState = new Triple4ProcessorState("1 2 3 4 5 6 7 8 9 10 11 12 0 14 15 13");
         assert(myState.getMemorySize() == 16);
         assert(myState.getNoRegisters() == 2);
         assert(myState.getMemory(0).toInteger() == 1);
         assert(myState.getMemory(1).toInteger() == 2);
         assert(myState.getMemory(2).toInteger() == 3);
         assert(myState.getMemory(3).toInteger() == 4);
         assert(myState.getMemory(4).toInteger() == 5);
         assert(myState.getMemory(5).toInteger() == 6);
         assert(myState.getMemory(6).toInteger() == 7);
         assert(myState.getMemory(7).toInteger() == 8);
         assert(myState.getMemory(8).toInteger() == 9);
         assert(myState.getMemory(9).toInteger() == 10);
         assert(myState.getMemory(0xA).toInteger() == 11);
         assert(myState.getMemory(0xB).toInteger() == 12);
         assert(myState.getMemory(0xC).toInteger() == 0);
         assert(myState.getMemory(0xD).toInteger() == 14);
         assert(myState.getMemory(0xE).toInteger() == 15);
         assert(myState.getMemory(0xF).toInteger() == 13);
         assert(myState.getRegister(0).toInteger() == 0);
         assert(myState.getRegister(1).toInteger() == 0);

         System.out.println("   Testing registers can be set...");
         myState.setRegister(0, new uint_4(12));
         assert(myState.getRegister(0).toInteger() == 12);
         assert(myState.getRegister(1).toInteger() == 0);
         myState.setRegister(1, new uint_4(11));
         assert(myState.getRegister(0).toInteger() == 12);
         assert(myState.getRegister(1).toInteger() ==11);
         
         System.out.println("   Testing memory can be set...");
         myState.setMemory(0, new uint_4(12));
         assert(myState.getMemory(0).toInteger() == 12);
         assert(myState.getMemory(1).toInteger() == 2);
         assert(myState.getMemory(2).toInteger() == 3);
         assert(myState.getMemory(3).toInteger() == 4);
         assert(myState.getMemory(4).toInteger() == 5);
         assert(myState.getMemory(5).toInteger() == 6);
         assert(myState.getMemory(6).toInteger() == 7);
         assert(myState.getMemory(7).toInteger() == 8);
         assert(myState.getMemory(8).toInteger() == 9);
         assert(myState.getMemory(9).toInteger() == 10);
         assert(myState.getMemory(0xA).toInteger() == 11);
         assert(myState.getMemory(0xB).toInteger() == 12);
         assert(myState.getMemory(0xC).toInteger() == 0);
         assert(myState.getMemory(0xD).toInteger() == 14);
         assert(myState.getMemory(0xE).toInteger() == 15);
         assert(myState.getMemory(0xF).toInteger() == 13);
         assert(myState.getRegister(0).toInteger() == 0);
         assert(myState.getRegister(1).toInteger() == 0);
         
         myState.setMemory(12, new uint_4(6));
         assert(myState.getMemory(0).toInteger() == 12);
         assert(myState.getMemory(1).toInteger() == 2);
         assert(myState.getMemory(2).toInteger() == 3);
         assert(myState.getMemory(3).toInteger() == 4);
         assert(myState.getMemory(4).toInteger() == 5);
         assert(myState.getMemory(5).toInteger() == 6);
         assert(myState.getMemory(6).toInteger() == 7);
         assert(myState.getMemory(7).toInteger() == 8);
         assert(myState.getMemory(8).toInteger() == 9);
         assert(myState.getMemory(9).toInteger() == 10);
         assert(myState.getMemory(0xA).toInteger() == 11);
         assert(myState.getMemory(0xB).toInteger() == 12);
         assert(myState.getMemory(0xC).toInteger() == 6);
         assert(myState.getMemory(0xD).toInteger() == 14);
         assert(myState.getMemory(0xE).toInteger() == 15);
         assert(myState.getMemory(0xF).toInteger() == 13);
         assert(myState.getRegister(0).toInteger() == 0);
   
      }
   }
}
