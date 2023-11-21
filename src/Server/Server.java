package Server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Server extends Thread {
    Socket serverSocket;
    String[] questions = new String[]{"What color is red?"};
    String[] answers = new String[]{"1: red", "2: blue", "3: green", "4: yellow"};
    ArrayList<Category> categories;
    String correctAnswer = "1";
    String answer;
    int counter = 0;

    public Server(Socket s){
        this.serverSocket = s;
    }

    @Override
    public void run(){
        createQuestionsAndCategoriesFromFile();

        for(Category c: categories){
            System.out.println(c.getCategoryText());
        }

        try(BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true)){
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createQuestionsAndCategoriesFromFile(){
        Path path = Paths.get("src/Server/Categories and questions");
        String read;
        categories = new ArrayList<>();

        try(BufferedReader bf = Files.newBufferedReader(path)){
            while((read = bf.readLine()) != null){
                boolean categoryExists = false;
                String question = read.substring(0, read.indexOf("("));
                String category = read.substring(read.indexOf("(") + 1, read.indexOf(")"));
                String answerOne = bf.readLine();
                String answerTwo = bf.readLine();
                String answerThree = bf.readLine();
                String answerFour = bf.readLine();

                Category c = new Category(category);
                Questions q = new Questions(question, new Answers(true, answerOne),
                        new Answers(false, answerTwo), new Answers(false, answerThree),
                        new Answers(false, answerFour));

                if(!categories.isEmpty()){
                    for(Category ca: categories){
                        if (ca.getCategoryText().equals(category)){
                            categoryExists = true;
                            ca.addQuestionToList(q);
                            break;
                        }
                    }
                }
                if(!categoryExists){
                    c.addQuestionToList(q);
                    categories.add(c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
       } catch (IOException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }

}