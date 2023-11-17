
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends Thread {
    Socket serverSocket;
    String[] questions = new String[]{"What color is red?"};
    String[] answers = new String[]{"1: red", "2: blue", "3: green", "4: yellow"};
    String correctAnswer = "1";
    String answer;
    int counter = 0;

    public Server(Socket s){
        this.serverSocket = s;
    }

    @Override
    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true)){

            while (true){
                out.println(questions[counter]);
                for(String s: answers){
                    out.println(s);
                }
                answer = in.readLine();
                if(answer.equalsIgnoreCase(correctAnswer)){
                    out.println("You win!");
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}