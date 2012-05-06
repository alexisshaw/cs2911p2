package dt8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class BufferedClientConnection extends BufferedConnection {

   public BufferedClientConnection (String name, int port) {
      try {
         incomingSocket = new Socket (name, port);
         this.out = new PrintWriter (incomingSocket.getOutputStream(), true);
         this.in = new BufferedReader (new InputStreamReader(incomingSocket.getInputStream()));

      } catch (UnknownHostException e) {
         System.err.println ("Don't know about machine: "+name+":"+port);
         System.err.println (e);
         System.exit (ABNORMAL_TERMINATION);

      } catch (IOException e) {
         System.err.println (e);
         System.err.println ("Couldn't connect to: "+name+":"+port);
         System.exit (ABNORMAL_TERMINATION);
      }
   }

}
