package dt8;
/**
* A very simple tcp client, opens socket to www.cse.unsw.edu.au
* port 80 and then reads user input from command line till eof
* echoing this to the console and also sending it thru the socket.
* reads server response from socket and echos that to the console
* too.  then halts.  ie Only issues one request, then halts.
*
* @author richardb April 2010
* @author damonS April 2012 (refactored)
* @author alexisS April 2012 (refactored)
* simple class to demonstrate networking for comp2911
*
*/

import java.io.*;
import java.util.LinkedList;


public class MindlessClient implements Client {

   private BufferedClientConnection clientConnection;
   
   private MindlessClient() {
   
   }

   public Client newClient() {
      return new MindlessClient();
   }

   public void run () throws IOException {
      final String targetName = "localhost";
      final int    targetPort = 111;
      clientConnection = ConnectionFactory.newClientConnection (targetName, targetPort);
      sendRequests(getRequests());
      readResponse();
      clientConnection.close();
      System.out.println ("Done!");
   }
   
   private String[] getRequests() throws IOException{
      BufferedReader stdIn = new BufferedReader (new InputStreamReader (System.in));
      LinkedList<String> responses = new LinkedList<String>();  
      String requestLine = stdIn.readLine ();
      responses.add(requestLine);
      while (requestLine != null) {
         requestLine = stdIn.readLine ();
         responses.add(requestLine);
      }
      stdIn.close();
      return convertListToArray(responses);
   }
   
   private String[] convertListToArray(LinkedList<String> responses) {
      String[] output = new String[responses.size()]; 
      int i= 0;
      for (String s : responses) {
         output[i] = s;
         i++;
      } 
      return output;  
   } 
   
   private void sendRequests(String[] requests) {
      // type <EOF> to terminate your request
      String requestLine;
      int i = 0;
      while (i < requests.length) {
         requestLine = requests[i];
         System.out.println("sending ->> " + requestLine);
         clientConnection.send (requestLine);
         i++;
      }
   } 
   
   private void readResponse() {
      //reads the response and echos it to the command line
      String responseLine = clientConnection.receive ();
      while (responseLine != null) {
         System.out.println ("response <<- "+responseLine);
         responseLine = clientConnection.receive ();
      }
   }
}
