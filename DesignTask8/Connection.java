package dt8;

public interface Connection {

   public void send (String request);
   public String receive ();
   public void close();
   public boolean finishedRecieving ();

}
