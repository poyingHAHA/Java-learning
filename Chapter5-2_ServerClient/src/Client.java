import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void startClient(){
        Socket socket = null;

        try{
            socket = new Socket("127.0.0.1", 8000);

            DataInputStream fromServer = new DataInputStream(socket.getInputStream());
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

            while(true){
                Scanner scanner = new Scanner(System.in);
                double radius = scanner.nextDouble();

                toServer.writeDouble(radius);
                toServer.flush();

                double perimeter = fromServer.readDouble();
                System.out.println("Perimeter is "+perimeter);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        startClient();
    }
}
