package Server;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    Socket serverSocket;
    String[] questions = new String[]{"What color is red?"};
    String[] answers = new String[]{"1: red", "2: blue", "3: green", "4: yellow"};
    String correctAnswer = "1";
    int counter = 0;
    Answers a1 = new Answers(true,"red");
    Answers a2 = new Answers(true,"red2");
    Answers a3 = new Answers(true,"red3");
    Answers a4 = new Answers(true,"red4");
    Questions q = new Questions("What is the color red?",a1,a2,a3,a4);


    public Server(Socket s){
        this.serverSocket = s;
    }
   @Override
   public void run(){
       try(ObjectOutputStream out = new ObjectOutputStream(serverSocket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream())){

           while (true){
               out.writeObject(q);
               Object answer = in.readObject();
               if (answer.toString().equalsIgnoreCase("red")){
                   out.writeObject("You win!");
               }
           }

       } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }

}