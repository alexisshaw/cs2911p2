package dt8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BufferedServerConnection extends BufferedConnection {

   public BufferedServerConnection (int port) {
      try {
         this.outGoingSocket = new ServerSocket (port);
      } catch (IOException e) {
         System.err.println ("Could not listen on port: "+port);
         System.exit (ABNORMAL_TERMINATION);
      }
      // when someone tries to connect to our port returns a Socket
      // for communicating with them (function waits until someone
      // tries to connect)
      try {
         this.incomingSocket = outGoingSocket.accept ();

         this.out = new PrintWriter (incomingSocket.getOutputStream (), true);
         this.in = new BufferedReader (
                      new InputStreamReader (
                         incomingSocket.getInputStream () ) );
      } catch (IOException e) {
         e.printStackTrace ();
         System.exit (ABNORMAL_TERMINATION);
      }

   }


}
