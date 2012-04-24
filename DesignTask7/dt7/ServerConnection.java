package dt7;
/**
* Simple class to look after the details of Socket connections for
* a server.  It only generates one   Socket then halts when that
* Socket is closed.  A real server would keep generating new sockets
* / new connections of course!
* i wrote it to pull socket housekeeping code out of the
* MindlessServer class - it's not a beautiful class AT ALL feel free
* to refactor or redesign or ignore for design task 07
*
* @author richardb
* april 2010 comp2911 unsw
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

   public final static int ABNORMAL_TERMINATION = 1;

   private PrintWriter   out;
   private BufferedReader in;
   private ServerSocket serverSocket;
   private Socket socket;

   public ServerConnection (int port) {

      try {
         this.serverSocket = new ServerSocket (port);
      } catch (IOException e) {
         System.err.println ("Could not listen on port: "+port);
         System.exit (ABNORMAL_TERMINATION);
      }

      // when someone tries to connect to our port returns a Socket
      // for communicating with them (function waits until someone
      // tries to connect)
      try {
         this.socket = serverSocket.accept ();

         this.out = new PrintWriter (socket.getOutputStream (), true);
         this.in = new BufferedReader (
                      new InputStreamReader (
                         socket.getInputStream () ) );
      } catch (IOException e) {
         e.printStackTrace ();
         System.exit (ABNORMAL_TERMINATION);
      }

   }

   public void send (String request) {
      this.out.println (request);
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

   public void close() {
      try {
         this.in.close ();
         this.out.close ();
         this.socket.close ();
         this.serverSocket.close ();
      } catch (IOException e) {
         System.out.println ("problems closing connection");
         e.printStackTrace();
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
