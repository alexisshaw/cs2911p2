package dt8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;

public abstract class BufferedConnection implements Connection {

   public final static int ABNORMAL_TERMINATION = 1;

   protected PrintWriter   out;
   protected BufferedReader in;
   protected ServerSocket outGoingSocket;
   protected Socket incomingSocket;

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
         this.incomingSocket.close ();
         this.outGoingSocket.close ();
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
