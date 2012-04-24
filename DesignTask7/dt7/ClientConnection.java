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
   public final static int ABNORMAL_TERMINATION = 1;

   private PrintWriter   out;
   private BufferedReader in;
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

   public void send (String request) {
      this.out.println(request);
   }

   public String receive () {
      String response = null;
      try {
         response = this.in.readLine ();
      } catch (IOException e) {
         e.printStackTrace ();
         System.exit (ABNORMAL_TERMINATION);
      }
      return response;
   }

   public void close () {
      try {
         this.in.close ();
         this.out.close ();
         this.echoSocket.close ();
      } catch (IOException e) {
         System.out.println ("problems closing connection");
         e.printStackTrace ();
      }
   }

   public boolean finishedRecieving () {
      boolean finished;
      try {
         finished = !this.in.ready();
      } catch (Exception e) {
         // if any problems reading lets just say we are done
         finished = true;
      }
      return finished;
   }

}
