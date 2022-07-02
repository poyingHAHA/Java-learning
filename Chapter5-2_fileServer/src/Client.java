import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void startClient(){
        Socket socket = null;

        try{
            socket = new Socket("localhost", 8000);
            DataInputStream fromServer = new DataInputStream(socket.getInputStream());
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

            while(true){
                System.out.print("Enter a filename: ");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.next();

                toServer.writeChars(s+'\n');
                toServer.flush();

                int i = 0;
                int length =fromServer.readInt();

                while(i < length){
                    System.out.print((char) fromServer.readByte());
                    i++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startClient();
    }
}
