//to do: get website url and ip out of request

package dt7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


public class MinistryOfTruthProxy {

   public static final int port = 1105;

   public static final String tempFileName = "temp";
   public static final String logFileName = "log";
   public String secondaryProxy = "localhost";
   public int secondaryProxyPort = 8080;

   public static void main (String[] args) {
      try {
         MinistryOfTruthProxy me = new MinistryOfTruthProxy();
         if (args.length >= 2) {
            me.secondaryProxy = args[1];
         }
         if (args.length >= 3) {
            me.secondaryProxyPort = Integer.parseInt(args[2]);
         }


         me.run();
      } catch (IOException e) {
         System.out.println(e);
      }
   }
   
   
   public void run() throws IOException {
      //create server stuff and wait for a user to send data to us. 
      System.out.println ("proxy starting...");
      System.out.println ("listening on "+port);

      ServerConnection connection;
      int i = 0;
      while (true) {         
         connection= new ServerConnection (port);
         List<String> requests = new ArrayList<String>();
         String requestLine = "";

         int requestCount = 0;
         while (!connection.finishedRecieving()) {
            if (requestLine == null) {
               break;
            }
            requestLine = connection.receive();
            requests.add (requestLine);
            System.out.println ("   request line "+(requestCount++)+": "+requestLine);
         } 
         if (requests.size() <= 1) {
            continue; 
         }
         System.out.println("Recieved Request!");
         String IPUsed = findIP(requests);
         String websiteURL = findURL(requests);

         getWebPage(requests);
         sanitizeWebPage(websiteURL, IPUsed);
         sendPage(connection);
         connection.close();
      }
   } 
   
   private String findURL(List<String> requests) {
      return "";
   }
   
   private String findIP(List<String> requests) {
      return "";
   }
      
   private void getWebPage(List<String> request) throws IOException {
      File f = new File(tempFileName);
      f.delete();
      f.createNewFile();

      FileWriter fstream = new FileWriter(tempFileName);
      BufferedWriter out = new BufferedWriter(fstream);

      ClientConnection clientConnection = new ClientConnection (secondaryProxy, secondaryProxyPort);
      
      System.out.println("Sending request on... ");
      String requestLine;
      if (request.size() > 0) {
         requestLine = request.get(0);
      } else {
         return;
      }

      System.out.println("Getting web page requested..");
      
      int i = 1;
      while (requestLine != null && i < request.size()) {
         if ((!requestLine.startsWith("Proxy-Connection:")) && (!requestLine.startsWith("Accept-Encoding:"))) {
            System.out.print("   <<" + requestLine + ">>\r\n");
            clientConnection.send (requestLine + "\r");
         }
         requestLine = request.get(i);
         i++;
      }
      clientConnection.send ("" + "\r\n");

      System.out.println("Saving webPage to file... \n\n");
      String responseLine = clientConnection.receive ();
      i = 1;
      while (responseLine != null) {
         out.write(responseLine + "\r\n");
         System.out.println(" " + responseLine);
         responseLine = clientConnection.receive();
         i++;
      }
      clientConnection.close();
      out.close();
   }
   
   private void sanitizeWebPage(String URL, String IPUsed) throws IOException {
      System.out.println("Sanitizing...");
      HTTPSanitizer mySanitizer = new CreditSanitizer();
      mySanitizer.sanitize(tempFileName, URL, IPUsed);
      mySanitizer = new DistinctionSanitizer();
      //mySanitizer.sanitize(tempFileName, URL, IPUsed);
      mySanitizer = new HighDistinctionSanitizer();
      //mySanitizer.sanitize(tempFileName, URL, IPUsed);
   }
   
   
   private void sendPage(ServerConnection connection) throws IOException {
      System.out.println("Sending Page On to Browser...");
      FileInputStream fstream = new FileInputStream(tempFileName);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine = ""; 
      while ((strLine = br.readLine()) != null)   {
         System.out.println (strLine);
         connection.send(strLine);
      }
      connection.send("\r\n");
      in.close();
      System.out.println("Completed");
   }
   

}
