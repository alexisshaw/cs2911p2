package dt7;

import java.io.*;
import java.util.LinkedList;


public class DistinctionSanitizer implements HTTPSanitizer {

   String[] sanitizedWords = {"disney",
                              "pepsi",
                              "war",
                             };
                             
   String[] replacedWords = {"The Wonderful Disney(tm)",
                             "Coca Cola",
                             "humanitarian intervention",
                            };                     


                           
   String[] refusedClassificationWords = 
                            {/*"control order",
                             "tiananmen square",
                             "tibet",
                             "goto",
                             "eureka stockade",
                             "george williams",
                             "dictation test",
                             "isp filtering",
                             "separation of powers",
                             "NSA",
                             "bill of rights",
                             "prometheus",*/
                             "area 51",
                             "openoffice"
                            };

   public void sanitize(String filename, String URL, String IPUsed) throws IOException {

      LinkedList<String> oldData = new LinkedList<String>();
      LinkedList<String> data = new LinkedList<String>();
   
      FileInputStream fstream = new FileInputStream(filename);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine = ""; 
      while ((strLine = br.readLine()) != null)   {
         oldData.add(strLine);
      }

      in.close();

      
      String[] temp;
      int whereIsLittleString = -1;
     // while (input != null) {
      for (String input : oldData) {  
         //change input appropriately
         for (int i = 0; i < sanitizedWords.length; i++) {
            while (strstr(input,sanitizedWords[i]) != -1) {
               whereIsLittleString = strstr(input,sanitizedWords[i]);
               if (whereIsLittleString != -1) {
                  temp = new String[3];
                  temp[0] = input.substring(0, whereIsLittleString);
                  temp[1] = replacedWords[i];
                  temp[2] = input.substring(whereIsLittleString + sanitizedWords[i].length());
                  input = temp[0] + temp[1] + temp[2];
               }
            } 
         }
         data.add(input);
      }
      
      File f;
      f=new File(filename);
      f.delete();
      f.createNewFile();
      
      FileWriter fwriter = new FileWriter(filename);
      BufferedWriter out = new BufferedWriter(fwriter);

      for (String input : data) {  
         System.out.println(" " +input);
         out.write(input + "\n");
      }
      out.close();
      
      if (shouldRefuseClassification(oldData)) {
         replacePageWithSanitized(filename);
      }

   }
   
   public int strstr(String haystack, String needle){
      for(int i = 0; i < haystack.length(); i++ ) {
         for(int j = 0; j < needle.length() && i+j < haystack.length(); j++ ) {
            if(needle.charAt(j) != haystack.charAt(i+j)) {
               break;
            } else if (j == needle.length()-1) {
               return i;
            }
         }
      }
      return -1;
   }
   
   private boolean shouldRefuseClassification(LinkedList<String> page) {
        
      boolean shouldI = false;
      for (String input : page) {
         for (int j = 0; j < refusedClassificationWords.length; j++) {
            if (strstr(input,refusedClassificationWords[j]) != -1) {
               shouldI = true;
            } 
         }
      }
      return shouldI;
   }

   private void replacePageWithSanitized(String filename) throws IOException {

      File f = new File(filename);
      f.delete();
      f.createNewFile();

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter out = new BufferedWriter(fstream);
   
      out.write ("HTTP/1.1 200 OK\r\n");
      out.write ("Date: Wed, 14 Apr 2010 06:33:01 GMT\r\n");
      out.write ("Server: Apache-Coyote/1.1\r\n");
      out.write ("Last-Modified: Wed, 14 Apr 2010 06:33:03 GMT\r\n");
      out.write ("Content-Type: text/html;charset=UTF-8\r\n");
      out.write ("X-Cache: MISS from www.cse.unsw.edu.au\r\n");
      out.write ("Connection: close\r\n");
      out.write ("\r\n");
      out.write ("<html>\r\n");
      out.write ("<head>\r\n");
      out.write ("</head>\r\n");
      out.write ("<body>\r\n");
      out.write ("<PRE>\r\n");
      out.write ("I Am sanitizing you! ><\r\n");
      out.write ("</pre>\r\n");
      out.write ("</body>\r\n");
      out.write ("</html>\r\n");
      out.close();
      
  }

}   

