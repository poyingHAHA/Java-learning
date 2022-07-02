import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class HandleClient implements Runnable{
    private Socket socket;

    public HandleClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputFromClient = new DataInputStream(this.socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(this.socket.getOutputStream());

            while(true){ // 如果有新的inputFromClient進來要一直readDouble
                double radius = inputFromClient.readDouble();
                double perimeter = 2 * Math.PI * radius;

                outputToClient.writeDouble(perimeter);
                outputToClient.flush();
                System.out.println("Perimeter is "+perimeter+", and has already sent to the client.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadServer {
    private int client_number = 0; // 辨認client用

    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(8000);

            while(true){ // server要不斷地跑
                // listen for a new connection
                Socket socket = serverSocket.accept(); // 每次有新的client就創建一個新的socket

                client_number++;
                System.out.println("Client number #"+client_number+" connecting...");
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println(inetAddress.getHostAddress());
                System.out.println(inetAddress.getHostName());

                // create and start a new thread
                new Thread(new HandleClient(socket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MultiThreadServer().start();
    }
}
