package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    public static void main(String[] args) throws IOException {
        int port = 12344;

        GameCoordinator g = new GameCoordinator();

        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(true){
                Socket sock = serverSocket.accept();
                Server st = new Server(sock, g);
                st.start();
            }
        }
    }
}