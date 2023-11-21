package Server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Server extends Thread {
   Socket serverSocket;
   ArrayList<Category> categories;
   GameCoordinator gameCoordinator;

   public Server(Socket s, GameCoordinator g){
        this.serverSocket = s;
        this.gameCoordinator = g;
    }
   @Override
   public void run(){
            createQuestionsAndCategoriesFromFile();
            Protocol p = new Protocol(categories);

       try(ObjectOutputStream out = new ObjectOutputStream(serverSocket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream())){

           Player player = new Player(out);
           gameCoordinator.addPlayer(player);
           gameCoordinator.setTwoPlayers(!gameCoordinator.isTwoPlayers);

           while (true){
               p.processUserInput(in.readObject(), out);
           }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }

    /**
     * Läser in frågor och kategorier från Questions and Answers-filen.
     * Skapar kategori om det inte redan finns en kategori vid det namnet och lägger den i en lista.
     * Skapar frågor och lägger det hos kategorin.
     * Skapar svarsalternativ och lägger det hos frågorna.
     */
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
               Question q = new Question(question, new Answers(true, answerOne),
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

}