package dt8;
import java.io.*;

public interface Server {

   public void run() throws IOException;   
   
   public Server newServer();

}
