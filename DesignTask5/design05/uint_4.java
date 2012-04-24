package design05;

public class uint_4 {

   private final int maxSize = 16;
   private int number;
   public uint_4 (int initialVal) {
      number = initialVal % 16;
   } 

   public uint_4 add (int value) {
      while(value < 0) {
         value += maxSize;
      }
      number += value;
      number %= maxSize;
      return this;
   }

   public uint_4 sub (int value) {
      number -= value;
      while(number < 0) {
         number += maxSize;
      }
      number %= maxSize;
      return this;
   }

   public uint_4 XOR (int value) {
      number ^= value;
      return this;
   }

   public uint_4 add (uint_4 value) {
      return add(value.toInteger());
   }

   public uint_4 sub (uint_4 value) {
      return sub(value.toInteger());
   }
   
   public uint_4 XOR (uint_4 value) {
      return XOR(value.toInteger());
   }
   
   @Override
   public boolean equals(Object aThat){
      if (aThat == null) return false;
      if ( this == aThat ) return true;
      if ( !(aThat instanceof uint_4) ) return false;
      if (((uint_4) aThat).toInteger() != number) return false;
      return true;
   }   

   @Override
   public String toString() {
      return ("" + number + "");
   }
   

   
   public int toInteger() {
      return number;
   }

   public uint_4 fromInteger(int num) {
      number = num % 16;
      return this;
   }


}
