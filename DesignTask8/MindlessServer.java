package dt8;
/**
* A very simple http server, listens for connection,
* saves the request lines (& echoes them to console)
* then returns a dummy web page (which includes the
* request lines - uesful for http debugging).
* Only responds to one request, then halts.
*
* @author richardb April 2010
* simple class to demonstrate networking for comp2911
*
*/

import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class MindlessServer {

   public static final int port = 2911;

   public static void main (String[] args) throws IOException {
      System.out.println ("server starting...");
      System.out.println ("listening on "+port);

      BufferedServerConnection connection = ConnectionFactory.newServerConnection (port);

      List<String> requests = getRequests(connection);
      System.out.println ("Server responding...");
      for (String s : getServerResponse(requests)) {
         connection.send(s);
      }
      System.out.println ("Server done!");
   }

   private static List<String> getRequests(BufferedServerConnection connection) {
      List<String> requests = new ArrayList<String>();
      String requestLine;
      int requestCount = 0;
      while (!connection.finishedRecieving()) {
         requestLine = connection.receive ();
         requests.add (requestLine);
         System.out.println ("request line "+(requestCount++)+": "+requestLine);
      }
      return requests;
   }

   private static List<String> getServerResponse(List<String> requests) {
      List<String> webPage = new LinkedList<String>();
      webPage.addAll(getServerHeader());
      webPage.addAll(getWebSiteBody(requests));
      return webPage;
   }
   
   private static List<String> getServerHeader() {
      List<String> header = new LinkedList<String>();
      header.add ("HTTP/1.1 200 OK");
      header.add ("Date: Wed, 14 Apr 2010 06:33:01 GMT");
      header.add ("Server: Apache-Coyote/1.1");
      header.add ("Last-Modified: Wed, 14 Apr 2010 06:33:03 GMT");
      header.add ("Content-Type: text/html;charset=UTF-8");
      header.add ("Content-Length: 29127");
      header.add ("X-Cache: MISS from www.cse.unsw.edu.au");
      header.add ("Connection: close");
      header.add ("");
      return header;
   }
   
   private static List<String> getWebSiteBody(List<String> requests) {
      List<String> body = new LinkedList<String>();
      body.add ("<html>");
      body.add ("<head>");
      body.add ("</head>");
      body.add ("<body>");
      body.add ("<PRE>");
      body.add ("eh??? Did you say:");
      body.addAll(requests);
      body.add ("</pre>");
      body.add ("</body>");
      body.add ("</html>");
      return body;
   }  
   
}
