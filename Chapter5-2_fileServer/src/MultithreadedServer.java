import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.sql.SQLOutput;

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

            while(true){
                String filename = "";
                char i = ' ';
                while((int) i != 10){ // '\n' = 10
                    i = inputFromClient.readChar();
                    if((int) i != 10){ // 不要把'\n'讀進來
                        filename += i;
                    }
                }

                try{
                    File f = new File(filename);
                    byte[] fileContent = Files.readAllBytes(f.toPath());
                    outputToClient.writeInt(fileContent.length);
                    for(int j=0; j<fileContent.length; j++){
                        outputToClient.writeByte(fileContent[j]);
                    }
                }catch (NoSuchFileException e){
                    String message = "File not Found. Please try again. \n";
                    outputToClient.writeInt(message.length());
                    outputToClient.writeBytes(message);
                    e.printStackTrace();
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

public class MultithreadedServer {
    private int client_number = 0;

    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(8000);
            while(true){
                Socket socket = serverSocket.accept();
                client_number++;
                System.out.println("Client number #"+client_number+" connecting ...");
                new Thread(new HandleClient(socket)).start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MultithreadedServer().start();
    }
}
