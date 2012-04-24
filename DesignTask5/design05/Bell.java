package design05;
public class Bell implements Peripheral {

   private final String bellWavFileName = "bell.wav";
   public Bell() {
   }
   
   public void doAction(String input) {
      if (input != null) {
         AePlayWave player = new AePlayWave(bellWavFileName);     
         player.start();
      }
      
   }

   public String getNameOfPeripheral() {
      return "Bell";
   }
   
   public String getState() {
      return "off";
   }


}
