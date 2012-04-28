package dt8;
/**
* A very simple tcp client, opens socket to www.cse.unsw.edu.au
* port 80 and then reads user input from command line till eof
* echoing this to the console and also sending it thru the socket.
* reads server response from socket and echos that to the console
* too.  then halts.  ie Only issues one request, then halts.
*
* @author richardb April 2010
* simple class to demonstrate networking for comp2911
*
*/
   import java.io.*;

public class MindlessClient {

   public static void main(String[] args) throws IOException {
      final String targetName = "localhost";
      final int    targetPort = 111;

      BufferedClientConnection clientConnection = ConnectionFactory.newClientConnection (targetName, targetPort);

      BufferedReader stdIn = new BufferedReader (
            new InputStreamReader (System.in));

      // enter HTTP request lines into the console
      // type <EOF> to terminate your request
      String requestLine = stdIn.readLine ();
      while (requestLine != null) {
         System.out.println("sending ->> " + requestLine);
         clientConnection.send (requestLine);
         requestLine = stdIn.readLine ();
      }

      // read the response and echo it to the console
      String responseLine = clientConnection.receive ();
      while (responseLine != null) {
         System.out.println ("response <<- "+responseLine);
         responseLine = clientConnection.receive ();
      }

      clientConnection.close();
      stdIn.close();

      System.out.println ("Done!");
   }
}
