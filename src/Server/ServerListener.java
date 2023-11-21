package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    public static void main(String[] args) throws IOException {
        int port = 12344;

        /*Protokoll newProtokoll = new Protokoll();
        String txt = newProtokoll.getOutPut("");
        System.out.println(txt);*/

        try(ServerSocket serverSocket = new ServerSocket(port)){

            while(true){
                Socket sock = serverSocket.accept();
                Server st = new Server(sock);
                st.start();
            }
        }
    }
}