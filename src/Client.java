import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    PrintWriter pw;

    public Client() {
        try(Socket socketToServer = new Socket(ip, port)) {
            pw = new PrintWriter(socketToServer.getOutputStream(), true);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}