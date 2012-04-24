package design05;
public class LCDScreen implements Peripheral {

   String myState;
   public LCDScreen() {
      myState = "";
   }
   
   public void doAction(String input) {
      if (input != null) {
         //System.out.print(input);
         if (myState == null) {
            myState = input;
         } else {
            myState += input;
         }
      }
   }

   public String getNameOfPeripheral() {
      return "LCD Screen";
   }
   
   public String getState() {
      return myState;
   }


}
