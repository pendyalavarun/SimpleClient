import java.net.*;
import java.io.*;

public class tcpserver {
    public static void main(String arg[]) {
        ServerSocket s = null;
        String line;
        DataInputStream is = null, is1 = null;
        PrintStream os = null;
        Socket c = null;
        try {
            s = new ServerSocket(8888);
            System.out.println("Server listening IN PORT 8888");
        } catch (IOException e) {
            System.out.println("Server Not Started : "+e);
        }
        try {
            c = s.accept();
            System.out.println("Client connected ");
            is = new DataInputStream(c.getInputStream());
            is1 = new DataInputStream(System.in);
            os = new PrintStream(c.getOutputStream());
            // line = is.readLine();
            // System.out.println("Client: " + line);
            // os.println("Hello from server");
            new Thread(new tcpserver().new ClientmsgHandler(is)).start();;
            do {
                System.out.print("Server : ");
                line = is1.readLine();
                os.println(line);
            } while (line.equalsIgnoreCase("quit") == false);
            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private class ClientmsgHandler extends Thread{
        String line;
        DataInputStream is = null;
        ClientmsgHandler(DataInputStream is){
            this.is=is;
        }
        public void run(){
            try{
                while(true){
                    line = is.readLine();
                    System.out.println("Client:  " + line);
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
