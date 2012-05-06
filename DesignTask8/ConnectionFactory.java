package dt8;

public class ConnectionFactory {

   public static BufferedClientConnection newClientConnection(String host, int port) {
      return new BufferedClientConnection(host, port);   
   }
   
   
   public static BufferedServerConnection newServerConnection(int portHostedOn) {
      return new BufferedServerConnection(portHostedOn);      
   }

}
