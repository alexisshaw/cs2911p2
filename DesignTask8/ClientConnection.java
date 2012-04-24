package dt7;

/**
* Simple class to look after the details of a Socket connection
* i wrote it to pull socket housekeeping code out of the
* MindlessClient class - it's not a beautiful class feel free to
* refactor or redesign or ignore for design task 07
*
* @author richardb
* april 2010 comp2911 unsw
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
   private Socket echoSocket;

   public ClientConnection (String name, int port) {
      try {
         echoSocket = new Socket (name, port);
         this.out = new PrintWriter (echoSocket.getOutputStream(), true);
         this.in = new BufferedReader (
                      new InputStreamReader (
                         echoSocket.getInputStream () ) );

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