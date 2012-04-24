
package UnitTests;
import design05.*;

public class Testuint_4 implements Test {

   public String toString () {
      return "testing uint_4 operates as expected....";
   }

   public void run () {
      {

         System.out.println("   Testing creation works accurately..");
         uint_4 myTester = new uint_4(14);
         assert( myTester.toInteger() == 14);
         myTester = new uint_4(1);
         assert( myTester.toInteger() == 1);
         myTester = new uint_4(0);
         assert( myTester.toInteger() == 0);
         myTester = new uint_4(16);
         assert( myTester.toInteger() == 0);
         myTester = new uint_4(17);
         assert( myTester.toInteger() == 1);
         myTester = new uint_4(32);
         assert( myTester.toInteger() == 0);

         System.out.println("   Testing addition is correct...");         
         myTester.add(12); 
         assert(myTester.toInteger() == 12);
         myTester.add(3); 
         assert(myTester.toInteger() == 15);
         myTester.add(1); 
         assert(myTester.toInteger() == 0);
         myTester.add(0); 
         assert(myTester.toInteger() == 0);
         uint_4 mySecondTest = new uint_4(1);
         myTester.add(mySecondTest); 
         assert(myTester.toInteger() == 1);
         mySecondTest = new uint_4(26);
         myTester.add(mySecondTest); 
         assert(myTester.toInteger() == 11);
                  
         System.out.println("   Testing subtraction is correct...");         
         myTester.sub(12); 
         assert(myTester.toInteger() == 15);
         myTester.sub(3); 
         assert(myTester.toInteger() == 12);
         myTester.sub(1); 
         assert(myTester.toInteger() == 11);
         myTester.sub(0); 
         assert(myTester.toInteger() == 11);
         mySecondTest = new uint_4(1);
         myTester.sub(mySecondTest); 
         assert(myTester.toInteger() == 10);
         mySecondTest = new uint_4(26);
         myTester.sub(mySecondTest); 
         assert(myTester.toInteger() == 0);

         System.out.println("   Testing exclusive or (XOR) is correct...");         
         myTester.XOR(0xB); 
         assert(myTester.toInteger() == 0xB);
         myTester.XOR(3); 
         assert(myTester.toInteger() == 8);
         myTester.XOR(1); 
         assert(myTester.toInteger() == 9);
         myTester.XOR(0); 
         assert(myTester.toInteger() == 9);
         
      }
   }
   
}

/* For reference
0000  0
0001  1
0010  2
0011  3
0100  4
0101  5
0110  6
0111  7
1000  8
1001  9
1010  A
1011  B
1100  C
1101  D
1110  E
1111  F
*/
