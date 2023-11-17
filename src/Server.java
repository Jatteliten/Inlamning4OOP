
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends Thread {
    Socket serverSocket;
    String[] questions = new String[]{"1+1=", "1+2=", "1+3=", "1+4=", "1+5="};
    String[] answers = new String[]{"2", "3", "4", "5", "6"};
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
                out.println(questions[counter%5]);
                answer = in.readLine();
                if(answer.equalsIgnoreCase(answers[counter%5])){
                    counter++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}