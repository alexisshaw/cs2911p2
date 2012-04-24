package dt7;

import java.io.*;
import java.util.LinkedList;


public class CreditSanitizer implements HTTPSanitizer {

   String[] sanitizedWords = {"lie",
                              "censorship",
                              "censor",
                              "dictator",
                              "refugee",
                              "injustice",
                              "conroy",
                              "Intellectual",
                              "gunns", 
                              "Logged"
                             };
                             
   String[] replacedWords = {"truth",
                             "protection",
                             "protect",
                             "big brother",
                             "illegal immigrant",
                             "strict justice",
                             "Dear Leader", 
                             "ivory tower elite",
                             "The Tasmanian Government",
                             "tehehe"
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
            while (strstr(input.toLowerCase(),sanitizedWords[i].toLowerCase()) != -1) {
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
         out.write(input + "\r\n");
      }
      out.close();
      
   }
   
   public int strstr(String haystacka, String needlea){
      String haystack = haystacka.toUpperCase();
      String needle = needlea.toUpperCase();
      for(int i = 0; i < haystack.length(); i++) {
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
}   

