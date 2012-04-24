package dt7;
import java.io.*;

public interface HTTPSanitizer {

   public void sanitize(String filename, String URL, String IPUsed) throws IOException;

}
