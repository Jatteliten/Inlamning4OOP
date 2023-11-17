import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    PrintWriter out;
    BufferedReader in;
    String question;

    public Client() {
        try(Socket socketToServer = new Socket(ip, port)) {
            out = new PrintWriter(socketToServer.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while((question = in.readLine()) != null){
                System.out.println(question);
                out.println(userInput.readLine());
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}