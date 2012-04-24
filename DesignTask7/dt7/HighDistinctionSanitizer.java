//To Do: Censor images and videos

package dt7;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HighDistinctionSanitizer implements HTTPSanitizer {

   boolean isIllegalPage = false;
   
   String[] illegalURLs = {"stephenconroy.com.au", 
                           "wikileaks.org", 
                           "www.beta.csesoc.unsw.edu.au", 
                           "openinternet.com.au", 
                           "www.eff.org",
                           "secure.sunshinepress.org",
                           "www.indymedia.org",
                           "www.getup.org.au", 
                           "libertus.net",
                           "en.wikipedia.org/wiki/Internet_censorship_in_Australia"
                           };

   public void sanitize(String filename, String URL, String IPUsed) throws IOException{

      if (isIllegalURL(URL)) {
         FileWriter fstream = new FileWriter(MinistryOfTruthProxy.logFileName);
         BufferedWriter out = new BufferedWriter(fstream);
         Calendar cal = Calendar.getInstance();
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   	   Date date = new Date();
         out.write(URL + "was attempted to be connected to by " + IPUsed + "at time: " + dateFormat.format(cal.getTime()));
         replacePageWithSanitized(filename);
      }
   }
   
   private boolean isIllegalURL(String URL) {
      boolean isIllegal = false;    
      for (int i = 0; i < illegalURLs.length; i++) {
         if (URL.equals(illegalURLs[i])) {
            isIllegal = true;;
         }
      }
      return isIllegal;
   }



   private void replacePageWithSanitized(String filename) throws IOException {
   
      File f = new File(filename);
      f.delete();
      f.createNewFile();

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter out = new BufferedWriter(fstream);
   
      out.write ("HTTP/1.1 200 OK");
      out.write ("Date: Wed, 14 Apr 2010 06:33:01 GMT");
      out.write ("Server: Apache-Coyote/1.1");
      out.write ("Last-Modified: Wed, 14 Apr 2010 06:33:03 GMT");
      out.write ("Content-Type: text/html;charset=UTF-8");
      out.write ("Content-Length: 29127");
      out.write ("X-Cache: MISS from www.cse.unsw.edu.au");
      out.write ("Connection: close");
      out.write ("");
      out.write ("<html>");
      out.write ("<head>");
      out.write ("</head>");
      out.write ("<body>");
      out.write ("<PRE>");
      out.write ("eh??? Did you say:");
      out.write ("</pre>");
      out.write ("</body>");
      out.write ("</html>");
      out.close();
  }

}
