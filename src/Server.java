
import java.net.Socket;

public class Server extends Thread {
    Socket serverSocket;

    public Server(Socket s){
        this.serverSocket = s;
    }

    @Override
    public void run(){

    }

}