package Server;

import Utilities.Answers;
import Utilities.AvatarProperties;
import Utilities.Category;
import Utilities.Question;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Server extends Thread {
   Socket serverSocket;
   ArrayList<Category> categories = new ArrayList<>();
   GameCoordinator gameCoordinator;
   private static final String WELCOME = "START_GAME_FROM_CLIENT_XXX";

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

           out.writeObject(WELCOME);
           out.writeObject(p.getProperties().getProperty("numberOfQuestions","2"));
           out.writeObject(p.getProperties().getProperty("numberOfRounds", "3"));
           Player player = new Player(out, (AvatarProperties) in.readObject(), (String) in.readObject());
           gameCoordinator.addPlayer(player);
           gameCoordinator.setTwoPlayers(!gameCoordinator.isTwoPlayers);
           if(!gameCoordinator.isTwoPlayers){
               player.setPicksCurrentCategory(true);
               Collections.shuffle(categories);
               for(int i = 0; i < 3; i++){
                   out.writeObject(categories.get(i));
               }
           }

           while (true){
               p.processUserInput(in.readObject(), out, player, gameCoordinator);
           }

        } catch (IOException | ClassNotFoundException e) {
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
               Question q = new Question(question, new Answers(answerOne),
                       new Answers(answerTwo), new Answers(answerThree),
                       new Answers(answerFour));

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